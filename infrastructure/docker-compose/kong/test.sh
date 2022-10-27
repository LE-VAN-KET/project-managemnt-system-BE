#!/usr/bin/env bash

KEYCLOAK_HOST_PORT=${1:-"localhost:8180"}
PROJECT_SERVICE_URI="http://localhost:8187"
echo
echo "Getting admin access token"
echo "--------------------------"

PROJECT_REALM_CLIENT_SECRET="g1MBTQO7DPVJrHnKFag2SORN8CAalGIq"

echo "Getting user access token"
echo "-------------------------"

curl -s -X POST "http://$KEYCLOAK_HOST_PORT/auth/realms/project-management-system/protocol/openid-connect/token" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "username=levanket" \
  -d "password=1512001" \
  -d "grant_type=password" \
  -d "client_secret=$PROJECT_REALM_CLIENT_SECRET" \
  -d "client_id=project-service" | jq -r .access_token
echo