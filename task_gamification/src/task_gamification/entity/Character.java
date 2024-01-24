package task_gamification.entity;

import task_gamification.CSV.CSVReader;
import task_gamification.helpers.GetFilePath;
import task_gamification.task_manager.Level;

import java.util.List;

/**
 * Represents the entity character and the linked functions.
 *
 */
public class Character {

    private int characterLevel;
    private String characterName, characterStory,loggedInUser;
    private List<List<String>> characterContent;

    private CSVReader csvReader;
    private User user;
    private Level level;

    // path to csv files
    private GetFilePath FilePaths;
    private String storyFilePath = FilePaths.STORY_FILE_PATH;


    /**
     * Fetches the story of a users chosen character corresponding to its level.
     *
     * @param loggedInUser The username of the currently logged-in user.
     * */
    public String getStory(String loggedInUser) {

        user = new User();
        level = new Level();
        characterName = user.getCharacter(loggedInUser);
        characterLevel = Integer.parseInt(level.getLevel(loggedInUser));

        csvReader = new CSVReader();
        characterContent = characterContent(characterName, storyFilePath);

        characterStory = characterContent.get(0).get(2);
        for (int i = 1; i < characterLevel; i++) {
            characterStory = characterStory + "\n\n" + characterContent.get(i).get(2);
        }
        return characterStory;
    }

    /**
     * Fetches the character content.
     *
     * @param characterName the character name of the users chosen character when handeling the characters.csv.
     * */
    public List<List<String>> characterContent(String characterName, String filepath){
        characterContent = user.userContent(characterName, filepath);
        return characterContent;
    }


}