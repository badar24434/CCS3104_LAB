package com.csc3402.lab.ccs3104_lab.LAB6;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {
    public static void main(String[] args) {
        try {
            // Create the remote object
            StudentServerInterface obj = new StudentServerInterfaceImpl();

            // Create and start the registry on port 1099
            Registry registry = LocateRegistry.createRegistry(1098);

            // Bind the remote object to the registry
            registry.rebind("StudentServerInterface", obj);

            System.out.println("RMI Server is running. StudentServerInterface bound in registry.");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}