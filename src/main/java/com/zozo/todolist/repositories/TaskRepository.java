package com.zozo.todolist.repositories;

import com.zozo.todolist.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
