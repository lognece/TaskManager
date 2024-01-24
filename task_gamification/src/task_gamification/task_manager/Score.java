package task_gamification.task_manager;

import task_gamification.CSV.CSVReader;
import task_gamification.CSV.CSVWriter;
import task_gamification.entity.User;
import task_gamification.helpers.GetFilePath;

import java.io.IOException;
import java.util.List;

/**
 * Represents user score and its linked functions.
 * It provides functionalities to get and update the user score
 * and also update the linked story progress with a linked level-up.
 */
public class Score {

    private User newUserScore;
    private int currentScore, nextLevelXP, userIndex, intNextLevel;
    private String loggedInUser, userLevel, nextLevel, newStoryLine, characterName;
    private List<List<String>> csvContent, usersContent, storyContent;

    private CSVReader csvReader;
    private CSVWriter updateCSV;
    private GetLevelXP getLevelXP;

    // path to csv files
    private GetFilePath FilePaths;
    private String userFilePath = FilePaths.USER_FILE_PATH,
            storyFilePath = FilePaths.STORY_FILE_PATH,
            levelFilePath = FilePaths.LEVEL_FILE_PATH;

    public Score() {
        newUserScore = new User();
    }
    /**
     * Function to return the user score.
     */
    public int getScore(){
        return currentScore;
    }

    /**
     * Function to update the user score.
     *
     * @param newScore new score of the logged-in user.
     * @param loggedInUser The username of the currently logged-in user.
     */
    public boolean updateScore(String loggedInUser, int newScore) throws IOException {

        userIndex = newUserScore.getIndex(loggedInUser, userFilePath);

        csvReader = new CSVReader();
        csvContent = csvReader.readCSV(userFilePath);

        currentScore = Integer.parseInt(csvContent.get(userIndex).get(2));
        currentScore += newScore;

        csvContent.get(userIndex).set(2, String.valueOf(currentScore));

        updateCSV = new CSVWriter();
        updateCSV.writeCSV(userFilePath, csvContent);

        return true;
    }


}