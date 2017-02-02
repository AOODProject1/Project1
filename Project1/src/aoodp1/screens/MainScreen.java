package aoodp1.screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import aoodp1.item.ActionItem;
import aoodp1.item.Priority;

public class MainScreen {
		private static JFrame f = new JFrame();
		private static JPanel p;
		private static ArrayList<ActionItem> toDos;
		private static File whereToSave=null;
		private static final String FILEHEADER = System.getProperty("user.home") + "/ToDoList/ListData/"; 
		public static void main(String[] args) {
			p = new JPanel();
			JMenuItem save = new JMenuItem("Save As...");
			f.setSize(500, 500);
			f.add(p);
			f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			f.addWindowListener(new SaveAtClose());
			JMenuBar bar = new JMenuBar();
			f.add(bar);
			JMenu file = new JMenu("File");
			JMenuItem quit= new JMenuItem("Quit");
	        JMenu closedActionItems = new JMenu("Closed Action Items");
	        bar.add(file);
	        bar.add(quit);
	        bar.add(closedActionItems);
	        file.add(save);
	        save.addActionListener(new SaveListener());
	        quit.addActionListener(new QuitListener());
	        p.add(bar);
			f.setVisible(true);
			//EditActionScreen.editActionItem(new ActionItem("Get groceries",Priority.URGENT)); //Test EditActionItem
		}
		private static boolean close() {
			int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit", "Confirm Quit", JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.NO_OPTION) return false;
			
				ObjectOutputStream p = null;
				//Save the actionitems to a file of the users' choosing
				if (whereToSave==null) {
					String fileName = JOptionPane.showInputDialog("Input a file name");
					whereToSave = new File(FILEHEADER+fileName+".tdl");
				} 
				if (whereToSave==null) System.exit(0);
				new File(FILEHEADER).mkdirs();
				try {
					whereToSave.createNewFile();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					p = new ObjectOutputStream(new FileOutputStream(whereToSave));
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					p.writeObject(toDos);
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (NullPointerException e1) {
					//Shouldn't happen except with an empty To-Do List
				}
				try {
					p.close();
				} catch (IOException e1) {
				e1.printStackTrace();
				}// catch (NullPointerEx)
				System.exit(0);
				return true;
			
			
		}
		private static class SaveAtClose implements WindowListener {

			public void windowClosing(WindowEvent e) {
				close();
			}
			
			public void windowActivated(WindowEvent e) {}
			public void windowClosed(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowOpened(WindowEvent e) {}
			
		}
		private static class SaveListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				String fileName = JOptionPane.showInputDialog("Input a file name");
				try {
					whereToSave = new File(FILEHEADER+fileName+".tdl");
					System.out.println(whereToSave);
				} catch (NullPointerException x){System.err.println(x.getMessage());}
			}
		}
		private static class QuitListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (!close()) {
					return;
				}
				
			}

		}
}
