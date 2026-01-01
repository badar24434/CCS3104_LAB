package com.csc3402.lab.ccs3104_lab.PROJECT_EXAMPLE;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Task Service Implementation with RMI support
 * Handles all task and user management operations
 * Thread-safe using concurrent collections
 */
public class TaskServiceImpl extends UnicastRemoteObject implements TaskService {
    private static final long serialVersionUID = 1L;
    
    // Thread-safe collections
    private final CopyOnWriteArrayList<Task> tasks;
    private final CopyOnWriteArrayList<String> users;
    private final ConcurrentHashMap<String, Integer> taskCountByUser;
    private final LocalDateTime serverStartTime;
    
    public TaskServiceImpl() throws RemoteException {
        super();
        this.tasks = new CopyOnWriteArrayList<>();
        this.users = new CopyOnWriteArrayList<>();
        this.taskCountByUser = new ConcurrentHashMap<>();
        this.serverStartTime = LocalDateTime.now();
        
        // Load existing data from files
        loadDataFromFiles();
        
        // Start auto-save thread
        startAutoSaveThread();
    }
    
    /**
     * Load tasks and users from backup files
     */
    
    
    /**
     * Auto-save thread to periodically backup data
     */
    private void startAutoSaveThread() {
        Thread autoSaveThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(60000); // Save every 1 minute
                    saveDataToFiles();
                } catch (InterruptedException e) {
                    FileManager.logAction("Auto-save thread interrupted");
                    break;
                }
            }
        });
        autoSaveThread.setDaemon(true);
        autoSaveThread.setName("AutoSaveThread");
        autoSaveThread.start();
        FileManager.logAction("Auto-save thread started");
    }
    
    /**
     * Save tasks and users to backup files
     */
    private synchronized void saveDataToFiles() {
        FileManager.saveTasks(new ArrayList<>(tasks));
        FileManager.saveUsers(new ArrayList<>(users));
    }
    
    @Override
    public synchronized Task createTask(Task task) throws RemoteException {
        tasks.add(task);
        taskCountByUser.merge(task.getAssignedTo(), 1, Integer::sum);
        FileManager.logAction("Task created: " + task.getTaskId() + " - " + task.getTitle() + 
                             " (Assigned to: " + task.getAssignedTo() + ")");
        saveDataToFiles();
        return task;
    }
    
    @Override
    public synchronized boolean updateTask(Task updatedTask) throws RemoteException {
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getTaskId().equals(updatedTask.getTaskId())) {
                // Update task count if assignee changed
                if (!task.getAssignedTo().equals(updatedTask.getAssignedTo())) {
                    taskCountByUser.merge(task.getAssignedTo(), -1, Integer::sum);
                    taskCountByUser.merge(updatedTask.getAssignedTo(), 1, Integer::sum);
                }
                tasks.set(i, updatedTask);
                FileManager.logAction("Task updated: " + updatedTask.getTaskId() + " - " + 
                                    updatedTask.getTitle());
                saveDataToFiles();
                return true;
            }
        }
        return false;
    }
    
    @Override
    public synchronized boolean deleteTask(String taskId) throws RemoteException {
        for (Task task : tasks) {
            if (task.getTaskId().equals(taskId)) {
                tasks.remove(task);
                taskCountByUser.merge(task.getAssignedTo(), -1, Integer::sum);
                FileManager.logAction("Task deleted: " + taskId + " - " + task.getTitle());
                saveDataToFiles();
                return true;
            }
        }
        return false;
    }
    
    @Override
    public Task getTask(String taskId) throws RemoteException {
        return tasks.stream()
                .filter(task -> task.getTaskId().equals(taskId))
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public List<Task> getAllTasks() throws RemoteException {
        return new ArrayList<>(tasks);
    }
    
    @Override
    public List<Task> getTasksByAssignee(String username) throws RemoteException {
        return tasks.stream()
                .filter(task -> task.getAssignedTo().equals(username))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Task> getTasksByStatus(Task.TaskStatus status) throws RemoteException {
        return tasks.stream()
                .filter(task -> task.getStatus() == status)
                .collect(Collectors.toList());
    }
    
    @Override
    public synchronized boolean registerUser(String username) throws RemoteException {
        if (users.contains(username)) {
            return false;
        }
        users.add(username);
        taskCountByUser.put(username, 0);
        FileManager.logAction("User registered: " + username);
        saveDataToFiles();
        return true;
    }
    
    @Override
    public List<String> getAllUsers() throws RemoteException {
        return new ArrayList<>(users);
    }
    
    @Override
    public String generateSummaryReport() throws RemoteException {
        StringBuilder report = new StringBuilder();
        
        // Summary statistics
        long totalTasks = tasks.size();
        long todoTasks = tasks.stream().filter(t -> t.getStatus() == Task.TaskStatus.TODO).count();
        long inProgressTasks = tasks.stream().filter(t -> t.getStatus() == Task.TaskStatus.IN_PROGRESS).count();
        long reviewTasks = tasks.stream().filter(t -> t.getStatus() == Task.TaskStatus.REVIEW).count();
        long completedTasks = tasks.stream().filter(t -> t.getStatus() == Task.TaskStatus.COMPLETED).count();
        
        report.append("OVERALL STATISTICS\n");
        report.append("-".repeat(80)).append("\n");
        report.append(String.format("Total Tasks: %d\n", totalTasks));
        report.append(String.format("Total Users: %d\n", users.size()));
        report.append(String.format("\nTask Status Breakdown:\n"));
        report.append(String.format("  • To Do: %d (%.1f%%)\n", todoTasks, totalTasks > 0 ? todoTasks * 100.0 / totalTasks : 0));
        report.append(String.format("  • In Progress: %d (%.1f%%)\n", inProgressTasks, totalTasks > 0 ? inProgressTasks * 100.0 / totalTasks : 0));
        report.append(String.format("  • Review: %d (%.1f%%)\n", reviewTasks, totalTasks > 0 ? reviewTasks * 100.0 / totalTasks : 0));
        report.append(String.format("  • Completed: %d (%.1f%%)\n", completedTasks, totalTasks > 0 ? completedTasks * 100.0 / totalTasks : 0));
        
        // Tasks by priority
        long urgentTasks = tasks.stream().filter(t -> t.getPriority() == Task.TaskPriority.URGENT).count();
        long highTasks = tasks.stream().filter(t -> t.getPriority() == Task.TaskPriority.HIGH).count();
        long mediumTasks = tasks.stream().filter(t -> t.getPriority() == Task.TaskPriority.MEDIUM).count();
        long lowTasks = tasks.stream().filter(t -> t.getPriority() == Task.TaskPriority.LOW).count();
        
        report.append(String.format("\nTask Priority Breakdown:\n"));
        report.append(String.format("  • Urgent: %d\n", urgentTasks));
        report.append(String.format("  • High: %d\n", highTasks));
        report.append(String.format("  • Medium: %d\n", mediumTasks));
        report.append(String.format("  • Low: %d\n", lowTasks));
        
        // Tasks by user
        report.append("\n\nTASKS BY USER\n");
        report.append("-".repeat(80)).append("\n");
        for (String user : users) {
            int count = taskCountByUser.getOrDefault(user, 0);
            long userCompleted = tasks.stream()
                    .filter(t -> t.getAssignedTo().equals(user) && t.getStatus() == Task.TaskStatus.COMPLETED)
                    .count();
            report.append(String.format("%-20s: %d tasks (%d completed)\n", user, count, userCompleted));
        }
        
        // All tasks
        report.append("\n\nALL TASKS\n");
        report.append("-".repeat(80)).append("\n");
        for (Task task : tasks) {
            report.append(String.format("\n[%s] %s\n", task.getTaskId(), task.getTitle()));
            report.append(String.format("  Status: %s | Priority: %s | Assigned to: %s\n",
                    task.getStatus().getDisplayName(),
                    task.getPriority().getDisplayName(),
                    task.getAssignedTo()));
            report.append(String.format("  Created: %s | Updated: %s\n",
                    task.getFormattedCreatedAt(),
                    task.getFormattedUpdatedAt()));
            report.append(String.format("  Description: %s\n", task.getDescription()));
        }
        
        String reportStr = report.toString();
        FileManager.generateReport(reportStr);
        FileManager.logAction("Summary report generated");
        
        return reportStr;
    }
    
    @Override
    public String getServerStats() throws RemoteException {
        long uptime = java.time.Duration.between(serverStartTime, LocalDateTime.now()).toMinutes();
        return String.format("Server Uptime: %d minutes | Tasks: %d | Users: %d | Active Connections: Active",
                uptime, tasks.size(), users.size());
    }
}
