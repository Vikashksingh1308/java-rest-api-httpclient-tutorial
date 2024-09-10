package todo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class TodoClient {
    private final String BASE_URL = "https://jsonplaceholder.typicode.com/todos";
    /**
     * Instance of the HTTP client, this is what is going to make calls to other services
     *
     * We are creating this here cause we'll use in every method like
     * - get a list of to-dos
     * - get a single to-do
     */

    private final HttpClient client; //this is basically the initialization and instance is being created in the constructor below.
    private final ObjectMapper objectMapper; //This comes with jackson
    /**
     * Idea behind doing this:
     * I wanna go ahead and initialize that, create an instance of it in the constructor and the way that I'll do that is
     * I'll just say client is equal to
    */
    //Constructor
    public TodoClient() {
        client = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();
            // Now we can start to convert what we are getting back as a string into some JSON data.
            // To do this we are gonna need somthing that represents a to-do in our system
    }
    /**
     * After the instance of httpclient is created, first thing we want to do is get a list of all the to-dos in the system.
     * To do this, we have to create a method called 'find all'
     *
     * After this, we need to create a request, so the client is going to send a request and get a response.
     *
     * How do we setup a request call?
     * The way we get a request is through the Http request class
     */

    /*
    public String findAll() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder() // newBuilder syntax to create a new instance of the request
                .uri(URI.create(BASE_URL)) //after creating the instance we set a URI, but we cant just send a string in here because we've created this as a string and it expects a URI. So we can use the URI class to create a URI from this string here's my base URL
                .GET() //Now we need to mention what type of request it is
                .build(); //we are calling build on it to get an instance of the request back
        */

    public List<Todo> findAll() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder() // newBuilder syntax to create a new instance of the request
                .uri(URI.create(BASE_URL)) //after creating the instance we set a URI, but we cant just send a string in here because we've created this as a string and it expects a URI. So we can use the URI class to create a URI from this string here's my base URL
                .GET() //Now we need to mention what type of request it is
                .build(); //we are calling build on it to get an instance of the request back

        /* Breakdown of the above code
          1. HttpResponse<String> response
           - This declares a variable 'response' of type HttpResponse<String>.
           - HttpResponse is a class in Java that represents the response received from an HTTP request.
           - The <String> part means that the response body will be returned as a String.
             This is a generic type, so you can specify the type of the response body (like String, byte[], etc.).

          2. client.send(request, HttpResponse.BodyHandlers.ofString())
          client.send() is a method that sends the request (which is an HttpRequest object) synchronously. This means it waits for the server to respond before proceeding.
          It uses two arguments:
          request: This is an instance of HttpRequest that contains the details of the HTTP request you're sending (like the URI, headers, request method, etc.).
          HttpResponse.BodyHandlers.ofString(): This specifies how the response body should be handled when it is received.

          Breaking it down further:
          client.send():
          This sends the HTTP request using an HttpClient instance, which manages HTTP communications. It's part of the java.net.http package in Java 11 and later.
          It performs a synchronous HTTP call, meaning the program waits for the request to complete and for a response to be returned from the server before moving to the next line of code.

          HttpResponse.BodyHandlers.ofString():
          This tells the HttpClient how to process the response body.
          ofString() is a built-in body handler that converts the response body into a String. It reads the response's body bytes and converts them into a string using the default character set (usually UTF-8).

          What happens when this line is executed:
          The client.send() method sends the HTTP request defined by request to the server.
          The server processes the request and sends back an HTTP response.
          HttpResponse.BodyHandlers.ofString() reads the response body as a string and stores it in the response object.
          The response object now holds both:
          The HTTP status code (e.g., 200, 404, etc.).
          The body of the response as a string (e.g., JSON, HTML, plain text).

          For example, if the response body from the server is some JSON data, HttpResponse.BodyHandlers.ofString() would convert it into a String, and you'd be able to access it with response.body().

          Example:
          HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
          System.out.println(response.body()); // This will print the body of the response.
          System.out.println(response.statusCode()); // This will print the HTTP status code.

          In summary, this line sends an HTTP request and retrieves the response, with the response body being processed and returned as a string.
         */

        /**
         * Now we have a request that we can send off using the client
        */

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //So now we have a response, next we wanna return that raw string back to whoever is calling this, back to the caller.
        //return response.body(); //this will return the string

        //To make a list from raw string
        return objectMapper.readValue(response.body(), new TypeReference<>() {});
    }

    public Todo findById(int i) throws IOException, InterruptedException, TodoNotFoundException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + i))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        if(response.statusCode() == 404) {
            throw new TodoNotFoundException("todo.Todo not found");
        }

        return objectMapper.readValue(response.body(), Todo.class);
    }

    public HttpResponse<String> create(Todo todo) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(todo)))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> update(Todo todo) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + todo.id()))
                .PUT(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(todo)))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> delete(Todo todo) throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + todo.id()))
                .DELETE()
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }


}
