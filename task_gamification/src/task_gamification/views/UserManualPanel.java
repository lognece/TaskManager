package task_gamification.views;

import task_gamification.helpers.ComponentSizePanel;
import task_gamification.helpers.GetFilePath;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Represents the class for the UserManual Panel.
 */
public class UserManualPanel extends ComponentSizePanel {

    private File userManual;

    // path to csv files
    private GetFilePath FilePaths;
    private String userManualPath = FilePaths.USER_MANUAL;

    /**
     * If possible the application will open the PDF file of the user manual
     */
    public void openManual() {

        if (Desktop.isDesktopSupported()) {
            try {
                userManual = new File(userManualPath);
                Desktop.getDesktop().open(userManual);
            } catch (IOException ex) {
                // no application registered for PDFs
            }
        }

    }

}
