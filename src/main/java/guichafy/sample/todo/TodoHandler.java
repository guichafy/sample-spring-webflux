package guichafy.sample.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class TodoHandler {

    @Autowired
    private StorageTodo storageTodo;

    private final WebClient client = WebClient.create("https://jsonplaceholder.typicode.com/todos/");
    private final ParameterizedTypeReference todoListType = new ParameterizedTypeReference<TodoDTO>() {};
    private final String ID_TODO = "id";

    public Mono<ServerResponse> get(ServerRequest request) {
        String id = request.pathVariable(ID_TODO);

        //Async
        var todo = client.get()
                .uri(id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(TodoDTO.class)
                .doOnNext( todoDTO -> {
                    storageTodo.save(todoDTO);
                })
                .log();
        //Sync
        var storage = storageTodo.getAll();
        System.out.println(storage);

    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(todo, TodoDTO.class);
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {

        var todos = client.get().retrieve().bodyToFlux(todoListType);
         return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(todos, todoListType);
    }

}
