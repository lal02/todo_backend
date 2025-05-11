package com.lalbr.repository;

import com.lalbr.model.CategoryModel;
import com.lalbr.model.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskModel,Long> {

    List<TaskModel> findAllByCategory(CategoryModel category);
}
