package task_gamification.task_manager;

import task_gamification.entity.User;
import task_gamification.CSV.CSVReader;
import task_gamification.CSV.CSVWriter;
import task_gamification.helpers.GetFilePath;
import java.util.List;

/**
 * Class managing all methods related to level management.
 */
public class Level{

	private int userIndex, userXP, currentLevelXP, nextLevelXP, progressValue, levelXP;
	private String characterXP, characterLevel, userLevel;
	private List<List<String>> usersContent, levelContent;

	private CSVWriter csvWriter;
	private CSVReader csvReader;
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

	/**
	 * Fetches the XP corresponding to a level using the level.csv
	 *
	 * @param level The level of the currently logged-in user.
	 */
	public int getLevelXP(String level) {

		csvReader = new CSVReader();
		levelContent = csvReader.readCSV(levelFilePath);

		for ( int i = 0; i < levelContent.size(); i++) {
			// assuming that username is saved at index 0
			if (levelContent.get(i).get(0).equals(level)) {
				levelXP = Integer.parseInt(levelContent.get(i).get(1));
			}
		}
		return levelXP;

	}

	/**
	 * Updates the users level.
	 *
	 * @param loggedInUser The username of the currently logged-in user.
	 * @param newLevel The users new level.
	 */
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

	/**
	 * Calculates progress value for progress bar.
	 *
	 * @param currentLevel The users current level.
	 * @param userXP The users current XP.
	 */
	public int getProgressValue(int currentLevel, int userXP) {
		currentLevelXP = getLevelXP(String.valueOf(currentLevel));
		nextLevelXP = getLevelXP(String.valueOf(currentLevel + 1));
		progressValue = ((userXP - currentLevelXP) * 100)  / (nextLevelXP - currentLevelXP);

		return progressValue;
	}
}