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
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SignUpClientView extends JFrame {

	private JPanel contentPane;
	private JTextField txt_TK;
	private JPasswordField pw_mk;
	private JPasswordField pw_xacnhan;
	private JButton btn_signUp;
	private JToggleButton toggleButton_hideAnShow;

	DBConnect cn = new DBConnect();
	Connection connection;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUpClientView frame = new SignUpClientView();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public SignUpClientView() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 603, 450);
		setTitle("Sign Up");
		contentPane = new JPanel();
		contentPane.setBackground(new Color(192, 192, 192));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel jLabel_tenDangNhap = new JLabel("Username");
		jLabel_tenDangNhap.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel_tenDangNhap.setForeground(Color.BLACK);
		jLabel_tenDangNhap.setFont(new Font("Calibri", Font.BOLD, 26));
		jLabel_tenDangNhap.setBackground(Color.BLACK);
		jLabel_tenDangNhap.setBounds(71, 112, 152, 65);
		jLabel_tenDangNhap.setIcon(new ImageIcon(
				Toolkit.getDefaultToolkit().createImage(LogInClientView.class.getResource("Icon/User.png"))));
		contentPane.add(jLabel_tenDangNhap);

		txt_TK = new JTextField();
		txt_TK.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txt_TK.setColumns(10);
		txt_TK.setBounds(233, 129, 209, 26);
		contentPane.add(txt_TK);

		JLabel jLable_matKhau = new JLabel("Password");
		jLable_matKhau.setHorizontalAlignment(SwingConstants.RIGHT);
		jLable_matKhau.setForeground(Color.BLACK);
		jLable_matKhau.setFont(new Font("Cambria", Font.BOLD, 26));
		jLable_matKhau.setBackground(Color.BLACK);
		jLable_matKhau.setBounds(71, 164, 152, 65);
		jLable_matKhau.setIcon(new ImageIcon(
				Toolkit.getDefaultToolkit().createImage(LogInClientView.class.getResource("Icon/Lock.png"))));
		contentPane.add(jLable_matKhau);

		pw_mk = new JPasswordField();
		pw_mk.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pw_mk.setBounds(233, 188, 209, 26);
		contentPane.add(pw_mk);

		JLabel jLable_matKhau_1 = new JLabel("Confirm password");
		jLable_matKhau_1.setHorizontalAlignment(SwingConstants.LEFT);
		jLable_matKhau_1.setForeground(Color.BLACK);
		jLable_matKhau_1.setFont(new Font("Cambria", Font.BOLD, 26));
		jLable_matKhau_1.setBackground(Color.BLACK);
		jLable_matKhau_1.setBounds(0, 229, 244, 65);
		contentPane.add(jLable_matKhau_1);

		pw_xacnhan = new JPasswordField();
		pw_xacnhan.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pw_xacnhan.setBounds(233, 253, 209, 26);
		contentPane.add(pw_xacnhan);

		btn_signUp = new JButton("Sign Up");
		btn_signUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connection = cn.connect();
				SignUpClientView frame = new SignUpClientView();
				try {
					String username = txt_TK.getText();
					String password = pw_mk.getText();
					String passwordConfirm = pw_xacnhan.getText();
					String passwordEncrypted = EncryptByMD5.encryptMD5(password);
					if (username.equals("") || passwordConfirm.equals("") || password.equals("")) {
						JOptionPane.showMessageDialog(frame, "Bạn cần nhập đủ dữ liệu", "ERROR",
								JOptionPane.ERROR_MESSAGE);
					} else {
						if (!passwordConfirm.equals(password)) {
							JOptionPane.showMessageDialog(frame, "Mật khẩu xác nhận không trùng khớp!", "ERROR",
									JOptionPane.ERROR_MESSAGE);
						} else {
							StringBuffer sb = new StringBuffer();
							String sql_check_pk = "SELECT username FROM TAIKHOAN WHERE username = '" + username + "'";
							Statement st = connection.createStatement();
							ResultSet rs = st.executeQuery(sql_check_pk);

							if (rs.next()) {
								sb.append("Tài khoản này đã tồn tại!");
							}
							if (sb.length() > 0) {
								JOptionPane.showMessageDialog(frame, sb.toString());
							} else {
								String sql = "Insert into TAIKHOAN values (N'" + username + "', '" + passwordEncrypted
										+ "')";
								st = connection.createStatement();
								int kq = st.executeUpdate(sql);

								if (kq > 0) {
									JOptionPane.showMessageDialog(frame, "Tạo tài khoản thành công!");
									frame.dispose();
								}
							}
							st.close();
							rs.close();
						}
					}
					cn.disconnect();
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
		btn_signUp.setBounds(199, 322, 194, 55);
		contentPane.add(btn_signUp);

		JLabel dangKy = new JLabel("User Sign Up");
		dangKy.setOpaque(true);
		dangKy.setHorizontalAlignment(SwingConstants.CENTER);
		dangKy.setForeground(Color.WHITE);
		dangKy.setFont(new Font("Calibri", Font.BOLD, 38));
		dangKy.setBackground(new Color(0, 128, 192));
		dangKy.setBounds(0, 0, 589, 97);
		contentPane.add(dangKy);

		toggleButton_hideAnShow = new JToggleButton("Show");
		toggleButton_hideAnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (toggleButton_hideAnShow.isSelected()) {
					pw_mk.setEchoChar((char) 0);
					toggleButton_hideAnShow.setText("Hide");
				} else {
					pw_mk.setEchoChar('●');
					toggleButton_hideAnShow.setText("Show");
				}
			}
		});
		toggleButton_hideAnShow.setOpaque(true);
		toggleButton_hideAnShow.setForeground(Color.RED);
		toggleButton_hideAnShow.setFont(new Font("Calibri", Font.BOLD, 24));
		toggleButton_hideAnShow.setBorderPainted(false);
		toggleButton_hideAnShow.setBackground(new Color(0, 255, 255));
		toggleButton_hideAnShow.setBounds(457, 175, 104, 39);
		contentPane.add(toggleButton_hideAnShow);
	}

}
