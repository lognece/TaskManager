package task_gamification.helpers;

import task_gamification.CSV.CSVReader;
import task_gamification.helpers.GetFilePath;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for fetching level-related XP.
 */
public class GetLevelXP {

    // Path to the level data file.
    private String levelFilePath = GetFilePath.LEVEL_FILE_PATH;
    private List<List<String>> csvContent;

    private CSVReader csvReader;

    private int levelXP;

    /**
     * Fetches the XP corresponding to a level using the level.csv
     *
     * @param taskFilePath The file path where level data is stored.
     * @param level The level of the currently logged-in user.
     */
    public int getLevelXP(String level, String filePath) {

        csvReader = new CSVReader();
        csvContent = csvReader.readCSV(filePath);

        for ( int i = 0; i < csvContent.size(); i++) {
            // assuming that username is saved at index 0
            if (csvContent.get(i).get(0).equals(level)) {
                levelXP = Integer.parseInt(csvContent.get(i).get(1));
            }
        }
        return levelXP;

    }

}