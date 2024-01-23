package task_gamification.views;

import task_gamification.CSV.CSVReader;
import task_gamification.helpers.GetFilePath;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import java.awt.*;
import java.util.List;

/**
 * Represents the panel to display completed tasks.
 * It provides functionalities to view completed tasks for a logged-in user.
 */
public class DonePanel extends JPanel {

    private JTable table;
    private JScrollPane scrollPane;

    private String loggedInUser;
    private List<List<String>> taskData;

    private DefaultTableModel tableModel;
    private TableColumnModel columnModel;

    // path to csv files
    private GetFilePath FilePaths;
    private String taskFilePath = FilePaths.TASK_FILE_PATH;


    /**
     * Constructor for DonePanel.
     * Initializes the panel with the file path of the task data and the currently logged-in user.
     *
     * @param taskFilePath The file path where task data is stored.
     * @param loggedInUser The username of the currently logged-in user.
     */
    public DonePanel(String taskFilePath, String loggedInUser) {
        this.taskFilePath = taskFilePath;
        this.loggedInUser = loggedInUser;
        initializeGUI();
    }
    
    /**
     * Initializes the graphical user interface of the panel.
     * Sets up the layout, table, and scroll pane.
     */
    private void initializeGUI() {
        setLayout(new BorderLayout());
        setupTable();
        scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);  
        refreshTableData();
    }

    /**
     * Sets up the table to display the task data.
     * Configures the table model and removes unnecessary columns.
     */
    private void setupTable() {
    	// Define column names for the table
        tableModel = new DefaultTableModel(new String[]{"Task ID", "Title", "Description", "Priority", "Task XP", "Status"}, 0);
                
        table = new JTable(tableModel);
        
        // Configure table column model and remove unnecessary columns for display
        columnModel = table.getColumnModel();
        columnModel.removeColumn(columnModel.getColumn(0)); // remove taskid column from view
        columnModel.removeColumn(columnModel.getColumn(4)); // remove status column from view
        
        // Enable sorting of table rows
        table.setAutoCreateRowSorter(true);
    }   
    
    /**
     * Refreshes the table data by loading completed tasks from the CSV file.
     * Only includes tasks that belong to the logged-in user and are marked as 'done'.
     */
    public void refreshTableData() {
        try {
            tableModel.setRowCount(0); // Clear existing table data
            taskData = CSVReader.readCSV(taskFilePath);

            for (List<String> row : taskData) {
            	// Iterate through each task and add it to the table if it belongs to the user and is completed
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

