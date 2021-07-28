package guichafy.sample.todo;


import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class StorageTodo {

    private Map<Long, TodoDTO> database = new HashMap<>();

    public synchronized void save(TodoDTO todoDTO){
        this.database.put(todoDTO.getId(), todoDTO);
    }

    public List<Map.Entry<Long, TodoDTO>> getAll(){
        return this.database.entrySet().stream().collect(Collectors.toList());
    }
}
