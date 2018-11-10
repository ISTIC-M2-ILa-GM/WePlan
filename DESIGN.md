# WePlan Design

## Model

![Model](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/ISTIC-M2-ILa-GM/WePlan/dev/spec/model.puml)

## Architecture

To develop this project, we use test driven developpment (TDD) and pair programming.

### Front end

The front end application runs with the Angular 6+ framework.

The web-app sends and receives data asynchronously to the back-end server using services stored in `src/app/services`.

All pages (templates) are written in HTML and stored in `src/app/components`. The app is designed using [Materialize CSS](https://materializecss.com/) wrapped in the [ngx-materialize](https://github.com/sherweb/ngx-materialize) Angular module.

### Back end

The Back end application used the hexagonal architecture (also known as *Ports and Adapters*). The domain layer isn't depending on any other layer. It provides some interfaces for the infrastructure layer known as Service Provider Interfaces (SPI), we can found them at *domain/src/main/java/fr/istic/gm/weplan/domain/adapter*. It also provides others interfaces for the server layer known as Application Provider Interfaces (API), wan found them at *domain/src/main/java/fr/istic/gm/weplan/domain/service*. 

## Technology

* Languages: TypeScript/Java
* Front end: Angular - Single page application (SPA)
* Back end: Spring Boot - Restful webserver
* Build: maven + npm
* Deploy: docker


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

You need to create an account on the weatherbit api: https://www.weatherbit.io/api
It's quick, easy and free on a limit usage.

Build

    mvn install

Maven will also build the front application and the docker image. 
    
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
  
### Available profile

    dev: for the development database h2
    prod: for the production database mariadb
    weather: always required for the weather api    

## Project Management

We use a KANBAN board to manage the project.

### Features done

#### Project

- [X] Use continous integration with travis
- [X] Use quality code checking with sonarqube
- [X] Generate an API documentation with swagger
- [X] Make some UML diagram with plantuml
- [X] Use the hexagonal architecture
- [X] Use test driven development
- [X] Use pair programming
- [X] Deploy the application with docker compose

#### Back end

- [X] Develop API to manage User, City, Department, Region, Activity, Event, and Authentication
- [X] Generate event with an algorithm which use a weather api at thursday 6:00pm
- [X] Broadcast event generated with STOMP websocket protocol
- [X] Use aspect to parse all exception of the application with a custom format
- [X] Use aspect to display all logging message
- [X] Validate all API request params to protect the domain layer
- [X] Communication with a weather API and generate client with swagger
- [X] Encrypt user's password in the database
- [X] Protect the API with a security config
- [X] Test the application with a coverage higher than 95%
- [X] Make some automatic and manual (postman) integration testing
- [X] Generate some data in the database at the start of the application

#### Front end

- [X] Develop the API communication
- [X] Implement a proxy in develop mode
- [X] Use an angular front library which implement Material Design
- [X] Create some view like login, register, user's profile, user's preference and events list
- [X] Develop an authentication management

### Features todo

- [ ] Enhance entities (more validation)
- [ ] Add missing units and integrations tests
- [ ] Add support of deletedAt of child entities
- [ ] Implement API to patch an activity
- [ ] Add admin view/API to manage all entities and to search them
- [ ] Add authentication support on the websocket event api
- [ ] Correct patch api to support custom data / object likes password
- [ ] Add ssl support
- [ ] Switch authentication to JWT token
- [ ] Add errors messages to the front
- [ ] Add websocket event api support to the front