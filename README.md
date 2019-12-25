# Gear Valley
A price tracking app for gear shoppers.

## Local Development

### Running the application
Run the server which serves both the API and a front end using the following. Note that rebuilding the project hot reloads java changes:
```shell script
mvn clean package
mvn spring-boot:run -Drun.profiles=dev
```

### Debugging
For debugging, it is probably easiest to run the spring boot application from the IDE by navigating to in the ``GearValleyApplication.java`` class and selecting Debug from the menu.

You can also run spring boot in debug mode as follows and attach your debugger:
```shell script
mvn spring-boot:run -Dagentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000
``` 

### UI Development
For UI development with hot reload, run the below command which should launch the app at http://localhost:3000:

*** N.B. This assumes you have `node` and `yarn` installed globally on you system ***

```shell script
cd src/main/javascript/app
yarn run start
```


