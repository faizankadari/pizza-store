# Pizza store application using spring-boot(Backend)
A RESTful API built with Spring Boot and MongoDB to manage pizzas and orders in a local pizza shop. Supports reactive programming for scalability.

### Features
1. Reactive programming using Spring WebFlux.
2. Uses MongoDB as the database for storing pizza and order details.
3. Standardized logging at the service level using @Slf4j.
4. Error handling with custom exceptions.
5. Allows searching pizzas by name with dynamic queries.
6. Implements caching using Spring Boot's caching abstraction to optimize repeated data retrieval

### Technologies Used
- Java
- Spring Boot
- Spring WebFlux
- MongoDB
- Maven

### Prerequisites
To successfully run the project after cloning the repository, ensure the following prerequisites are met:
- Install Java 21 on your system.
- Install Apache Maven 3.9.9.
- Install and configure MongoDB on your system and start the MongoDB server
- Open the application.properties file in the project and update the MongoDB connection details as given below
- spring.data.mongodb.uri=mongodb://localhost:27017/your_database_name
### Build and Run Commands
Navigate to the root directory of the cloned repository and build the project using Maven:
- mvn clean install
Run Application using
- mvn spring-boot:run

### Pizza Endpoints

| Method | Endpoint          | Description                     | Request Body                          | Response Body                       |
|--------|-------------------|---------------------------------|---------------------------------------|-------------------------------------|
| POST   | `/api/pizzas`     | Create a new pizza              | `{name, description, toppings, size, price}` | Created pizza object               |
| GET    | `/api/pizzas/{id}`| Get a pizza by ID               | None                                  | Pizza object                        |
| GET    | `/api/pizzas`     | Get all pizzas                  | None                                  | List of all pizzas                  |
| GET    | `/api/pizzas/search` | Search pizzas by name           | `name` query parameter                | List of matching pizzas             |
| PUT    | `/api/pizzas/{id}`| Update an existing pizza        | `{name, description, toppings, size, price}` | Updated pizza object               |
| DELETE | `/api/pizzas/{id}`| Delete a pizza by ID            | None                                  | `"Pizza Deleted Successfully"`      |

### Sample Request for POST and PUT Method
  
- **{
  "id": 1,
  "name": "Margherita",
  "description": "Classic cheese and tomato pizza",
  "toppings": ["cheese", "paneer"],
  "size": "Medium",
  "price": 8.99
}**
- **Note: For now, change the ID every time while adding a new object.**

### Orders Endpoints

| Method | Endpoint                | Description              | Request Body           | Response Body                   |
|--------|-------------------------|--------------------------|------------------------|---------------------------------|
| POST   | `/api/orders`           | Create a new order       | `{orderID, pid}`       | Created order details object    |
| GET    | `/api/orders/{id}`      | Get an order by ID       | None                   | Order details object            |
| GET    | `/api/orders`           | Get all orders           | None                   | List of all orders              |
| PUT    | `/api/orders/{id}/status` | Update order status      | `{status}`             | Updated order object            |
| DELETE | `/api/orders/{id}`      | Cancel an order          | None                   | `"Order Deleted Successfully"`  |

### Sample Request for post method
- **{
    "orderId": "1",
    "pizzasID": [
        1
    ]
}**
- **Note: For now, change the ID every time while adding a new object.**

**Additional Notes**

The project is designed to be modular and scalable, making it suitable for deployment in cloud environments or containerized setups (e.g., Docker/Kubernetes).
Front-end frameworks like ReactJS or Angular can be used to build a user interface that interacts with these APIs.
This feature list aligns with the provided codebase while highlighting caching, logging, and reactive programming as key strengths of the project.
