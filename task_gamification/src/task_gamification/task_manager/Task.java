package task_gamification.task_manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Task {

    private int taskID;
    private String taskTitle;
    private String taskDescription;
    private String taskPriority;
    private int taskXP;
    private boolean complete;
    private String userName;

    public List<String> getDetails(int taskID){
        List<String> taskDetails = new ArrayList<>();
        return taskDetails;
    }

    public boolean getComplete(int taskID){

        return complete;
    }

    public void sumXP(int score, int taskXP) throws IOException {
        int updatedScore = score + taskXP;

        Score newScore = new Score();
        newScore.updateScore(userName, updatedScore);
    }
}
