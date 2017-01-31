package aoodp1.screens;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
public class LoginScreen extends JFrame{
	JFrame f = new JFrame();
	JPanel p;
	
	public LoginScreen(){
		p = new JPanel();
		f.setSize(500, 500);
		f.setVisible(true);
		f.add(p);
		JTextField user = new JTextField(10);
		JTextField password = new JTextField(10);
		user.setBounds(100, 100, 150, 50);
		password.setBounds(200, 100, 150, 50);
		JButton login = new JButton();
		p.add(user);
		p.add(password);
		p.add(login);
	}
	
	public static void main(String args[]){
        LoginScreen x = new LoginScreen();
	}
}
