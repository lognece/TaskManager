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

/**
 * Class representing the Highscore panel in the app.
 * It allows users to see their score in comparison to other users
 */
public class HighscorePanel extends JPanel {

	// size and position
	public static final int W_FRAME = 1080;
	public static final int H_FRAME = (int) (W_FRAME / ((Math.sqrt(5) + 1) / 2));
	private static final int centerX = W_FRAME / 2;

	private JTable table;
	private Insets insets;

	private int score1, score2;
	private List<List<String>> highscoreData;

	private DefaultTableModel tableModel;

	// path to csv files
	private GetFilePath filePaths = new GetFilePath();
	private String userFilePath = filePaths.USER_FILE_PATH;

	/**
	 * Constructor for HighscorePanel.
	 * Initializes the panel with the highscore information.
	 */
	public HighscorePanel() {
		insets = this.getInsets();
		initializeGUI();
		refreshTableData();
	}

	/**
	 * Initializes the graphical user interface of the panel.
	 * Sets up the layout, lables, progressbar and textfield.
	 */
	private void initializeGUI() {
		setLayout(null);
		setBounds(insets.left, insets.top, W_FRAME - insets.left - insets.right,
				H_FRAME - insets.bottom - insets.top);

		// setLayout(new BorderLayout());
		// setPreferredSize(new Dimension(W_FRAME, H_FRAME));

		setupTable();
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(centerX - (W_FRAME/2) + 30, 30, W_FRAME - 60, H_FRAME - 110);
		add(scrollPane);
	}

	/**
	 * Sets up the table configurations.
	 */
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

	/**
	 * Refreshes the table data to always stay up to date.
	 */
	public void refreshTableData() {
		try {
			tableModel.setRowCount(0); // Clear existing table data
			highscoreData = CSVReader.readCSV(userFilePath);

			// Sort highscoreData based on score in descending order
			Collections.sort(highscoreData, new Comparator<List<String>>() {
				@Override
				public int compare(List<String> row1, List<String> row2) {
					score1 = Integer.parseInt(row1.get(2));
					score2 = Integer.parseInt(row2.get(2));
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
