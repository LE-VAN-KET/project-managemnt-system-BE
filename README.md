# Microservice Project Management System
### Prerequisites:
- [x] [Java 8+](https://www.oracle.com/java/technologies/downloads/#java8)
- [x] [Docker](https://www.docker.com/)
- [x] [Docker-compose](https://docs.docker.com/compose/install/)
### Start Environment step by steps:
  * Open a terminal and inside **project-management-be** folder run:
    * <code>mvn clean install</code>
    * <code>cd /infrastructure/docker-compose</code>
    * <code>docker compose -f common.yml -f api-gateway.yml -f elk.yml -f service.yml up --build -d</code>
    * <code>bash start_kong.sh</code>
    * <code>bash init_kong.sh</code>
  * Create account konga and connection kong to konga:
    - Access konga admin ui: http://localhost:1337
    - Kong Admin URL: http://kong:8001
  * Swagger UI:
    - User-service: http://localhost:8182/swagger-ui/index.html
  * Test use postman:
    - api signup: http://localhost:8000/api/auth/signup
    - api signin: http://localhost:8000/api/auth/signin
    - api validation token: http://localhost:8000/api/auth/validate/token
    - api refresh token when token expired: http://localhost:8000/api/auth/refresh-token
    
