# Kindergarten information system

Vocational school project designed for learning purposes. 
Developed system registers and processes children's requests to the kindergarten. A child is allocated a place at a kindergarten or a place ij a waiting list depending on pre-agreed criteria.

System user roles and their authorities:

| ROLE | AUTHORITIES |
| --- | --- |
| ADMIN |  create user, delete user, reset user password, lock application queue editing manager, review system logs, update owned account |
|MANAGER | create a kindergarten, update kindergarten, start/ stop application submission, deactivate users' applications before approval (if not locked by admin), process applications queue, confirm applications queue, update owned account |
| USER | submit an application (if not locked by manager), review submitted applications and their status, submit/ review pdf documents, delete application, get user data, update and delete owned account |

** Technologies used: **
- React 17.0.1
- Spring Boot 2.4.0, Java 11
- Spring security
- H2 database
- Apache Tomcat 9.0.40 server
- Swagger-UI
- Maven


## Getting Started

- Clone the repository git clone https://github.com/JurgitaVisa/Projektas_Darzeliu_IS.git

### Run on Tomcat Server

- go to project folder `cd .../Projektas_Darzeliu_IS/back`
- run the application on Tomcat Server (port 8081):
  
```
 mvn clean install org.codehaus.cargo:cargo-maven2-plugin:1.7.7:run -Dcargo.maven.containerId=tomcat9x -Dcargo.servlet.port=8081 -Dcargo.maven.containerUrl=https://repo1.maven.org/maven2/org/apache/tomcat/tomcat/9.0.40/tomcat-9.0.40.zip
 ```
 - the application will start on your browser http://localhost:8081/darzelis

### Run with Spring boot and npm/yarn

- go to project folder `cd .../Projektas_Darzeliu_IS/back`
- Run `mvn spring-boot:run` (application will start on port 8080)
- go to project folder `cd .../Projektas_Darzeliu_IS/front`
- run `npm install` or `yarn install`
- open file `..\Projektas_Darzeliu_IS\front\src\components\10Services\endpoint.js`
- change `const apiEndpoint` to `const apiEndpoint = "http://localhost:8080"`
- run `npm run start` or `yarn start`
- application will open in your browser at http://localhost:3000

### Accessing the database

http://localhost:8081/darzelis/console

```
JDBC URL:jdbc:h2:~/tmp/neverLatte1.db
User Name:sa
Password:

```

### Accessing API documentation

http://localhost:8081/darzelis/swagger-ui/


## Running the tests

Explain how to run the automated tests for this system

### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```

## Deployment

To make a war file for deployment:
- run `mvn clean install` while in the project folder `.../Projektas_Darzeliu_IS/back`
- `darzelis.war` file will appear in the `..\Projektas_Darzeliu_IS\back\target` folder


## Authors

List of [contributors](https://github.com/JurgitaVisa/Projektas_Darzeliu_IS/graphs/contributors) who participated in this project.

Copyright ©️ 2021, It's Never Too Latte