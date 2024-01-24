package task_gamification.task_manager;

import task_gamification.helpers.GetLevelXP;
import task_gamification.CSV.CSVReader;
import task_gamification.CSV.CSVWriter;
import task_gamification.helpers.GetFilePath;
import java.util.List;
import java.util.Map;

public class Level {
	private GetLevelXP getLevelXP;
	private String userFilePath = GetFilePath.USER_FILE_PATH;
	private String levelFilePath;

	public Level(String levelFilePath) {
		this.levelFilePath = levelFilePath;
		this.getLevelXP = new GetLevelXP();
	}

	public int determineLevel(int userXP) {
		Map<Integer, Integer> levelXPs = getLevelXP.getAllLevelXPs(levelFilePath);
		int userLevel = 0;

		for (Map.Entry<Integer, Integer> entry : levelXPs.entrySet()) {
			if (userXP >= entry.getValue()) {
				userLevel = entry.getKey();
			} else {
				break;
			}
		}

		return userLevel;
	}

	public void updateUserLevel(String loggedInUser, int newLevel) {
		CSVReader csvReader;
		CSVWriter updateCSV;

		try {
			List<List<String>> usersContent = CSVReader.readCSV(userFilePath);

			// Find and update the user's level
			for (List<String> user : usersContent) {
				if (user.get(0).equals(loggedInUser)) { // username
					user.set(4, String.valueOf(newLevel)); // level
					break;
				}
			}

			// Write the updated user data back to the CSV file
			CSVWriter.writeCSV(userFilePath, usersContent);
		} catch (Exception e) {
			e.printStackTrace(); // Handle exceptions appropriately
		}
	}
}