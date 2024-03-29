package task_gamification.helpers;

import java.awt.CardLayout;

import javax.swing.JPanel;

/**
 * Helperclass to add new JPanels to the MainFrame
 */
public class ShowPanel {
	private JPanel contentPanel;

	/**
	 *
	 */
	public ShowPanel(JPanel contentPanel) {
		this.contentPanel = contentPanel;
	}

	/**
	 *
	 */
	public void getShowPanel(JPanel panel, String name) {
		showPanel(panel, name);
	}

	/**
	 *
	 */
	private void showPanel(JPanel panel, String panelName) {
		contentPanel.removeAll();
		contentPanel.add(panel, panelName);
		CardLayout cl = (CardLayout)(contentPanel.getLayout());
		cl.show(contentPanel, panelName);
		contentPanel.revalidate();
		contentPanel.repaint();
	}

}