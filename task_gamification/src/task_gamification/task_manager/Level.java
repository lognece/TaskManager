package task_gamification.task_manager;

import task_gamification.entity.User;
import task_gamification.helpers.GetLevelXP;
import task_gamification.CSV.CSVReader;
import task_gamification.CSV.CSVWriter;
import task_gamification.helpers.GetFilePath;
import java.util.List;
import java.util.Map;

public class Level{

	private int userIndex;
	private String characterXP, characterLevel;
	private List<List<String>> usersContent, levelContent;

	private CSVWriter csvWriter;
	private CSVReader csvReader;
	private GetLevelXP getLevelXP;
	private User user;

	// path to csv files
	private GetFilePath FilePaths;
	private String userFilePath = FilePaths.USER_FILE_PATH,
			levelFilePath = FilePaths.LEVEL_FILE_PATH;

	/**
	 * Fetches the level of a user by accessing the users.csv and the level.csv
	 *
	 * @param loggedInUser The username of the currently logged-in user.
	 * */
	public String getLevel(String loggedInUser) {

		user = new User();
		userIndex = user.getIndex(loggedInUser, userFilePath);

		csvReader = new CSVReader();
		usersContent = csvReader.readCSV(userFilePath);
		characterXP = usersContent.get(userIndex).get(2);

		levelContent = csvReader.readCSV(levelFilePath);
		characterLevel = "1";

		for (int i = 0; i < levelContent.size(); i++) {
			// assuming that XP limit is saved at index 1
			if (Integer.parseInt(levelContent.get(i).get(1)) < Integer.parseInt(characterXP)) {
				characterLevel = String.valueOf(i);
			}
		}
		return characterLevel;
	}

	public void updateUserLevel(String loggedInUser, int newLevel) {

		try {
			List<List<String>> usersContent = CSVReader.readCSV(userFilePath);

			// Find and update the user's level
			for (List<String> user : usersContent) {
				if (user.get(0).equals(loggedInUser)) { // username
					user.set(3, String.valueOf(newLevel)); // level
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