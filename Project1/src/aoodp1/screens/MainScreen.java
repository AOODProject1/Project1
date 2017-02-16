package aoodp1.screens;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.MouseInputAdapter;

import aoodp1.item.ActionItem;
import aoodp1.item.Priority;
import aoodp1.util.Constants;
//http://stackoverflow.com/questions/3804361/how-to-enable-drag-and-drop-inside-jlist
public class MainScreen {
	
		private static JFrame f;
		private static ArrayList<ActionItem> toDos = new ArrayList<ActionItem>();
		private static File whereToSave=null;
		private String username;
		private static JList<ActionItem> items;
		private static int compOption=Constants.SORTBYNAME;
		private static Priority dateOption=Priority.URGENT;
		public static void main(String[] args) {
			new MainScreen("default");
		} 
		public MainScreen (String user){
			this.username=user;
			whereToSave = new File(Constants.FILEHEADER + username + "/ListData.tdl");
			f = new JFrame();
			JMenuItem save = new JMenuItem("Save");
			JMenu sort = new JMenu("Sort");
			JMenuItem sn = new JMenuItem("...By Name");
			JMenu sd = new JMenu("...By Date");
			JMenuItem[] prioDates = new JMenuItem[3];
			for (int i=0;i<prioDates.length;i++) {
				prioDates[i] = new JMenuItem(Priority.values()[i].toString());
				prioDates[i].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						compOption=Constants.SORTBYDATE;
						dateOption = Priority.toPriority((((JMenuItem)e.getSource()).getText())); //changes dateOption to the priority on the JMenuItems' name
						sortToDos();
					}
				});
				sd.add(prioDates[i]);
			}
			sn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						compOption=Constants.SORTBYNAME;
						sortToDos();
					}
				});
			sort.add(sn);
			sort.add(sd);
			f.setSize(500, 500);
			f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			f.addWindowListener(new SaveAtClose());
			f.setLayout(new FlowLayout());
			JMenuBar bar = new JMenuBar();
			f.setLayout(new BorderLayout());
			mouseAdapter drag = new mouseAdapter();
			items = new JList<ActionItem>(toDos.toArray(new ActionItem[0]));
			items.addMouseListener(drag);
			items.addMouseMotionListener(drag);
			JMenu file = new JMenu("File");
			JMenuItem quit= new JMenuItem("Quit");
			JMenuItem closedActionItems = new JMenuItem("Closed Action Items");
	        bar.add(file);
	        bar.add(sort);
	        bar.add(quit);
	        bar.add(closedActionItems);
	        file.add(save);
	        save.addActionListener(new SaveListener());
	        quit.addActionListener(new QuitListener());
	        f.setJMenuBar(bar);
			f.add(items, BorderLayout.LINE_START);
	        //f.pack();
			f.setVisible(true);
			f.repaint();
			EditActionScreen.editActionItem(toDos.get(0)); //Test EditActionItem
			//new ActionItem("Get groceries",Priority.URGENT)
		}
		private static void sortToDos() {
			Collections.sort(toDos);
			items.setListData(toDos.toArray(new ActionItem[0]));
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
			} catch (IOException e1) {}
			System.exit(0);
			return true; //technically unreachable
		}
		public static int getComparason() {
			return compOption;
		}
		public static Priority getDateOption() {
			return dateOption;
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
		private static class mouseAdapter extends MouseInputAdapter{
			private boolean mouseDrag = false;
			private int dragSourceIndex;
			@Override
			public void mousePressed(MouseEvent e){
				dragSourceIndex = items.getSelectedIndex();
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!mouseDrag){
					int dragTargetIndex = items.getSelectedIndex();
					ActionItem[] dragElement = model.get(dragSourceIndex);
					model.remove(dragSourceIndex);
					model.add(dragTargetIndex, dragElement);
				}
				mouseDrag = false;
			}
			@Override
			public void mouseDragged(MouseEvent e){
				mouseDrag = true;
			}
		}	
	}