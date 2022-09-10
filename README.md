# Retail Store
This application is about Retail Store Discount, it explains the percentage of discount assigned to a different type of buyer based on the kind of buyer and how long the buyer has been present with the company.
The application is built using Spring/Spring boot and Java.


## Table of Content

- [Retail Store](#retail-store)
    - [Table of Contents](#table-of-contents)
    - [General Information](#general-information)
    - [Technologies Used](#technologies-used)
    - [Project Structure](#project-structure)
        - [Config](#config)
        - [Controller](#controller)
        - [Exception](#exception)
        - [Mapper](#mapper)
        - [Model](#model)
        - [Repository](#repository)
        - [Service](#service)
        - [Utils](#utils)
    - [Endpoints](#endpoints)
        - [Item Endpoints](#item-endpoints)
        - [Transaction Endpoints](#transaction-endpoints)
        - [User Endpoint](#user-endpoints)
    - [Contributor](#contributor)


## General Information
### Assumption
* Docker desktop is install on the host machine [docker installation](https://www.docker.com/products/docker-desktop/)
* Docker compose should be installed, if not available in the docker desktop use [docker compose installation](https://docs.docker.com/compose/install/)
* JDK version 11+ is available on the host machine.
* Maven 3.6+ is available on the host machine

### Requirements
Database needs to be setup in dockers by running the script in the terminal using the following command, this will pull MySQl version 8 if not available in the docker 
```
docker run -p 3307:3306 --name retail_store -e MYSQL_ROOT_PASSWORD=test  --restart unless-stopped -e MYSQL_DATABASE=item_store -d mysql:8
```
The project environment variable needs to be exported into the machine host environment using the .env file present in the root directory of the project.
### Description
The application is a spring/spring-boot project which runs in a maven environment and packaged into dockers container, MySQL database was used, the project structure is explained [here](#project-structure)  
To run the project on the host machine, the following information will be required:
#### To run the project without using docker
```run
 mvn clean spring-boot:run
```
* It runs on the default port: 5000,
* The context-path is: "retail-store"
* Access the application using _http://localhost:5000/retail-store/swagger-ui.html_
#### To run and deploy the application using the test script
**On Windows**
Open the run.bat file in edit mode, change the variable DEVELOPMENT_HOME to point to the location of the project.
Open the Command Line terminal and run the file
```
 run.bat
```
**On Linux**
Open the terminal and run the file named below
```
 sh run.sh
```
The above files i.e run.bat and run.sh will complete the following process
* build the project
* create docker image
* start docker container

To stop and start the docker container, 
```
 docker-compose up
 docker-compose down
```
#### To run the integration test manually
```compile
 mvn clean verify 
```

#### To run both integration and unit test manually
```
 mvn clean build
```


The project uses a docker image called test-container for Integration testing using the db_ps.sql in the resource directory.
#### Layout
The project model layout is captured in the technology-assessment.png image in the root directory.

#### General Information

This Application gives user discount based on the following criteria:
1. If the user is an employee of the store, he gets a 30% discount
1. If the user is an affiliate of the store, he gets a 10% discount
1. If the user has been a customer for over 2 years, he gets a 5% discount.
1. For every $100 on the bill, there would be a $ 5 discount (e.g. for $ 990, you get $45
   as a discount).
1. The percentage-based discounts do not apply to groceries.
1. A user can get only one of the percentage-based discounts on a bill.


## Technologies Used
* Programming Language(s) and Frameworks
    * Java 11
    * Spring 5.0/Spring boot 2.7
* Dependencies
    * jackson-datatype-jsr310
    * commons-lang3
    * springfox-swagger-ui
    * spring-boot-starter-web
    * lombok
* Tools
    * IntelliJ IDEA
* Testing
    * Mockito
    * JUnit 5
    * TestContainer


## Project Structure

### Config
This contains the configuration files, it has two files:

**AppConfig** which contains the AsyncExecutor and CORS configuration of the project **SwaggerConfig** file that has the Swagger-UI configuration.
### Controller
This contains the exposed endpoints of the project

### Exception
This contains the custom exception classes of the application such as ApiError, BadRequest, ResourceNotFoundException etc.

### Mapper
This is the package that has the Mapper class that helps with converting one class from one to another.

### Model
This package has Three(3) sub-packages:
* **Entity**
This consist of the project entity classes that is mapped to the Database.
* **DTO** 
This contains the response and request classes that is sent externally from  or to the application.
* **Enums**
This contains the enumeration classes that defines different states of the application.

### Repository
This is the package that connects the database to the project implementation classes through the application.properties file and it has a sub-package named **listener**.

### Service
This contains the class implementation and the interface classes of the application, this is where the project business logic is written.

### Utils
This contains utility classes, these classes comprise of the reusable resource.

## Endpoints

* ### Item endpoints

|               Endpoint             |     Method    |              Route                      | Payload                                 |
|------------------------------------|---------------|-----------------------------------------|-----------------------------------------|
 | Add Item | POST | item/add| StoreItemRequest class                  |
 | Add Item by Category | POST | item/add/category  | categoryName, description, postedByUser |
 | List item | GET | item/list | page (optional), size (optional)                         |
 | List item category | GET | item/list/category | page (optional), size (optional)        |
 | List item by category code | GET | item/list/category-code | None                                    |
 | Update item | POST | item/update | StoreItemUpdateRequest class            |

*  ### Transaction Endpoints


  |              Endpoint           | Method |              Route           |                   Payload                         |
  |---------------------------------|--------|------------------------------|---------------------------------------------------|
  |Purchase item | POST | /transactions | buyerToken, itemCode, quantity |
  | List ransaction by item code | GET | /transactions/list | itemcode  |
  | List Transactions by Users | GET | /transctions/list/buyer | userToken |


* ### User Endpoints
|  Endpoints  | Method | Route | Payload |
|-------------|--------|-------|---------|
| Add User | POST | /user/add | userRequest class |
| List user | GET | /user/list | page (optional), size (optional) |


## Testing
So far different testing has been done on the application, testing such as end-to-end testing, Unit Testing and Integrated Testing.
For the Unit testing, Mockito was used to test each controller or route, the tests are written in the test folder.
Test done on this application
* Unit Testing
* Integrated Testing
* End-to-end Testing

## Contributors
| Name      | Email | Contact | Github                                      |
|-----------|-------|---------|---------------------------------------------|
| Oyejide Odofin | odofintimothy@gmail.com | +234 7065990878 | [github](https://github.com/timothy-odofin) |
