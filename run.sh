
docker-compose -f common.yml -f micro-service-dev-v1.0.0.yml down
docker rmi vanket/issues-service:v2.0.0 vanket/member-service:v2.0.0 \
  vanket/user-service:v2.0.0  vanket/organization-service:v2.0.0 -f
docker-compose -f common.yml -f micro-service-dev-v1.0.0.yml up -d
docker restart kong