package task_gamification.helpers;

import javax.swing.*;

/**
 * Helperclass for easier implementation of UI-Components.
 */
public class UIComponentHelper {
	/**
	 * Creates a JLabel with specified properties.
	 *
	 * @param text The text for the label.
	 * @param x The x-coordinate of the label.
	 * @param y The y-coordinate of the label.
	 * @param width The width of the label.
	 * @param height The height of the label.
	 * @return The created JLabel object.
	 */
	public static JLabel createLabel(String text, int x, int y, int width, int height) {
		JLabel label = new JLabel(text);
		label.setBounds(x, y, width, height);
		return label;
	}

	/**
	 * Creates a JTextField with specified properties.
	 *
	 * @param x The x-coordinate of the text field.
	 * @param y The y-coordinate of the text field.
	 * @param width The width of the text field.
	 * @param height The height of the text field.
	 * @return The created JTextField object.
	 */
	public static JTextField createTextField(int x, int y, int width, int height) {
		JTextField textField = new JTextField();
		textField.setBounds(x, y, width, height);
		return textField;
	}


	/**
	 * Creates a password field with specified properties.
	 *
	 * @param x The x-coordinate of the password field.
	 * @param y The y-coordinate of the password field.
	 * @param width The width of the password field.
	 * @param height The height of the password field.
	 * @return The created JPasswordField object.
	 */
	public static JPasswordField createPasswordField(int x, int y, int width, int height) {
		JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(x, y, width, height);
		return passwordField;
	}

	/**
	 * Creates a JComboBox with specified properties.
	 *
	 * @param items The items displayed in the combobox.
	 * @param x The x-coordinate of the password field.
	 * @param y The y-coordinate of the password field.
	 * @param width The width of the password field.
	 * @param height The height of the password field.
	 * @return The created comboBox object.
	 */
	public static JComboBox<String> createComboBox(String[] items, int x, int y, int width, int height) {
		JComboBox<String> comboBox = new JComboBox<>(items);
		comboBox.setBounds(x, y, width, height);
		return comboBox;
	}

	/**
	 * Creates a JSpinner with specified properties.
	 *
	 * @param minValue The minimum value of JSpinner.
	 * @param maxValue The maximum value of JSpinner.
	 * @param stepSize The stepsize for clicking through JSpinner.
	 * @param initialValue The initial value set in JSpinner.
	 * @param x The x-coordinate of the password field.
	 * @param y The y-coordinate of the password field.
	 * @param width The width of the password field.
	 * @param height The height of the password field.
	 * @return The created JSpinner object.
	 */
	public static JSpinner createSpinner(int minValue, int maxValue, int stepSize,
										 int initialValue, int x, int y, int width, int height) {
		JSpinner spinner = new JSpinner(new SpinnerNumberModel(initialValue, minValue, maxValue, stepSize));
		spinner.setBounds(x, y, width, height);
		return spinner;
	}


}
