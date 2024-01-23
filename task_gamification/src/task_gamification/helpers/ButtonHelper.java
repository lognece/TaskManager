package task_gamification.helpers;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Helperclass for handeling button creations
 */
public class ButtonHelper {

	/**
	 * Creates and returns a new JButton.
	 *
	 * @param text          the text of the button
	 * @param actionCommand the command that identifies the action of this button
	 * @param listener      the ActionListener to be added to the button
	 * @param x             the x coordinate of the button
	 * @param y             the y coordinate of the button
	 * @param width         the width of the button
	 * @param height        the height of the button
	 * @return the newly created JButton
	 */

	public static JButton newButton(String text, String actionCommand,
									ActionListener listener, int x, int y, int width, int height) {
		JButton button = new JButton(text);
		button.setActionCommand(actionCommand);
		button.addActionListener(listener);
		button.setBounds(x, y, width, height);
		return button;
	}
}
