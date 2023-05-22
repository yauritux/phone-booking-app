# Phone-Booking-App

A simple Phone-Booking-App service to demonstrate on how to implement **Hexagonal Architecture** (a.k.a. `Port and Adapter`) with Reactive programming using `project-reactor`. 
This application service is capable of performing these following transactions:
1. Registering a user (endpoint: `POST /users`)
2. List all registered users (endpoint: `GET /users`)
3. Registering a phone (endpoint: `POST /phones`)
4. List all registered phones (endpoint: `GET /phones`)
5. User booking a phone (endpoint: `POST /bookings`)
6. User returning a phone by providing its **bookingId** (endpoint: `PUT /booking_return/{phoneId}`)
7. Get phone spec details along with its availability (endpoint: `GET /phones/{phoneId}`)

For further details, check the open-api doc (swagger) that can be accessed at `localhost:9000/webjars/swagger-ui/index.html` once you run the service.

Despite that we're exposing the service in **RESTful API**, we can expand this application through different channels (e.g., `cli-application`, `gRPC service`, `GraphQL`) 
due to the nature of how we built this application using the **Hexagonal Architecture**. 
We put all of the core business logic inside the `phone-booking-core` module, hence if you need to build and expose another service, you merely need to 
create another module and utilize the existing business logic from the `phone-booking-core` module, and no changes are needed as long as the business requirement doesn't change.

So essentially by having this kind of **Hexagonal architecture**, we can protect our business logic within the `core` domain layer
regardless of its application interface or any infrastructure layer such as database, framework, etc that we're going to embrace.
As what we've done here Here as an example by providing a **RESTful API** as one of its application layer to interact with our user/client.

## Prerequisites

- Java 17
- Maven 3+
- Docker

## Running the App

1. Compile the project by executing this following command from the project base directory:
```shell
mvn clean package
```
2. Run mongo database by invoking our `docker-compose` file.
```shell
docker-compose up
```
3. Still within the project base directory, execute the `phone-booking-restws` jar file (DO NOT FORGET to provide the environment variables!)
```shell
SERVER_PORT=9000 \
MONGO_DB_URI=mongodb://root:tux123@localhost:27017/phonebooking?authSource=admin \
FONOAPI_URL=https://fonoapi.freshpixl.com/v1/ \
FONOAPI_TOKEN=77safdsf#1$# \
FONOAPI_TIMEOUT_MS=9500 \
java -jar `./phone-booking-restws/target/phone-booking-restws-1.0.jar`
```
