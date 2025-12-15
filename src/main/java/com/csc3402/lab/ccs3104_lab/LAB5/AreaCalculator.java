package com.csc3402.lab.ccs3104_lab.LAB5;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Remote interface for calculating circle area
 */
public interface AreaCalculator extends Remote {
    /**
     * Computes the area of a circle given its radius
     * @param radius The radius of the circle
     * @return The area of the circle (π * radius²)
     * @throws RemoteException if remote communication fails
     */
    double computeArea(double radius) throws RemoteException;
}
