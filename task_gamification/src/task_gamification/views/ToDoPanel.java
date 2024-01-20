package task_gamification.views;

import task_gamification.task_manager.Task;
import task_gamification.task_manager.TaskMode;
import task_gamification.CSV.CSVReader;
import task_gamification.CSV.CSVWriter;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ToDoPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private String taskFilePath;
    private String loggedInUser;
    private String taskId;
    private boolean isEditMode = false;

    public ToDoPanel(String taskFilePath, String loggedInUser) {
        this.taskFilePath = taskFilePath;
        this.loggedInUser = loggedInUser;
        initializeGUI();
        refreshTableData();
    }

    private void initializeGUI() {
        setLayout(new BorderLayout());
        setupTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel addTaskPanel = new JPanel();
        add(addTaskPanel, BorderLayout.SOUTH);
        
        JButton addButton = new JButton("Add Task");
        setupButton(addButton, e -> openTaskDialog());
        
        JButton editButton = new JButton("Edit");
        setupButton(editButton, e -> {
        	isEditMode = true;
        	if (isEditMode) {
        		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        		table.clearSelection();
        		JOptionPane.showMessageDialog(this,
                        "Please click on the row you want to edit.",
                        "Select Row to Edit",
                        JOptionPane.INFORMATION_MESSAGE);
        	}
        });
        
        JButton btnDelete = new JButton("Delete");
        //setupButton(btnDelete, e -> deleteTask(table.getSelectedRow()));
        
        addTaskPanel.add(addButton);
        addTaskPanel.add(editButton);
        addTaskPanel.add(btnDelete);
    }

	private void setupButton(JButton button, ActionListener actionListener) {
        button.setMinimumSize(new Dimension(100, 25));
        button.setMaximumSize(new Dimension(100, 25));
        button.addActionListener(actionListener);
    }

    private void setupTable() {
        tableModel = new DefaultTableModel(new String[]{"Task ID", "Title", "Description", "Priority", "Task XP", "Status"}, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 5 ? Boolean.class : String.class; // The "Status" column
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Only the "Status" column is editable
            }
        };
                
        table = new JTable(tableModel);
        table.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 5) {
                int row = e.getFirstRow();
                if (row >= 0 && row < tableModel.getRowCount()) {
                    boolean isComplete = (boolean) tableModel.getValueAt(row, 5); // Check if the status is complete
                    String taskId = (String) tableModel.getValueAt(row, 0);

                    if (isComplete) {
                        updateTaskInCSV(taskId, true); // Update the CSV file
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
                    int rowToEdit = table.getSelectedRow();
                    // Convert view index to model index in case table is sorted
                    rowToEdit = table.convertRowIndexToModel(rowToEdit);
                    openEditTaskDialog(rowToEdit);
                    isEditMode = false; // Reset the edit mode
                }
            }
        });

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.removeColumn(columnModel.getColumn(0));
        
        table.setAutoCreateRowSorter(true);
    }

    public void refreshTableData() {
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
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


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
            JOptionPane.showMessageDialog(this, "Error updating task: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openTaskDialog() {
        Task taskDialog = new Task(taskFilePath, loggedInUser, this::refreshTableData, TaskMode.ADD);
        taskDialog.setVisible(true);
    }
    

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
	   taskDialog.setVisible(true);
	}
    
    //add delete
    // when the task is checked as done the score should be updated (score + taskXP)
    
}

