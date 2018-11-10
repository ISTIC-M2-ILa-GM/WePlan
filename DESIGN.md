# WePlan Design

## Model

![Model](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/ISTIC-M2-ILa-GM/WePlan/dev/spec/model.puml)

## Architecture

### Front end

The front end application runs with the Angular 6+ framework.

The web-app sends and receives data asynchronously to the back-end server using services stored in `src/app/services`.

All pages (templates) are written in HTML and stored in `src/app/components`. The app is designed using [Materialize CSS](https://materializecss.com/) wrapped in the [ngx-materialize](https://github.com/sherweb/ngx-materialize) Angular module.

### Back end

The Back end application used the hexagonal architecture (also known as *Ports and Adapters*). The domain layer isn't depending on any other layer. It provides some interfaces for the infrastructure layer known as Service Provider Interfaces (SPI), we can found them at *domain/src/main/java/fr/istic/gm/weplan/domain/adapter*. It also provides others interfaces for the server layer known as Application Provider Interfaces (API), wan found them at *domain/src/main/java/fr/istic/gm/weplan/domain/service*. 

The development is in TDD (Test Driven Development).

## Technology

* Languages: TypeScript/Java
* Front end: Angular - Single page application (SPA)
* Back end: Spring Boot - Restful webserver
* Build: maven + npm

* Package: fr.istic.gm.weplan
* Artifact: we-plan
* Server artifact: we-plan-server

## Modules

    we-plan: Parent module.
    we-plan-front: It contains the presentation layer.
    we-plan-domain: It contains the business logic and defines how the layer outside of it can interact with it.
    we-plan-infra: It contains all the infrastructure logic and communication likes external API or databases communications.
    we-plan-server: It contains all the server logic and the API exposure.
    
## Restful API

See more information at [spec/endpoint.md](https://github.com/ISTIC-M2-ILa-GM/WePlan/blob/dev/spec/endpoint.md)

## Build

You need to configure the token of the weatherbit API, rename and configure the file:
    
    server/src/main/resources/application-weather.properties.default

Build

    mvn install

Maven will also build the front application. 
    
### Quicker build without starting tests

    mvn install -DskipTests
    
### Build docker image

    mvn package -DskipTests
    
## Start

with Docker compose:

    docker-compose up
    
connect to the docker database:

    sudo mysql -uroot -pweplan -h$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' CONTAINER_ID)
    
    CONTAINER_ID the container_id of the mariadb container.
  

## Available profile

    dev: for the development database h2
    prod: for the production database mariadb
    weather: always required for the weather api    