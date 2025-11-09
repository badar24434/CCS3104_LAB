# Remote Task Tracker System

A distributed team productivity tracker built with Java RMI, JavaFX, and multithreading. Features a modern dark theme UI inspired by Notion.

## 🎯 Project Overview

This system allows distributed teams to create, assign, and monitor tasks remotely. It uses RMI for client-server communication, multithreading for background updates, and file I/O for persistent storage.

## ✨ Features

### Core Features
- **RMI Client-Server Communication**: Remote task management using Java RMI
- **User Management**: Register and track multiple users
- **Task CRUD Operations**: Create, Read, Update, and Delete tasks
- **Task Assignment**: Assign tasks to team members
- **Status Tracking**: Track task progress (To Do, In Progress, Review, Completed)
- **Priority Levels**: Set task priorities (Low, Medium, High, Urgent)

### Advanced Features
- **Auto-Refresh**: Background thread polls server every 5 seconds for updates
- **File I/O Backup**: Automatic task and user data backup
- **Task Logging**: Comprehensive activity logging
- **Summary Reports**: Generate detailed task reports
- **Real-time Sync**: All changes sync across connected clients
- **Modern Dark UI**: Notion-inspired interface with smooth interactions

### UI Features
- **Filter Options**: View all tasks, my tasks, or filter by status
- **Visual Indicators**: Color-coded status and priority badges
- **Interactive Table**: Double-click to edit, select to delete
- **Live Statistics**: Real-time task statistics in header
- **Status Bar**: Connection status and auto-refresh indicator

## 🏗️ Architecture

### Components

1. **Task.java** - Task model with properties and serialization
2. **TaskService.java** - RMI remote interface
3. **TaskServiceImpl.java** - RMI service implementation with thread-safe operations
4. **TaskServer.java** - RMI server with registry and command interface
5. **TaskTrackerClient.java** - JavaFX client with auto-refresh
6. **FileManager.java** - File I/O operations and logging
7. **modern-dark-theme.css** - Notion-inspired dark theme styling

### Technology Stack
- **Java RMI**: Remote method invocation for distributed communication
- **JavaFX**: Rich desktop UI framework
- **Multithreading**: Background auto-refresh and auto-save threads
- **File I/O**: Persistent storage using serialization
- **Concurrent Collections**: Thread-safe data structures

## 🚀 Getting Started

### Prerequisites
- Java JDK 11 or higher
- JavaFX SDK (included in project)
- Maven (for dependency management)

### Running the Application

#### Step 1: Start the Server

Run `TaskServer.java` to start the RMI server:

```powershell
# From project root
mvn clean compile
mvn exec:java -Dexec.mainClass="com.csc3402.lab.ccs3104_lab.PROJECT_EXAMPLE.TaskServer"
```

Or run directly in your IDE.

**Server Commands:**
- `s` - View server statistics
- `r` - Generate summary report
- `l` - View recent logs
- `q` - Shutdown server

#### Step 2: Start Client(s)

Run `TaskTrackerClient.java` to start a client:

```powershell
mvn exec:java -Dexec.mainClass="com.csc3402.lab.ccs3104_lab.PROJECT_EXAMPLE.TaskTrackerClient"
```

You can run multiple clients simultaneously to test distributed features.

#### Step 3: Login

- Enter a username when prompted
- The system will register new users automatically

## 📖 User Guide

### Creating Tasks
1. Click "➕ Create New Task" button
2. Fill in task details:
   - Title
   - Description
   - Assign to (select user)
   - Priority level
3. Click "Create"

### Editing Tasks
1. Select a task from the table
2. Click "✏️ Edit Task" or double-click the row
3. Modify task details
4. Update status and priority as needed
5. Click "Save"

### Deleting Tasks
1. Select a task from the table
2. Click "🗑️ Delete Task"
3. Confirm deletion

