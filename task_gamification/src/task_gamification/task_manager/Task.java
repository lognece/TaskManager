package task_gamification.task_manager;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import task_gamification.CSV.CSVReader;
import task_gamification.CSV.CSVWriter;
import task_gamification.helpers.ButtonHelper;
import task_gamification.helpers.getFilePath;

/**
 * Represents a dialog for managing tasks (add, edit, delete).
 * It provides a graphical interface for task operations.
 */
public class Task extends JDialog {
    private Runnable onTaskAddedCallback;
    private JTextField titleField, descriptionField;
    private JComboBox<String> priorityBox;
    private JSpinner taskXPField;
    private JButton addButton, cancelButton, editButton, deleteButton;
    private String taskId, loggedInUser;
    private TaskMode mode;

    // path to csv files
    private getFilePath FilePaths;
    private String taskFilePath = FilePaths.TASK_FILE_PATH;


    /**
     * Constructor for the Task dialog.
     *
     * @param filePath The path to the task data file.
     * @param loggedInUser The username of the currently logged-in user.
     * @param onTaskAddedCallback A callback to execute when a task operation is completed.
     * @param mode The mode of operation (add, edit, delete).
     */
    public Task(String filePath, String loggedInUser, Runnable onTaskAddedCallback, TaskMode mode) {
        this.taskFilePath = filePath;
        this.loggedInUser = loggedInUser;
        this.onTaskAddedCallback = onTaskAddedCallback;
        this.mode = mode;
        initializeComponents();
        pack();
    }

    /**
     * Initializes the components of the dialog.
     * Sets up the layout and components based on the specified task mode.
     */
    private void initializeComponents() {
        setLayout(new GridLayout(0, 2));

        titleField = new JTextField();
        descriptionField = new JTextField();
        priorityBox = new JComboBox<>(new String[]{"", "Low", "Medium", "High"});
        taskXPField = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));

        // Sets task XP based on the selected priority
        priorityBox.addActionListener(e -> {
            String selectedPriority = (String) priorityBox.getSelectedItem();
            if ("Low".equals(selectedPriority)) {
                taskXPField.setValue(10);
            } 
            
            else if ("Medium".equals(selectedPriority)) {
                taskXPField.setValue(20);
            } 
            
            else {
                taskXPField.setValue(30);
            }
        });

        add(new JLabel("Title:"));
        add(titleField);
        add(new JLabel("Description:"));
        add(descriptionField);
        add(new JLabel("Priority:"));
        add(priorityBox);
        add(new JLabel("Task XP:"));
        add(taskXPField);
        
        // Button setup based on the task mode
        if (mode == TaskMode.ADD) {
            addButton = ButtonHelper.newButton("Add Task", "add", e -> addTask(),
                    0, 0, 0, 0);
            add(addButton);
        }
        
        else if (mode == TaskMode.EDIT) {
            editButton = ButtonHelper.newButton("Edit Task", "edit", e -> editTask(),
                    0, 0, 0, 0);
            add(editButton);
        }
        
        else if (mode == TaskMode.DELETE) {
            deleteButton = ButtonHelper.newButton("Delete Task", "delete", e -> deleteTask(),
                    0, 0, 0, 0);
            add(deleteButton);
        }

        cancelButton = ButtonHelper.newButton("Cancel", "cancel", e -> dispose(),
                0, 0, 0, 0);
        add(cancelButton);

    }
    
    /**
     * Sets the initial data for the task fields when in edit mode.
     */
	public void setTaskData(String taskId, String title, String description, String priority, int taskXP) {
        this.taskId = taskId;
        this.titleField.setText(title);
        this.descriptionField.setText(description);
        this.priorityBox.setSelectedItem(priority);
        this.taskXPField.setValue(taskXP);
    }

	/**
     * Adds a new task to the task data file.
     */
    private void addTask() {
        // Validate input
        if (titleField.getText().trim().isEmpty() || descriptionField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fields cannot be empty", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            List<List<String>> taskData = CSVReader.readCSV(taskFilePath);

            List<String> newTask = new ArrayList<>();
            // Assuming the columns as [username, taskID, title, description, priority, taskXP, status]
            newTask.add(loggedInUser);
            newTask.add(UUID.randomUUID().toString()); 
            newTask.add(titleField.getText());
            newTask.add(descriptionField.getText());
            newTask.add((String) priorityBox.getSelectedItem());
            newTask.add(taskXPField.getValue().toString());
            newTask.add("incomplete");

            taskData.add(newTask);
            CSVWriter.writeCSV(taskFilePath, taskData);

            // Update ToDoPanel
            if (onTaskAddedCallback != null) {
                onTaskAddedCallback.run();
            }

            JOptionPane.showMessageDialog(this, "Task added successfully.", "Task Added", JOptionPane.INFORMATION_MESSAGE);

            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding task: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Edits an existing task in the task data file.
     */
    private void editTask() {
        // Validate input
        if (titleField.getText().trim().isEmpty() || descriptionField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fields cannot be empty", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            List<List<String>> taskData = CSVReader.readCSV(taskFilePath);
            
            // Find and update the task in taskData
            for (List<String> task : taskData) {
                if (task.get(1).equals(this.taskId)) { // taskID is at index 1
                    task.set(2, titleField.getText()); // Title
                    task.set(3, descriptionField.getText()); // Description
                    task.set(4, (String) priorityBox.getSelectedItem()); // Priority
                    task.set(5, taskXPField.getValue().toString()); // Task XP
                    break;
                }
            }

            CSVWriter.writeCSV(taskFilePath, taskData);

            if (onTaskAddedCallback != null) {
                onTaskAddedCallback.run();
            }

            JOptionPane.showMessageDialog(this, "Task edited successfully.", "Task Edited", JOptionPane.INFORMATION_MESSAGE);

            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error editing task: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
            
    /**
     * Deletes the specified task from the task data file.
     */
    public void deleteTask() {
    	try {
            List<List<String>> taskData = CSVReader.readCSV(taskFilePath);
            taskData.removeIf(task -> task.get(1).equals(this.taskId)); // Remove the task with matching taskId

            CSVWriter.writeCSV(taskFilePath, taskData);

            if (onTaskAddedCallback != null) {
                onTaskAddedCallback.run();
            }

            JOptionPane.showMessageDialog(this, "Task deleted successfully.", "Task Deleted", JOptionPane.INFORMATION_MESSAGE);

            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting task: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Create helper methods like createButton, validateInput, createTaskRow, updateTaskRow, executeCallbackAndDispose, displayError, etc.

}

