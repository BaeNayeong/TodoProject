package com.example.todoproject.dto;

import com.example.todoproject.model.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDto {

    private String id;
    private String title;
    private boolean done;

    public TodoDto(final TodoEntity entity){
        this.id=entity.getId();
        this.title=entity.getTitle();
        this.done=entity.isDone();
    }
}
