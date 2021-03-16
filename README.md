# Kindergarten information system

Vocational school project designed for learning purposes. 
Developed system registers and processes children's requests to the kindergarten. A child is allocated a place at a kindergarten or a place in a waiting list depending on pre-determined criteria.

System user roles and their authorities:

| ROLES | AUTHORITIES |
| --- | --- |
| ADMIN |  create user, delete user, reset user password, lock application queue editing for manager, review system logs, update own account |
|MANAGER | create a kindergarten, update kindergarten, start/ stop application submission, deactivate users' applications before approval (if not locked by admin), process applications queue, confirm applications queue, update own account |
| USER | submit an application (if not locked by manager), review submitted applications and their status, submit/ review pdf documents, delete application, get user data, update and delete own account |

#### Technologies used: 
- React 17.0.1,  Boostrap 4.5.3
- Spring Boot 2.4.0, Java 11
- Spring security
- H2 database
- Apache Tomcat 9.0.40 server
- Swagger-UI, Maven
- Selenium 3.141.59
- TestNG 

#### Design previews:

ADMIN pages: https://agn709575.invisionapp.com/console/share/VM26ELGVGT/550993621

MANAGER pages: https://agn709575.invisionapp.com/console/share/JB26EZTCR9/550996106

USER pages: https://agn709575.invisionapp.com/console/share/GB26F5SD2U/550996143

<sub>*actual pages may differ to a degree from the initial designs<sub>

## Getting Started

- Clone the repository `git clone https://github.com/JurgitaVisa/Projektas_Darzeliu_IS.git`

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
- change `const apiEndpoint= process.env.PUBLIC_URL` to `const apiEndpoint = "http://localhost:8080"`
- run `npm run start` or `yarn start`
- application will open on your browser at http://localhost:3000

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

- for smoke tests, run smoke.xml
- for regression tests, run regression.xml

### Break down into end to end tests

There are 7 different test packages: adminTests, specialistTests, parentTests, login, smokeTests, generalMethods and basetest. First 3 are the main ones, generalMethods holds reusable code for different test cases and basetest is for set up (getting Chrome driver and application link) and closing all tests after running them.

```
adminTests package tests:
- create and delete new user (all three roles)
- update admin details (change user information, password, reset password)

specialistTests package tests:
- create and delete new kindergarten
- update specialist details (change user information, password, reset password)

parentPages package tests:
- submit and delete new application
- submit 2 applications and check if the one with more priority points gets the place at the kindergarten
- update parent details (change user information, password, reset password)
- upload medical document (pdf)

```

## Deployment

To make a war file for deployment:
- run `mvn clean install` while in the project folder `.../Projektas_Darzeliu_IS/back`
- `darzelis.war` file will appear in the `..\Projektas_Darzeliu_IS\back\target` folder


## Authors

List of [contributors](https://github.com/JurgitaVisa/Projektas_Darzeliu_IS/graphs/contributors) who participated in this project.

Copyright ©️ 2021, It's Never Too Latte