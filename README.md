# user Spring Boot Application
This is a simple Spring Boot application that exposes some CRUD methods for user data.  The data is stored in a MongoDB database.

## Dependencies
This application uses MongoDB for data persistence
## Build
```aidl
gradle build
gradle bootRun
```
## Deployment to Pivotal Cloud Foundry

Deploying this app to PCF is straight forward.  Make sure to setup a MongoDB database service that this application can use for data persistence.  As of the writng of this document, the mlab cloud service is available on Pivotal Cloud Foundry for use as a MongoDB database.
1.  Build the application using `gradle build`
2.  Change to the dist directory by `cd build/libs`
3.  Login to PCF `cf login -a api.run.pivotal.io`
4.  Push the application
```
cf push -b java_buildpack user-0.0.1-SNAPSHOT.jar
```
or if the manifest.yml file is configured with enough detail, use 
```aidl
cf push
```