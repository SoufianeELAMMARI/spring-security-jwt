package org.sid.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.sid.model.entities.Task;


@Data  @AllArgsConstructor  @NoArgsConstructor @ToString
public class TaskDto {

    private Long id;
    private String name;
    private String status;

    public TaskDto(Task task){
        this.id=task.getId();
        this.name=task.getName();
        this.status=task.getStatus();
    }




}
