package com.taskflow.service;

import com.taskflow.dto.request.TaskRequest;
import com.taskflow.dto.response.TaskResponse;
import com.taskflow.entity.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    TaskResponse  createTask(TaskRequest request, Long userId);
    TaskResponse  getTask(Long taskId, Long userId, boolean isAdmin);
    Page<TaskResponse> getAllTasks(Long userId, TaskStatus status, Pageable pageable, boolean isAdmin);
    TaskResponse  updateTask(Long taskId, TaskRequest request, Long userId, boolean isAdmin);
    void          deleteTask(Long taskId, Long userId, boolean isAdmin);
}
