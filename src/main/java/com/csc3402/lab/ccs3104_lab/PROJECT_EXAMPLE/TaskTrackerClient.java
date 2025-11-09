package com.csc3402.lab.ccs3104_lab.PROJECT_EXAMPLE;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.rmi.Naming;
import java.util.List;
import java.util.Optional;

/**
 * Task Tracker Client - JavaFX GUI with Notion-inspired dark theme
 * Includes auto-refresh using multithreading
 */
public class TaskTrackerClient extends Application {
    private static final String SERVER_URL = "rmi://localhost:1099/TaskService";
    private static final int REFRESH_INTERVAL = 5000; // 5 seconds
    
    private TaskService taskService;
    private String currentUser;
    private ObservableList<Task> taskList;
    private TableView<Task> taskTable;
    private Label statusLabel;
    private Label statsLabel;
    private Thread refreshThread;
    private volatile boolean isRefreshing = true;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            // Connect to server
            connectToServer();
            
            // Login/Register
            if (!showLoginDialog()) {
                Platform.exit();
                return;
            }
            
            // Initialize task list
            taskList = FXCollections.observableArrayList();
            
            // Create main UI
            BorderPane root = createMainUI();
            
            // Apply dark theme
            Scene scene = new Scene(root, 1400, 800);
            scene.getStylesheets().add(getClass().getResource("/com/csc3402/lab/ccs3104_lab/PROJECT_EXAMPLE/modern-dark-theme.css").toExternalForm());
            
            primaryStage.setTitle("Task Tracker - " + currentUser);
            primaryStage.setScene(scene);
            primaryStage.show();
            
            // Start auto-refresh thread
            startAutoRefresh();
            
            // Load initial tasks
            refreshTasks();
            
