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

    /**
     * Gets all level thresholds from the level.csv file.
     *
     * @return A map with level numbers as keys and their corresponding XP requirements as values.
     */
    public Map<Integer, Integer> getAllLevelXPs(String levelFilePath) {
        CSVReader csvReader = new CSVReader();
        List<List<String>> csvContent = csvReader.readCSV(this.levelFilePath);
        Map<Integer, Integer> levelXPs = new HashMap<>(); // Map to store level and corresponding XP requirements.

        for (List<String> row : csvContent) {
            int level = Integer.parseInt(row.get(0)); // Parse the level number.
            int xpRequired = Integer.parseInt(row.get(1)); // Parse the XP required for the level.
            levelXPs.put(level, xpRequired); // Add the level and its XP requirement to the map.
        }
        return levelXPs;
    }
}