# Spring Microservices Project

This project is my first project developed using Spring Boot. It is a Spring-based microservices architecture consisting of three microservices:

1. **Portal** - Acts as a proxy and delegates requests to the other two microservices.
2. **UserAccountInfo** - Manages user account details.
3. **Authentication** - Manages user authentication, sign-in, sign-up, and token refresh functionality.

The **Portal** microservice serves as the central point for incoming requests and routes them to the appropriate microservices using `FeignClient`, `ApacheHttpClient`, and `RestTemplate`.

## Project Structure

The project is divided into three main components:
- **Portal**: Uses Feign, RestTemplate, and ApacheHttpClient to communicate with other services.
- **UserAccountInfo**: Provides endpoints for managing user account information.
- **Authentication**: Handles user authentication, including sign-in, sign-up, and token refresh operations.
![System Architecture Diagram](./systemArchitecture%20Diagram.png)

---

## API Documentation

### Portal Microservice

The **Portal** service communicates with the `UserAccountInfo` and `Authentication` microservices by acting as a gateway. Here are the API methods for each client:

---

### User Account Information Client (FeignClient)

#### `@GetMapping` - `List<AddressDTO> getAllAddresses()`
Fetches a list of all addresses.

#### `@GetMapping("/{id}")` - `AddressDTO getAddressById(Long id)`
Fetches an address by its unique identifier.

#### `@GetMapping("/search")` - `Page<AddressDTO> searchAddresses(String street, Pageable pageable)`
Searches addresses by street name with pagination.

#### `@GetMapping("/search-addresses-by-user-account-id")` - `Page<AddressDTO> searchAddressesByUserAccountId(Long userAccountId, Pageable pageable)`
Searches addresses by user account ID with pagination.

#### `@PostMapping` - `AddressDTO createAddress(AddressDTO addressDto)`
Creates a new address.

#### `@PutMapping` - `AddressDTO updateAddress(AddressDTO addressDto)`
Updates an existing address.

#### `@DeleteMapping("/{id}")` - `AddressDTO deleteAddress(Long id)`
Deletes an address by its ID.

---

### Authentication Client (ApacheHttpClient)

#### `POST /sign-in` - `JWTResponseDTO signIn(UserCredentialsDTO userCredentialsDTO)`
Authenticates a user and returns a JWT response.

#### `POST /sign-up` - `UserCredentialsDTO signUp(UserCredentialsDTO userCredentialsDTO)`
Registers a new user and returns their credentials.

#### `POST /refresh-token` - `JWTResponseDTO refreshToken(RefreshTokenDTO refreshTokenDTO)`
Refreshes the JWT token for an authenticated user.

---

### User Account Client (RestTemplate)

#### `GET /` - `List<UserAccountDTO> getAllUserAccounts()`
Fetches all user accounts.

#### `GET /{id}` - `UserAccountDTO getUserAccountById(Long id)`
Fetches a user account by its unique identifier.

#### `GET /search` - `Page<UserAccountDTO> searchByName(String name, Pageable pageable)`
Searches user accounts by name with pagination support.

#### `POST /` - `UserAccountDTO createUserAccount(UserAccountDTO userAccountDto)`
Creates a new user account.

#### `PUT /` - `UserAccountDTO updateUserAccount(UserAccountDTO userAccountDto)`
Updates an existing user account.

#### `DELETE /{id}` - `void deleteUserAccount(Long id)`
Deletes a user account by its ID.

---

## Project Setup

### Prerequisites
- Java 21
- Maven
- Docker

## Technologies Used

- **Java 21**
- **Spring Boot 3**
- **Spring Cloud OpenFeign**
- **Spring Security**
- **Spring Data JPA**
- **Lombok**
- **Gradle**
- **Swagger**
- **RabbitMQ**
- **Quartz**
- **JSON Web Token (JWT)**
- **Docker**
- **Liquibase**
- **PostgreSQL**



![System Architecture Diagram](./systemArchitectureDiagram.png)
![RabbitMQ Diagram](./rabbitMQ.png)
