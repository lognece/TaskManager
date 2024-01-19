package task_gamification.entity;

import task_gamification.CSV.CSVReader;
import task_gamification.CSV.CSVWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class User {

    private String userName;
    private String character;

    private int userIndex;

    public boolean createNewUser(String filePath, List<String> newUserContent){

        CSVReader csvReader = new CSVReader();
        List<List<String>> csvContent = csvReader.readCSV(filePath);
        csvContent.add(newUserContent);

        CSVWriter csvWriter = new CSVWriter();
        try {
            csvWriter.writeCSV(filePath, csvContent);
            return true;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean authenticate(String userName, String filePath){

        CSVReader csvReader = new CSVReader();
        List<List<String>> csvContent = csvReader.readCSV(filePath);

        boolean containsUsername = false;

        for ( int i = 0; i < csvContent.size(); i++) {
            // assuming that username is saved at index 0
            if (csvContent.get(i).get(0).equals(userName)) {
                containsUsername = true;
            }
        }
        return containsUsername;
    }

    public int userIndex(String userName, String filePath){

        CSVReader csvReader = new CSVReader();
        List<List<String>> csvContent = csvReader.readCSV(filePath);

        for ( int i = 0; i < csvContent.size(); i++) {
            // assuming that username is saved at index 0
            if (csvContent.get(i).get(0).equals(userName)) {
                userIndex = i;
            }
        }
        return userIndex;
    }

    public List<List<String>> userContent(String userName, String filePath){

        CSVReader csvReader = new CSVReader();
        List<List<String>> csvContent = csvReader.readCSV(filePath);

        List<List<String>> userContent = new ArrayList<>();

        for ( int i = 0; i < csvContent.size(); i++) {
            // assuming that username is saved at index 0
            if (csvContent.get(i).get(0).equals(userName)) {
                userContent.add(csvContent.get(i));
            }
        }
        return userContent;
    }

    public boolean addTask(List<String> newTask, String filePath){
    	CSVReader csvReader = new CSVReader();
        List<List<String>> existingTasks = csvReader.readCSV(filePath);
        existingTasks.add(newTask);

        CSVWriter csvWriter = new CSVWriter();
        try {
            csvWriter.writeCSV(filePath, existingTasks);
            return true;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    	
    }

    public boolean editTask(int taskID){
        return true;
    }

    public boolean deleteTask(int taskID){
        return true;
    }

    public boolean completeTask(int taskID){
        return true;
    }

    public String getUserName(){
        return userName;
    }

    public String getCharacter(){
        return character;
    }





}
