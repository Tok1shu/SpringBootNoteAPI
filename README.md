# Spring Boot Note Api

I'm testing spring boot, and it might become my main framework for building APIs

## Authentication Endpoints
Handle user registration and login. All return JWT tokens on success.

### `POST /api/auth/register` - Register a new user
Request body:
```json
{
    "username":"Tokishu",
    "password":"SuperSecretPassword"
}
```

### `POST /api/auth/login` - Authorize
Request body:
```json
{
    "username":"Tokishu",
    "password":"SuperSecretPassword"
}
```

### `/api/auth/new-root` - Initial setup endpoint to create a root user (e.g. admin), can only be used once.

## Note Endpoints
All endpoints below require a valid Bearer token in the `Authorization` header.
`Authorization: Bearer <your_token>`

### `GET /api/notes` - Get all notes

### `GET /api/note/{id}` - Find note by id

### `POST /api/note` - Add a new note
Request body:
```json
{
    "name":"What should I buy?",
    "text":"Tanya's note"
}
```

### `PUT /api/note/{id}` - Update note
Request body:
```json
{
    "name":"What should I buy?",
    "text":"I bought the apple!"
}
```

### `DELETE /api/note/{id}` - Delete note
