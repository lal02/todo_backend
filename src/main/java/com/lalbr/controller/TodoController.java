package com.lalbr.controller;

import com.lalbr.model.CategoryModel;
import com.lalbr.model.TaskModel;
import com.lalbr.repository.CategoryRepository;
import com.lalbr.repository.TaskRepository;
import com.lalbr.service.TodoService;
import com.lalbr.util.Priority;
import com.lalbr.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TodoController {

    @Autowired
    TodoService todoService;

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/category/all")
    public List<CategoryModel> getAllCategory(){
        return todoService.getAllCategory();
    }

    /**
     *
     * @param order priority order (desc = Highest -> lowest)
     * @return
     */
    @GetMapping("/task")
    public List<TaskModel> getAllTask(
            @RequestParam(required = false, defaultValue = "desc") String order,
            @RequestParam(required = false, defaultValue = "OPEN") String status,
            @RequestParam(required = false) Long categoryId){

        List<TaskModel> list = new ArrayList<>();
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
        CategoryModel categoryModel = categoryRepository.findById(categoryid).get();
        return todoService.getAllTaskByCategory(categoryModel);
    }

    @PostMapping("/task")
    public void addTask(@ModelAttribute TaskModel taskModel){
        todoService.saveTaskModel(taskModel);
    }

    @PostMapping("/category")
    public void addCategory(@ModelAttribute CategoryModel categoryModel){
        todoService.saveCategoryModel(categoryModel);
    }

    @GetMapping("/test")
    public void test(){

        //CategoryModel categoryModel = new CategoryModel();
        //categoryModel.setName("testcategory3");
        //categoryModel.setPriority(Priority.HIGH);
        //todoService.saveCategoryModel(categoryModel);

        TaskModel taskModel = new TaskModel();
        taskModel.setName("testtask66");
        taskModel.setPriority(Priority.MEDIUM);
        taskModel.setCategory(categoryRepository.findById(1L).get());
        taskModel.setStatus(Status.OPEN);
        taskModel.setDeadline(LocalDateTime.now());
        taskModel.setNotificationLeadTime(0);
        taskModel.setRepetitionDelay(0);
        taskModel.setNote("das ist eine notiz");
        todoService.saveTaskModel(taskModel);

    }
}
