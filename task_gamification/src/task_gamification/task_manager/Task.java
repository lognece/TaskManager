package task_gamification.task_manager;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import task_gamification.CSV.CSVReader;
import task_gamification.CSV.CSVWriter;
import task_gamification.views.ToDoPanel;

public class Task extends JDialog {
    private Runnable onTaskAddedCallback;
    private JTextField titleField, descriptionField;
    private JComboBox<String> priorityBox;
    private JSpinner taskXPField;
    private JButton addButton, cancelButton;
    private String filePath;
    private String loggedInUser;
    private JTable table;

    public Task(String filePath, String loggedInUser, JTable table, Runnable onTaskAddedCallback) {
        this.filePath = filePath;
        this.loggedInUser = loggedInUser;
        this.table = table;
        this.onTaskAddedCallback = onTaskAddedCallback;
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

        addButton = new JButton("Add Task");
        addButton.addActionListener(e -> addTask());
        add(addButton);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);
    }

    private void addTask() {
        // Validate input
        if (titleField.getText().trim().isEmpty() || descriptionField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fields cannot be empty", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            List<List<String>> taskData = CSVReader.readCSV(filePath);

            List<String> newTask = new ArrayList<>();
            // Assuming the columns as [username, character, score, taskID, title, description, priority, taskXP, status]
            newTask.add(loggedInUser);
            newTask.add(""); // character is not relevant here
            newTask.add("0"); // score is initially 0
            newTask.add(""); // taskID placeholder
            newTask.add(titleField.getText());
            newTask.add(descriptionField.getText());
            newTask.add((String) priorityBox.getSelectedItem());
            newTask.add(taskXPField.getValue().toString());
            newTask.add("incomplete");

            taskData.add(newTask);
            CSVWriter.writeCSV(filePath, taskData);

            // Update ToDoPanel
            if (onTaskAddedCallback != null) {
                onTaskAddedCallback.run();
            }

            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding task: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Implement the editTask method similar to addTask, but updating existing task details.
    // Implement the deleteTask method to remove a selected task from the CSV.
}

