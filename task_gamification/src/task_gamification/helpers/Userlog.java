package task_gamification.helpers;

import task_gamification.CSV.CSVReader;
import task_gamification.CSV.CSVWriter;
import task_gamification.entity.User;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class to handel all methods related to userlog entry.
 */
public class Userlog {

    private LocalDateTime startTime, endTime;
    private DateTimeFormatter formatter;
    private CSVReader csvReader;
    private CSVWriter csvWriter, updateCSV;
    private Duration difference;
    private User user;
    
    private long duration;
    private String loggedInUser;
    private List<List<String>> csvContent;
    private List<String> newUserLogContent;

    // path to csv files
    private GetFilePath FilePaths;
    private String userlogFilePath = FilePaths.USERLOG_FILE_PATH,
            userFilePath = FilePaths.USER_FILE_PATH;

    /**
     * Creates a entry in the userlog with the username and login-time.
     *
     * @param loggedInUser username of the user currently logged-in.
     * @return success
     */
    public boolean startUserlog(String loggedInUser) {

        startTime = LocalDateTime.now();

        newUserLogContent = new ArrayList<>();

        newUserLogContent.add(loggedInUser); // col 1 = username
        newUserLogContent.add(String.valueOf(LocalDateTime.now())); // col 2 = start time
        newUserLogContent.add("0"); // col 3 = end time
        newUserLogContent.add("0"); // col 4 = duration
        newUserLogContent.add("0"); // col 5 = score

        csvReader = new CSVReader();
        csvContent = csvReader.readCSV(userlogFilePath);
        csvContent.add(newUserLogContent);

        csvWriter = new CSVWriter();
        try {
            csvWriter.writeCSV(userlogFilePath, csvContent);
            return true;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Adds missing data to userlog (end time, duration, score).
     *
     * @throws IOException
     */
    public void endUserlog() throws IOException {

        user = new User();
        csvReader = new CSVReader();
        csvContent = csvReader.readCSV(userlogFilePath);

        //formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        startTime = LocalDateTime.parse(csvContent.get(csvContent.size() - 1).get(1));
        endTime = LocalDateTime.now();
        difference = Duration.between(startTime, endTime);
        duration = Math.abs(difference.toSeconds());

        loggedInUser = csvContent.get(csvContent.size() - 1).get(0);

        csvContent.get(csvContent.size() - 1).set(2, String.valueOf(LocalDateTime.now()));
        csvContent.get(csvContent.size() - 1).set(3, String.valueOf(duration));
        csvContent.get(csvContent.size() - 1).set(4, String.valueOf(user.getUserHighscore(loggedInUser, userFilePath)));

        updateCSV = new CSVWriter();
        updateCSV.writeCSV(userlogFilePath, csvContent);
    }

}
