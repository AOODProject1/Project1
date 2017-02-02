package aoodp1.screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import aoodp1.item.ActionItem;

public class MainScreen {
		private static JFrame f = new JFrame();
		private static JPanel p;
		private static ArrayList<ActionItem> toDos;
		private static File whereToSave;
		public static void main(String[] args) {
			p = new JPanel();
			JMenuItem save = new JMenuItem("Save As...");
			f.setSize(500, 500);
			f.setVisible(true);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.add(p);
			f.addWindowListener(new SaveAtClose());
			JMenuBar bar = new JMenuBar();
			f.add(bar);
			JMenu file = new JMenu("File");
			JMenu quit= new JMenu("Quit");
	        JMenu closedActionItems = new JMenu("Closed Action Items");
	        bar.add(file);
	        bar.add(quit);
	        bar.add(closedActionItems);
	        file.add(save);
	        save.addActionListener(new SaveListener());
	        quit.addActionListener(new QuitListener());
	        p.add(bar);
		}
		private static class SaveAtClose implements WindowListener {
			public void windowActivated(WindowEvent e) {}
			public void windowClosing(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowOpened(WindowEvent e) {}

			public void windowClosed(WindowEvent e) {
				//Save the actionitems to a file of the users' choosing
				if (whereToSave==null) return;
				
			}
			
		}
		private static class SaveListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showInputDialog("Input a file name");
			}
		}
		private static class QuitListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit", "Confirm Quit", JOptionPane.YES_NO_OPTION);
				if (confirm == JOptionPane.YES_OPTION) System.exit(0);
			}
		}
}
