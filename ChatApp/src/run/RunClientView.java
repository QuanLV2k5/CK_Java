package run;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import view.ChatClientView;
import view.LogInClientView;

public class RunClientView {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			LogInClientView clientView = new LogInClientView();
			clientView.setVisible(true);
			clientView.setLocationRelativeTo(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
