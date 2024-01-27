package task_gamification.views;


import task_gamification.CSV.CSVReader;
import task_gamification.helpers.ComponentSizePanel;
import task_gamification.helpers.GetFilePath;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Class representing the Credits panel in the app.
 * It allows users to see app information and credits.
 */
public class CreditsPanel extends ComponentSizePanel {

    private JLabel versionLabel, versionOutputLabel, aboutLabel, compatibilityLabel, compatibilityOutputLabel;
    private JTextArea aboutText;
    private JScrollPane storyScrollPane;
    private Insets insets;

    private String about, version, compatibleWith;
    private List<List<String>> aboutContent;

    private CSVReader csvReader;

    // path to csv files
    private GetFilePath FilePaths;
    private String aboutFilePath = FilePaths.ABOUT_FILE_PATH;

    /**
     * Constructor for CreditsPanel.
     * Initializes the panel with the app information.
     */
    public CreditsPanel(){
        insets = this.getInsets();
        super.LABEL_WIDTH = 120;
        aboutText = new JTextArea(""); // Initialize storyText with an empty string
        aboutText.setLineWrap(true);
        aboutText.setEditable(false);
        initializeGUI();
    }

    /**
     * Initializes the graphical user interface of the panel.
     * Sets up the layout, lables, progressbar and textfield.
     */
    private void initializeGUI() {
        setLayout(null);
        setBounds(insets.left, insets.top, W_FRAME - insets.left - insets.right,
                H_FRAME - insets.bottom - insets.top);

        // Add label and output for version
        versionLabel = new JLabel("Version:", SwingConstants.LEFT);
        versionLabel.setBounds(CENTER_X - (W_FRAME/2)+ 30, 30, LABEL_WIDTH, LABEL_HEIGHT);
        add(versionLabel);

        version = "1.0.0";

        versionOutputLabel = new JLabel(version, SwingConstants.LEFT);
        versionOutputLabel.setBounds(CENTER_X - (W_FRAME/2) + LABEL_WIDTH + 50, 30, LABEL_WIDTH, LABEL_HEIGHT);
        add(versionOutputLabel);

        // Add label and output for compatibility
        compatibilityLabel = new JLabel("Compatibility:", SwingConstants.LEFT);
        compatibilityLabel.setBounds(CENTER_X - (W_FRAME/2)+ 30, 60, LABEL_WIDTH, LABEL_HEIGHT);
        add(compatibilityLabel);

        compatibleWith = "Windows, Linux, MacOS";

        compatibilityOutputLabel = new JLabel(compatibleWith, SwingConstants.LEFT);
        compatibilityOutputLabel.setBounds(CENTER_X - (W_FRAME/2) + LABEL_WIDTH + 50, 60, 200, LABEL_HEIGHT);
        add(compatibilityOutputLabel);

        // Add label and output for about
        aboutLabel = new JLabel("About:", SwingConstants.LEFT);
        aboutLabel.setBounds(CENTER_X - (W_FRAME/2)+ 30, 150, LABEL_WIDTH, LABEL_HEIGHT);
        add(aboutLabel);

        about = getAbout();

        aboutText = new JTextArea(about);
        aboutText.setLineWrap(true);
        aboutText.setEditable(false);
        storyScrollPane = new JScrollPane(aboutText);
        storyScrollPane.setBounds(CENTER_X - (W_FRAME/2) + 30, 180, W_FRAME - 60, H_FRAME - 260);
        add(storyScrollPane);
    }

    private String getAbout() {
        csvReader = new CSVReader();
        aboutContent = csvReader.readCSV(aboutFilePath);

        about = aboutContent.get(0).get(0);
        for (int i = 1; i < aboutContent.size(); i++) {
            about = about + "\n\n" + aboutContent.get(i).get(0);
        }
        return about;
    }


}
