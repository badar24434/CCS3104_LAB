# LAB 6 - RMI with Oracle Database Setup Guide

This guide will help you set up Java RMI (Remote Method Invocation) with Oracle Database connectivity.

---

## Prerequisites

Before starting, ensure you have:
- ✅ JDK 17 or higher installed
- ✅ IntelliJ IDEA or any Java IDE
- ✅ Oracle Database installed (Oracle XE recommended) OR access to Oracle Cloud
- ✅ Oracle SQL Developer (for database management)

---

## Step 1: Add Oracle JDBC Driver to `pom.xml`

Add this dependency inside the `<dependencies>` section:

```xml
<!-- Oracle JDBC Driver -->
<dependency>
    <groupId>com.oracle.database.jdbc</groupId>
    <artifactId>ojdbc11</artifactId>
    <version>23.3.0.23.09</version>
</dependency>
```

**Full example location in pom.xml:**
```xml
<dependencies>
    <!-- ... other dependencies ... -->
    
    <!-- Oracle JDBC Driver -->
    <dependency>
        <groupId>com.oracle.database.jdbc</groupId>
        <artifactId>ojdbc11</artifactId>
        <version>23.3.0.23.09</version>
    </dependency>
</dependencies>
```

---

## Step 2: Update `module-info.java`

Add these two lines to your `module-info.java` file:

```java
requires java.rmi;   // For RMI functionality
requires java.sql;   // For JDBC database connectivity
```

**Full example:**
```java
module your.module.name {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;    // ADD THIS
    requires java.sql;    // ADD THIS
    
    // Export your LAB6 package
    exports com.yourpackage.LAB6;
    opens com.yourpackage.LAB6 to javafx.fxml;
}
```

---

## Step 3: Configure Oracle Database Connection

In `StudentServerInterfaceImpl.java`, update these configuration values:

```java
// ============ ORACLE DATABASE CONFIGURATION ============
private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
private static final String DB_USER = "your_username";     // Your Oracle username
private static final String DB_PASSWORD = "your_password"; // Your Oracle password
// ========================================================
```

### Connection URL Formats:

| Oracle Setup | URL Format |
|--------------|------------|
| Local Oracle XE | `jdbc:oracle:thin:@localhost:1521:xe` |
| Local Oracle with SID | `jdbc:oracle:thin:@localhost:1521:ORCL` |
| With Service Name | `jdbc:oracle:thin:@//localhost:1521/ORCLPDB1` |
| Remote Server | `jdbc:oracle:thin:@192.168.1.100:1521:xe` |

### How to Find Your Connection Details:
1. Open **Oracle SQL Developer**
2. Right-click your connection → **Properties**
3. Note: **Hostname**, **Port**, **SID** or **Service Name**

---

## Step 4: Start Oracle Database Services

### For Windows:
1. Press `Win + R`, type `services.msc`, press Enter
2. Find and **Start** these services:
   - `OracleServiceXE` (or `OracleServiceORCL`)
   - `OracleOraDB21Home1TNSListener` (or similar listener)

**OR** run in Command Prompt (as Administrator):
```cmd
lsnrctl start
net start OracleServiceXE
```

### Verify Oracle is Running:
```cmd
lsnrctl status
```

---

## Step 5: Create the Database Table

Open **Oracle SQL Developer** and run this SQL:

```sql
-- Create the Scores table
CREATE TABLE Scores (
    name VARCHAR2(50),
    score NUMBER(5,2),
    permission NUMBER(1)
);

-- Insert sample data
INSERT INTO Scores VALUES ('John', 90.5, 1);
INSERT INTO Scores VALUES ('Michael', 100, 1);
INSERT INTO Scores VALUES ('Michelle', 100, 0);

-- Save changes
COMMIT;

-- Verify data
SELECT * FROM Scores;
```

**Note:** `permission` uses NUMBER(1) where:
- `1` = true (can view score)
- `0` = false (cannot view score)

---

## Step 6: Run the Application

### Order of Execution:
1. **First:** Run `RMIServer.java` - Wait until you see "RMI Server is running"
2. **Then:** Run `StudentClient.java` - The GUI will appear

### Expected Console Output (Server):
```
Oracle JDBC Driver loaded
Connected to Oracle database
Scores table already exists.
RMI Server is running. StudentServerInterface bound in registry.
```

---

## Common Errors and Solutions

### Error: ORA-12541 - No listener
**Cause:** Oracle listener is not running
**Solution:** Start the Oracle listener service (see Step 4)

### Error: ORA-01017 - Invalid username/password
**Cause:** Wrong credentials in DB_USER or DB_PASSWORD
**Solution:** Verify your Oracle username and password in SQL Developer

### Error: ORA-12514 - Listener does not know of service
**Cause:** Wrong SID or Service Name in connection URL
**Solution:** Check your Oracle connection properties in SQL Developer

### Error: ClassNotFoundException - oracle.jdbc.driver.OracleDriver
**Cause:** Oracle JDBC driver not added to pom.xml
**Solution:** Add the ojdbc11 dependency (see Step 1)

### Error: Package java.sql is not visible
**Cause:** Missing `requires java.sql;` in module-info.java
**Solution:** Add the required modules (see Step 2)

---

## Project Structure

```
LAB6/
├── StudentServerInterface.java      # Remote interface
├── StudentServerInterfaceImpl.java  # Server implementation (DB connection)
├── RMIServer.java                   # Starts the RMI registry
├── StudentClient.java               # JavaFX GUI client
└── SETUP_INSTRUCTIONS.md            # This file
```

---

## Quick Checklist

- [ ] Oracle JDBC driver added to `pom.xml`
- [ ] `requires java.rmi;` in module-info.java
- [ ] `requires java.sql;` in module-info.java
- [ ] LAB6 package exported in module-info.java
- [ ] Oracle database running
- [ ] Oracle listener running
- [ ] Correct DB_URL, DB_USER, DB_PASSWORD configured
- [ ] Scores table created in database
- [ ] RMI Server started before Client

---

## Test Data

After setup, test with these student names:
| Name | Score | Has Permission |
|------|-------|----------------|
| John | 90.5 | Yes ✓ |
| Michael | 100.0 | Yes ✓ |
| Michelle | 100.0 | No ✗ |

---

**Need Help?** Check the error messages carefully - they usually indicate exactly what's wrong!
