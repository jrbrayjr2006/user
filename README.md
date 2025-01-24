# user Spring Boot Application
This is a simple Spring Boot application that exposes some CRUD methods for user data.  The data is stored in a MongoDB database.

## Dependencies
This application uses MongoDB for data persistence
## Build
```aidl
gradle build
gradle bootRun
```

## Operations
The methods are exposed via RESTful operations which use request path parameters.

For example: 
`https://localhost:8080/user/123` 
will return a user object with the id of `123` if that user exists in the data repository.

## Deployment to Pivotal Cloud Foundry (PCF)

Deploying this app to PCF is straight forward.  Make sure to setup a MongoDB database service that this application can use for data persistence.  As of the writng of this document, the mlab cloud service is available on Pivotal Cloud Foundry for use as a MongoDB database.
1.  Build the application using `gradle build`
2.  Change to the dist directory by `cd build/libs`
3.  Login to PCF `cf login --sso -a api.run.pcfone.io`
4.  Get the login token and compete login
5.  Push the application

```
cf push -b java_buildpack user-0.0.1-SNAPSHOT.jar
```
or if the manifest.yml file is configured with enough detail, use 
```aidl
cf push
```
After the application has been uploaded, deployed, and started on PCF, logout of the CLI.
```aidl
cf logout
```

## Updates

This code repository was created in 2017 and had a substantial update in 2020.  The unit testing framework has been updated to JUnit 5

This repository was updated again in 2023 to use Java 17 and Spring Boot 3.1 (Spring 6.1).

### REST API Documentation

This application uses Swagger for API documentation.

## Local Configuration

The application can be run locally by using the following command `gradlew bootRun`.

If you are running this application on a Windows/WSL (Windows Subsystem for Linux) environment, you may need to make the following configuration changes in order for the application to connect to the proper port for MongoDB.

The [Go WSL Host](https://github.com/shayne/go-wsl2-host) utility can be used to allow a connection to the port.


## References

- [REST Resource Naming Guide](https://restfulapi.net/resource-naming/)
- [Springfox Reference Documentation](https://springfox.github.io/springfox/docs/current/)
- [Documenting a Spring REST API Using OpenAPI 3.0](https://www.baeldung.com/spring-rest-openapi-documentation)
- [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa)
- [Spring Boot With H2 Database](https://www.baeldung.com/spring-boot-h2-database)
- [Quick Guide on Loading Initial Data with Spring Boot](https://www.baeldung.com/spring-boot-data-sql-and-schema-sql)
- [mongodb_6_on_wsl2_ubuntu_2204.md](https://gist.github.com/leobeeson/8ac8c8ddbb0a704af26e4bd153658bde)