# LAB 5 - RMI (Remote Method Invocation) Example

## Overview
This lab demonstrates Java RMI (Remote Method Invocation) technology by implementing a client/server application that calculates the area of a circle. The client sends a radius to the server, and the server computes and returns the area.

## What is RMI?
RMI (Remote Method Invocation) is a Java API that allows an object running in one JVM to invoke methods on an object running in another JVM. It provides a mechanism for distributed computing in Java.

## Components

### 1. **AreaCalculator.java** - Remote Interface
- Extends `java.rmi.Remote`
- Defines the remote method `computeArea(double radius)`
- All methods must throw `RemoteException`

### 2. **AreaCalculatorServer.java** - Server Implementation
- Implements the `AreaCalculator` interface
- Extends `UnicastRemoteObject`
- Creates/binds to RMI registry on port 1099
- Computes circle area: π × radius²
- Logs incoming requests

### 3. **AreaCalculatorClient.java** - Client Application
- JavaFX-based GUI client
- Connects to the RMI server via registry
- Sends radius values and displays results
- Provides user-friendly interface

## How to Run

### Step 1: Start the Server
**Important: Always start the server FIRST!**

```bash
# Navigate to the project directory
cd c:\Users\LL5\IdeaProjects\CCS3104_LAB

# Compile and run the server
mvn compile
mvn exec:java -Dexec.mainClass="com.csc3402.lab.ccs3104_lab.LAB5.AreaCalculatorServer"
```

**Expected Output:**
```
RMI registry created on port 1099
Server started at Sat Dec 14 15:30:00 PST 2025
AreaCalculator service is ready and waiting for clients...
```

### Step 2: Start the Client
**After the server is running**, open a NEW terminal:

```bash
# In a new terminal window
mvn exec:java -Dexec.mainClass="com.csc3402.lab.ccs3104_lab.LAB5.AreaCalculatorClient"
```

The client GUI window will appear.

## Usage

1. Enter a radius value (e.g., 4.0) in the text field
2. Click "Calculate Area" or press Enter
3. The result will be displayed showing:
   - The radius you entered
   - The area calculated by the server

## Example Interaction

**Client Side:**
```
Enter radius: 4
Radius: 4.0
Area received from server: 50.26548245743669
```

**Server Side (Console):**
```
Radius received from client: 4.0
Area computed: 50.26548245743669
```

## RMI Architecture

```
┌─────────────────┐                    ┌─────────────────┐
│     Client      │                    │     Server      │
│                 │                    │                 │
│  Enter Radius   │     RMI Call      │  Compute Area   │
│       ↓         │  ─────────────→   │       ↓         │
│  Lookup Stub    │                    │  Area = π·r²    │
│       ↓         │   Return Area      │                 │
│ Display Result  │  ←─────────────   │   Log Request   │
└─────────────────┘                    └─────────────────┘
         ↓                                      ↑
         └──────────→ RMI Registry ←───────────┘
                    (Port 1099)
```

## Key RMI Concepts Demonstrated

1. **Remote Interface**: Defines methods that can be invoked remotely
2. **RMI Registry**: Naming service where servers register their services
3. **Stub**: Client-side proxy for the remote object
4. **Skeleton**: Server-side counterpart (handled automatically in modern Java)
5. **UnicastRemoteObject**: Makes an object available for remote calls

## Technical Details

- **Protocol**: JRMP (Java Remote Method Protocol)
- **Port**: 1099 (default RMI registry port)
- **Serialization**: Automatic for primitive types and serializable objects
- **Exception Handling**: All remote methods must handle `RemoteException`

## Troubleshooting

### "Connection refused" error
- Make sure the server is running BEFORE starting the client
- Check if port 1099 is available

### "java.rmi.NotBoundException"
- Verify the server successfully registered the service
- Check the binding name matches ("AreaCalculator")

### "java.rmi.UnmarshalException"
- Ensure client and server use the same interface version
- Recompile all classes

## Advantages of RMI

✓ Easy to use - simpler than sockets  
✓ Object-oriented approach to distributed computing  
✓ Automatic parameter serialization  
✓ Built-in exception handling  
✓ Supports callbacks and distributed garbage collection  

## Comparison with Sockets

| Feature | RMI | Sockets |
|---------|-----|---------|
| Complexity | High-level, simple | Low-level, complex |
| Data Passing | Automatic serialization | Manual byte streams |
| Method Calls | Direct remote calls | Custom protocol needed |
| Type Safety | Compile-time checking | Runtime parsing |
| Performance | Slight overhead | More efficient |

## Next Steps

- Try adding more calculation methods (volume, circumference)
- Implement security using RMI security managers
- Add multiple clients simultaneously
- Explore RMI over SSL for secure communication

---

**Course:** CSC3104 Advanced Programming  
**Lab:** 5 - Remote Method Invocation  
**Date:** December 2025
