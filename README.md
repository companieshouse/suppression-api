# Suppression API
API for the 'Suppress My Details' service

## Technologies
- [OpenJDK 11](https://jdk.java.net/archive/)
- [Maven](https://maven.apache.org/download.cgi)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Swagger OpenAPI](https://swagger.io/docs/specification/about/)

## How to run

### Run with Spring boot

1. `mvn clean install spring-boot:run`

2. Navigate to the running app in a browser: 

    `http://localhost:9000`

### Run with Docker

1. `mvn compile jib:dockerBuild -Dimage=suppression-api`

2. `docker run -p 9000:9000 -t suppression-api`

3. Navigate to the running app in a browser: 

    `http://localhost:9000`


## Useful Endpoints

### Health

http://localhost:9000/actuator/health
