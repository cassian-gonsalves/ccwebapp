# CSYE 6225 - Summer 2019

## Team Information

| Name | NEU ID | Email Address |
| --- | --- | --- |
| Cassian Gonsalves | 001475176 | gonsalves.c@husky.neu.edu |
| Dhanashri Palodkar | 001357556 | palodkar.d@husky.neu.edu |
| Karun Kesavadas | 001475574 | kesavadas.k@husky.neu.edu |

## Technology Stack
Programming Language and framework used: Java, Spring Boot Framework, MySQL, Maven


Prerequisites for building and deploying your application locally
1. Intellij IDE
2. Postman
3. MySQL


### Operating System
* Linux (Ubuntu)

### Backend Frameworks
* Spring Boot Web
* Spring Security
* Spring Data JPA
* Hibernate ORM
* Hibernate Validator
* Passay

## Build Instructions
1. Create a spring boot application
2. Update application.properties file with database connection details
3. Run the application as "Java Application"
4. Open Postman application to test API results
5. Now select the POST option and enter the URL as "http://localhost:8080/user/register"
6. In the body section below, select 'raw' and then select 'JSON(application/json)'
7. Write the parameters to be sent in JSON format and click on 'Send', see the results on the window below
6. Now select GET option and enter the URL as "http://localhost:8080/"
7. In the 'authorization' section, select 'Basic Auth'
8. Enter the credentials and click 'Send'
9. If the credentials are correct, the current timestamp is shown


## REST API endpoints
- Register a new user
  * **Method:** `POST`
  * **URL:** `/user/register`
  * **Success Response:**

  * **Code:** `201 CREATED`
 
  * **Error Response:**

  * **Code:** `400 BAD REQUEST`
    **Content:** 
    ```json
    {"error" : "BAD REQUEST" }
    ```

    OR

  * **Code:** `409 CONFLICT` 
    **Content:** 
    ```json
    { "error" : "User already exits" }


All below endpoints are authenticated with basic authentication
- Get current time
  * **Method:** `GET`
`URL:**` /`
  * **Success Response:**

  * **Code:** `200 OK`
 
  * **Error Response:**

  * **Code:** `401 UNAUTHORIZED`
    **Content:** 
    ```json
    {"error" : "You are unauthorized to make this request." }
    ```





## Deploy Instructions


## Running Tests


## CI/CD


