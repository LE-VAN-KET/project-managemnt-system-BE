#!/usr/bin/env bash

KEYCLOAK_HOST_PORT=${1:-"localhost:8180"}
PROJECT_SERVICE_URI="http://localhost:8187"
echo
echo "Getting admin access token"
echo "--------------------------"

ADMIN_TOKEN=$(curl -s -X POST "http://$KEYCLOAK_HOST_PORT/auth/realms/master/protocol/openid-connect/token" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "username=admin" \
  -d 'password=admin' \
  -d 'grant_type=password' \
  -d 'client_id=admin-cli' | jq -r '.access_token')

echo
echo "Creating realm"
echo "--------------"

curl -i -X POST "http://$KEYCLOAK_HOST_PORT/auth/admin/realms" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"realm": "project-management-system", "enabled": true}'

echo
echo "Creating client"
echo "---------------"

CLIENT_ID=$(curl -si -X POST "http://$KEYCLOAK_HOST_PORT/auth/admin/realms/project-management-system/clients" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"clientId": "project-management-system", "directAccessGrantsEnabled": true, "redirectUris": ["$PROJECT_SERVICE_URI/*"]}' \
  | grep -oE '[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}')

echo "Getting client secret"
echo "---------------------"

PROJECT_REALM_CLIENT_SECRET=$(curl -s -X POST "http://$KEYCLOAK_HOST_PORT/auth/admin/realms/project-management-system/clients/$CLIENT_ID/client-secret" \
  -H "Authorization: Bearer $ADMIN_TOKEN" | jq -r '.value')

echo "Creating user"
echo "-------------"

USER_ID=$(curl -si -X POST "http://$KEYCLOAK_HOST_PORT/auth/admin/realms/project-management-system/users" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"username": "levanket", "enabled": true, "credentials": [{"type": "password", "value": "1512001", "temporary": false}]}' \
  | grep -oE '[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}')

echo "Getting user access token"
echo "-------------------------"

curl -s -X POST "http://$KEYCLOAK_HOST_PORT/auth/realms/project-management-system/protocol/openid-connect/token" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "username=levanket" \
  -d "password=1512001" \
  -d "grant_type=password" \
  -d "client_secret=$PROJECT_REALM_CLIENT_SECRET" \
  -d "client_id=project-management-system" | jq -r .access_token
echo