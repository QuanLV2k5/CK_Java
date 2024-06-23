package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.ChatClientView;

public class ClientListener implements ActionListener {
	ChatClientView view;

	public ClientListener(ChatClientView view) {
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String ac = e.getActionCommand();
		if (ac.equals("Send")) {
			this.view.sendMessage();
		} else if (ac.equals("Delete")) {
			this.view.delete();
		}
	}

}
