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

public class Task extends JDialog {
    private Runnable onTaskAddedCallback;
    private JTextField titleField, descriptionField;
    private JComboBox<String> priorityBox;
    private JSpinner taskXPField;
    private JButton addButton, cancelButton, editButton, deleteButton;
    private String taskFilePath, taskId, loggedInUser;
    private TaskMode mode;
    
    public Task(String filePath, String loggedInUser, Runnable onTaskAddedCallback, TaskMode mode) {
        this.taskFilePath = filePath;
        this.loggedInUser = loggedInUser;
        this.onTaskAddedCallback = onTaskAddedCallback;
        this.mode = mode;
        initializeComponents();
        pack();
    }

    private void initializeComponents() {
        setLayout(new GridLayout(0, 2));

        titleField = new JTextField();
        descriptionField = new JTextField();
        priorityBox = new JComboBox<>(new String[]{"", "Low", "Medium", "High"});
        taskXPField = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));

        priorityBox.addActionListener(e -> {
            String selectedPriority = (String) priorityBox.getSelectedItem();
            if ("Low".equals(selectedPriority)) {
                taskXPField.setValue(10);
            } else if ("Medium".equals(selectedPriority)) {
                taskXPField.setValue(20);
            } else {
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
        
        if (mode == TaskMode.ADD) {
        	addButton = new JButton("Add Task");
            addButton.addActionListener(e -> addTask());
            add(addButton);            
        }
        
        else if (mode == TaskMode.EDIT) {
            editButton = new JButton("Edit Task");
            editButton.addActionListener(e -> editTask());
            add(editButton);
        }
        
        else if (mode == TaskMode.DELETE) {
        	deleteButton = new JButton("Delete Task");
            deleteButton.addActionListener(e -> deleteTask());
            add(deleteButton);
        }
        
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);

    }
    
	public void setTaskData(String taskId, String title, String description, String priority, int taskXP) {
        this.taskId = taskId;
        this.titleField.setText(title);
        this.descriptionField.setText(description);
        this.priorityBox.setSelectedItem(priority);
        this.taskXPField.setValue(taskXP);
    }

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

            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding task: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
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

            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error editing task: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
            
    
    public void deleteTask() {
    	try {
            List<List<String>> taskData = CSVReader.readCSV(taskFilePath);
            taskData.removeIf(task -> task.get(1).equals(this.taskId)); // Remove the task with matching taskId

            CSVWriter.writeCSV(taskFilePath, taskData);

            if (onTaskAddedCallback != null) {
                onTaskAddedCallback.run();
            }

            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting task: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Implement the deleteTask method to remove a selected task from the CSV.
}

