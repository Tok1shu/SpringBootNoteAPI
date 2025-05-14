# Spring Boot Note Api

I'm testing spring boot, and it might become my main framework for building APIs

## Endpoits 
### `GET /api/notes` - Get all notes

### `GET /api/note/{id}` - Find note by id

### `POST /api/note` - Add a new note
```
{
    "name":"What should I buy?",
    "text":"Tanya's note"
}
```

### `PUT /note/{id}` - Update note
```
{
    "name":"What should I buy?",
    "text":"I bought the apple!"
}
```

### `DELETE /note/{id}` - Delete note
