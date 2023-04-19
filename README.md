# RecipeManager

Introduction
------------

The application allows users to add, update, remove, and fetch recipes, as well as filter recipes based on various criteria. The application is implemented as a REST API using the Spring framework and is designed to be production-ready.

Architecture Overview
---------------------

The application consists of three main components:

1.  REST API layer: This layer provides the RESTful interface for the application, allowing clients to perform CRUD operations on the recipes. It is implemented using the Spring framework and uses Spring Boot to simplify configuration and deployment.

2.  Service layer: This layer contains the business logic for managing the recipes. It is responsible for validating inputs, executing business rules, and interfacing with the data access layer. It is implemented using Spring components.

3.  Data access layer: This layer provides access to the MySQL database that stores the recipe data. It is implemented using Spring Data JPA, which simplifies database access and provides an object-relational mapping framework.

The architecture follows the principles of layered architecture, with each layer responsible for a distinct set of tasks. This separation of concerns allows for easier maintenance, testing, and scalability.

REST API Layer
--------------

The REST API layer is implemented using Spring MVC, which provides a powerful and flexible framework for building RESTful web services. The API is designed to be easily consumable by clients, with a clear and consistent interface.

The API supports the following operations:

-   GET /recipes: Returns a list of all recipes.
-   GET /recipes/{id}: Returns the details of a specific recipe.
-   POST /recipes: Adds a new recipe.
-   PUT /recipes/{id}: Updates an existing recipe.
-   DELETE /recipes/{id}: Deletes a recipe.
-   POST /recipes/filter: Performs a search for recipes based on various criteria.

-   POST /auth/signin: User login, generates Bearer token to be used in APIs.
-   POST /auth/signup: Registers a user.
-   GET /auth/logout: User logout

Service Layer
-------------

The service layer provides the business logic for managing the recipes. It is responsible for validating inputs, enforcing business rules, and interfacing with the data access layer. The service layer is implemented using Spring components, which are wired together using dependency injection.

The service layer supports the following operations:
-   signup: Register a user with username, email and password.
-   signin: Return bearer token for authorization. 
-   getAllRecipes: Returns a list of all recipes.
-   getRecipe: Returns the details of a specific recipe.
-   createRecipe: Adds a new recipe.
-   updateRecipe: Updates an existing recipe.
-   deleteRecipe: Deletes a recipe.
-   filterRecipes: Performs a search for recipes based on various criteria.

Data Access Layer
-----------------

The data access layer provides access to the MySQL database that stores the recipe data. It is implemented using Spring Data JPA, which simplifies database access and provides an object-relational mapping framework.

The data access layer consists of the following components:

-   Recipe: A JPA entity representing a recipe.
-   Ingredient: A JPA entity representing an ingredient.
-   User: A JPA entity representing a user.
-   RecipeRepository: A Spring Data JPA repository interface for managing recipes.
-   UserRepository: A Spring Data JPA repository interface for managing users.

Technologies Used
-----------------

The following technologies are used in the Recipe Manager Application:

-   Java 8
-   Spring Boot
-   Spring Data
-   Spring MVC
-   JWT
-   MySQL
-   Docker
=   Maven

Deployment
----------

The application is designed to be deployed as a Docker container, which simplifies deployment and ensures consistency across environments. The Docker image includes the application and its dependencies, as well as the MySQL database. 

To create app jar, use the following command:
```
mvn clean package
```

To run the Docker container, use the following command:

```
docker-compose -f docker-composer.yaml up --build
```
Database will be initialise with example data. init.db can be found in /db folder.

Postman API Examples
----------
Sample API calls can be found under /postman folder.

Bear token needs to be used in /recipe APIs. To generate the token, user registeration and then user signin is necessary.
To get token /auth/signin needs to be used. 
Sample response for /auth/signin
```
{
    "username": "admin",
    "tokenType": "Bearer",
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY4MTg1NjMwOCwiZXhwIjoxNjgxOTQyNzA4fQ.rKzBREt-DnYHksd4s4UGeftfoArp2dQHM0_CQMG0cKoi_KrxoCxxyBAtDD5kJ83yA7_eOMGt6noM-pwg9uWn5Q"
}
```

Sample Header for /recipes APIs
```
Authorization:eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY4MTg1NjMwOCwiZXhwIjoxNjgxOTQyNzA4fQ.rKzBREt-DnYHksd4s4UGeftfoArp2dQHM0_CQMG0cKoi_KrxoCxxyBAtDD5kJ83yA7_eOMGt6noM-pwg9uWn5Q
Content-Type:application/json
```

Conclusion
----------

In conclusion, this project demonstrates how to build a RESTful API using Spring Boot and integrate it with MySQL database. It provides endpoints to manage recipes including creating, updating, deleting, and retrieving them. Additionally, a Dockerfile has been created to containerize the application.

The implementation is based on good programming practices, such as separation of concerns, dependency injection, and the use of DTOs to decouple the domain model from the API. 

Overall, this project serves as a solid starting point for building a RESTful API using Spring Boot and integrating it with a MySQL database. 