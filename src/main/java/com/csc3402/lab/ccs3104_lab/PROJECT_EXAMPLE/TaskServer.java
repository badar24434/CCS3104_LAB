package com.csc3402.lab.ccs3104_lab.PROJECT_EXAMPLE;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

/**
 * Task Server - RMI Server for Task Tracker System
 * Runs the RMI registry and binds the TaskService implementation
 */
public class TaskServer {
    private static final int PORT = 1099;
    private static final String SERVICE_NAME = "TaskService";
    
    public static void main(String[] args) {
        try {
            // Start RMI registry
            System.out.println("Starting RMI Registry on port " + PORT + "...");
            LocateRegistry.createRegistry(PORT);
            System.out.println("✓ RMI Registry started successfully");
            
            // Create and bind the service
            System.out.println("\nInitializing Task Service...");
            TaskServiceImpl taskService = new TaskServiceImpl();
            String url = "rmi://localhost:" + PORT + "/" + SERVICE_NAME;
            Naming.rebind(url, taskService);
            
            System.out.println("✓ Task Service bound successfully");
            System.out.println("\n" + "=".repeat(60));
            System.out.println("   TASK TRACKER SERVER - RUNNING");
            System.out.println("=".repeat(60));
            System.out.println("RMI URL: " + url);
            System.out.println("Port: " + PORT);
            System.out.println("Status: ONLINE");
            System.out.println("=".repeat(60));
            System.out.println("\nServer is ready to accept client connections.");
            System.out.println("Press 'q' + Enter to shutdown server");
            System.out.println("Press 's' + Enter to view server stats");
            System.out.println("Press 'r' + Enter to generate report");
            System.out.println("Press 'l' + Enter to view recent logs");
            
            FileManager.logAction("Task Server started successfully");
            
           
            while (running) {
                if (scanner.hasNextLine()) {
                    String command = scanner.nextLine().trim().toLowerCase();
                    
                    switch (command) {
                        case "q":
                            System.out.println("\nShutting down server...");
                            FileManager.logAction("Server shutdown initiated");
                            running = false;
                            break;
                            
                        case "s":
                            System.out.println("\n" + "=".repeat(60));
                            System.out.println(taskService.getServerStats());
                            System.out.println("=".repeat(60));
                            break;
                            
                        case "r":
                            System.out.println("\nGenerating summary report...");
                            String report = taskService.generateSummaryReport();
                            System.out.println("\n" + report);
                            break;
                            
                        case "l":
                            System.out.println("\n" + "=".repeat(60));
                            System.out.println("RECENT LOGS (Last 20 lines)");
                            System.out.println("=".repeat(60));
                            System.out.println(FileManager.readLog(20));
                            System.out.println("=".repeat(60));
                            break;
                            
                        default:
                            System.out.println("Unknown command. Use: q (quit), s (stats), r (report), l (logs)");
                    }
                }
                
                Thread.sleep(100);
            }
            
            scanner.close();
            System.out.println("✓ Server shutdown complete");
            System.exit(0);
            
        } catch (Exception e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
            FileManager.logAction("ERROR: Server failed - " + e.getMessage());
            System.exit(1);
        }
    }
}
