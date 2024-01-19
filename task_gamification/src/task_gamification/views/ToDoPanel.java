package task_gamification.views;

import task_gamification.task_manager.Task;
import task_gamification.CSV.CSVReader;
import task_gamification.CSV.CSVWriter;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ToDoPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private String filePath;
    private String loggedInUser;

    public ToDoPanel(String filePath, String loggedInUser) {
        this.filePath = filePath;
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
        
        JButton btnEdit = new JButton("Edit");
        // create action listeners for edit abd delete
        //setupButton(btnEdit, e -> openEditTaskDialog(table.getSelectedRow()));
        
        JButton btnDelete = new JButton("Delete");
        //setupButton(btnDelete, e -> deleteTask(table.getSelectedRow()));
        
        addTaskPanel.add(addButton);
        addTaskPanel.add(btnEdit);
        addTaskPanel.add(btnDelete);
    }

    private void setupButton(JButton button, ActionListener actionListener) {
        button.setMinimumSize(new Dimension(100, 25));
        button.setMaximumSize(new Dimension(100, 25));
        button.addActionListener(actionListener);
    }

    private void setupTable() {
        tableModel = new DefaultTableModel(new String[]{"Title", "Description", "Priority", "Task XP", "Status"}, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 4 ? Boolean.class : String.class; // The "Status" column
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Only the "Status" column is editable
            }
        };
                
        table = new JTable(tableModel);
        table.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 4) {
                int row = e.getFirstRow();
                boolean isComplete = (boolean) tableModel.getValueAt(row, 4);
                updateTaskInCSV(row, isComplete);
            }
        });
        
        table.setAutoCreateRowSorter(true);
    }

    public void refreshTableData() {
        try {
            tableModel.setRowCount(0);
            List<List<String>> taskData = CSVReader.readCSV(filePath);

            for (List<String> row : taskData) {
                // Check if the row is for the logged in user and has enough elements
                if (row.get(0).equals(loggedInUser) && row.size() > 8 && !"done".equalsIgnoreCase(row.get(8))) {
                    // Adding row to the table
                    tableModel.addRow(new Object[]{row.get(4), row.get(5), row.get(6), row.get(7), false});
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void updateTaskInCSV(int row, boolean isComplete) {
        try {
            List<List<String>> taskData = CSVReader.readCSV(filePath);

            // Find and update the task
            for (List<String> task : taskData) {
                if (task.get(0).equals(loggedInUser) && task.get(4).equals(tableModel.getValueAt(row, 0))) {
                    task.set(8, isComplete ? "done" : "incomplete");
                    break;
                }
            }

            CSVWriter.writeCSV(filePath, taskData);
            refreshTableData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating task: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openTaskDialog() {
        Task taskDialog = new Task(filePath, loggedInUser, table, this::refreshTableData);
        taskDialog.setVisible(true);
    }
    
    //add dialog for editing and update refresh table/updatetaskincsv to work with edit and delete
    // when the task is checked as done the score should be updated (score + taskXP)
    
}

