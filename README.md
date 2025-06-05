# Book Inventory Application

## Project Description

Backend for books inventory tracker app using Java, JWT and Spring Security for auth and PostgreSQL

## Tools & Technologies

## User Stories

### User Story 1: Register User

registerUser()

- Endpoint: /api/users/register
- Method: POST
- Data:

```json
{
  "firstName": "Joseph",
  "lastName": "Smith",
  "email": "js@testmail.com",
  "password": ""
}
```

Upon submission, you get a JWT token in response

### User Story 2: User Login

loginUser

- Endpoint: POST request to /api/users/login
- Json request body

```json
{
  "email": "jo@testmail.com",
  "password": ""
}
```

Upon login, you get a JWT token. All of the remaining endpoints are protected, and those are related to various categories and transactions.

For each of the remaining endpoints, pass the token in the header.

## ERD
