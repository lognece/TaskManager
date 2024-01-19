/* package task_gamification;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.JCheckBox;
import javax.swing.JList;
import net.miginfocom.swing.MigLayout;
import javax.swing.JScrollBar;
import javax.swing.JProgressBar;
import java.awt.Color;

public class frame_gui extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTable table_1;
	private JTextField storyLine_text;
	private JTextField textField;
	private JTable table_highscoreBoard;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame_gui frame = new frame_gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public frame_gui() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1136, 818);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);
		
		JPanel panel_taskOverview = new JPanel();
		tabbedPane.addTab("Task Overview", null, panel_taskOverview, null);
		panel_taskOverview.setLayout(new MigLayout("", "[1105px]", "[16px][718px]"));
		
		JLabel taskOverview_Label = new JLabel("Task Overview");
		panel_taskOverview.add(taskOverview_Label, "cell 0 0,growx,aligny top");
		
		JTabbedPane tabbedPane_taskOverview = new JTabbedPane(JTabbedPane.TOP);
		panel_taskOverview.add(tabbedPane_taskOverview, "cell 0 1,grow");
		
		JPanel panel_todo = new JPanel();
		tabbedPane_taskOverview.addTab("TO-DO", null, panel_todo, null);
		panel_todo.setLayout(new MigLayout("", "[grow][]", "[grow][]"));
		

		String[] columnTasks = {"Done", "Task Description", "XP", " "};
		String[][] dataTasks = {{"a", "b", "c", "d"}};
		JTable table_taskTodo = new JTable(dataTasks, columnTasks);
		panel_todo.add(table_taskTodo, "cell 0 0,grow");
		
		JScrollBar scrollBar_taskoverview_toDo = new JScrollBar();
		panel_todo.add(scrollBar_taskoverview_toDo, "cell 1 0");
		
		JButton button_add_taskoverview_toDo = new JButton("add task");
		panel_todo.add(button_add_taskoverview_toDo, "flowx,cell 0 1");
		
		JButton button_edit_taskoverview_toDo = new JButton("edit task");
		panel_todo.add(button_edit_taskoverview_toDo, "cell 0 1");
		
		JButton button_delete_taskoverview_toDo = new JButton("delete task");
		panel_todo.add(button_delete_taskoverview_toDo, "cell 0 1");
		
		JPanel panel_done = new JPanel();
		tabbedPane_taskOverview.addTab("DONE", null, panel_done, null);
		panel_done.setLayout(new MigLayout("", "[grow][]", "[grow][]"));
		
		JTable table_taskDone = new JTable(dataTasks, columnTasks);
		panel_done.add(table_taskDone, "cell 0 0,grow");
		
		JScrollBar scrollBar_taskoverview_done = new JScrollBar();
		panel_done.add(scrollBar_taskoverview_done, "cell 1 0");
		
		JButton button_add_taskoverview_done = new JButton("add task");
		panel_done.add(button_add_taskoverview_done, "flowx,cell 0 1");
		
		JButton button_edit_taskoverview_done = new JButton("edit task");
		panel_done.add(button_edit_taskoverview_done, "cell 0 1");
		
		JButton button_delete_taskoverview_done = new JButton("delete task");
		pathis.taskTitle = taskTitle;
    	this.taskDescription = taskDescription;
		this.taskPriority = taskPriority;
		this.taskXP = taskXP;nel_done.add(button_delete_taskoverview_done, "cell 0 1");
		
		JPanel panel_characterProgress = new JPanel();
		tabbedPane.addTab("Character", null, panel_characterProgress, null);
		panel_characterProgress.setLayout(new MigLayout("", "[grow][]", "[][][][][][][][][][][][][][][][][][][][][][][][][][]"));
		
		JLabel characterProgress_Lable = new JLabel("Character Progress");
		panel_characterProgress.add(characterProgress_Lable, "cell 0 0");
		
		JProgressBar characterProgress = new JProgressBar();
		characterProgress.setBackground(new Color(253, 253, 253));
		characterProgress.setValue(0);
		characterProgress.setStringPainted(true);

		characterProgress.setValue(40);
		panel_characterProgress.add(characterProgress, "cell 0 1");
		
		JTextField text_levelIndicator = new JTextField("Level 1");
		panel_characterProgress.add(text_levelIndicator, "cell 0 2,alignx left");
		text_levelIndicator.setColumns(10);
		
		JLabel storyLine_Label = new JLabel("Story-Line");
		panel_characterProgress.add(storyLine_Label, "cell 0 4");
		
		storyLine_text = new JTextField();
		panel_characterProgress.add(storyLine_text, "cell 0 5 1 21,grow");
		storyLine_text.setColumns(10);
		
		JScrollBar scrollBar_storyLine = new JScrollBar();
		panel_characterProgress.add(scrollBar_storyLine, "cell 1 6 1 20,alignx right");
		
		JPanel panel_highscore = new JPanel();
		tabbedPane.addTab("Highscore", null, panel_highscore, null);
		panel_highscore.setLayout(new MigLayout("", "[grow]", "[][grow]"));
		
		JLabel higgscoreBoard_Label = new JLabel("Highscore Board");
		panel_highscore.add(higgscoreBoard_Label, "cell 0 0");
		
		// test table
		String[] columnScores = {"Place", "Username", "XP", " "};
		String[][] dataScores = {{"1", "The_Best", "2000", "XP"}};
		table_highscoreBoard = new JTable(dataScores, columnScores);
		panel_highscore.add(table_highscoreBoard, "flowx,cell 0 1,grow");
		
		JScrollBar scrollBar_highscoreBoard = new JScrollBar();
		panel_highscore.add(scrollBar_highscoreBoard, "cell 0 1");
	}
}
*/