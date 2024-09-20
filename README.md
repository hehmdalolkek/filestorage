# FILESTORAGE

REST API that interacts with file storage and provides the ability to access files and download history.

### Technologies 

* Java 
* MySQL
* Hibernate
* HTTP
* Servlets
* Maven
* Flyway

### Requirements

* All CRUD operations for each of the entities
* Adhere to the MVC approach
* Use Maven to build the project
* For interaction with the database - Hibernate
* For configuring Hibernate - annotations
* Initialization of the database must be implemented using flyway
* Interaction with the user must be implemented using Postman

### Run locally

1. You must have Tomcat 10 or later on your PC to run the app.
2. To build the project you need to have Maven.
3. Start locally MySQL database with dbname="filestorage", username="root", password="".
4. To build the project, enter the command in the root of the project:
```
    mvn package 
```
5. Move the collected filestorage.war from /target to /tomcat/webapps.
6. Run the Tomcat script:
```
    /bin/startup.bat // for windows
    /bin/startup.sh // for linux
```
7. After starting Tomcat the application will be available at http://localhost:8080/filestorage
