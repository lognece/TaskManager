package task_gamification.helpers;

import javax.swing.*;

/**
 * Helperclass to implement fixed sizes and positions for the panels.
 */
public class ComponentSizePanel extends JPanel {

    // size and position
    public static final int W_FRAME = 1080;
    public static final int H_FRAME = (int) (W_FRAME / ((Math.sqrt(5) + 1) / 2));
    public static final int CENTER_X = W_FRAME / 2;
    public static final int CENTER_Y = H_FRAME / 2;
    public static int LABEL_WIDTH = 100;
    public static final int LABEL_HEIGHT = 30;
    public static final int BUTTON_WIDTH = 150;
    public static final int BUTTON_HEIGHT = 25;
    public static final int POP_UP_WIDTH = 600;
    public static final int POP_UP_HIGHT = 370;

    // TO-Do panel specific
    public static final int TASK_EDIT_BUTTON = CENTER_X - (BUTTON_WIDTH/2);
    public static final int TASK_ADD_BUTTON = TASK_EDIT_BUTTON - BUTTON_WIDTH - 30;
    public static final int TASK_DELETE_BUTTON = TASK_EDIT_BUTTON + BUTTON_WIDTH + 30;

}
