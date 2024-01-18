package task_gamification.task_manager;

import task_gamification.CSV.CSVReader;
import task_gamification.CSV.CSVWriter;
import task_gamification.entity.User;

import java.io.IOException;
import java.util.List;

public class Score {

    private String userName;
    private int score;

    public int getScore(){
        return score;
    }

    public boolean updateScore(String userName, int score) throws IOException {

        User newUserScore = new User();
        int userIndex = newUserScore.userIndex(userName, "src/users.csv");

        CSVReader csvReader = new CSVReader();
        List<List<String>> csvContent = csvReader.readCSV("src/users.csv");
        csvContent.get(userIndex).set(1, String.valueOf(score));

        CSVWriter updateCSV = new CSVWriter();
        updateCSV.writeCSV("src/users.csv", csvContent);

        // update score value for user
        // update user content in csv
        return true;
    }

}
