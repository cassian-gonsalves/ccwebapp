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
    ````


All below endpoints are authenticated with basic authentication
- Get all books 
  * **Method:**` GET `
  * **URL:**`/book`
  * **Success Response:**

  * **Code:** `200 OK`
    **Content:** 
    ```json
  	{
    "id": "d290f1ee-6c54-4b01-90e6-d701748f0851",
    "title": "Computer Networks",
    "author": "Andrew S. Tanenbaum",
    "isbn": "978-0132126953",
    "quantity": 5,
    "image": {
      "id": "d290f1ee-6c54-4b01-90e6-d701748f0851",
      "url": "https://s3-eu-central-1.amazonaws.com/BUCKET/FILE"
    	}
  	}
    ```
 
  * **Error Response:**

  * **Code:** `401 UNAUTHORIZED`
    **Content:** 
    ```json
    { "error" : "You are unauthorized to make this request." }
    ```


- Create a new book
  * **Method:**` POST`
  * **URL:**` /book`
  * **Success Response:**

  * **Code:** `201 CREATED`
    **Content:** 
    ```json
  	{
    "id": "d290f1ee-6c54-4b01-90e6-d701748f0851",
    "title": "Computer Networks",
    "author": "Andrew S. Tanenbaum",
    "isbn": "978-0132126953",
    "quantity": 5,
    "image": {
      "id": "d290f1ee-6c54-4b01-90e6-d701748f0851",
      "url": "https://s3-eu-central-1.amazonaws.com/BUCKET/FILE"
    	}
  	}
    ```
 
  * **Error Response:**

  * **Code:** `400 BAD REQUEST`
    **Content:** 
    ```json
    {"error" : "BAD REQUEST" }
    ```

     OR

  * **Code:** `401 UNAUTHORIZED`
    **Content:** 
    ```json
    { "error" : "You are unauthorized to make this request." }
    ```


- Update a new book
  * **Method:**` POST`
  * **URL:**` /book`
  * **Success Response:**

  * **Code:** `204 NO CONTENT`
 
  * **Error Response:**

  * **Code:** `400 BAD REQUEST`
    **Content:** 
    ```json
    {"error" : "BAD REQUEST" }
    ```

     OR

  * **Code:** `401 UNAUTHORIZED`
    **Content:** 
    ```json
    ```json
    {"error" : "You are unauthorized to make this request." }
    ```

- Get book by id
  * **Method:**` GET`
  * **URL:**` /book/{id}`
  * **Success Response:**

  * **Code:** `200 OK`
    **Content:** 
    ```json
  	{
    "id": "d290f1ee-6c54-4b01-90e6-d701748f0851",
    "title": "Computer Networks",
    "author": "Andrew S. Tanenbaum",
    "isbn": "978-0132126953",
    "quantity": 5,
    "image": {
      "id": "d290f1ee-6c54-4b01-90e6-d701748f0851",
      "url": "https://s3-eu-central-1.amazonaws.com/BUCKET/FILE"
    	}
  	}
    ```

 
  * **Error Response:**

  * **Code:** `404 NOT FOUND`
    **Content:** 
    ```json
    {"error" : "Book doesn't exist" }
    ```

     OR

  * **Code:**`401 UNAUTHORIZED`
    **Content:** 
    ```json
    { "error" : "You are unauthorized to make this request." }
    ```
  

- Delete book by id
  * **Method:**`DELETE`
  * **URL:** /book/:{id}`
  * **Success Response:**

  * **Code:** `204 NO CONTENT`
 
  * **Error Response:**

  * **Code:** `400 BAD REQUEST`
    **Content:** 
    ```json
    {"error" : "BAD REQUEST" }
    ```

    OR

  * **Code:** `401 UNAUTHORIZED`
    **Content:** 
    ```json
    { "error" : "You are unauthorized to make this request." }
     ```


- Add Image to book
  * **Method:**` POST`
  * **URL:**` /book/{id}/image`
  * **Success Response:**

  * **Code:** `200 OK`
    **Content:** 
    ```json
    {
  		"id": "d290f1ee-6c54-4b01-90e6-d701748f0851",
 		"url": "https://s3-eu-central-1.amazonaws.com/BUCKET/FILE"
	}
    ```
 
  * **Error Response:**

  * **Code:**`401 UNAUTHORIZED`
    **Content:** 
    ```json
    { "error" : "You are unauthorized to make this request." }
    ```
 

- Get Book Image
  * **Method:**` GET`
  * **URL:**` /book/{id}/image/{id}`
  * **Success Response:**

  * **Code:** `200 OK`
    **Content:** 
    ```json
    {
  		"id": "d290f1ee-6c54-4b01-90e6-d701748f0851",
  		"url": "https://s3-eu-central-1.amazonaws.com/BUCKET/FILE"
	}
    ```
 
  * **Error Response:**

  * **Code:**`401 UNAUTHORIZED`
    **Content:** 
    ```json
    { "error" : "You are unauthorized to make this request." }
    ```

- Update a book's image
  * **Method:**` PUT`
  * **URL:**` /book/{id}/image/{id}`
  * **Success Response:**

  * **Code:** `204 NO CONTENT`
 
  * **Error Response:**

  * **Code:** `401 UNAUTHORIZED`
    **Content:** 
    ```json
    { "error" : "You are unauthorized to make this request." }
    ```


- Delete a book's image
  * **Method:**` DELETE`
  * **URL:**` /book/{id}/image/{id}`
  * **Success Response:**

  * **Code:** `204 NO CONTENT`
 
  * **Error Response:**

  * **Code:** `401 UNAUTHORIZED`
    **Content:** 
    ```json
    { "error" : "You are unauthorized to make this request." }
    ```


- Password Reset
  * **Method:**` POST`
  * **URL:**` /reset`
  * **Success Response:**

  * **Code:** `204 NO CONTENT`
 
  * **Error Response:**

  * **Code:** `400 BAD REQUEST`
    **Content:** 
    ```json
    { "error" : "Bad request." }
    ```


## Deploy Instructions
#### For deployment on Circle CI add the following environment variables 
````
1. APPLICATION_NAME
2. AWS_ACCESS_KEY_ID
3. AWS_DEFAULT_REGION
4. AWS_REGION
5. AWS_SECRET_ACCESS_KEY
6. CODEDEPLOY_S3_BUCKET
````

## Running Tests
```
1. Download Jmeter and extract it
2. Open terminal and go into the bin folder in the Jmeter folder
3. Run the script by typing in the following command
sh jmeter.sh -n -t LOCATION_OF_THE SCRIPT -JDomain=DOMAIN_NAME -JFilepath=PATH_TO_THE_IMAGE_FILE
```

## CI/CD
Implemented using CI/CD


