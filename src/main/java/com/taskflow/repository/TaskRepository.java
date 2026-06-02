package com.taskflow.repository;

import com.taskflow.entity.Task;
import com.taskflow.entity.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /** All tasks belonging to a specific user (paginated) */
    Page<Task> findAllByUserId(Long userId, Pageable pageable);

    /** User's tasks filtered by status */
    Page<Task> findAllByUserIdAndStatus(Long userId, TaskStatus status, Pageable pageable);

    /** Fetch single task only if it belongs to the user */
    Optional<Task> findByIdAndUserId(Long id, Long userId);

    /** Count tasks by status for a user (dashboard stats) */
    long countByUserIdAndStatus(Long userId, TaskStatus status);
}
