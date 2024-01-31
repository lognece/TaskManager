package task_gamification.entity;

import task_gamification.CSV.CSVReader;
import task_gamification.CSV.CSVWriter;
import task_gamification.helpers.GetFilePath;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the entity user and the linked functions.
 * It provides functionalities to create a new user, authenticate a user and get user related data.
 */
public class User {

    private int index, userXP, userIndex, characterIndex, userHighscore;
    private boolean containsUsername;
    private String userName, usercharacter, characterNum, characterName, characterXP,
            characterLevel, characterStory, creationDate, eMailAddress;
    private List<List<String>> charactersContent, characterContent, levelContent,
            levelIndex, csvContent, usersContent, userContent, existingTasks;

    private CSVWriter csvWriter;
    private CSVReader csvReader;

    // path to csv files
    private GetFilePath FilePaths;
    private String taskFilePath = FilePaths.TASK_FILE_PATH,
            userFilePath = FilePaths.USER_FILE_PATH,
            characterFilePath = FilePaths.CHARACTER_FILE_PATH,
            storyFilePath = FilePaths.STORY_FILE_PATH,
            levelFilePath = FilePaths.LEVEL_FILE_PATH;


    /**
     * Creates a new user adding the user related content to the users.csv
     *
     * @param filePath The file path where user data is stored.
     * @param loggedInUser The username of the currently logged-in user.
     * */
    public boolean createNewUser(String filePath, List<String> newUserContent){

        csvReader = new CSVReader();
        csvContent = csvReader.readCSV(filePath);
        csvContent.add(newUserContent);

        csvWriter = new CSVWriter();
        try {
            csvWriter.writeCSV(filePath, csvContent);
            return true;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Authenticates a username with the content in users.csv
     *
     * @param filePath The file path where user data is stored.
     * @param userName The username of the currently logged-in user.
     * */
    public boolean authenticate(String userName){

        csvReader = new CSVReader();
        csvContent = csvReader.readCSV(userFilePath);

        containsUsername = false;

        for ( int i = 0; i < csvContent.size(); i++) {
            // assuming that username is saved at index 0
            if (csvContent.get(i).get(0).equals(userName)) {
                containsUsername = true;
            }
        }
        return containsUsername;
    }

    public boolean passwordAuthentification(String userName, String password) {

        csvReader = new CSVReader();
        csvContent = csvReader.readCSV(userFilePath);

        containsUsername = false;

        for ( int i = 0; i < csvContent.size(); i++) {
            // assuming that username is saved at index 0
            if (csvContent.get(i).get(0).equals(userName) &&
                    csvContent.get(i).get(5).equals(password)) {
                containsUsername = true;
            }
        }
        return containsUsername;

    }

    /**
     * Fetches the index of a user in the users.csv
     *
     * @param filePath The file path where user data is stored.
     * @param loggedInUser The username of the currently logged-in user.
     * */
    public int getIndex(String loggedInUser, String filePath){

        csvReader = new CSVReader();
        csvContent = csvReader.readCSV(filePath);

        for ( int i = 0; i < csvContent.size(); i++) {
            // assuming that username is saved at index 0
            if (csvContent.get(i).get(0).equals(loggedInUser)) {
                index = i;
            }
        }
        return index;
    }

    /**
     * Fetches the experience points (XP) of a user from the users.csv
     *
     * @param filePath The file path where user data is stored.
     * @param loggedInUser The username of the currently logged-in user.
     * */
    public int getXP(String loggedInUser){

        csvReader = new CSVReader();
        csvContent = csvReader.readCSV(userFilePath);

        for ( int i = 0; i < csvContent.size(); i++) {
            // assuming that username is saved at index 0
            if (csvContent.get(i).get(0).equals(loggedInUser)) {
                userXP = Integer.parseInt(csvContent.get(i).get(2));
            }
        }
        return userXP;
    }

    /**
     * Fetches the character name of a user chosen character from the users.csv
     *
     * @param loggedInUser The username of the currently logged-in user.
     * */
    public String getCharacter(String loggedInUser) {

        userIndex = getIndex(loggedInUser, userFilePath);

        csvReader = new CSVReader();
        usersContent = csvReader.readCSV(userFilePath);
        characterNum = usersContent.get(userIndex).get(1);

        characterIndex = getIndex(characterNum, characterFilePath);
        charactersContent = csvReader.readCSV(characterFilePath);
        characterName = charactersContent.get(characterIndex).get(2);

        return characterName;
    }

    /**
     * Fetches the creatin date of a users account from the users.csv
     *
     * @param loggedInUser The username of the currently logged-in user.
     * */
    public String getCreationDate(String loggedInUser) {

        userIndex = getIndex(loggedInUser, userFilePath);

        csvReader = new CSVReader();
        usersContent = csvReader.readCSV(userFilePath);
        creationDate = usersContent.get(userIndex).get(4);

        return creationDate;
    }

    /**
     * Fetches the creatin date of a users account from the users.csv
     *
     * @param loggedInUser The username of the currently logged-in user.
     * */
    public String getEMail(String loggedInUser) {

        userIndex = getIndex(loggedInUser, userFilePath);

        csvReader = new CSVReader();
        usersContent = csvReader.readCSV(userFilePath);
        eMailAddress = usersContent.get(userIndex).get(6);

        return eMailAddress;
    }


    /**
     * Fetches the user content.
     *
     * @param userName The username of the currently logged-in user when handeling the users.csv
     * */
    public List<List<String>> userContent(String userName, String filePath){

        csvReader = new CSVReader();
        csvContent = csvReader.readCSV(filePath);

        userContent = new ArrayList<>();

        for (int i = 0; i < csvContent.size(); i++) {
            // assuming that username is saved at index 0
            if (csvContent.get(i).get(0).equals(userName)) {
                userContent.add(csvContent.get(i));
            }
        }
        return userContent;
    }

    /**
     * Fetches the user highscore as requested in "Implementierung 4.".
     *
     * @param loggedInUser The username of the currently logged-in user when handeling the users.csv
     * */
    public int getUserHighscore(String loggedInUser, String filePath) {

        userIndex = getIndex(loggedInUser, filePath);

        csvReader = new CSVReader();
        usersContent = csvReader.readCSV(filePath);
        userHighscore = Integer.parseInt(usersContent.get(userIndex).get(2));

        return userHighscore;

    }

    /**
     * Fetches the username.
     * */
    public String getUserName(){
        return userName;
    }

}