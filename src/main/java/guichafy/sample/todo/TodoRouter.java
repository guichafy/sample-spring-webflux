package guichafy.sample.todo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;



@Configuration
public class TodoRouter {

    @Bean
    public RouterFunction<ServerResponse> route(TodoHandler todoHandler) {

        return RouterFunctions.route()
                .GET("/todos/{id}", todoHandler::get)
                .GET("/todos",  todoHandler::getAll)
                .build();


    }
}
