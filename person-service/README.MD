Component in person service.

1: REST api for person CRUD operation
2: Security
3: Testing ( unit testing and integration testing)
4: Open Api Documentation
5: Docker 
6: Logging


Instruction to start and view and run the code.


1: To view the code import the project 'person-service' as gradle in Intellij.

2: After successful import run 'gradle clean build', this will build the project as well as build the docker image.
    If you docker is not available in system or you want to build the service without building docker image then comment the 'buildDocker' task in build.gradle file.

3: To view the original docker file, location - /src/main/docker/Dockerfile after build the the jar and docker file will be in /build/docker folder.

4: After building docker or application, you can run the application by right clicking the Application and press Run
    Run application by command 'docker run -p 8080:8080 person-service'

5:Once the application is started  then you can navigate couple of url to perform CRUD operations.
   For Swagger Documentation : http://localhost:8080/swagger-ui.html   
   For Api Documentation json : http://localhost:8080/v3/api-docs

6: As this solution have Spring Security so you can first sign-up and then get the access token by login.
    Recommended to navigate to swagger 'http://localhost:8080/swagger-ui.html' to view all available apis and perform all operations, 
    Or use postman.
    
    Sign-Up User / Create User:

    POST : http://localhost:8080/api/users/sign-up 
    Request Body: {  "username": "user",  "password": "password" .}

    Login User:     

    POST : http://localhost:8080/api/users/sign-up 
    Request Body: {  "username": "user",  "password": "password" .}

    Capture the Bearer token from header response 'Authorization' 
    and use it in request header to access all person apis 'http://localhost:8080/api/persons'
    
    Access all the api specification on swagger http://localhost:8080/swagger-ui.html

    GET All persons : http://localhost:8080/api/person'
    
    POST Create person : http://localhost:8080/api/person'
    Request Body:   {"first_name": "string",
                    "last_name": "string",
                    "age": 7,
                    "favourite_colour": "string"}

    GET persons By ID : http://localhost:8080/api/person/id'

    PUT update person by Id : http://localhost:8080/api/person/id'
    Request Body: {"first_name": "string",
                    "last_name": "string",
                     "age": 7,
                     "favourite_colour": "string"}

    DELETE delete person by Id : http://localhost:8080/api/person/id'