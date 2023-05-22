# Movie Store

Table of Contents:
1. [Introduction](#introduction)
2. [Features](#features)
3. [REST API](#rest-api)
   - [User Management](#user-management)
   - [Films](#films)
   - [Orders](#orders)
   - [Shopping Cart](#shopping-cart)
4. [Demo](#demo)
5. [Build Instructions](#build-instructions)
6. [Dependencies](#dependencies)

## Introduction <a name="introduction"></a>

This project is an online store application built with Java Spring Boot for the backend and Angular for the frontend. It provides a simple visualization of a functional REST API for managing an online store. The backend REST API is fully implemented and functional, while the frontend implementation is still under development.

## Features <a name="features"></a>

- User Registration: Users can register in the application by providing their first name, last name, email, and password.
- User Login: Registered users can log into the application using their email and password.
- Product Management: The application provides a list of available products in the store, allowing users to view and select products.
- Shopping Cart: Users can add selected products to their shopping cart for easy checkout.
- Sorting: The application offers sorting options for products, allowing users to sort products by price, name, or other criteria.
- Administrative Panel: Adding an administrative panel for managing store settings, products, and user accounts.
- User Profile Editing: Allowing users to edit their profiles and update their personal information.
- Adding New Products: Implementing functionality to allow administrators to add new products to the store.

## REST API <a name="rest-api"></a>

The application utilizes a RESTful API for seamless communication between the frontend and backend. Here are the main endpoints of the REST API:

### User Management <a name="user-management"></a>

1. **User Registration**
   - Endpoint: `POST /api/v1/register`
   - Description: Allows users to register in the application.

2. **User Login**
   - Endpoint: `POST /api/v1/login`
   - Description: Allows users to log into the application.

3. **Logout**
   - Endpoint: `POST /api/v1/logout`
   - Description: Allows users to log out of the application.

### Films <a name="films"></a>

- **Get All Films**
  - Endpoint: `GET /api/films`
  - Description: Retrieves a list of films.
  - Parameters:
    - `limit` (optional): Limits the number of films returned (default: 20).

- **Get Single Film**
  - Endpoint: `GET /api/films/{id}`
  - Description: Retrieves a single film by its ID.

- **Get Film Categories**
  - Endpoint: `GET /api/films/categories`
  - Description: Retrieves a list of all film categories.

- **Get Films by Category**
  - Endpoint: `GET /api/films/category/{category}`
  - Description: Retrieves films belonging to a specific category.
  - Parameters:
    - `limit` (optional): Limits the number of films returned (default: 20).

- **Add Film**
  - Endpoint: `POST /api/films`
  - Description: Adds a new film to the database.
  - Request Body: FilmDTO object representing the film to be added.

- **Edit Film**
  - Endpoint: `PUT /api/films/{id}`
  - Description: Updates an existing film.
  - Parameters:
    - `id`: ID of the film to be edited.
  - Request Body: FilmDTO object representing the updated film.

- **Delete Film**
  - Endpoint: `DELETE/api/films/{id}`
  - Description: Deletes a film by its ID.

### Orders <a name="orders"></a>

- **Get All Orders**
  - Endpoint: `GET /api/orders`
  - Description: Retrieves a list of all orders.

- **Get User Orders**
  - Endpoint: `GET /api/orders/{userId}`
  - Description: Retrieves orders associated with a specific user.
  - Parameters:
    - `userId`: ID of the user.

- **Place Order**
  - Endpoint: `POST /api/orders/{userId}`
  - Description: Places an order for a user with the specified address.
  - Parameters:
    - `userId`: ID of the user.
  - Request Body: AddressDto object representing the address for the order.

- **Delete Orders**
  - Endpoint: `DELETE /api/orders/{userId}`
  - Description: Deletes all orders associated with a specific user.
  - Parameters:
    - `userId`: ID of the user.

### Shopping Cart <a name="shopping-cart"></a>

- **Get User Shopping Cart**
  - Endpoint: `GET /api/cart/{userId}`
  - Description: Retrieves the shopping cart for a specific user.
  - Parameters:
    - `userId`: ID of the user.

- **Add Film to Cart Item**
  - Endpoint: `POST /api/cart/{userId}/{filmId}`
  - Description: Adds a film to the user's cart item.
  - Parameters:
    - `userId`: ID of the user.
    - `filmId`: ID of the film.
  - Request Body: CartItemDto object representing the cart item.

- **Edit Quantity**
  - Endpoint: `PUT /api/cart/{cartItemId}`
  - Description: Updates the quantity of a cart item.
  - Parameters:
    - `cartItemId`: ID of the cart item.
  - Request Body: CartItemDto object representing the updated quantity.

- **Delete Shopping Cart**
  - Endpoint: `DELETE /api/cart/{userId}`
  - Description: Deletes the shopping cart for a specific user.
  - Parameters:
    - `userId`: ID of the user.

- **Delete Cart Item from Shopping Cart**
  - Endpoint: `DELETE /api/cart/{cartId}/{cartItemId}`
  - Description: Deletes a specific cart item from the shopping cart.
  - Parameters:
    - `cartId`: ID of the shopping cart.
    - `cartItemId`: ID of the cart item.

- **Delete All Items from Shopping Cart**
  - Endpoint: `DELETE /api/cart/{cartId}/clear`
  - Description: Deletes all items from the shopping cart.
  - Parameters:
    - `cartId`: ID of the shopping cart.

Please note that the above API endpoints represent the basic functionality of the application and may require additional authentication or authorization for certain operations.

Security and authentication in the REST API are implemented using JSON Web Tokens (JWT). A JWT token is generated during the login process and is required for authentication in certain endpoints.

## Demo <a name="demo"></a>
![Demo Video](<https://gist.github.com/KamilWasiewicz/5fcea2ea38ae5a7127688dcdc7983aab>)
## Build Instructions <a name="build-instructions"></a>

To build and run the application locally, follow these steps:

1. Clone the repository.
2. Set up the backend by navigating to the `backend` directory and running the necessary commands (e.g., `mvn install`, `mvn spring-boot:run`).
3. Set up the frontend by navigating to the `frontend` directory and running the necessary commands (e.g., `npm install`, `ng serve`).
4. Access the application in your web browser at `http://localhost:4200`.

## Dependencies <a name="dependencies"></a>

The project utilizes the following dependencies:

- Java Spring Boot for building the backend REST API.


- Angular for the frontend user interface.
- JWT (JSON Web Tokens) for secure authentication.

Please ensure that you have the necessary dependencies installed before running the application.

**Note:** The backend implementation of the application is complete and fully functional, while the frontend implementation is still under development.

Enjoy exploring and using the Online Store Application!
