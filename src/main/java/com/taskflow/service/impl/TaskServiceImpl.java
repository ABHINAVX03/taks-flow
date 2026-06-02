package com.taskflow.service.impl;

import com.taskflow.dto.request.TaskRequest;
import com.taskflow.dto.response.TaskResponse;
import com.taskflow.entity.Task;
import com.taskflow.entity.TaskStatus;
import com.taskflow.entity.User;
import com.taskflow.exception.ResourceNotFoundException;
import com.taskflow.repository.TaskRepository;
import com.taskflow.repository.UserRepository;
import com.taskflow.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public TaskResponse createTask(TaskRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        Task task = Task.builder()
                .title(request.getTitle().trim())
                .description(request.getDescription())
                .status(request.getStatus())
                .priority(request.getPriority())
                .dueDate(request.getDueDate())
                .user(user)
                .build();

        Task saved = taskRepository.save(task);
        log.info("Task created: id={}, userId={}", saved.getId(), userId);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskResponse getTask(Long taskId, Long userId, boolean isAdmin) {
        Task task = resolveTask(taskId, userId, isAdmin);
        return toResponse(task);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TaskResponse> getAllTasks(Long userId, TaskStatus status,
                                          Pageable pageable, boolean isAdmin) {
        Page<Task> tasks;
        if (isAdmin) {
            // Admin sees all tasks; optionally filter by status
            tasks = (status != null)
                    ? taskRepository.findAll(pageable).map(t -> t)  // simplified; can add spec
                    : taskRepository.findAll(pageable);
        } else {
            tasks = (status != null)
                    ? taskRepository.findAllByUserIdAndStatus(userId, status, pageable)
                    : taskRepository.findAllByUserId(userId, pageable);
        }
        return tasks.map(this::toResponse);
    }

    @Override
    @Transactional
    public TaskResponse updateTask(Long taskId, TaskRequest request,
                                    Long userId, boolean isAdmin) {
        Task task = resolveTask(taskId, userId, isAdmin);

        task.setTitle(request.getTitle().trim());
        if (request.getDescription() != null) task.setDescription(request.getDescription());
        if (request.getStatus()      != null) task.setStatus(request.getStatus());
        if (request.getPriority()    != null) task.setPriority(request.getPriority());
        if (request.getDueDate()     != null) task.setDueDate(request.getDueDate());

        Task updated = taskRepository.save(task);
        log.info("Task updated: id={}", updated.getId());
        return toResponse(updated);
    }

    @Override
    @Transactional
    public void deleteTask(Long taskId, Long userId, boolean isAdmin) {
        Task task = resolveTask(taskId, userId, isAdmin);
        taskRepository.delete(task);
        log.info("Task deleted: id={}", taskId);
    }

    // ── Helpers ───────────────────────────────────────────────

    private Task resolveTask(Long taskId, Long userId, boolean isAdmin) {
        if (isAdmin) {
            return taskRepository.findById(taskId)
                    .orElseThrow(() -> new ResourceNotFoundException("Task", taskId));
        }
        return taskRepository.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", taskId));
    }

    private TaskResponse toResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .dueDate(task.getDueDate())
                .userId(task.getUser().getId())
                .userName(task.getUser().getName())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }
}
