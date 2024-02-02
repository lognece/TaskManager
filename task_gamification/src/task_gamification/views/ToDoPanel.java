package task_gamification.views;

import task_gamification.helpers.ButtonHelper;
import task_gamification.helpers.ComponentSizePanel;
import task_gamification.helpers.GetFilePath;
import task_gamification.task_manager.Score;
import task_gamification.task_manager.Task;
import task_gamification.task_manager.TaskMode;
import task_gamification.CSV.CSVReader;
import task_gamification.CSV.CSVWriter;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Class representing the To-Do panel in the app.
 * It allows users to manage their tasks (add, edit, delete) and view them in a table format.
 */

public class ToDoPanel extends ComponentSizePanel {

    private JTable table;
    private JButton addButton, editButton, deleteButton;
    private Insets insets;

    private boolean isEditMode = false;
    private String loggedInUser, taskId;

    private DefaultTableModel tableModel;
    private TaskMode mode;

    // path to csv files
    private GetFilePath FilePaths;
    private String taskFilePath = FilePaths.TASK_FILE_PATH,
            userFilePath = FilePaths.USER_FILE_PATH;

    /**
     * Constructor for ToDoPanel.
     * Initializes the panel with the file path of the task data and the currently logged-in user.
     *
     * @param taskFilePath The file path where task data is stored.
     * @param loggedInUser The username of the currently logged-in user.
     */
    public ToDoPanel(String taskFilePath, String loggedInUser) {
        this.taskFilePath = taskFilePath;
        this.loggedInUser = loggedInUser;
        insets = this.getInsets();
        initializeGUI();
        refreshTableData();
    }

    /**
     * Initializes the graphical user interface of the panel.
     * Sets up the layout, table, and scroll pane.
     */
    private void initializeGUI() {
        setLayout(null);
        configurePaneBounds();
        setupTable();
        JScrollPane scrollPane = setupScrollPane();
        setupButtons(scrollPane);
    }

    /**
     * Configures the bounds (size and position) of the panel.
     */
    private void configurePaneBounds() {
        setBounds(insets.left, insets.top, W_FRAME - insets.left - insets.right,
                H_FRAME - insets.bottom - insets.top);
    }

    /**
     * Sets up the scroll pane.
     * @return
     */
    private JScrollPane setupScrollPane() {
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(CENTER_X - (W_FRAME/2) + 30, 30, W_FRAME - 60, H_FRAME - 150);
        add(scrollPane);
        return scrollPane;
    }

    /**
     * Sets up the button configurations.
     */
    private void setupButtons(JScrollPane scrollPane) {
        setupAddButton(scrollPane);
        setupEditButton(scrollPane);
        setupDeleteButton(scrollPane);
    }

    /**
     * Sets up the "Add Task" button.
     *
     * @param scrollPane The JScrollPane used for button positioning.
     */
    private void setupAddButton(JScrollPane scrollPane) {
        // Create addButton using ButtonHelper
        addButton = ButtonHelper.newButton("Add Task", "add",
                e -> openTaskDialog(), TASK_ADD_BUTTON, scrollPane.getHeight() + 50, BUTTON_WIDTH, BUTTON_HEIGHT);
        add(addButton);
    }

    /**
     * Sets up the "Edit Task" button.
     *
     * @param scrollPane The JScrollPane used for button positioning.
     */
    private void setupEditButton(JScrollPane scrollPane) {
        editButton = ButtonHelper.newButton("Edit Task", "edit", e -> {
            isEditMode = true;
            if (isEditMode) {
                table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                table.clearSelection();
                JOptionPane.showMessageDialog(this, "Please click on the row you want to edit.",
                        "Select Row to Edit", JOptionPane.INFORMATION_MESSAGE);
            }
        }, TASK_EDIT_BUTTON, scrollPane.getHeight() + 50, BUTTON_WIDTH, BUTTON_HEIGHT);
        add(editButton);
    }

