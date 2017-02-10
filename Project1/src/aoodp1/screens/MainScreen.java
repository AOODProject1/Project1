package aoodp1.screens;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import aoodp1.item.ActionItem;
import aoodp1.item.Priority;
import aoodp1.util.Constants;

public class MainScreen {
	
		private static JFrame f;
		private static JPanel p;
		private static JPanel layoutPanel;
		private static ArrayList<ActionItem> toDos = new ArrayList<ActionItem>();
		private static File whereToSave=null;
		private String username;
		public static void main(String[] args) {
			new MainScreen("default");
		} 
		public MainScreen (String user){
			this.username=user;
			whereToSave = new File(Constants.FILEHEADER + username + "/ListData.tdl");
			f = new JFrame();
			p = new JPanel();
			layoutPanel = new JPanel();
			JMenuItem save = new JMenuItem("Save");
			f.setSize(500, 500);
			f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			f.addWindowListener(new SaveAtClose());
			f.setLayout(new FlowLayout());
			JMenuBar bar = new JMenuBar();
			f.add(bar);
			LayoutManager layout = new BoxLayout(layoutPanel, BoxLayout.PAGE_AXIS);
			layoutPanel.setLayout(layout);
			toDos.add(new ActionItem("wowoee", Priority.CURRENT));
			toDos.add(new ActionItem("fjnejf", Priority.COMPLETED));
			toDos.add(new ActionItem("wonfvbebwoee", Priority.EVENTUAL));
			DefaultListModel<ActionItem[]> model = new DefaultListModel<ActionItem[]>();
			model.addElement(toDos.toArray(new ActionItem[0]));
			model.addElement(toDos.toArray(new ActionItem[1]));
			model.addElement(toDos.toArray(new ActionItem[2]));
			JList items = new JList<>(model);
			JMenu file = new JMenu("File");
			JButton quit= new JButton("Quit");
			JButton closedActionItems = new JButton("Closed Action Items");
	        bar.add(file);
	        bar.add(quit);
	        bar.add(closedActionItems);
	        file.add(save);
	        save.addActionListener(new SaveListener());
	        quit.addActionListener(new QuitListener());
	        p.add(bar);
	        layoutPanel.setBounds(0, 50, 200, 400);
	        layoutPanel.add(items);
			f.add(layoutPanel);
			f.add(p);
	        //f.pack();
			f.setVisible(true);
			f.repaint();
			new EditActionScreen().editActionItem(new ActionItem("Get groceries",Priority.URGENT)); //Test EditActionItem
			//System.out.println("EEEE");
		}
		private static boolean close() {
			int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit", "Confirm Quit", JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.NO_OPTION) return false;
				if (whereToSave==null) System.exit(0);
				try {
					whereToSave.getParentFile().mkdirs();
					whereToSave.createNewFile();
					ObjectOutputStream p = new ObjectOutputStream(new FileOutputStream(whereToSave));
					p.writeObject(toDos);
					p.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
				}
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
				//String fileName = JOptionPane.showInputDialog("Input a file name");
				//try {
				//	whereToSave = new File(FILEHEADER+fileName+".tdl");
				//	System.out.println(whereToSave);
				//} catch (NullPointerException x){System.err.println(x.getMessage());}
				try {
					//System.out.println(whereToSave.getAbsolutePath());
					whereToSave.getParentFile().mkdirs();
					whereToSave.createNewFile();
					ObjectOutputStream p = new ObjectOutputStream(new FileOutputStream(whereToSave));
					p.writeObject(toDos);
					p.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		private static class QuitListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (!close()) {
					return;
				}
				
			}

		}
		public void boxLayout(Container target, int axis){
			
		}
}
