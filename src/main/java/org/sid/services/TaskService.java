package org.sid.services;


import org.sid.dao.TaskRepository;
import org.sid.model.dto.TaskDto;
import org.sid.model.entities.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Transactional
    public Map<String, Object> getAllTask(){
        Map<String,Object> map=new HashMap<String,Object>();
        try {
              map.put("list",taskRepository.findAll());
            map.put("success",true);

        } catch (Exception e) {
            map.put("success",false);
            e.printStackTrace();
        }

        return map;
    }
    @Transactional
    public Map<String, Object> SaveTask(TaskDto taskDTO){
        Map<String,Object> map=new HashMap<String,Object>();
        Task task=new Task();
        try {
            task.setName(taskDTO.getName());
            task.setStatus(taskDTO.getStatus());
            task=taskRepository.saveAndFlush(task);

        } catch (Exception e) {
            map.put("message", "erreur");
            map.put("success",false);
        }
        taskDTO=new TaskDto(task);
        map.put("task", taskDTO);
        map.put("success",true);
        return map;
    }


}
