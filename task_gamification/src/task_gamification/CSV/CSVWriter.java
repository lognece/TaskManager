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

        //write header line here if you need.


        for (List<String> userEntry : contentCSV) {
            System.out.println(userEntry);
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < userEntry.size(); i++) {
                // line.append("\"");
                 line.append(userEntry.get(i)); //.replaceAll("\"","\"\""));
                // line.append("\"");
                if (i != userEntry.size() - 1) {
                    line.append(';');
                }
            }
            line.append("\n");
            fileWriter.write(line.toString());
        }
        fileWriter.close();
    }

}





