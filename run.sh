cd ./docker-compose
docker-compose -f common.yml -f micro-service-dev-v1.0.0.yml down
docker rmi vanket/issues-service:v1.0.0 vanket/member-service:v1.0.0 \
  vanket/user-service:v1.0.0 vanket/organization-service:v1.0.0 -f
docker-compose -f common.yml -f micro-service-dev-v1.0.0.yml up -d