    /**
     * Sets up the "Delete Task" button.
     *
     * @param scrollPane The JScrollPane used for button positioning.
     */
    private void setupDeleteButton(JScrollPane scrollPane) {
        deleteButton = ButtonHelper.newButton("Delete Task", "delete", e -> {
            if (table.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(this,
                        "Please click on the row you want to delete.",
                        "Select Row to Delete", JOptionPane.INFORMATION_MESSAGE);
            } else {
                int selectedRow = table.getSelectedRow();
                String taskId = (String) tableModel.getValueAt(selectedRow, 0);
                if (showConfirmationDialog("Are you sure you want to delete this task?",
                        "Confirm Deletion")) {
                    Task taskDialog = new Task(taskFilePath, loggedInUser, this::refreshTableData, TaskMode.DELETE);
                    taskDialog.setTaskData(taskId, null, null, null, 0);
                    taskDialog.deleteTask();
                }
            }
        }, TASK_DELETE_BUTTON, scrollPane.getHeight() + 50, BUTTON_WIDTH, BUTTON_HEIGHT);
        add(deleteButton);
    }

    /**
     * Sets up the table to display the task data.
     * Configures the table model and removes unnecessary columns.
     */

    private void setupTable() {
        initializeTableModel();
        configureTableProperties();
        configureTableColumns();
        setupTableListeners();
    }

