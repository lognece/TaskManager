package task_gamification.task_manager;

import helpers.GetLevelXP;
import task_gamification.CSV.CSVReader;
import task_gamification.CSV.CSVWriter;
import task_gamification.entity.User;

import java.io.IOException;
import java.util.List;

public class Score {

    private String loggedInUser, userLevel, nextLevel, newStoryLine, characterName;
    private int score, nextLevelXP, userIndex, intNextLevel;

    public int getScore(){
        return score;
    }

    public boolean updateScore(String loggedInUser, int score) throws IOException {

        User newUserScore = new User();
        userIndex = newUserScore.getIndex(loggedInUser, "src/users.csv");

        checkLevelChange(loggedInUser, score);

        CSVReader csvReader = new CSVReader();
        List<List<String>> csvContent = csvReader.readCSV("src/users.csv");
        csvContent.get(userIndex).set(1, String.valueOf(score));

        CSVWriter updateCSV = new CSVWriter();
        updateCSV.writeCSV("src/users.csv", csvContent);

        return true;
    }

    public boolean checkLevelChange(String loggedInUser, int newScore) {

        User user = new User();
        userLevel = user.getLevel(loggedInUser);
        nextLevel = String.valueOf(Integer.parseInt(userLevel) + 1);
        GetLevelXP getLevelXP = new GetLevelXP();
        nextLevelXP = getLevelXP.getLevelXP(nextLevel, "src/level.csv");

        if (newScore < nextLevelXP){
            return false;

        } else {
            updateStoryLine(loggedInUser);
            return true;
        }
    }

    public boolean updateStoryLine(String loggedInUser) {

        User user = new User();
        userLevel = user.getLevel(loggedInUser);
        intNextLevel = Integer.parseInt(userLevel) + 1;
        userIndex = user.getIndex(loggedInUser, "src/users.csv");
        characterName = user.getCharacter(loggedInUser);

        CSVReader csvReader = new CSVReader();
        List<List<String>> usersContent = csvReader.readCSV("src/users.csv");

        List<List<String>> storyContent = user.userContent(characterName, "src/story.csv");
        for (int i = 0; i < intNextLevel + 1; i++) {
            newStoryLine = newStoryLine + storyContent.get(i).get(2);
        }

        usersContent.get(userIndex).set(3, newStoryLine);
        return true;
    }

}
