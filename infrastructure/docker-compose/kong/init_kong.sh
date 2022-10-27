#!/usr/bin/env bash
PROJECT_SERVICE_CLIENT_SECRET="g1MBTQO7DPVJrHnKFag2SORN8CAalGIq"
KONG_ADMIN_HOST_PORT=${1:-"localhost:8001"}
PROJECT_HOST_PORT=${2:-"project-service:8187"}
KEYCLOAK_HOST_PORT=${3:-"keycloack:8180"}

echo
echo "Add service"
echo "-----------"

PROJECT_SERVICE_SERVICE_ID=$(curl -s -X POST "http://$KONG_ADMIN_HOST_PORT/services/" \
  -d "name=project-service" \
  -d "url=http://$PROJECT_HOST_PORT" | jq -r '.id')

echo
echo "Add Public Route to Service"
echo "---------------------------"
PROJECT_SERVICE_PUBLIC_ROUTE_ID=$(curl -s -X POST "http://$KONG_ADMIN_HOST_PORT/services/project-service/routes/" \
  -d "protocols[]=http" \
  -d "paths[]=/actuator" \
  -d "hosts[]=localhost" \
  -d "strip_path=false" | jq -r '.id')

echo
echo "Add Private Route to Service"
echo "----------------------------"
PROJECT_SERVICE_PRIVATE_ROUTE_ID=$(curl -s -X POST "http://$KONG_ADMIN_HOST_PORT/services/project-service/routes/" \
  -d "protocols[]=http" \
  -d "paths[]=/api" \
  -d "hosts[]=localhost" \
  -d "strip_path=false" | jq -r '.id')

echo
echo "Add kong-oidc Plugin to Private Route"
echo "-------------------------------------"
PROJECT_SERVICE_PRIVATE_ROUTE_KONG_OIDC_PLUGIN_ID=$(curl -s -X POST "http://$KONG_ADMIN_HOST_PORT/routes/$PROJECT_SERVICE_PRIVATE_ROUTE_ID/plugins" \
  -d "name=oidc" \
  -d "config.client_id=project-service" \
  -d "config.bearer_only=yes" \
  -d "config.client_secret=$PROJECT_SERVICE_CLIENT_SECRET" \
  -d "config.realm=project-management-system" \
  -d "config.introspection_endpoint=http://$KEYCLOAK_HOST_PORT/auth/realms/project-management-system/protocol/openid-connect/token/introspect" \
  -d "config.discovery=http://$KEYCLOAK_HOST_PORT/auth/realms/project-management-system/.well-known/openid-configuration" | jq -r '.id')

echo
echo "Add Serverless Function (post-function) to Private Route"
echo "--------------------------------------------------------"

PROJECT_SERVICE_PRIVATE_ROUTE_SERVERLESS_FUNCTION_ID=$(curl -s -X POST "http://$KONG_ADMIN_HOST_PORT/routes/$PROJECT_SERVICE_PRIVATE_ROUTE_ID/plugins" \
  -F "name=post-function" \
  -F "config.access[1]=@kong/serverless/extract-username.lua" | jq -r '.id')

echo "List services: curl http://$KONG_ADMIN_HOST_PORT/services"
echo "List project-service routes: curl http://$KONG_ADMIN_HOST_PORT/services/project-service/routes"