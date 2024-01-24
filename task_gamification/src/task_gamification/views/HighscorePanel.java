package task_gamification.views;

import task_gamification.helpers.GetFilePath;
import task_gamification.CSV.CSVReader;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HighscorePanel extends JPanel {

	public static final int W_FRAME = 1080;
	public static final int H_FRAME = (int) (W_FRAME / ((Math.sqrt(5) + 1) / 2));
	private DefaultTableModel tableModel;
	private JTable table;
	private GetFilePath filePaths = new GetFilePath();
	private String userFilePath = filePaths.USER_FILE_PATH;

	public HighscorePanel() {
		initializeGUI();
		refreshTableData();
	}

	private void initializeGUI() {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(W_FRAME, H_FRAME));

		setupTable();
		add(new JScrollPane(table), BorderLayout.CENTER);
	}

	private void setupTable() {
		tableModel = new DefaultTableModel(new String[]{"#", "Username", "Score"}, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // This makes the table cells non-editable
			}
		};
		table = new JTable(tableModel);
		table.setAutoCreateRowSorter(false);
	}

	public void refreshTableData() {
		try {
			tableModel.setRowCount(0); // Clear existing table data
			List<List<String>> highscoreData = CSVReader.readCSV(userFilePath);

			// Sort highscoreData based on score in descending order
			Collections.sort(highscoreData, new Comparator<List<String>>() {
				@Override
				public int compare(List<String> row1, List<String> row2) {
					int score1 = Integer.parseInt(row1.get(2));
					int score2 = Integer.parseInt(row2.get(2));
					return Integer.compare(score2, score1);
				}
			});

			int rank = 1; // Initialize rank
			for (List<String> row : highscoreData) {
				// username is at index 0 and score is at index 2
				tableModel.addRow(new Object[]{rank++, row.get(0), row.get(2)});
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	// TODO: make it look nice

}
