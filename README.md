# Java REST API Client with JDK Http Client and JUnit Tests

## Overview

This project demonstrates how to interact with a REST API using the JDK Http Client and how to write JUnit test cases to verify the functionality of the API client. We use a sample Todo API from `https://jsonplaceholder.typicode.com/todos` to showcase the concepts.

## Project Structure

The project is structured as follows:

- **`Application.java`**: Contains the main application logic to interact with the Todo API.
- **`TodoClient.java`**: The client class responsible for making HTTP requests to the Todo API.
- **`Todo.java`**: A record class representing a Todo item.
- **`TodoNotFoundException.java`**: A custom exception thrown when a Todo item is not found.
- **`TodoClientTest.java`**: JUnit test cases for testing the `TodoClient` class.

## Dependencies

- **Jackson**: For JSON parsing and serialization.
- **JUnit 5**: For unit testing.

## Code Explanation

### `Todo.java`

A simple record class that represents a Todo item with four fields:

- **`userId`**: The ID of the user associated with the Todo.
- **`id`**: The ID of the Todo item.
- **`title`**: The title of the Todo item.
- **`completed`**: A boolean indicating if the Todo item is completed.

### `TodoClient.java`

This class contains methods to interact with the Todo API:

- **`findAll()`**: Retrieves a list of all Todo items.
- **`findById(int id)`**: Retrieves a single Todo item by its ID.
- **`create(Todo todo)`**: Creates a new Todo item.
- **`update(Todo todo)`**: Updates an existing Todo item.
- **`delete(Todo todo)`**: Deletes a Todo item by its ID.

### `Application.java`

The main class that demonstrates how to use `TodoClient` to fetch and print Todo items. It also prints a message indicating it's time to check the tests.

### `TodoNotFoundException.java`

A custom exception thrown when a Todo item is not found by its ID.

### `TodoClientTest.java`

JUnit test cases for the `TodoClient` class:

- **`findAll()`**: Tests retrieving all Todo items.
- **`shouldReturnTodoGivenValidId()`**: Tests retrieving a Todo item by a valid ID.
- **`shouldThrowNotFoundExceptionGivenInvalidId()`**: Tests that a `TodoNotFoundException` is thrown for an invalid ID.
- **`shouldCreateNewTodo()`**: Tests creating a new Todo item.
- **`shouldUpdateExistingNewTodo()`**: Tests updating an existing Todo item.
- **`shouldDeleteExistingNewTodo()`**: Tests deleting a Todo item.
