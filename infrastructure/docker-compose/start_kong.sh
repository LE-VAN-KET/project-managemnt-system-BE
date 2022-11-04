#!/usr/bin/env bash

echo "Starting kong"
docker compose -f common.yml -f api-gateway.yml build kong
docker compose -f common.yml -f api-gateway.yml up -d kong_database

echo "Kong database migration"
docker compose -f common.yml -f api-gateway.yml run --rm kong kong migrations bootstrap
docker compose -f common.yml -f api-gateway.yml run --rm kong kong migrations up
docker compose -f common.yml -f api-gateway.yml up -d kong

docker compose -f common.yml -f api-gateway.yml up -d kong
docker compose -f common.yml -f api-gateway.yml ps

docker compose -f common.yml -f api-gateway.yml up -d db_konga konga
