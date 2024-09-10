import todo.Todo;
import todo.TodoClient;

import java.io.IOException;
import java.util.List;

public class Application {
    public static void main(String[] args) throws IOException, InterruptedException {
        TodoClient todoClient = new TodoClient();
        //System.out.println(todoClient.findAll());

        /* Now that we have received the data in String format, the next we want to do is to turn it into a type like list.

        To do this we have to bring something that is capable of turning JSON data into types. This is where 'Jackson' comes in.
        Jackson is a library that turn raw string data into type like list.
         */
        List<Todo> todos = todoClient.findAll();
        System.out.println(todos);

        System.out.println("Time to check out the tests!");

    }
}
