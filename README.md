This is a problem-solving spring boot project - you need to identify the problems and solve them.
Finally, you need to ensure that you can send requests to the server and get responses back:
- `GET /todos` to get all 
- `POST /todos` to create a new todo item
  - Ensure that if `X-TOKEN` header is not present in the request or doesn't match a predefined value, then return a 401 Unauthorized response.
