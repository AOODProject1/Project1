package aoodp1.screens;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
public class LoginScreen {//extends JFrame{
	private static JFrame f = new JFrame("Login Screen");
	private static JPanel p;
	public static void main(String[] args){
		p = new JPanel();
		f.setSize(500, 500);
		f.add(p);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTextField user = new JTextField("Username");
		JTextField password = new JTextField("Password");
		user.setBounds(100, 100, 150, 50);
		password.setBounds(200, 100, 150, 50);
		JButton login = new JButton("Login");
		login.setSize(50, 10);
		p.add(user);
		p.add(password);
		p.add(login);
		login.addActionListener(new LoginButton(user, password));
		f.pack();
		f.setVisible(true);
	}
	private static class LoginButton implements ActionListener {
		JTextField user;
		JTextField password;
		ArrayList<String> userData = new ArrayList<>();
		ArrayList<String> passwordData = new ArrayList<>();
		public LoginButton(JTextField u, JTextField p){
			user = u;
			password = p;
		}
		public void actionPerformed(ActionEvent e) {
			for(String s: (String[])userData.toArray()){
				if(s.equals(user.getText())){
					
					MainScreen.main (new String[0]);
					f.dispose();
				} else {
					userData.add(user.getText());
					passwordData.add(user.getText());
					MainScreen.main (new String[0]);
					f.dispose();
				}
			}
			
		}
	}
}
