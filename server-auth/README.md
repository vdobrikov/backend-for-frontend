# server-auth

Extremely basic auth server. The only thing it does is validation for provided token against predefined list. 
#### cURL
```shell
curl --location --request POST 'http://localhost:8080/validate' \
--header 'Content-Type: text/plain' \
--data-raw 'open-sesame'
```
#### HTTPie
```
http localhost:8080/validate 'Content-type:text/plain'<<< incorrect-token

HTTP/1.1 401 Unauthorized
Content-Length: 0
```
```
http localhost:8080/validate 'Content-type:text/plain'<<< open-sesame

HTTP/1.1 200 OK
Content-Length: 0
```

## Build the application

You can build the project and docker image with the following command 
```
./build.sh
```  

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

## Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces the `server-auth-1.0.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/server-auth-1.0.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your native executable with: `./target/server-auth-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image.