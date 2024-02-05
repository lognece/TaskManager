package task_gamification.task_manager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.swing.*;

import task_gamification.CSV.CSVReader;
import task_gamification.CSV.CSVWriter;
import task_gamification.helpers.ButtonHelper;
import task_gamification.helpers.GetFilePath;
import task_gamification.helpers.UIComponentHelper;

/**
 * Represents a dialog for managing tasks (add, edit, delete).
 * It provides a graphical interface for task operations.
 */
public class Task extends JDialog {
    // size and position
    public static final int popUpWidth = 600;
    public static final int popUpHight = 370;
    private static final int centerX = popUpWidth / 2;
    private static final int labelWidth = 100;
    private static final int labelHeight = 25;
    private static final int textWidth = 370;
    private static final int textHeight = 67;
    private static final int buttonWidth = 200;
    private static final int buttonHight = 30;

    private JTextField titleField, descriptionField;
    private JComboBox<String> priorityBox;
    private JSpinner taskXPField;
    private JButton addButton, cancelButton, editButton, deleteButton;
    private JLabel titleLabel, descriptionLabel, priorityLabel, taskXPLabel;
    private Insets insets;

    private String taskId, loggedInUser;

    private TaskMode mode;
    private Runnable onTaskAddedCallback;

    // path to csv files
    private GetFilePath FilePaths;
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
        insets = this.getInsets();
        initializeComponents();
        pack();
    }

    /**
     * Initializes the components of the dialog.
     * Sets up the layout and components based on the specified task mode.
     */
    private void initializeComponents() {
        // setLayout(new GridLayout(0, 2));
        setLayout(null);
        setBounds(insets.left, insets.top, popUpWidth - insets.left - insets.right,
                popUpHight - insets.bottom - insets.top);
        setResizable(false);

        createTextFields();
        createButtons();
        createLabels();
    }

    /**
     * Creates and configures the text input fields for task data.
     */
    private void createTextFields() {
        // Create and configure text fields for title, description, priority, and task XP
        titleField = UIComponentHelper.createTextField(centerX - 100, 30, textWidth, textHeight);
        descriptionField = UIComponentHelper.createTextField(centerX - 100, titleField.getY() + textHeight + 4,
                textWidth, textHeight);

        String[] priorities = {"", "Low", "Medium", "High"};
        priorityBox = UIComponentHelper.createComboBox(priorities, centerX - 100,
                descriptionField.getY() + textHeight + 4, textWidth, 25);

        taskXPField = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
        taskXPField.setBounds(centerX - 100, descriptionField.getY() + textHeight + labelHeight + 8, textWidth, textHeight);
        taskXPField.setEditor(new JSpinner.DefaultEditor(taskXPField));

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

        // Add the text input fields to the dialog
        add(titleField);
        add(descriptionField);
        add(priorityBox);
        add(taskXPField);
    }

    /**
     * Creates and configures the labels for task data fields.
     */
    private void createLabels() {
        // Create and configure labels for title, description, priority, and task XP
        titleLabel = UIComponentHelper.createLabel("Title:", centerX - (popUpWidth / 2) + 30,
                30 + (textHeight / 2) - (labelHeight / 2), labelWidth, labelHeight);

        descriptionLabel = UIComponentHelper.createLabel("Description:",centerX - (popUpWidth / 2) + 30,
                titleLabel.getY() + textHeight + 4,
                labelWidth, labelHeight);

        priorityLabel = UIComponentHelper.createLabel("Priority:", centerX - (popUpWidth / 2) + 30,
                descriptionLabel.getY() + (textHeight / 2) + (labelHeight / 2) + 4, labelWidth, labelHeight);

        taskXPLabel = UIComponentHelper.createLabel("Task XP:", centerX - (popUpWidth / 2) + 30,
                descriptionLabel.getY() + textHeight + labelHeight + 8, labelWidth, labelHeight);

        // Add the labels to the dialog
        add(titleLabel);
        add(descriptionLabel);
        add(priorityLabel);
        add(taskXPLabel);

    }

    /**
     * Creates and configures buttons based on the selected task mode (add, edit, delete).
     */
    private void createButtons() {
        int buttonX = centerX - (popUpWidth/2) + 50;
        int buttonY = popUpHight - 80;

        if (mode == TaskMode.ADD) {
            addButton = ButtonHelper.newButton("Add Task", "add", e -> addTask(),
                    buttonX, buttonY, buttonWidth, buttonHight);
            add(addButton);
        } else if (mode == TaskMode.EDIT) {
            editButton = ButtonHelper.newButton("Edit Task", "edit", e -> editTask(),
                    buttonX, buttonY, buttonWidth, buttonHight);
            add(editButton);
        } else if (mode == TaskMode.DELETE) {
            deleteButton = ButtonHelper.newButton("Delete Task", "delete", e -> deleteTask(),
                    buttonX, buttonY, buttonWidth, buttonHight);
            add(deleteButton);
        }

        cancelButton = ButtonHelper.newButton("Cancel", "cancel", e -> dispose(),
                buttonX + 300, buttonY, buttonWidth, buttonHight);
        add(cancelButton);
    }

    /**
     * Sets the value of the task XP field based on the selected priority.
     * This method is called when the user selects a priority in the combo box.
     */
    private void setTaskXPBasedOnPriority() {
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
    }

    /**
     * Sets the initial data for the task fields when in edit mode.
     * @param taskId A specifik task ID to identify the task
     * @param title The task titel the user has chosen.
     * @param description The description the user has chosen.
     * @param priority The priority the user has chosen.
     * @param taskXP The task XP the user has chosen.
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
        if (validateInput()) {
            try {
                List<List<String>> taskData = CSVReader.readCSV(taskFilePath);
                List<String> newTask = createNewTask();
                taskData.add(newTask);
                CSVWriter.writeCSV(taskFilePath, taskData);
                updateToDoPanel();
                showSuccessMessage("Task added successfully.");
                dispose();
            } catch (Exception e) {
                showError("Error adding task: " + e.getMessage());
            }
        }
    }

    /**
     * Creates a new task as a List of Strings containing task details.
     *
     * @return A List of Strings representing the new task with the following columns:
     *         [username, taskID, title, description, priority, taskXP, status]
     */
    private List<String> createNewTask() {
        List<String> newTask = new ArrayList<>();
        // [username, taskID, title, description, priority, taskXP, status]
        newTask.add(loggedInUser);
        newTask.add(UUID.randomUUID().toString());
        newTask.add(titleField.getText());
        newTask.add(descriptionField.getText());
        newTask.add((String) priorityBox.getSelectedItem());
        newTask.add(taskXPField.getValue().toString());
        newTask.add("incomplete");

        return newTask;
    }


    /**
     * Edits an existing task in the task data file.
     */
    private void editTask() {
        if (validateInput()) {
            try {
                List<List<String>> taskData = CSVReader.readCSV(taskFilePath);
                updateTaskInData(taskData);
                CSVWriter.writeCSV(taskFilePath, taskData);
                updateToDoPanel();
                showSuccessMessage("Task edited successfully.");
                dispose();
            } catch (Exception e) {
                showError("Error editing task: " + e.getMessage());
            }
        }
    }

    /**
     * Deletes the specified task from the task data file.
     */
    public void deleteTask() {
        try {
            List<List<String>> taskData = CSVReader.readCSV(taskFilePath);
            removeTaskFromData(taskData);
            CSVWriter.writeCSV(taskFilePath, taskData);
            updateToDoPanel();
            showSuccessMessage("Task deleted succesfully.");
            dispose();
        } catch (Exception e) {
            showError("Error deleting task" + e.getMessage());
        }
    }

    /**
     * Validates the user input for creating or editing a task.
     *
     * @return true if the input is valid, false otherwise.
     */
    private boolean validateInput() {
        if (titleField.getText().trim().isEmpty() || descriptionField.getText().trim().isEmpty()) {
            showError("Fields cannot be empty");
            return false;
        }
        return true;
    }

    /**
     * Updates the task details in the provided task data list.
     *
     * @param taskData The list of task data to update.
     */
    private void updateTaskInData(List<List<String>> taskData) {
        for (List<String> task : taskData) {
            if (task.get(1).equals(this.taskId)) {
                task.set(2, titleField.getText());
                task.set(3, descriptionField.getText());
                task.set(4, (String) priorityBox.getSelectedItem());
                task.set(5, taskXPField.getValue().toString());
                break;
            }
        }
    }

    /**
     * Updates the To-Do panel after a task has been added or edited.
     */

    private void updateToDoPanel() {
        if (onTaskAddedCallback != null) {
            onTaskAddedCallback.run();
        }
    }

    /**
     * Removes a task from the provided task data list.
     *
     * @param taskData The list of task data to remove the task from.
     */
    private void removeTaskFromData(List<List<String>> taskData) {
        taskData.removeIf(task -> task.get(1).equals(this.taskId));
    }

    /**
     * Displays a success message to the user.
     *
     * @param message The message to display as a success message.
     */
    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Task Operation",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to display.
     */
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message,"Error", JOptionPane.ERROR_MESSAGE);
    }
}