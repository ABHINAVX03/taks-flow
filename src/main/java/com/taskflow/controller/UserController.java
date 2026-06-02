package com.taskflow.controller;

import com.taskflow.dto.response.ApiResponse;
import com.taskflow.dto.response.DashboardStats;
import com.taskflow.entity.TaskStatus;
import com.taskflow.entity.User;
import com.taskflow.repository.TaskRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "User Profile", description = "Current user profile and dashboard")
public class UserController {

    private final TaskRepository taskRepository;

    @GetMapping("/me")
    @Operation(summary = "Get current user's profile")
    public ResponseEntity<ApiResponse<User>> getProfile(
            @AuthenticationPrincipal User currentUser) {
        currentUser.setPassword(null);
        return ResponseEntity.ok(ApiResponse.success("Profile retrieved", currentUser));
    }

    @GetMapping("/me/dashboard")
    @Operation(summary = "Get current user's task statistics")
    public ResponseEntity<ApiResponse<DashboardStats>> getDashboard(
            @AuthenticationPrincipal User currentUser) {

        long total      = taskRepository.findAllByUserId(currentUser.getId(),
                org.springframework.data.domain.Pageable.unpaged()).getTotalElements();
        long todo       = taskRepository.countByUserIdAndStatus(currentUser.getId(), TaskStatus.TODO);
        long inProgress = taskRepository.countByUserIdAndStatus(currentUser.getId(), TaskStatus.IN_PROGRESS);
        long done       = taskRepository.countByUserIdAndStatus(currentUser.getId(), TaskStatus.DONE);

        DashboardStats stats = DashboardStats.builder()
                .totalTasks(total)
                .todoTasks(todo)
                .inProgressTasks(inProgress)
                .doneTasks(done)
                .userName(currentUser.getName())
                .role(currentUser.getRole().name())
                .build();

        return ResponseEntity.ok(ApiResponse.success("Dashboard retrieved", stats));
    }
}
