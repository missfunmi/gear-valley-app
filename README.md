# Gear Valley
A price tracking app for gear shoppers.

## Local Development

### Running the application
#### Pre-requisites
1. **Required:** Mongo DB [(available here)](https://docs.mongodb.com/manual/mongo/#start-the-mongo-shell-and-connect-to-mongodb)
2. **Optional:** yarn (link TBA)

### Steps to running the app
1. Start up Mongo locally (see instructions in link above)
2. Run the server which serves both the API and a front end as follows:
```shell script
mvn clean package
mvn spring-boot:run -Drun.profiles=dev
```
Note that rebuilding the project hot reloads java changes.

### Debugging
For debugging, it is probably easiest to run the spring boot application from the IDE by navigating to the ``GearValleyApplication.java`` class and selecting Debug from the menu.

You can also run spring boot in debug mode as follows and attach your debugger:
```shell script
mvn spring-boot:run -Dagentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000
``` 

To inspect the DB contents, the MongoDB name is specified in the [application.properties file](src/main/resources/application.properties).


### UI Development
For UI development with hot reload, run the below command which should launch the app at http://localhost:3000:

*** N.B. This assumes you have `node` and `yarn` installed globally on your system ***

```shell script
cd src/main/javascript/app
yarn run start
```             

### "Production" aka hitting live websites
Production for now means hitting the real external site (as opposed to loading static HTML results from file). To run against the real site, set the following in ``application.properties``:
```shell script 
spring.profiles.active=prod
application.expected.active.profile=prod
```
