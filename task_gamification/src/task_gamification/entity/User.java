package task_gamification.entity;

import task_gamification.CSV.CSVReader;
import task_gamification.CSV.CSVWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class User {

    private String userName, usercharacter, characterNum, characterName, characterXP, characterLevel, characterStory, creationDate;
    private int index, userXP, userIndex, characterIndex;
    private List<List<String>> charactersContent, levelContent, levelIndex;

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

    public int getIndex(String string, String filePath){

        CSVReader csvReader = new CSVReader();
        List<List<String>> csvContent = csvReader.readCSV(filePath);

        for ( int i = 0; i < csvContent.size(); i++) {
            // assuming that username is saved at index 0
            if (csvContent.get(i).get(0).equals(string)) {
                index = i;
            }
        }
        return index;
    }

    public int getXP(String string, String filePath){

        CSVReader csvReader = new CSVReader();
        List<List<String>> csvContent = csvReader.readCSV(filePath);

        for ( int i = 0; i < csvContent.size(); i++) {
            // assuming that username is saved at index 0
            if (csvContent.get(i).get(0).equals(string)) {
                userXP = Integer.parseInt(csvContent.get(i).get(2));
            }
        }
        return userXP;
    }

    public String getCharacter(String userName) {

        userIndex = getIndex(userName, "src/users.csv");

        CSVReader csvReader = new CSVReader();
        List<List<String>> usersContent = csvReader.readCSV("src/users.csv");
        characterNum = usersContent.get(userIndex).get(1);

        characterIndex = getIndex(characterNum, "src/characters.csv");
        charactersContent = csvReader.readCSV("src/characters.csv");
        characterName = charactersContent.get(characterIndex).get(2);

        return characterName;
    }

    public String getCreationDate(String userName) {

        userIndex = getIndex(userName, "src/users.csv");

        CSVReader csvReader = new CSVReader();
        List<List<String>> usersContent = csvReader.readCSV("src/users.csv");
        creationDate = usersContent.get(userIndex).get(3);

        return creationDate;
    }

    public String getLevel(String loggedInUser) {

        userIndex = getIndex(loggedInUser, "src/users.csv");

        CSVReader csvReader = new CSVReader();
        List<List<String>> usersContent = csvReader.readCSV("src/users.csv");
        characterXP = usersContent.get(userIndex).get(2);

        levelContent = csvReader.readCSV("src/level.csv");
        characterLevel = "1";

        for (int i = 0; i < levelContent.size(); i++) {
            // assuming that XP limit is saved at index 1
            if (Integer.parseInt(levelContent.get(i).get(1)) < Integer.parseInt(characterXP)) {
                characterLevel = String.valueOf(i);
            }
        }

        return characterLevel;
    }

    public String getStory(String loggedInUser) {

        characterName = getCharacter(loggedInUser);
        characterLevel = getLevel(loggedInUser);

        CSVReader csvReader = new CSVReader();
        List<List<String>> characterContent = userContent(characterName, "src/story.csv");

        characterStory = characterContent.get(0).get(2);
        for (int i = 1; i < Integer.parseInt(characterLevel); i++) {
            characterStory = characterStory + "\n\n" + characterContent.get(i).get(2);
        }
        return characterStory;
    }

    public List<List<String>> userContent(String userName, String filePath){

        CSVReader csvReader = new CSVReader();
        List<List<String>> csvContent = csvReader.readCSV(filePath);

        List<List<String>> userContent = new ArrayList<>();

        for (int i = 0; i < csvContent.size(); i++) {
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







}
