package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

import DAO.DBConnect;
import model.ClientModel;
import security.EncryptByMD5;

public class Server implements Runnable {
	private ServerSocket serverSocket;
	private Thread thread;
	private ObjectInputStream inputStream;
	private Object receivedData;
	private ClientModel model;
	private List<Socket> listClient = new ArrayList<>();
	private int countClient = 0;

	Connection connection;
	Statement stm;
	ResultSet rst;

	public Server() {
		try {
			serverSocket = new ServerSocket(2501);
			System.out.println("Port 2501 can be connect by client!");

			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url = "jdbc:sqlserver://quanlv\\SQLEXPRESS:1433;databaseName=QLTKClient";
			String userName = "sa";
			String password = "12345678";

			connection = DriverManager.getConnection(url, userName, password);
			stm = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				Socket clientSocket = serverSocket.accept();

				inputStream = new ObjectInputStream(clientSocket.getInputStream());
				receivedData = inputStream.readObject();
				listClient.add(clientSocket);
				System.out.println("New client connected!");
				++countClient;
				System.out.println("Client live: " + countClient);
				System.out.println("----------------------------");
				handleClient(clientSocket);

				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void handleClient(Socket clientSocket) {
		new Thread(() -> {
			try {
				while (true) {
					ObjectInputStream inputStreamClient = new ObjectInputStream(clientSocket.getInputStream());
					Object receivedData = inputStreamClient.readObject();

					for (Socket client : listClient) {
						if (client != clientSocket) {
							ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
							outputStream.writeObject(receivedData);
							outputStream.flush();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}
}
