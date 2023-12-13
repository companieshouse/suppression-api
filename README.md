# Suppression API
API for the 'Suppress My Details' service

## Technologies
- [OpenJDK 21](https://jdk.java.net/archive/)
- [Maven](https://maven.apache.org/download.cgi)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Swagger OpenAPI](https://swagger.io/docs/specification/about/)

## Running locally

1. Clone [Docker CHS Development](https://github.com/companieshouse/docker-chs-development) and follow the steps in the README.

2. Enable the `suppression` module

3. Navigate to `http://api.chs.local:9000`

### To access swagger documentation

Swagger documentation is available for this service in the the docker CHS development

1. Navigate to `http://api.chs.local/api-docs/suppression-api/swagger-ui.html`

### To make local changes

Development mode is available for this service in [Docker CHS Development](https://github.com/companieshouse/docker-chs-development).

    ./bin/chs-dev development enable suppression-api

### To build the Docker container

    mvn compile jib:dockerBuild -Dimage=169942020521.dkr.ecr.eu-west-1.amazonaws.com/local/suppression-api


## Useful Endpoints

### Health
`/healthcheck`

