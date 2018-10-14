# WePlan Design

## Model

![Model](https://raw.githubusercontent.com/ISTIC-M2-ILa-GM/WePlan/dev/spec/model.png)

## Architecture

### Front end

### Back end

The Back end application used the hexagonal architecture (also known as *Ports and Adapters*). The domain layer isn't dependents of others layers. It provides some interfaces for the infrastructure layer known as Service Provider Interfaces (SPI), we can found them at *domain/src/main/java/fr/istic/gm/weplan/domain/adapter*. It also provides others interfaces for the server layer known as Application Provider Interfaces (API), wan found them at *domain/src/main/java/fr/istic/gm/weplan/domain/service*. 

The development is in TDD (Test Driven Development).

## Technology

* Languages: JavaScript/Java
* Front end: Angular - Singlepage Application
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