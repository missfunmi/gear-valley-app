# Gear Valley
A price tracking app for gear shoppers.

## Local Development

Run the server which serves api and ui using the following. Rebuilding the project hot reloads java changes:
```shell script
mvn clean package
mvn spring-boot:run
```

For debugging, it is probably easiest to run the spring boot application from the IDE by navigating to in the GearValleyApplication class and selecting Debug from the menu.

You can also attach run spring boot in debug mode as follows and attach your debugger:
```shell script
mvn spring-boot:run -Dagentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000
``` 

For UI development with hot reload run the below command which should launch the app at http://localhost:3000:

*** N.B. This assumes you have `node` and `yarn` installed globally on you system ***

```shell script
cd src/main/javascript/app
yarn run start
```


