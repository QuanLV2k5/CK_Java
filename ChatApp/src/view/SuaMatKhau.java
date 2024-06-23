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
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class SuaMatKhau extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txt_Username;

	DBConnect cn = new DBConnect();
	Connection connection;
	private JPasswordField txt_NewPass;
	private JPasswordField txt_ConfirmPass;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SuaMatKhau frame = new SuaMatKhau();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public SuaMatKhau() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 496, 424);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(192, 192, 192));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel doimk = new JLabel("Change password");
		doimk.setOpaque(true);
		doimk.setHorizontalAlignment(SwingConstants.CENTER);
		doimk.setForeground(Color.WHITE);
		doimk.setFont(new Font("Calibri", Font.BOLD, 38));
		doimk.setBackground(new Color(0, 128, 192));
		doimk.setBounds(0, 0, 482, 68);
		contentPane.add(doimk);

		JLabel jLabel_username = new JLabel("Username");
		jLabel_username.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel_username.setForeground(Color.BLACK);
		jLabel_username.setFont(new Font("Cambria", Font.BOLD, 24));
		jLabel_username.setBackground(Color.BLACK);
		jLabel_username.setBounds(81, 89, 148, 65);
		contentPane.add(jLabel_username);

		JLabel jLabel_confirmpass = new JLabel("Confirm password");
		jLabel_confirmpass.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel_confirmpass.setForeground(Color.BLACK);
		jLabel_confirmpass.setFont(new Font("Cambria", Font.BOLD, 24));
		jLabel_confirmpass.setBackground(Color.BLACK);
		jLabel_confirmpass.setBounds(10, 239, 219, 65);
		contentPane.add(jLabel_confirmpass);

		txt_Username = new JTextField();
		txt_Username.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txt_Username.setColumns(10);
		txt_Username.setBounds(247, 111, 193, 26);
		contentPane.add(txt_Username);

		JButton btn_change = new JButton("Change");
		btn_change.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connection = cn.connect();
				try {
					String username = txt_Username.getText();
					String newpass = txt_NewPass.getText();
					String confirmpass = txt_ConfirmPass.getText();
					String encryptedpass = EncryptByMD5.encryptMD5(newpass);
					if (username.equals("") || newpass.equals("") || confirmpass.equals("")) {
						JOptionPane.showMessageDialog(contentPane, "Bạn cần nhập đủ dữ liệu", "ERROR",
								JOptionPane.ERROR_MESSAGE);
					} else {
						if (!confirmpass.equals(newpass)) {
							JOptionPane.showMessageDialog(contentPane, "Mật khẩu xác nhận không trùng khớp!", "ERROR",
									JOptionPane.ERROR_MESSAGE);
						} else {
							Statement st = connection.createStatement();
							String sql = "Update TAIKHOAN set username ='" + username + "', password ='" + encryptedpass
									+ "' where username ='" + username + "'";
							st.executeUpdate(sql);
							JOptionPane.showMessageDialog(contentPane, "Đổi mật khẩu thành công!");
							new SuaMatKhau().dispose();
							st.close();
						}
					}
					cn.disconnect();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btn_change.setOpaque(true);
		btn_change.setForeground(new Color(0, 100, 0));
		btn_change.setFont(new Font("Calibri", Font.BOLD, 26));
		btn_change.setBorderPainted(false);
		btn_change.setBackground(Color.CYAN);
		btn_change.setBounds(138, 332, 219, 45);
		contentPane.add(btn_change);

		JLabel jLabel_newpass_1 = new JLabel("New password");
		jLabel_newpass_1.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel_newpass_1.setForeground(Color.BLACK);
		jLabel_newpass_1.setFont(new Font("Cambria", Font.BOLD, 24));
		jLabel_newpass_1.setBackground(Color.BLACK);
		jLabel_newpass_1.setBounds(31, 164, 198, 65);
		contentPane.add(jLabel_newpass_1);

		txt_NewPass = new JPasswordField();
		txt_NewPass.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txt_NewPass.setBounds(247, 187, 193, 26);
		contentPane.add(txt_NewPass);

		txt_ConfirmPass = new JPasswordField();
		txt_ConfirmPass.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txt_ConfirmPass.setBounds(247, 262, 193, 26);
		contentPane.add(txt_ConfirmPass);
	}
}
