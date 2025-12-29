// File: com/example/rmi/common/StudentServerInterface.java
package com.csc3402.lab.ccs3104_lab.LAB6;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StudentServerInterface extends Remote {
    double findScore(String name) throws RemoteException;
}