package com.lalbr.service;

import com.lalbr.model.CategoryModel;
import com.lalbr.model.TaskModel;
import com.lalbr.repository.CategoryRepository;
import com.lalbr.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TaskRepository taskRepository;

    public List<CategoryModel> getAllCategory(){
        return categoryRepository.findAll();
    }

    public List<TaskModel> getAllTask(){
        return taskRepository.findAll();
    }


    public List<TaskModel> getAllTaskByCategory(CategoryModel category){
        return taskRepository.findAllByCategory(category);
    }

    public TaskModel saveTaskModel(TaskModel taskModel){
        return taskRepository.save(taskModel);
    }

    public CategoryModel saveCategoryModel(CategoryModel categoryModel){
        return categoryRepository.save(categoryModel);
    }
}
