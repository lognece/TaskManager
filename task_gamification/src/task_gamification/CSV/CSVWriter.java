package task_gamification.CSV;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVWriter {

    public CSVWriter() {
        return;
    }

    public static void writeCSV(String filePath, List<List<String>> contentCSV) throws IOException {
        File csvFile = new File(filePath);
        FileWriter fileWriter = new FileWriter(csvFile);

        for (List<String> userEntry : contentCSV) {
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < userEntry.size(); i++) {
                line.append(userEntry.get(i));
                if (i < userEntry.size() - 1) {
                    line.append(';');
                }
            }
            line.append("\n");
            fileWriter.write(line.toString());
        }
        fileWriter.close();
    }
}




