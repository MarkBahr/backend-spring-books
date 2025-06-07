# Book Inventory Application

## Project Description

Backend for books inventory tracker app using Java, JWT and Spring Security for auth and PostgreSQL as database.

## Tools & Technologies

## User Stories

### Summary

- Sign up
- Sign in
- Create book
- Update book
- Delete book
- Get all books
- Get book by id

### User Story 1: Register User

registerUser()

- Endpoint: /api/auth/public/signup
- Method: POST
- Data:

Example:

```json
{
  "firstName": "Joe",
  "lastName": "Smith",
  "email": "js@testmail.com",
  "password": "password"
}
```

Upon submission, you get a JWT token in response

### User Story 2: User Login

authenticateUser()

- Endpoint: POST request to /api/auth/public/signin
- Json request body

Example:

```json
{
  "email": "js@testmail.com",
  "password": "password"
}
```

Upon login, you get a JWT token. All of the remaining endpoints are protected, and those are related to various categories and transactions.

For each of the remaining endpoints, pass the token in the header.

## ERD
