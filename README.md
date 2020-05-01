# TravelAgency
Application for managing travel

# Installing
To install the application, use the following maven command:<br>`mvn compile`<br/>
By default used H2 database profile. To use a different database, use the 
following maven command:<br>`mvn compile -Ddatabase=other_database_profile`<br/>

# Running the tests
To run the tests, use the following maven command:<br>`mvn test`<br/>

# Deployment
To deploy the application to Apache Tomcat web server,
use the following maven command:<br>`mvn tomcat7:deploy`<br/>

# Built With
- **Java 8**
- **Core Container**: Spring Beans, Spring Core, Spring Context
- **Data Access**: Spring Data JPA, Hibernate as JPA provider
- **Database**: Liquibase as migration tool, H2 as test embedded database
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