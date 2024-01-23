package task_gamification.task_manager;

import helpers.GetLevelXP;
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

    private int score, nextLevelXP, userIndex, intNextLevel;
    private String loggedInUser, userLevel, nextLevel, newStoryLine, characterName;
    private List<List<String>> csvContent, usersContent, storyContent;

    private User newUserScore, user;
    private CSVReader csvReader;
    private CSVWriter updateCSV;
    private GetLevelXP getLevelXP;

    // path to csv files
    private GetFilePath FilePaths;
    private String userFilePath = FilePaths.USER_FILE_PATH,
            storyFilePath = FilePaths.STORY_FILE_PATH,
            levelFilePath = FilePaths.LEVEL_FILE_PATH;

    /**
     * Function to return the user score.
     */
    public int getScore(){
        return score;
    }

    /**
     * Function to update the user score.
     *
     * @param newScore new score of the logged-in user.
     * @param loggedInUser The username of the currently logged-in user.
     */
    public boolean updateScore(String loggedInUser, int newScore) throws IOException {

        newUserScore = new User();
        userIndex = newUserScore.getIndex(loggedInUser, userFilePath);

        checkLevelChange(loggedInUser, score);

        csvReader = new CSVReader();
        csvContent = csvReader.readCSV(userFilePath);
        csvContent.get(userIndex).set(1, String.valueOf(score));

        updateCSV = new CSVWriter();
        updateCSV.writeCSV(userFilePath, csvContent);

        return true;
    }

    /**
     * Function to check if the new score triggers a level-up.
     *
     * @param newScore new score of the logged-in user.
     * @param loggedInUser The username of the currently logged-in user.
     */
    public boolean checkLevelChange(String loggedInUser, int newScore) {

        user = new User();
        userLevel = user.getLevel(loggedInUser);
        nextLevel = String.valueOf(Integer.parseInt(userLevel) + 1);
        getLevelXP = new GetLevelXP();
        nextLevelXP = getLevelXP.getLevelXP(nextLevel, levelFilePath);

        if (newScore < nextLevelXP){
            return false;

        } else {
            updateStoryLine(loggedInUser);
            return true;
        }
    }

    /**
     * Function to update the character story.
     *
     * @param loggedInUser The username of the currently logged-in user.
     */
    public boolean updateStoryLine(String loggedInUser) {

        user = new User();
        userLevel = user.getLevel(loggedInUser);
        intNextLevel = Integer.parseInt(userLevel) + 1;
        userIndex = user.getIndex(loggedInUser, userFilePath);
        characterName = user.getCharacter(loggedInUser);

        csvReader = new CSVReader();
        usersContent = csvReader.readCSV(userFilePath);

        storyContent = user.userContent(characterName, storyFilePath);
        for (int i = 0; i < intNextLevel + 1; i++) {
            newStoryLine = newStoryLine + storyContent.get(i).get(2);
        }

        usersContent.get(userIndex).set(3, newStoryLine);
        return true;
    }

}
