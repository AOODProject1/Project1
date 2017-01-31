package aoodp1.screens;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

public class MainScreen {
		JFrame f = new JFrame();
		JPanel p;	
		public MainScreen(){
			p = new JPanel();
			f.setSize(500, 500);
			f.setVisible(true);
			f.add(p);
			JMenuBar bar = new JMenuBar();
			f.add(bar);
			JMenu file = new JMenu("File");
			JMenu quit= new JMenu("Quit");
	        JMenu closedActionItems = new JMenu("Closed Action Items");
	        bar.add(file);
	        bar.add(quit);
	        bar.add(closedActionItems);
	        p.add(bar);
		}
}
