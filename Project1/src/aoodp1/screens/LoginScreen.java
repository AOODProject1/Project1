package aoodp1.screens;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
		System.out.print(System.getProperty("user.home"));
	}
	private static class LoginButton implements ActionListener {
		JTextField user;
		JTextField password;
		public LoginButton(JTextField u, JTextField p){
			user = u;
			password = p;
		}
		public void actionPerformed(ActionEvent e) {
				if(new File(System.getProperty("user.home") + "/ToDoList/" + user.getText() + "/").exists()){
					if(password.getText().equals(System.getProperty("user.home") + "/ToDoList/" + user.getText() + "/password.txt")){
					
					MainScreen.main (new String[0]);
					f.dispose();
					}
				} else {
					int y = JOptionPane.showConfirmDialog(null, "Do you want to make a new user?");
					if(y == JOptionPane.YES_OPTION){
					new File(System.getProperty("user.home") + "/ToDoList/" + user.getText() + "/").mkdirs();
					try{
					new PrintStream(new File(System.getProperty("user.home") + "/ToDoList/" + user.getText() + "/password.txt")).print(password);
					}catch (FileNotFoundException x){
						System.err.println(x.getLocalizedMessage());
					}
					MainScreen.main (new String[0]);
					f.dispose();
					}
			}
			
		}
	}
}