    /**
     * Initializes the table model for displaying task data.
     * Configures column names and cell editability.
     */
    private void initializeTableModel() {
        tableModel = new DefaultTableModel(new String[]{"Task ID", "Title", "Description",
                "Priority", "Task XP", "Status"}, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 5 ? Boolean.class : String.class; // The "Status" column
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Only the "Status" column is editable
            }
        };
    }

    /**
     * Configures properties of the table, such as creating the JTable instance and enabling row sorting.
     */
    private void configureTableProperties() {
        table = new JTable(tableModel);
        table.setAutoCreateRowSorter(true);
    }

    /**
     * Configures the table columns, hiding the "Task ID" column.
     */
    private void configureTableColumns() {
        // Configure and hide the "Task ID" column
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.removeColumn(columnModel.getColumn(0));
    }

    /**
     * Sets up listeners for the table, including a listener for changes in the "Status" column and mouse click events.
     */
    private void setupTableListeners() {
        table.getModel().addTableModelListener(e -> {
            // Listener for changes in the "Status" column
            if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 5) {
                int row = e.getFirstRow();
                if (row >= 0 && row < tableModel.getRowCount()) {
                    boolean isComplete = (boolean) tableModel.getValueAt(row, 5); // Check if the status is complete
                    String taskId = (String) tableModel.getValueAt(row, 0);

                    if (isComplete) {
                        updateTaskInCSV(taskId, true); // Update the CSV file
                        updateScoreForUser(loggedInUser, taskId);  // Call method to update the score
                        SwingUtilities.invokeLater(() -> {
                            refreshTableData(); // Refresh the data on the Event Dispatch Thread
                        });
                    }
                }
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isEditMode && table.getSelectedRow() != -1) {
                    // Open edit dialog on mouse click if in edit mode
                    int rowToEdit = table.getSelectedRow();
                    rowToEdit = table.convertRowIndexToModel(rowToEdit);
                    openEditTaskDialog(rowToEdit);
                    isEditMode = false; // Reset the edit mode
                }
            }
        });
    }

    /**
     * Shows a confirmation dialog and returns the user's choice
     */
    private boolean showConfirmationDialog(String message, String title) {
        int choice = JOptionPane.showConfirmDialog(this, message, title, JOptionPane.YES_NO_OPTION);
        return choice == JOptionPane.YES_OPTION;
    }

    /**
     * Refreshes the table data by loading completed tasks from the CSV file.
     * Only includes tasks that belong to the logged-in user and are marked as 'to-do'.
     */
    public void refreshTableData() {
        // Read data from CSV and update the table model
        try {
            tableModel.setRowCount(0);
            List<List<String>> taskData = CSVReader.readCSV(taskFilePath);

            for (List<String> row : taskData) {
                // Check if the row is for the logged in user and has enough elements
                if (row.get(0).equals(loggedInUser) && row.size() > 6 && !"done".equalsIgnoreCase(row.get(6))) {
                    // Adding row to the table
                    // userName(0), taskID(1), title(2), description(3), priority(4), taskXP(5), complete(6)
                    tableModel.addRow(new Object[]{row.get(1), row.get(2), row.get(3), row.get(4), row.get(5), false});
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Updates a task's status in the CSV file
     */
    private void updateTaskInCSV(String taskId, boolean isComplete) {
        try {
            List<List<String>> taskData = CSVReader.readCSV(taskFilePath);

            // Find and update the task
            for (List<String> task : taskData) {
                if (task.get(0).equals(loggedInUser) && task.get(1).equals(taskId)) {
                    task.set(6, isComplete ? "done" : "incomplete");
                    break;
                }
            }

            CSVWriter.writeCSV(taskFilePath, taskData);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating task: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Opens the dialog for adding a new task
     */
    private void openTaskDialog() {
        Task taskDialog = new Task(taskFilePath, loggedInUser, this::refreshTableData, TaskMode.ADD);
        configureDialog(taskDialog);
    }

    /**
     * Configures the appearance and behavior of a task dialog.
     * Sets its size, centers it relative to the ToDoPanel, and makes it visible.
     *
     * @param taskDialog The dialog to be configured.
     */
    private void configureDialog(Task taskDialog) {
        taskDialog.setSize(POP_UP_WIDTH, POP_UP_HIGHT);
        taskDialog.setLocationRelativeTo(this); // Center the dialog relative to the ToDoPanel
        taskDialog.setVisible(true);
    }

    /**
     * Opens the dialog for editing a task
     */
    private void openEditTaskDialog(int rowToEdit) {
        // Convert view index to model index in case table is sorted
        int modelRow = table.convertRowIndexToModel(rowToEdit);

        String taskId = (String) tableModel.getValueAt(rowToEdit, 0);
        String title = (String) tableModel.getValueAt(modelRow, 1);
        String description = (String) tableModel.getValueAt(modelRow, 2);
        String priority = (String) tableModel.getValueAt(modelRow, 3);
        int taskXP = Integer.parseInt((String) tableModel.getValueAt(modelRow, 4));
        Task taskDialog = new Task(taskFilePath, loggedInUser, this::refreshTableData, TaskMode.EDIT);
        taskDialog.setTaskData(taskId, title, description, priority, taskXP);
        taskDialog.setSize(POP_UP_WIDTH, POP_UP_HIGHT);
        taskDialog.setLocationRelativeTo(this); // Center the dialog
        taskDialog.setVisible(true);
    }

    /**
     * Updates the user's score based on the XP earned from completing a task.
     *
     * @param loggedInUser The username of the logged-in user.
     * @param taskId The ID of the completed task.
     */
    private void updateScoreForUser(String loggedInUser, String taskId) {
        try {
            // Retrieve the XP value of the completed task
            int taskXP = getTaskXP(taskId);

            // Update the user's score
            Score scoreManager = new Score();
            scoreManager.updateScore(loggedInUser, taskXP, userFilePath);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating score: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Retrieves the XP value associated with a task from the CSV file.
     *
     * @param taskId The ID of the task for which XP is to be retrieved.
     * @return The XP value of the task, or 0 if the task is not found.
     */
    private int getTaskXP(String taskId) {
        List<List<String>> taskData = CSVReader.readCSV(taskFilePath);
        for (List<String> task : taskData) {
            if (task.get(1).equals(taskId)) {
                return Integer.parseInt(task.get(5)); // XP is stored at index 5
            }
        }
        return 0; // Return 0 if the task is not found
    }


}



    // TODO: Refactor shared parts of adding and editing into a common method
    // TODO: Separate UI and controller logic
    // TODO: Break down into smaller methods or classes for better modularity
    // TODO: Implement an interface for task operations
    // TODO: Update user score when a task is marked as done

