package aoodp1.screens;

import java.awt.BorderLayout;
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
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import aoodp1.item.ActionItem;
import aoodp1.item.Priority;
import aoodp1.util.Constants;
//http://stackoverflow.com/questions/3804361/how-to-enable-drag-and-drop-inside-jlist
public class MainScreen {
	
		private static JFrame f;
		private static JPanel p;
		private static ArrayList<ActionItem> toDos = new ArrayList<ActionItem>();
		private static File whereToSave=null;
		private String username;
		DefaultListModel<ActionItem[]> model;
		private static final String COMMENT="Comment",HISTORY="History",PRINT="Print to Console";
		public static void main(String[] args) {
			new MainScreen("default");
		} 
		public MainScreen (String user){
			this.username=user;
			whereToSave = new File(Constants.FILEHEADER + username + "/ListData.tdl");
			f = new JFrame();
			p = new JPanel();
			JMenuItem save = new JMenuItem("Save");
			f.setSize(500, 500);
			f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			f.addWindowListener(new SaveAtClose());
			f.setLayout(new FlowLayout());
			JMenuBar bar = new JMenuBar();
			f.add(bar);
			LayoutManager layout = new BorderLayout();
			toDos.add(new ActionItem("wowoee", Priority.CURRENT));
			toDos.add(new ActionItem("fjnejf", Priority.COMPLETED));
			toDos.add(new ActionItem("wonfvbebwoee", Priority.EVENTUAL));
			model = new DefaultListModel<ActionItem[]>();
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
	        f.setLayout(layout);
			f.add(items, BorderLayout.LINE_START);
			f.add(p, BorderLayout.PAGE_START);
	        //f.pack();
			f.setVisible(true);
			f.repaint();
			ActionEdit.editActionItem(new ActionItem("Get groceries",Priority.URGENT)); //Test EditActionItem
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
		private static class ActionEdit {
			private static ActionItem a;
			public static void main(String[] args) {
				//EditActionScreen.editActionItem(new ActionItem("EE",Priority.CURRENT));
			}
			public static void editActionItem(ActionItem ai) {
				a=ai;
				JFrame f = new JFrame();
				JPanel pB = new JPanel(); //The radio buttons for priorities
				JPanel pD = new JPanel(); //The dates and checkboxes and buttons
				JPanel[] d = new JPanel[3]; //the date/checkbox combos
				for (int i=0;i<d.length;i++) {
					d[i] = new JPanel();
					d[i].setLayout(new BoxLayout(d[i],BoxLayout.X_AXIS));
				}
				pB.setLayout(new BoxLayout(pB,BoxLayout.Y_AXIS));
				pD.setLayout(new BoxLayout(pD,BoxLayout.Y_AXIS));
				ButtonGroup prio = new ButtonGroup();
				f.setLayout(new FlowLayout());
				JTextField name = new JTextField(ai.getName(),30);
				JRadioButton[] p = new JRadioButton[5];
				JTextField[] dates = new JTextField[3];
				JCheckBox[] datesEnabled = new JCheckBox[3];
				JButton comment = new JButton(COMMENT);
				JButton history = new JButton(HISTORY);
				JButton print = new JButton(PRINT);
				comment.addActionListener(new ButtonListener());
				history.addActionListener(new ButtonListener());
				print.addActionListener(new ButtonListener());
				pD.add(comment);
				pD.add(history);
				pD.add(print);
				for (int i=0;i<dates.length;i++) {
					dates[i] = new JTextField("__/__/____");
					dates[i].setEnabled(false);
					datesEnabled[i] = new JCheckBox();
				}
				for (int i=0;i<p.length;i++) {
					p[i] = new JRadioButton(Priority.values()[i].toString());
					prio.add(p[i]);
				}
				f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				//f.setSize(500, 500);
				f.add(name);
				//f.add();
				for (JRadioButton r : p) {
					pB.add(r);
				}
				for (int i=0;i<dates.length;i++) {
					d[i].add(datesEnabled[i]);
					d[i].add(dates[i]);
					pD.add(d[i]);
				}
				//f.add(new JLabel("EEEEEEe"));
				f.add(pB);
				f.add(pD);
				f.pack();
				f.setVisible(true);
			}
			private static class TextEdit implements ActionListener {
				public void actionPerformed(ActionEvent e) {
					a.changeName(((JTextField) e.getSource()).getText());
				}
			}
			private static class PriorityEdit implements ActionListener { //called when the checkboxes are pressed, activating date field
				public void actionPerformed(ActionEvent e) {
					
				}
			}
			private static class ButtonListener implements ActionListener {
				public void actionPerformed(ActionEvent e) {
					JButton source = (JButton)e.getSource();
					switch (source.getText()) {
						case COMMENT:JOptionPane.showMessageDialog(null, a.getComment());
						case HISTORY:JOptionPane.showMessageDialog(null, a.getHistory());
						case PRINT:
						default:
					}
				}
			}
			
		}
}
