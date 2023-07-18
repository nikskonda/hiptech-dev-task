In the project directory, you can run:

### `./mvnw spring-boot:run`

Runs the app using port 8080 by default.

The application exposes REST endpoint /rest/v1/routing/{origin}/{destination} that
returns a list of border crossings to get from origin to destination.

Call e.g. http://localhost:8080/rest/v1/routing/KAZ/PRT to test this application.