package helpers;

import task_gamification.CSV.CSVReader;

import java.util.List;

public class GetLevelXP {

    private int levelXP;

    public int getLevelXP(String string, String filePath) {

        CSVReader csvReader = new CSVReader();
        List<List<String>> csvContent = csvReader.readCSV(filePath);

        for ( int i = 0; i < csvContent.size(); i++) {
            // assuming that username is saved at index 0
            if (csvContent.get(i).get(0).equals(string)) {
                levelXP = Integer.parseInt(csvContent.get(i).get(1));
            }
        }
        return levelXP;

    }



}
