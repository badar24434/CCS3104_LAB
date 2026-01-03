package com.csc3402.lab.ccs3104_lab.PROJECT_EXAMPLE;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * File Manager for Task Backup and Restore
 * Handles file I/O operations for persistent storage
 */
public class FileManager {
    private static final String TASKS_FILE = "tasks_backup.dat";
    private static final String USERS_FILE = "users_backup.dat";
    private static final String LOG_FILE = "task_log.txt";
    
    /**
     * Save tasks to file
     */
    public static synchronized void saveTasks(List<Task> tasks) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(TASKS_FILE))) {
            oos.writeObject(tasks);
            logAction("Tasks saved to file. Total tasks: " + tasks.size());
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
            logAction("ERROR: Failed to save tasks - " + e.getMessage());
        }
    }
    
    /**
    
    /**
     * Load users from file
     */
    @SuppressWarnings("unchecked")
    public static synchronized List<String> loadUsers() {
        File file = new File(USERS_FILE);
        if (!file.exists()) {
            logAction("No existing users file found. Starting with empty user list.");
            return new ArrayList<>();
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(USERS_FILE))) {
            List<String> users = (List<String>) ois.readObject();
            logAction("Users loaded from file. Total users: " + users.size());
            return users;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading users: " + e.getMessage());
            logAction("ERROR: Failed to load users - " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Log an action to the log file
     */
    public static synchronized void logAction(String action) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            String timestamp = LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            out.println("[" + timestamp + "] " + action);
        } catch (IOException e) {
            System.err.println("Error writing to log: " + e.getMessage());
        }
    }
    
    /**
     * Generate a summary report and save to file
     */
    public static void generateReport(String report) {
        String filename = "task_report_" + 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + 
                ".txt";
        
        try (FileWriter fw = new FileWriter(filename);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println("=".repeat(80));
            out.println("TASK TRACKER SYSTEM - SUMMARY REPORT");
            out.println("Generated: " + LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("MMMM dd, yyyy HH:mm:ss")));
            out.println("=".repeat(80));
            out.println();
            out.println(report);
            out.println();
            out.println("=".repeat(80));
            out.println("End of Report");
            out.println("=".repeat(80));
            
            logAction("Summary report generated: " + filename);
            System.out.println("Report saved to: " + filename);
        } catch (IOException e) {
            System.err.println("Error generating report: " + e.getMessage());
            logAction("ERROR: Failed to generate report - " + e.getMessage());
        }
    }
    
    /**
     * Read the log file
     */
    public static String readLog(int lastNLines) {
        File file = new File(LOG_FILE);
        if (!file.exists()) {
            return "No log file found.";
        }
        
        StringBuilder log = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(LOG_FILE))) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            
            int start = Math.max(0, lines.size() - lastNLines);
            for (int i = start; i < lines.size(); i++) {
                log.append(lines.get(i)).append("\n");
            }
        } catch (IOException e) {
            return "Error reading log: " + e.getMessage();
        }
        
        return log.toString();
    }
}
