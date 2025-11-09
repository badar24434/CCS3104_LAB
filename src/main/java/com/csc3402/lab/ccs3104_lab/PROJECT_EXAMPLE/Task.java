package com.csc3402.lab.ccs3104_lab.PROJECT_EXAMPLE;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Task model class representing a task in the system
 * Implements Serializable for RMI communication
 */
public class Task implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String taskId;
    private String title;
    private String description;
    private String assignedTo;
    private String createdBy;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public enum TaskStatus {
        TODO("To Do"), 
        IN_PROGRESS("In Progress"), 
        REVIEW("Review"), 
        COMPLETED("Completed");
        
        private final String displayName;
        
        TaskStatus(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    public enum TaskPriority {
        LOW("Low"), 
        MEDIUM("Medium"), 
        HIGH("High"), 
        URGENT("Urgent");
        
        private final String displayName;
        
        TaskPriority(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    public Task() {
        this.taskId = generateTaskId();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = TaskStatus.TODO;
        this.priority = TaskPriority.MEDIUM;
    }
    
    public Task(String title, String description, String assignedTo, String createdBy) {
        this();
        this.title = title;
        this.description = description;
        this.assignedTo = assignedTo;
        this.createdBy = createdBy;
    }
    
    private String generateTaskId() {
        return "TASK-" + System.currentTimeMillis() + "-" + (int)(Math.random() * 1000);
    }
    
    // Getters and Setters
    public String getTaskId() {
        return taskId;
    }
    
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getAssignedTo() {
        return assignedTo;
    }
    
    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    public TaskStatus getStatus() {
        return status;
    }
    
    public void setStatus(TaskStatus status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }
    
    public TaskPriority getPriority() {
        return priority;
    }
    
    public void setPriority(TaskPriority priority) {
        this.priority = priority;
        this.updatedAt = LocalDateTime.now();
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public String getFormattedCreatedAt() {
        return createdAt.format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm"));
    }
    
    public String getFormattedUpdatedAt() {
        return updatedAt.format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm"));
    }
    
    @Override
    public String toString() {
        return String.format("[%s] %s - %s (Assigned to: %s, Status: %s, Priority: %s)",
                taskId, title, description, assignedTo, status.getDisplayName(), priority.getDisplayName());
    }
}
