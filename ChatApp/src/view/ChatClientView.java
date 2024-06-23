package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import controller.ClientListener;
import model.ClientModel;

import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.MatteBorder;

import DAO.DBConnect;

public class ChatClientView extends JFrame implements FocusListener {
	private JPanel contentPane;
	private JTextField txt_Message;
	private Socket socket;
	private ClientModel inforOfClient;
	private ObjectOutputStream outputStream;
	private String message = "";
	private JTextArea chatArea;
	private String nameofUser;
	private JLabel jLable_client;
	private JPanel panel;

	public ChatClientView(String nickName) {
		this.nameofUser = nickName;
		init();
		try {
			socket = new Socket("localhost", 2501);
			listenToSerVer();
			jLable_client.setText(nickName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		appear();
	}

	public void init() {
		ActionListener actionListener = new ClientListener(this);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 619, 622);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(192, 192, 192));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLocation(150, 100);
		this.setTitle("Chat Room");

		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new JPanel();
		panel.setBackground(new Color(0, 128, 192));
		panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 128, 255)));
		panel.setBounds(0, 0, 605, 108);
		contentPane.add(panel);
		panel.setLayout(null);

		jLable_client = new JLabel("");
		jLable_client.setHorizontalAlignment(SwingConstants.LEFT);
		jLable_client.setBounds(10, 10, 183, 86);
		panel.add(jLable_client);
		jLable_client.setOpaque(true);
		jLable_client.setForeground(new Color(0, 0, 0));
		jLable_client.setFont(new Font("Cambria", Font.BOLD, 28));
		jLable_client.setBackground(new Color(0, 128, 192));
		jLable_client.setIcon(new ImageIcon(
				Toolkit.getDefaultToolkit().createImage(ChatClientView.class.getResource("Icon/Unknown person.png"))));

		chatArea = new JTextArea();
		chatArea.setForeground(new Color(0, 0, 0));
		chatArea.setFont(new Font("Calibri", Font.BOLD, 23));
		chatArea.setBounds(0, 123, 599, 397);
		chatArea.setEnabled(false);

		txt_Message = new JTextField("Enter your message...");
		txt_Message.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txt_Message.setBounds(0, 530, 376, 45);
		txt_Message.addFocusListener(this);
		contentPane.add(txt_Message);
		txt_Message.setColumns(10);

		JButton btnSend = new JButton("Send");
		btnSend.setForeground(new Color(255, 255, 255));
		btnSend.setOpaque(true);
		btnSend.setBorderPainted(false);
		btnSend.setBackground(new Color(0, 128, 192));
		btnSend.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnSend.setBounds(386, 530, 102, 45);
		btnSend.addActionListener(actionListener);
		contentPane.add(btnSend);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setOpaque(true);
		btnDelete.setForeground(Color.WHITE);
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnDelete.setBorderPainted(false);
		btnDelete.setBackground(new Color(255, 0, 0));
		btnDelete.setBounds(498, 530, 102, 45);
		btnDelete.addActionListener(actionListener);
		contentPane.add(btnDelete);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setViewportView(chatArea);
		scrollPane_1.setBounds(0, 120, 605, 400);
		contentPane.add(scrollPane_1, BorderLayout.CENTER);
	}

	public void delete() {
		this.txt_Message.setText("");
	}

	public void appear() {
		try {
			this.inforOfClient = new ClientModel();
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			outputStream.writeObject(inforOfClient);
			outputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void listenToSerVer() {
		new Thread(() -> {
			try {
				while (true) {
					ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
					Object receivedData = inputStream.readObject();
					ClientModel client = (ClientModel) receivedData;
					this.message = client.getText();
					chatArea.append("\n                                                        " + client.getNickName()
							+ " - " + message );
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}

	public void sendMessage() {
		try {
			this.inforOfClient = new ClientModel();
			this.inforOfClient.setText(this.txt_Message.getText());
			this.inforOfClient.setNickName(nameofUser);

			outputStream = new ObjectOutputStream(socket.getOutputStream());
			outputStream.writeObject(inforOfClient);
			outputStream.flush();
			chatArea.append("\n" + this.nameofUser + " - " + this.txt_Message.getText());
			txt_Message.setText("");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		if (txt_Message.getText().equals("Enter your message...")) {
			txt_Message.setText("");
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (txt_Message.getText().isEmpty()) {
			txt_Message.setText("Enter your message...");
		}
	}
}
