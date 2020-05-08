# backend for frontend

Education project about creating frontend friendly backend microservice infrastructure

#### Stages
[x] Feel the pain - communicate with backend microservices directly

[ ] Relief - communicate thru API gateway

[ ] Shiny modern world - API gateway + GraphQL

## Backend

### Build
```
./build-server-components.sh
```

### Run
```
docker-compose up -d
```

### Use
Add new author with HTTPie:
```
echo '{"lastName":"Fry","address":"New York","language":"English","firstName":"Phillip J."}' | http POST http://localhost:8080/api/v1/authors -a httpie:open-sesame
```

## Frontend
For demo purposes clommand line clients mimic frontend

### Build
```
cd ./client
./mvnw clean package
```

### Run
Min client (minimal output values):
```
cd ./client
java -jar client-min/target/client-min-1.0-SNAPSHOT.jar
```

Max client (verbose output values)
```
cd ./client
java -jar client-max/target/client-max-1.0-SNAPSHOT.jar
```
