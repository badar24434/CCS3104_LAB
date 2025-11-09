package com.csc3402.lab.ccs3104_lab.PROJECT_EXAMPLE;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * RMI Remote Interface for Task Service
 * Defines all remote methods that clients can invoke
 */
public interface TaskService extends Remote {
    
    /**
     * Create a new task
     * @param task The task to create
     * @return The created task with generated ID
     * @throws RemoteException if RMI communication fails
     */
    Task createTask(Task task) throws RemoteException;
    
    /**
     * Update an existing task
     * @param task The task with updated information
     * @return true if update successful, false otherwise
     * @throws RemoteException if RMI communication fails
     */
    boolean updateTask(Task task) throws RemoteException;
    
    /**
     * Delete a task by ID
     * @param taskId The ID of the task to delete
     * @return true if deletion successful, false otherwise
     * @throws RemoteException if RMI communication fails
     */
    boolean deleteTask(String taskId) throws RemoteException;
    
    /**
     * Get a task by ID
     * @param taskId The ID of the task
     * @return The task if found, null otherwise
     * @throws RemoteException if RMI communication fails
     */
    Task getTask(String taskId) throws RemoteException;
    
    /**
     * Get all tasks
     * @return List of all tasks
     * @throws RemoteException if RMI communication fails
     */
    List<Task> getAllTasks() throws RemoteException;
    
    /**
     * Get tasks assigned to a specific user
     * @param username The username
     * @return List of tasks assigned to the user
     * @throws RemoteException if RMI communication fails
     */
    List<Task> getTasksByAssignee(String username) throws RemoteException;
    
    /**
     * Get tasks by status
     * @param status The task status
     * @return List of tasks with the specified status
     * @throws RemoteException if RMI communication fails
     */
    List<Task> getTasksByStatus(Task.TaskStatus status) throws RemoteException;
    
    /**
     * Register a user in the system
     * @param username The username to register
     * @return true if registration successful, false if user already exists
     * @throws RemoteException if RMI communication fails
     */
    boolean registerUser(String username) throws RemoteException;
    
    /**
     * Get all registered users
     * @return List of all usernames
     * @throws RemoteException if RMI communication fails
     */
    List<String> getAllUsers() throws RemoteException;
    
    /**
     * Generate a summary report of all tasks
     * @return Summary report as a string
     * @throws RemoteException if RMI communication fails
     */
    String generateSummaryReport() throws RemoteException;
    
    /**
     * Get server status and statistics
     * @return Server statistics
     * @throws RemoteException if RMI communication fails
     */
    String getServerStats() throws RemoteException;
}
