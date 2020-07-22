# TravelAgency
Application for managing travel

# Compiling
To install the application, use the following maven command:
```bash
mvn compile
```
By default, used H2 database profile. To use a different database, use the 
following maven command:
```bash
mvn compile -Ddatabase=other_database_profile
```

# Running the tests
To run the tests, use the following maven command:
```bash
mvn test
```

# Deployment
To deploy the application to Apache Tomcat web server,
use the following maven command:
```bash
mvn tomcat7:deploy
```
Open [http://localhost:8080/travelAgency](http://localhost:8080/travelAgency) to view it in the browser.<br/>
To run front use the following node package manager command:
```bash
npm start
```
Open [http://localhost:3000](http://localhost:3000) to view it in the browser.

# Built With
- **Java 8**
- **Core Container**: Spring Beans, Spring Core, Spring Context
- **Data Access**: Spring Data JPA, Hibernate as JPA provider
- **Database**: Liquibase as migration tool, H2 as test embedded database, supports other relational databases
- **Validation**: 
- **Logging**: Log4j2
- **Serialization**: Jackson
- **Test**: Spring Test, TestNG
- **Web**: Spring Web, Spring Security
- **Other**: Maven for compilation and dependency management, Log4j2 for logging, Lombok 
to add new functionality to code before compilation

# Versioning
No versioning

# License
No license

# Authors
Pavel Zdanovich