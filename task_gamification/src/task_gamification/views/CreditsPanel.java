package task_gamification.views;


import task_gamification.CSV.CSVReader;
import task_gamification.helpers.GetFilePath;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Class representing the Credits panel in the app.
 * It allows users to see app information and credits.
 */
public class CreditsPanel extends JPanel{

    // size and position
    public static final int W_FRAME = 1080;
    public static final int H_FRAME = (int) (W_FRAME / ((Math.sqrt(5) + 1) / 2));
    private static final int centerX = W_FRAME / 2;
    private static final int labelWidth = 120;

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
        versionLabel.setBounds(centerX - (W_FRAME/2)+ 30, 30, labelWidth, 20);
        add(versionLabel);

        version = "1.0.0";

        versionOutputLabel = new JLabel(version, SwingConstants.LEFT);
        versionOutputLabel.setBounds(centerX - (W_FRAME/2) + labelWidth + 50, 30, labelWidth, 20);
        add(versionOutputLabel);

        // Add label and output for compatibility
        compatibilityLabel = new JLabel("Compatibility:", SwingConstants.LEFT);
        compatibilityLabel.setBounds(centerX - (W_FRAME/2)+ 30, 60, labelWidth, 20);
        add(compatibilityLabel);

        compatibleWith = "Windows, Linux, MacOS";

        compatibilityOutputLabel = new JLabel(compatibleWith, SwingConstants.LEFT);
        compatibilityOutputLabel.setBounds(centerX - (W_FRAME/2) + labelWidth + 50, 60, 200, 20);
        add(compatibilityOutputLabel);

        // Add label and output for about
        aboutLabel = new JLabel("About:", SwingConstants.LEFT);
        aboutLabel.setBounds(centerX - (W_FRAME/2)+ 30, 150, labelWidth, 20);
        add(aboutLabel);

        about = getAbout();

        aboutText = new JTextArea(about);
        aboutText.setLineWrap(true);
        aboutText.setEditable(false);
        storyScrollPane = new JScrollPane(aboutText);
        storyScrollPane.setBounds(centerX - (W_FRAME/2) + 30, 180, W_FRAME - 60, H_FRAME - 260);
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
