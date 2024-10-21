
# API Documentation

## 1. BookController

### Base URL: /books

#### 1.1. Get All Books
- **Endpoint**: GET /books
- **Description**: Retrieves a list of all books.
- **Response**:
    - **Status**: 200 OK
    - **Body**: List<BookDTO>

#### 1.2. Get Book by ID
- **Endpoint**: GET /books/{bookId}
- **Description**: Retrieves a specific book by its ID.
- **Path Variable**:
    - bookId (UUID) - The unique identifier of the book.
- **Response**:
    - **Status**: 200 OK
    - **Body**: BookDTO

#### 1.3. Get Recommendations for a User
- **Endpoint**: GET /books/recomendations/{libraryUserId}
- **Description**: Retrieves book recommendations for a specific library user.
- **Path Variable**:
    - libraryUserId (UUID) - The unique identifier of the library user.
- **Response**:
    - **Status**: 200 OK
    - **Body**: List<BookDTO>

#### 1.4. Create a New Book
- **Endpoint**: POST /books
- **Description**: Creates a new book.
- **Request Body**: BookCreateDTO
- **Response**:
    - **Status**: 201 Created
    - **Body**: BookCreateDTO

#### 1.5. Update a Book
- **Endpoint**: PUT /books/{bookId}
- **Description**: Updates an existing book.
- **Path Variable**:
    - bookId (UUID) - The unique identifier of the book.
- **Request Body**: BookCreateDTO
- **Response**:
    - **Status**: 200 OK
    - **Body**: BookDTO

#### 1.6. Delete a Book
- **Endpoint**: DELETE /books/{bookId}
- **Description**: Deletes a book by its ID.
- **Path Variable**:
    - bookId (UUID) - The unique identifier of the book.
- **Response**:
    - **Status**: 204 No Content

---

## 2. LibraryUserController

### Base URL: /users

#### 2.1. Get All Library Users
- **Endpoint**: GET /users
- **Description**: Retrieves a list of all library users.
- **Response**:
    - **Status**: 200 OK
    - **Body**: List<LibraryUserDTO>

#### 2.2. Get Library User by ID
- **Endpoint**: GET /users/{libraryUserId}
- **Description**: Retrieves a specific library user by their ID.
- **Path Variable**:
    - libraryUserId (UUID) - The unique identifier of the library user.
- **Response**:
    - **Status**: 200 OK
    - **Body**: LibraryUserDTO

#### 2.3. Find Library User by Email
- **Endpoint**: POST /users/email
- **Description**: Retrieves a library user by their email address.
- **Request Body**: LibraryUserDTO (with email field)
- **Response**:
    - **Status**: 200 OK
    - **Body**: UUID (The ID of the library user)

#### 2.4. Create a New Library User
- **Endpoint**: POST /users
- **Description**: Creates a new library user.
- **Request Body**: LibraryUserCreateDTO
- **Response**:
    - **Status**: 201 Created
    - **Body**: LibraryUserCreateDTO

#### 2.5. Update a Library User
- **Endpoint**: PUT /users/{libraryUserId}
- **Description**: Updates an existing library user.
- **Path Variable**:
    - libraryUserId (UUID) - The unique identifier of the library user.
- **Request Body**: LibraryUserDTO
- **Response**:
    - **Status**: 200 OK
    - **Body**: LibraryUserDTO

#### 2.6. Delete a Library User
- **Endpoint**: DELETE /users/{libraryUserId}
- **Description**: Deletes a library user by their ID.
- **Path Variable**:
    - libraryUserId (UUID) - The unique identifier of the library user.
- **Response**:
    - **Status**: 204 No Content

---

## 3. LoanController

### Base URL: /loans

#### 3.1. Get All Loans
- **Endpoint**: GET /loans
- **Description**: Retrieves a list of all loans, optionally filtered by library user ID.
- **Request Parameter**:
    - libraryUserId (UUID, optional) - The unique identifier of the library user.
- **Response**:
    - **Status**: 200 OK
    - **Body**: List<LoanDTO>
- **Error Response**:
    - **Status**: 400 Bad Request if libraryUserId is required but not provided.

#### 3.2. Get Loan by ID
- **Endpoint**: GET /loans/{loanId}
- **Description**: Retrieves a specific loan by its ID.
- **Path Variable**:
    - loanId (UUID) - The unique identifier of the loan.
- **Response**:
    - **Status**: 200 OK
    - **Body**: LoanDTO

#### 3.3. Create a New Loan
- **Endpoint**: POST /loans
- **Description**: Creates a new loan.
- **Request Body**: LoanCreateDTO
- **Response**:
    - **Status**: 201 Created
    - **Body**: LoanCreateDTO

#### 3.4. Update a Loan
- **Endpoint**: PUT /loans/{loanId}
- **Description**: Updates an existing loan.
- **Path Variable**:
    - loanId (UUID) - The unique identifier of the loan.
- **Response**:
    - **Status**: 200 OK
    - **Body**: LoanDTO

---

## Conclusion

This documentation provides a comprehensive overview of the API endpoints available in the library application, detailing the request methods, expected inputs, outputs, and possible HTTP status responses. Each controller manages a specific aspect of the library system, enabling operations for books, library users, and loans efficiently.