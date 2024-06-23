package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DAO.DBConnect;
import security.EncryptByMD5;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

public class LogInClientView extends JFrame {

	private JPanel contentPane;
	private JTextField jTextField_tenDangNhap;
	private JPasswordField passwordField;

	DBConnect cn = new DBConnect();
	Connection connection;
	private JTextField txt_NickName;

	public LogInClientView() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 569, 558);
		setTitle("Login");
		contentPane = new JPanel();
		contentPane.setBackground(new Color(192, 192, 192));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel dangNhap = new JLabel("User Login");
		dangNhap.setForeground(new Color(255, 255, 255));
		dangNhap.setHorizontalAlignment(SwingConstants.CENTER);
		dangNhap.setFont(new Font("Calibri", Font.BOLD, 38));
		dangNhap.setBackground(new Color(0, 128, 192));
		dangNhap.setOpaque(true);
		dangNhap.setBounds(0, 0, 555, 97);
		contentPane.add(dangNhap);

		JLabel jLabel_tenDangNhap = new JLabel("Username");
		jLabel_tenDangNhap.setForeground(new Color(0, 0, 0));
		jLabel_tenDangNhap.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel_tenDangNhap.setFont(new Font("Cambria", Font.BOLD, 26));
		jLabel_tenDangNhap.setBackground(Color.BLACK);
		jLabel_tenDangNhap.setBounds(50, 114, 171, 65);
		jLabel_tenDangNhap.setIcon(new ImageIcon(
				Toolkit.getDefaultToolkit().createImage(LogInClientView.class.getResource("Icon/User.png"))));
		contentPane.add(jLabel_tenDangNhap);

		jTextField_tenDangNhap = new JTextField();
		jTextField_tenDangNhap.setFont(new Font("Tahoma", Font.PLAIN, 16));
		jTextField_tenDangNhap.setBounds(231, 137, 193, 26);
		contentPane.add(jTextField_tenDangNhap);
		jTextField_tenDangNhap.setColumns(10);

		JLabel jLable_matKhau = new JLabel("Password");
		jLable_matKhau.setForeground(new Color(0, 0, 0));
		jLable_matKhau.setHorizontalAlignment(SwingConstants.RIGHT);
		jLable_matKhau.setFont(new Font("Cambria", Font.BOLD, 26));
		jLable_matKhau.setBackground(Color.BLACK);
		jLable_matKhau.setBounds(73, 173, 148, 65);
		jLable_matKhau.setIcon(new ImageIcon(
				Toolkit.getDefaultToolkit().createImage(LogInClientView.class.getResource("Icon/Lock.png"))));
		contentPane.add(jLable_matKhau);

		JButton btn_signIn = new JButton("Login");
		btn_signIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connection = cn.connect();
				LogInClientView login = new LogInClientView();
				try {
					String username = jTextField_tenDangNhap.getText();
					String password = passwordField.getText();
					String passwordEncrypted = EncryptByMD5.encryptMD5(password);
					String nickName = txt_NickName.getText();
					if (username.equals("") || password.equals("") || nickName.equals("")) {
						JOptionPane.showMessageDialog(login, "Bạn cần nhập đủ dữ liệu");
					} else {
						String sql = "SELECT * FROM TAIKHOAN WHERE username = ? and password = ?";
						PreparedStatement ps = connection.prepareCall(sql);
						ps.setString(1, username);
						ps.setString(2, passwordEncrypted);
						ResultSet rs = ps.executeQuery();

						if (rs.next()) {
							try {
								UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
								ChatClientView cv = new ChatClientView(nickName);
								cv.setVisible(true);
								cv.setLocationRelativeTo(null);
							} catch (Exception e2) {
								e2.printStackTrace();
							}
							login.dispose();
						} else {
							JOptionPane.showMessageDialog(login, "Sai tên tài khoản hoặc mật khẩu!", "ERROR",
									JOptionPane.ERROR_MESSAGE);
						}
						cn.disconnect();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btn_signIn.setBackground(new Color(0, 255, 255));
		btn_signIn.setOpaque(true);
		btn_signIn.setBorderPainted(false);
		btn_signIn.setForeground(new Color(0, 100, 0));
		btn_signIn.setFont(new Font("Calibri", Font.BOLD, 26));
		btn_signIn.setBounds(172, 363, 219, 45);
		contentPane.add(btn_signIn);

		String[] quyen = new String[] { "User", "Admin" };

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		passwordField.setBounds(231, 197, 193, 26);
		contentPane.add(passwordField);

		JButton btn_signUp = new JButton("Sign Up");
		btn_signUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					SignUpClientView sv = new SignUpClientView();
					sv.setVisible(true);
					sv.setLocationRelativeTo(null);

				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btn_signUp.setOpaque(true);
		btn_signUp.setBorderPainted(false);
		btn_signUp.setForeground(new Color(0, 100, 0));
		btn_signUp.setFont(new Font("Calibri", Font.BOLD, 26));
		btn_signUp.setBackground(Color.CYAN);
		btn_signUp.setBounds(172, 443, 219, 45);
		contentPane.add(btn_signUp);

		JToggleButton toggleButton_hideAnShow = new JToggleButton("Show");
		toggleButton_hideAnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (toggleButton_hideAnShow.isSelected()) {
					passwordField.setEchoChar((char) 0);
					toggleButton_hideAnShow.setText("Hide");
				} else {
					passwordField.setEchoChar('●');
					toggleButton_hideAnShow.setText("Show");
				}
			}
		});
		toggleButton_hideAnShow.setOpaque(true);
		toggleButton_hideAnShow.setForeground(Color.RED);
		toggleButton_hideAnShow.setFont(new Font("Calibri", Font.BOLD, 24));
		toggleButton_hideAnShow.setBorderPainted(false);
		toggleButton_hideAnShow.setBackground(Color.CYAN);
		toggleButton_hideAnShow.setBounds(451, 193, 94, 39);
		contentPane.add(toggleButton_hideAnShow);

		JLabel jLabel_nickname = new JLabel("Nick name");
		jLabel_nickname.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel_nickname.setForeground(Color.BLACK);
		jLabel_nickname.setFont(new Font("Cambria", Font.BOLD, 26));
		jLabel_nickname.setBackground(Color.BLACK);
		jLabel_nickname.setBounds(73, 231, 148, 65);
		contentPane.add(jLabel_nickname);

		txt_NickName = new JTextField();
		txt_NickName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txt_NickName.setColumns(10);
		txt_NickName.setBounds(231, 254, 193, 26);
		contentPane.add(txt_NickName);

		JButton btn_QuenMK = new JButton("Forgot password?");
		btn_QuenMK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					SuaMatKhau view = new SuaMatKhau();
					view.setVisible(true);
					view.setLocationRelativeTo(null);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btn_QuenMK.setOpaque(true);
		btn_QuenMK.setBorderPainted(false);
		btn_QuenMK.setBackground(new Color(192, 192, 192));
		btn_QuenMK.setForeground(new Color(255, 0, 0));
		btn_QuenMK.setFont(new Font("Cambria", Font.PLAIN, 18));
		btn_QuenMK.setBounds(192, 306, 183, 26);
		contentPane.add(btn_QuenMK);
	}
}
