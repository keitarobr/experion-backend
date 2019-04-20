# Experion backend

Experion backend server

## Required infrastructured

Postgres database

## Enviroment variables required

```properties
experion_db_host=postgresHostIP
experion_db_name=postgresDatabaseName
experion_db_username=postgresDatabaseUser
experion_db_password=postgresDatabaseUserPassword
```
## To Run

`mvn spring-boot:run`

## Creating users

Run the application with `mvn spring-boot:run` and use the embedded shell commands. Use `help` to see available commands.

## Browse the API

Go to `/swagger-ui.html`. Don't forget to authenticate and pass the token using _Bearer GENERATED_TOKEN_ in Swagger UI interface.

