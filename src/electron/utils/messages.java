package electron.utils;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class messages {
	public static void info(String text,String title) {
		JOptionPane.showMessageDialog(new JFrame(), text, title, JOptionPane.INFORMATION_MESSAGE);
	}
	public static String input(String question,String title) {
		String id = JOptionPane.showInputDialog(new JFrame(), question, title, JOptionPane.QUESTION_MESSAGE);
		return id;
	}
	public static void error(String err) {
		JOptionPane.showMessageDialog(new JFrame(), err, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
