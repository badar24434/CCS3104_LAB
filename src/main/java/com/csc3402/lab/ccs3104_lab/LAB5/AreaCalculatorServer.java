package com.csc3402.lab.ccs3104_lab.LAB5;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * RMI Server implementation for Area Calculator
 * This server implements the remote interface and provides area calculation service
 */
public class AreaCalculatorServer extends UnicastRemoteObject implements AreaCalculator {
    private static final long serialVersionUID = 1L;
    
    /**
     * Constructor for the server
     * @throws RemoteException if remote communication fails
     */
    protected AreaCalculatorServer() throws RemoteException {
        super();
    }
    
    /**
     * Implementation of the remote method to compute circle area
     * @param radius The radius of the circle
     * @return The area of the circle
     * @throws RemoteException if remote communication fails
     */
    @Override
    public double computeArea(double radius) throws RemoteException {
        double area = Math.PI * radius * radius;
        System.out.println("Radius received from client: " + radius);
        System.out.println("Area computed: " + area);
        return area;
    }
    
    /**
     * Main method to start the RMI server
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            // Create and export the remote object
            AreaCalculatorServer server = new AreaCalculatorServer();
            
            // Create or get the RMI registry on port 1099 (default RMI port)
            Registry registry;
            try {
                registry = LocateRegistry.createRegistry(1099);
                System.out.println("RMI registry created on port 1099");
            } catch (RemoteException e) {
                // Registry might already exist
                registry = LocateRegistry.getRegistry(1099);
                System.out.println("Using existing RMI registry on port 1099");
            }
            
            // Bind the remote object to the registry with a name
            registry.rebind("AreaCalculator", server);
            
            // Print server start message with timestamp
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E MMM dd HH:mm:ss z yyyy");
            String timestamp = ZonedDateTime.now().format(formatter);
            System.out.println("Server started at " + timestamp);
            System.out.println("AreaCalculator service is ready and waiting for clients...");
            
        } catch (Exception e) {
            System.err.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