### Filtering Tasks
Use the filter buttons to view:
- **All Tasks**: View all tasks in the system
- **My Tasks**: View only your assigned tasks
- **To Do**: Tasks not yet started
- **In Progress**: Tasks currently being worked on
- **Review**: Tasks under review
- **Completed**: Finished tasks

### Generating Reports
Click "📊 Generate Report" to create a comprehensive summary report including:
- Overall task statistics
- Task breakdown by status and priority
- Tasks per user
- Detailed task list

Reports are saved to the application directory with timestamps.

## 🔧 Technical Details

### RMI Configuration
- **Registry Port**: 1099
- **Service Name**: TaskService
- **URL**: rmi://localhost:1099/TaskService

### Multithreading
1. **Auto-Save Thread**: Saves data every 60 seconds
2. **Auto-Refresh Thread**: Refreshes client UI every 5 seconds

### File I/O
- **tasks_backup.dat**: Serialized task data
- **users_backup.dat**: Serialized user data
- **task_log.txt**: Activity log
- **task_report_[timestamp].txt**: Generated reports

### Thread Safety
- Uses `CopyOnWriteArrayList` for tasks and users
- Uses `ConcurrentHashMap` for task counting
- Synchronized methods for critical operations

## 🎨 UI Design

The application features a modern dark theme inspired by Notion:

### Color Scheme
- **Background**: Dark gray (#191919, #1e1e1e)
- **Accents**: Blue (#3182ce) for actions
- **Status Colors**:
  - To Do: Gray (#4a5568)
  - In Progress: Blue (#3182ce)
  - Review: Orange (#d69e2e)
  - Completed: Green (#38a169)
- **Priority Colors**:
  - Low: Gray (#4a5568)
  - Medium: Blue (#4299e1)
  - High: Orange (#ed8936)
  - Urgent: Red (#e53e3e)

### Layout
- **Header**: Title, subtitle, and live statistics
- **Main Area**: Task table with filtering
- **Side Panel**: Action buttons and quick info
- **Status Bar**: Connection status and auto-refresh indicator

## 📊 Data Flow

```
Client → RMI Call → Server → Update Data → Save to File
                                      ↓
Client ← Auto-Refresh ← Server ← Load from File
```

## 🔒 Compulsory Features Implemented

### 1. RMI Client-Server Communication ✅
- TaskService interface with remote methods
- TaskServiceImpl with RMI registry binding
- Multiple clients can connect simultaneously

### 2. Multithreading ✅
- Auto-refresh thread in client (5-second polling)
- Auto-save thread in server (60-second backup)
- Thread-safe collections and synchronized methods

### 3. File I/O ✅
- Serialized object storage (tasks and users)
- Activity logging with timestamps
- Report generation with detailed statistics

## 🐛 Troubleshooting

### Server Won't Start
- Check if port 1099 is available
- Ensure no other RMI registry is running
- Check firewall settings

### Client Can't Connect
- Ensure server is running
- Verify server URL is correct
- Check network connectivity

### UI Not Displaying Correctly
- Ensure CSS file is in the correct location
- Check JavaFX runtime is properly configured
- Verify module-info.java exports are correct

## 📝 Notes

- All tasks are stored persistently and reload on server restart
- Multiple clients can work simultaneously with real-time sync
- The system is designed for local network use (localhost)
- For production use, update RMI URL to server IP address

## 👥 Team Collaboration Features

- Real-time task updates across all clients
- Task assignment to specific team members
- Activity logging for audit trail
- Summary reports for team progress tracking
- Multi-user support with automatic registration

## 🎓 Learning Objectives

This project demonstrates:
1. **Distributed Computing**: RMI for remote method invocation
2. **Concurrent Programming**: Thread-safe operations and background tasks
3. **Persistent Storage**: File I/O with serialization
4. **UI Development**: JavaFX with custom styling
5. **System Design**: Client-server architecture
6. **Data Management**: CRUD operations and filtering

---

**Built with ❤️ for CCS3104 Lab Project**
