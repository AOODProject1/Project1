package aoodp1.screens;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
public class LoginScreen extends JFrame{
	private static JFrame f = new JFrame();
	private static JPanel p;
	
	public static void main(String[] args){
		p = new JPanel();
		f.setSize(500, 500);
		f.add(p);
		JTextField user = new JTextField(10);
		JTextField password = new JTextField(10);
		user.setBounds(100, 100, 150, 50);
		password.setBounds(200, 100, 150, 50);
		JButton login = new JButton();
		p.add(user);
		p.add(password);
		p.add(login);
		f.pack();
		f.setVisible(true);
	}
	private class LoginButton implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
		}
	}
}
