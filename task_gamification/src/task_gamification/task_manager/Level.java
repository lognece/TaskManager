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
	private String characterXP, characterLevel, userLevel;
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
		userLevel = usersContent.get(userIndex).get(3);

		return userLevel;
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