# Technical Assignment

This repository contains the codebase for the Technical Assignment project 'construction'.

## Instructions

Follow these steps to set up and run the project:

1. Clone the project using the following URL:
git clone https://github.com/shafi-88/technical-assignment.git

2. Open IntelliJ IDEA.

3. Go to `File` -> `New` -> `Project from Existing Sources`.

4. Select the `construction` directory inside the cloned project folder.

5. Choose `Gradle` as the build tool.

6. In the terminal inside the project path `construction\`, execute the following commands:
```
.\gradlew build
.\gradlew bootRun
```

8. Open Postman.

9. Add the header `x-api-key: test`.

10. Invoke the HTTP GET method at `localhost:8080/report` to retrieve the report for existing data.

**Note:** Existing data refers to sample input data provided in assignment file.

10. To test with custom data:
 - Create an HTTP POST method with the URL `localhost:8080/report`.
 - Set the JSON body as:
   ```
   {
       "data": "{{valid data}}",
       "delimiter": "{{fieldDelimiter}}"
   }
   ```
 - Invoke the API.

## Notes
- Replace `{{valid data}}` and `{{fieldDelimiter}}` with appropriate values for testing custom data.

## In Case of Unable to Run

If you are unable to run the project using the above instructions, follow these steps:

1. Directly open `Main.class` content along with the code in the `utils` package.
2. Run the project manually by executing the main method.