            // Handle window close
            primaryStage.setOnCloseRequest(e -> {
                isRefreshing = false;
                if (refreshThread != null) {
                    refreshThread.interrupt();
                }
            });
            
        } catch (Exception e) {
            showError("Startup Error", "Failed to start application: " + e.getMessage());
            e.printStackTrace();
            Platform.exit();
        }
    }
    
    /**
     * Connect to RMI server
     */
    private void connectToServer() throws Exception {
        taskService = (TaskService) Naming.lookup(SERVER_URL);
        System.out.println("Connected to server: " + SERVER_URL);
    }
    
    /**
     * Show login/register dialog
     */
    private boolean showLoginDialog() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Task Tracker - Login");
        dialog.setHeaderText("Welcome to Task Tracker\nEnter your username");
        
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField username = new TextField();
        username.setPromptText("Username");
        username.setPrefWidth(200);
        
        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        
        dialog.getDialogPane().setContent(grid);
        Platform.runLater(username::requestFocus);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return username.getText();
            }
            return null;
        });
        
        Optional<String> result = dialog.showAndWait();
        
        if (result.isPresent() && !result.get().trim().isEmpty()) {
            currentUser = result.get().trim();
            try {
                taskService.registerUser(currentUser);
                return true;
            } catch (Exception e) {
                showError("Login Error", "Failed to register user: " + e.getMessage());
                return false;
            }
        }
        
        return false;
    }
    
    /**
     * Create main UI layout
     */
    private BorderPane createMainUI() {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("root");
        
        // Top - Header
        root.setTop(createHeader());
        
        // Center - Task table
        root.setCenter(createTaskTable());
        
        // Right - Action panel
        root.setRight(createActionPanel());
        
        // Bottom - Status bar
        root.setBottom(createStatusBar());
        
        return root;
    }
    
    /**
     * Create header section
     */
    private VBox createHeader() {
        VBox header = new VBox(10);
        header.getStyleClass().add("header");
        header.setPadding(new Insets(20));
        
        Label title = new Label("Task Tracker");
        title.getStyleClass().add("header-title");
        
        Label subtitle = new Label("Manage your tasks efficiently • Logged in as: " + currentUser);
        subtitle.getStyleClass().add("header-subtitle");
        
        statsLabel = new Label("");
        statsLabel.getStyleClass().add("stats-label");
        updateStats();
        
        header.getChildren().addAll(title, subtitle, statsLabel);
        return header;
    }
    
    /**
     * Create task table
     */
    private VBox createTaskTable() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(20));
        container.getStyleClass().add("table-container");
        
        // Filter buttons
        HBox filterBox = new HBox(10);
        filterBox.setAlignment(Pos.CENTER_LEFT);
        
        Button allBtn = new Button("All Tasks");
        Button myTasksBtn = new Button("My Tasks");
        Button todoBtn = new Button("To Do");
        Button inProgressBtn = new Button("In Progress");
        Button reviewBtn = new Button("Review");
        Button completedBtn = new Button("Completed");
        
        allBtn.getStyleClass().addAll("filter-btn", "active");
        myTasksBtn.getStyleClass().add("filter-btn");
        todoBtn.getStyleClass().add("filter-btn");
        inProgressBtn.getStyleClass().add("filter-btn");
        reviewBtn.getStyleClass().add("filter-btn");
        completedBtn.getStyleClass().add("filter-btn");
        
        allBtn.setOnAction(e -> {
            refreshTasks();
            setActiveButton(filterBox, allBtn);
        });
        myTasksBtn.setOnAction(e -> {
            filterMyTasks();
            setActiveButton(filterBox, myTasksBtn);
        });
        todoBtn.setOnAction(e -> {
            filterByStatus(Task.TaskStatus.TODO);
            setActiveButton(filterBox, todoBtn);
        });
        inProgressBtn.setOnAction(e -> {
            filterByStatus(Task.TaskStatus.IN_PROGRESS);
            setActiveButton(filterBox, inProgressBtn);
        });
        reviewBtn.setOnAction(e -> {
            filterByStatus(Task.TaskStatus.REVIEW);
            setActiveButton(filterBox, reviewBtn);
        });
        completedBtn.setOnAction(e -> {
            filterByStatus(Task.TaskStatus.COMPLETED);
            setActiveButton(filterBox, completedBtn);
        });
        
        filterBox.getChildren().addAll(allBtn, myTasksBtn, todoBtn, inProgressBtn, reviewBtn, completedBtn);
        
        // Task table
        taskTable = new TableView<>();
        taskTable.setItems(taskList);
        taskTable.getStyleClass().add("task-table");
        taskTable.setPlaceholder(new Label("No tasks found. Create your first task!"));
        
        // Columns
        TableColumn<Task, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("taskId"));
        idCol.setPrefWidth(150);
        
        TableColumn<Task, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleCol.setPrefWidth(250);
        
        TableColumn<Task, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        descCol.setPrefWidth(300);
        
        TableColumn<Task, String> assignedCol = new TableColumn<>("Assigned To");
        assignedCol.setCellValueFactory(new PropertyValueFactory<>("assignedTo"));
        assignedCol.setPrefWidth(120);
        
        TableColumn<Task, Task.TaskStatus> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setPrefWidth(120);
        statusCol.setCellFactory(column -> new TableCell<Task, Task.TaskStatus>() {
            @Override
            protected void updateItem(Task.TaskStatus status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Label statusLabel = new Label(status.getDisplayName());
                    statusLabel.getStyleClass().add("status-badge");
                    statusLabel.getStyleClass().add("status-" + status.name().toLowerCase());
                    setGraphic(statusLabel);
                }
            }
        });
        
        TableColumn<Task, Task.TaskPriority> priorityCol = new TableColumn<>("Priority");
        priorityCol.setCellValueFactory(new PropertyValueFactory<>("priority"));
        priorityCol.setPrefWidth(100);
        priorityCol.setCellFactory(column -> new TableCell<Task, Task.TaskPriority>() {
            @Override
            protected void updateItem(Task.TaskPriority priority, boolean empty) {
                super.updateItem(priority, empty);
                if (empty || priority == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Label priorityLabel = new Label(priority.getDisplayName());
                    priorityLabel.getStyleClass().add("priority-badge");
                    priorityLabel.getStyleClass().add("priority-" + priority.name().toLowerCase());
                    setGraphic(priorityLabel);
                }
            }
        });
        
        TableColumn<Task, String> updatedCol = new TableColumn<>("Last Updated");
        updatedCol.setCellValueFactory(new PropertyValueFactory<>("formattedUpdatedAt"));
        updatedCol.setPrefWidth(150);
        
        taskTable.getColumns().add(idCol);
        taskTable.getColumns().add(titleCol);
        taskTable.getColumns().add(descCol);
        taskTable.getColumns().add(assignedCol);
        taskTable.getColumns().add(statusCol);
        taskTable.getColumns().add(priorityCol);
        taskTable.getColumns().add(updatedCol);
        
        VBox.setVgrow(taskTable, Priority.ALWAYS);
        container.getChildren().addAll(filterBox, taskTable);
        
        return container;
    }
    
    /**
     * Create action panel
     */
    private VBox createActionPanel() {
        VBox panel = new VBox(15);
        panel.setPadding(new Insets(20));
        panel.setPrefWidth(280);
        panel.getStyleClass().add("action-panel");
        
        Label actionTitle = new Label("Actions");
        actionTitle.getStyleClass().add("panel-title");
        
        Button createBtn = new Button("➕  Create New Task");
        Button editBtn = new Button("✏️  Edit Task");
        Button deleteBtn = new Button("🗑️  Delete Task");
        Button refreshBtn = new Button("🔄  Refresh Now");
        Button reportBtn = new Button("📊  Generate Report");
        
        createBtn.getStyleClass().add("action-btn");
        editBtn.getStyleClass().add("action-btn");
        deleteBtn.getStyleClass().add("action-btn");
        refreshBtn.getStyleClass().add("action-btn");
        reportBtn.getStyleClass().add("action-btn");
        
        createBtn.setMaxWidth(Double.MAX_VALUE);
        editBtn.setMaxWidth(Double.MAX_VALUE);
        deleteBtn.setMaxWidth(Double.MAX_VALUE);
        refreshBtn.setMaxWidth(Double.MAX_VALUE);
        reportBtn.setMaxWidth(Double.MAX_VALUE);
        
        createBtn.setOnAction(e -> showCreateTaskDialog());
        editBtn.setOnAction(e -> showEditTaskDialog());
        deleteBtn.setOnAction(e -> deleteSelectedTask());
        refreshBtn.setOnAction(e -> refreshTasks());
        reportBtn.setOnAction(e -> generateReport());
        
        Separator sep = new Separator();
        
        Label infoTitle = new Label("Quick Info");
        infoTitle.getStyleClass().add("panel-title");
        
        Label infoText = new Label(
            "• Auto-refresh: Every 5s\n" +
            "• Double-click to edit\n" +
            "• Select row to delete\n" +
            "• All changes sync live"
        );
        infoText.getStyleClass().add("info-text");
        infoText.setWrapText(true);
        
        panel.getChildren().addAll(
            actionTitle, 
            createBtn, 
            editBtn, 
            deleteBtn, 
            refreshBtn, 
            reportBtn,
            sep,
            infoTitle,
            infoText
        );
        
        return panel;
    }
    
    /**
     * Create status bar
     */
    private HBox createStatusBar() {
        HBox statusBar = new HBox(20);
        statusBar.setPadding(new Insets(10, 20, 10, 20));
        statusBar.getStyleClass().add("status-bar");
        statusBar.setAlignment(Pos.CENTER_LEFT);
        
        statusLabel = new Label("● Connected to server");
        statusLabel.getStyleClass().add("status-label");
        
        Label autoRefreshLabel = new Label("🔄 Auto-refresh: ON");
        autoRefreshLabel.getStyleClass().add("status-label");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        statusBar.getChildren().addAll(statusLabel, spacer, autoRefreshLabel);
        
        return statusBar;
    }
    
    /**
     * Set active filter button
     */
    private void setActiveButton(HBox container, Button activeBtn) {
        for (var node : container.getChildren()) {
            if (node instanceof Button) {
                node.getStyleClass().remove("active");
            }
        }
        activeBtn.getStyleClass().add("active");
    }
    
    /**
     * Show create task dialog
     */
    private void showCreateTaskDialog() {
        Dialog<Task> dialog = new Dialog<>();
        dialog.setTitle("Create New Task");
        dialog.setHeaderText("Create a new task");
        
        ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        
        TextField titleField = new TextField();
        titleField.setPromptText("Task title");
        
        TextArea descArea = new TextArea();
        descArea.setPromptText("Task description");
        descArea.setPrefRowCount(3);
        
        ComboBox<String> assigneeCombo = new ComboBox<>();
        try {
            assigneeCombo.setItems(FXCollections.observableArrayList(taskService.getAllUsers()));
        } catch (Exception e) {
            showError("Error", "Failed to load users: " + e.getMessage());
        }
        assigneeCombo.setValue(currentUser);
        
        ComboBox<Task.TaskPriority> priorityCombo = new ComboBox<>();
        priorityCombo.setItems(FXCollections.observableArrayList(Task.TaskPriority.values()));
        priorityCombo.setValue(Task.TaskPriority.MEDIUM);
        
        grid.add(new Label("Title:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Description:"), 0, 1);
        grid.add(descArea, 1, 1);
        grid.add(new Label("Assign to:"), 0, 2);
        grid.add(assigneeCombo, 1, 2);
        grid.add(new Label("Priority:"), 0, 3);
        grid.add(priorityCombo, 1, 3);
        
        dialog.getDialogPane().setContent(grid);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                Task task = new Task(
                    titleField.getText(),
                    descArea.getText(),
                    assigneeCombo.getValue(),
                    currentUser
                );
                task.setPriority(priorityCombo.getValue());
                return task;
            }
            return null;
        });
        
        Optional<Task> result = dialog.showAndWait();
        result.ifPresent(task -> {
            try {
                taskService.createTask(task);
                updateStatus("Task created successfully");
                refreshTasks();
            } catch (Exception e) {
                showError("Error", "Failed to create task: " + e.getMessage());
            }
        });
    }
    
    /**
     * Show edit task dialog
     */
    private void showEditTaskDialog() {
        Task selectedTask = taskTable.getSelectionModel().getSelectedItem();
        if (selectedTask == null) {
            showWarning("No Selection", "Please select a task to edit");
            return;
        }
        
        Dialog<Task> dialog = new Dialog<>();
        dialog.setTitle("Edit Task");
        dialog.setHeaderText("Edit task: " + selectedTask.getTitle());
        
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        
        TextField titleField = new TextField(selectedTask.getTitle());
        TextArea descArea = new TextArea(selectedTask.getDescription());
        descArea.setPrefRowCount(3);
        
        ComboBox<String> assigneeCombo = new ComboBox<>();
        try {
            assigneeCombo.setItems(FXCollections.observableArrayList(taskService.getAllUsers()));
        } catch (Exception e) {
            showError("Error", "Failed to load users: " + e.getMessage());
        }
        assigneeCombo.setValue(selectedTask.getAssignedTo());
        
        ComboBox<Task.TaskStatus> statusCombo = new ComboBox<>();
        statusCombo.setItems(FXCollections.observableArrayList(Task.TaskStatus.values()));
        statusCombo.setValue(selectedTask.getStatus());
        
        ComboBox<Task.TaskPriority> priorityCombo = new ComboBox<>();
        priorityCombo.setItems(FXCollections.observableArrayList(Task.TaskPriority.values()));
        priorityCombo.setValue(selectedTask.getPriority());
        
        grid.add(new Label("Title:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Description:"), 0, 1);
        grid.add(descArea, 1, 1);
        grid.add(new Label("Assign to:"), 0, 2);
        grid.add(assigneeCombo, 1, 2);
        grid.add(new Label("Status:"), 0, 3);
        grid.add(statusCombo, 1, 3);
        grid.add(new Label("Priority:"), 0, 4);
        grid.add(priorityCombo, 1, 4);
        
        dialog.getDialogPane().setContent(grid);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                selectedTask.setTitle(titleField.getText());
                selectedTask.setDescription(descArea.getText());
                selectedTask.setAssignedTo(assigneeCombo.getValue());
                selectedTask.setStatus(statusCombo.getValue());
                selectedTask.setPriority(priorityCombo.getValue());
                return selectedTask;
            }
            return null;
        });
        
        Optional<Task> result = dialog.showAndWait();
        result.ifPresent(task -> {
            try {
                taskService.updateTask(task);
                updateStatus("Task updated successfully");
                refreshTasks();
            } catch (Exception e) {
                showError("Error", "Failed to update task: " + e.getMessage());
            }
        });
    }
    
    /**
     * Delete selected task
     */
    private void deleteSelectedTask() {
        Task selectedTask = taskTable.getSelectionModel().getSelectedItem();
        if (selectedTask == null) {
            showWarning("No Selection", "Please select a task to delete");
            return;
        }
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Delete task: " + selectedTask.getTitle());
        alert.setContentText("Are you sure you want to delete this task?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                taskService.deleteTask(selectedTask.getTaskId());
                updateStatus("Task deleted successfully");
                refreshTasks();
            } catch (Exception e) {
                showError("Error", "Failed to delete task: " + e.getMessage());
            }
        }
    }
    
    /**
     * Refresh tasks from server
     */
    private void refreshTasks() {
        try {
            List<Task> tasks = taskService.getAllTasks();
            Platform.runLater(() -> {
                taskList.clear();
                taskList.addAll(tasks);
                updateStats();
                updateStatus("Tasks refreshed - " + tasks.size() + " tasks loaded");
            });
        } catch (Exception e) {
            Platform.runLater(() -> updateStatus("Error refreshing tasks: " + e.getMessage()));
        }
    }
    
    /**
     * Filter tasks by current user
     */
    private void filterMyTasks() {
        try {
            List<Task> tasks = taskService.getTasksByAssignee(currentUser);
            Platform.runLater(() -> {
                taskList.clear();
                taskList.addAll(tasks);
                updateStats();
                updateStatus("Showing your tasks - " + tasks.size() + " tasks");
            });
        } catch (Exception e) {
            Platform.runLater(() -> updateStatus("Error filtering tasks: " + e.getMessage()));
        }
    }
    
    /**
     * Filter tasks by status
     */
    private void filterByStatus(Task.TaskStatus status) {
        try {
            List<Task> tasks = taskService.getTasksByStatus(status);
            Platform.runLater(() -> {
                taskList.clear();
                taskList.addAll(tasks);
                updateStats();
                updateStatus("Showing " + status.getDisplayName() + " tasks - " + tasks.size() + " tasks");
            });
        } catch (Exception e) {
            Platform.runLater(() -> updateStatus("Error filtering tasks: " + e.getMessage()));
        }
    }
    
    /**
     * Generate summary report
     */
    private void generateReport() {
        try {
            taskService.generateSummaryReport();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Summary Report");
            alert.setHeaderText("Task Summary Report Generated");
            alert.setContentText("Report has been saved to file and logged.\n\n" +
                               "Check the application directory for the report file.");
            alert.showAndWait();
            
            updateStatus("Summary report generated successfully");
        } catch (Exception e) {
            showError("Error", "Failed to generate report: " + e.getMessage());
        }
    }
    
    /**
     * Start auto-refresh thread
     */
    private void startAutoRefresh() {
        refreshThread = new Thread(() -> {
            while (isRefreshing) {
                try {
                    Thread.sleep(REFRESH_INTERVAL);
                    refreshTasks();
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        refreshThread.setDaemon(true);
        refreshThread.setName("TaskRefreshThread");
        refreshThread.start();
    }
    
    /**
     * Update status label
     */
    private void updateStatus(String message) {
        Platform.runLater(() -> statusLabel.setText("● " + message));
    }
    
    /**
     * Update statistics
     */
    private void updateStats() {
        int total = taskList.size();
        long completed = taskList.stream().filter(t -> t.getStatus() == Task.TaskStatus.COMPLETED).count();
        long inProgress = taskList.stream().filter(t -> t.getStatus() == Task.TaskStatus.IN_PROGRESS).count();
        
        statsLabel.setText(String.format("📊 Total: %d tasks  |  ✅ Completed: %d  |  ⚡ In Progress: %d",
                total, completed, inProgress));
    }
    
    /**
     * Show error dialog
     */
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Show warning dialog
     */
    private void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
