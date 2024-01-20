package task_gamification.views;

import task_gamification.CSV.CSVReader;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import java.awt.*;
import java.util.List;

public class DonePanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private String taskFilePath;
    private String loggedInUser;
    
    public DonePanel(String taskFilePath, String loggedInUser) {
        this.taskFilePath = taskFilePath;
        this.loggedInUser = loggedInUser;
        initializeGUI();
    }

    private void initializeGUI() {
        setLayout(new BorderLayout());
        setupTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);  
        refreshTableData();
    }

    private void setupTable() {
        tableModel = new DefaultTableModel(new String[]{"Task ID", "Title", "Description", "Priority", "Task XP", "Status"}, 0);
                
        table = new JTable(tableModel);
        
        
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.removeColumn(columnModel.getColumn(0)); // remove taskid column from view
        columnModel.removeColumn(columnModel.getColumn(4)); // remove status column from view
        
        table.setAutoCreateRowSorter(true);
    }   
    
    public void refreshTableData() {
        try {
            tableModel.setRowCount(0);
            List<List<String>> taskData = CSVReader.readCSV(taskFilePath);

            for (List<String> row : taskData) {
                // Check if the row is for the logged in user and has enough elements
                if (row.get(0).equals(loggedInUser) && "done".equalsIgnoreCase(row.get(6))) {
                    // Adding row to the table
                	// userName(0), taskID(1), title(2), description(3), priority(4), taskXP(5), complete(6)
                    tableModel.addRow(new Object[]{row.get(1), row.get(2), row.get(3), row.get(4), row.get(5)});
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}

