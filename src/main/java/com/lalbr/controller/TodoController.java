package com.lalbr.controller;

import com.lalbr.model.CategoryModel;
import com.lalbr.model.TaskModel;
import com.lalbr.repository.CategoryRepository;
import com.lalbr.repository.TaskRepository;
import com.lalbr.service.TodoService;
import com.lalbr.util.Priority;
import com.lalbr.util.Status;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@RestController
public class TodoController {

    @Autowired
    TodoService todoService;

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/category/all")
    public List<CategoryModel> getAllCategory(){
        return todoService.getAllCategory();
    }

    /**
     *
     * @param status status which is to be filtered (only the given status)
     * @param order priority order (desc = Highest -> lowest)
     * @return list of all tasks
     */
    @GetMapping("/task")
    public List<TaskModel> getAllTask(
            @RequestParam(required = false, defaultValue = "desc") String order,
            @RequestParam(required = false, defaultValue = "OPEN") String status,
            @RequestParam(required = false) Long categoryId){
        List<TaskModel> list;
        if(categoryId == null){
            list = todoService.getAllTask();
        }
        else{
            list = todoService.getAllTaskByCategory(categoryRepository.findById(categoryId).get());
        }
        if(order.equals("desc")){
            list.sort(Comparator.comparingInt(a -> a.getPriority().getOrder()));
            Collections.reverse(list);
        }
        else if(order.equals("asc")){
            //ascending priority
            list.sort(Comparator.comparingInt(a -> a.getPriority().getOrder()));
        }
        if(status.equals("open") || status.equals("onhold") || status.equals("closed")){
            Status filter;
            switch(status){
                case "closed" -> filter = Status.CLOSED;
                case "onhold" -> filter = Status.ONHOLD;
                default -> filter = Status.OPEN;
            }
            list = list.stream().filter(taskModel -> taskModel.getStatus().equals(filter)).collect(Collectors.toList());
        }
        return list;
    }

    @GetMapping("/task/bycategory/{categoryid}")
    public List<TaskModel> getAllTaskByCategory(@PathVariable Long categoryid){
        CategoryModel categoryModel = categoryRepository.findById(categoryid).orElseThrow(() -> new EntityNotFoundException("Category not found"));
        return todoService.getAllTaskByCategory(categoryModel);
    }

    @PostMapping("/task")
    public void createTask(@RequestBody TaskModel taskModel){
        todoService.saveTaskModel(taskModel);
    }

    @DeleteMapping("/category/{id}")
    public void deleteCategory(@PathVariable Long id){
        List<TaskModel> allTasks = taskRepository.findAll();
        AtomicBoolean inUse = new AtomicBoolean(false);
        allTasks.forEach(taskModel -> {
            if(taskModel.getId().equals(id)){
                inUse.set(true);
            }
        });
        if(!inUse.get()){
            categoryRepository.deleteById(id);
        }
    }

    @DeleteMapping("/task/{id}")
    public void deleteTask(@PathVariable Long id){
        taskRepository.deleteById(id);
    }

    @PostMapping("/category")
    public void createCategory(@RequestBody CategoryModel categoryModel){
        todoService.saveCategoryModel(categoryModel);
    }

    @PatchMapping("/category/{categoryid}")
    public void editCategory(@PathVariable Long categoryid,@RequestBody Map<String, Object> updates) throws Exception {
        CategoryModel currentModel = categoryRepository.findById(categoryid).orElseThrow(() -> new Exception("Path ID doesnt match categoryModel id which is edited"));
        updates.forEach((key,value) -> {
            switch(key){
                case "name" -> currentModel.setName((String) value);
                case "priority" -> currentModel.setPriority(Priority.valueOf(String.valueOf(value)));
            }
        });
        categoryRepository.save(currentModel);
    }

    @PatchMapping("/task/{taskid}")
    public void editTask(@PathVariable Long taskid, @RequestBody Map<String,Object> updates) throws Exception {
        TaskModel currentModel = taskRepository.findById(taskid).orElseThrow(() -> new Exception("Path ID doesnt match taskModel id which is edited"));
        updates.forEach((key,value) -> {
           if(value == null) return;
           switch(key){
               case "name" -> currentModel.setName((String) value);
               case "priority" -> currentModel.setPriority(Priority.valueOf(String.valueOf(value)));
               case "category" -> currentModel.setCategory(categoryRepository.findById(Long.valueOf((String) value)).orElse(null));
               case "status" -> currentModel.setStatus(Status.valueOf(String.valueOf(value)));
               case "deadline" -> currentModel.setDeadline(LocalDateTime.parse((String) value));
               case "notificationLeadTime" -> currentModel.setNotificationLeadTime((int) value);
               case "repetitionDelay" -> currentModel.setRepetitionDelay((int) value);
               case "note" -> currentModel.setNote((String) value);
           }
        });
        taskRepository.save(currentModel);
    }

}
