package org.sid.web;
import org.sid.model.dto.TaskDto;
import org.sid.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/tasksManager")
public class TaskController {

    @Autowired TaskService taskService;

    @GetMapping("/allTasks")
    public Map<String ,Object> getAllTask(){

        return taskService.getAllTask();
    }

    @PostMapping("/saveTasks")
    public Map<String ,Object> saveTask(@RequestBody TaskDto taskDto){

        return taskService.SaveTask(taskDto);
    }


}
