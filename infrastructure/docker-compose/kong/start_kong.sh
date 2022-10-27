#!/usr/bin/env bash

echo "Starting kong"
docker compose -f api-gateway.yml build kong
docker compose -f api-gateway.yml up -d kong_database

echo "Kong database migration"
docker compose -f api-gateway.yml run --rm kong kong migrations bootstrap
docker compose -f api-gateway.yml run --rm kong kong migrations up
docker compose -f api-gateway.yml up -d kong
#docker run --rm --name kong-database-migration -e "KONG_DATABASE=postgres" \
#  -e "KONG_PG_HOST=kong_database" -e "KONG_PG_PASSWORD=kong_password_dut_team92" \
#  -e "KONG_PG_USER=kong" --network=kong_network \
#  kong:latest kong migrations bootstrap

#if [[ "$(docker images -q kong:lastest-oidc 2> /dev/null)" == "" ]]; then
#  echo
#  echo "Building kong docker image with kong-oidc plugin"
#  echo "------------------------------------------------"
#  docker build -t kong:lastest-oidc docker-compose/kong
#fi

docker compose -f api-gateway.yml up -d kong
docker compose -f api-gateway.yml ps

docker compose -f api-gateway.yml up -d konga
docker compose -f keycloack.yml up -d

curl -s http://localhost:8001 | jq .plugins.available_on_server.oidc
