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
		static DefaultListModel<ActionItem[]> model;
		static JList<ActionItem> items;
		private static final String COMMENT="Comment",HISTORY="History",PRINT="Print to Console";
		public static void main(String[] args) {
			new MainScreen("default");
		} 
		public MainScreen (String user){
			this.username=user;
			whereToSave = new File(Constants.FILEHEADER + username + "/ListData.tdl");
			f = new JFrame();
			JMenuItem save = new JMenuItem("Save");
			f.setSize(500, 500);
			f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			f.addWindowListener(new SaveAtClose());
			f.setLayout(new FlowLayout());
			JMenuBar bar = new JMenuBar();
			f.setLayout(new BorderLayout());
			
			toDos.add(new ActionItem("wowoee", Priority.CURRENT));
			toDos.add(new ActionItem("fjnejf", Priority.COMPLETED));
			toDos.add(new ActionItem("wonfvbebwoee", Priority.EVENTUAL));
			model = new DefaultListModel<ActionItem[]>();
			model.addElement(toDos.toArray(new ActionItem[0]));
			model.addElement(toDos.toArray(new ActionItem[1]));
			model.addElement(toDos.toArray(new ActionItem[2]));
			mouseAdapter drag = new mouseAdapter();
			items = new JList<ActionItem>(toDos.toArray(new ActionItem[0]));
			items.addMouseListener(drag);
			items.addMouseMotionListener(drag);
			JMenu file = new JMenu("File");
			JMenuItem quit= new JMenuItem("Quit");
			JMenuItem closedActionItems = new JMenuItem("Closed Action Items");
	        bar.add(file);
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
			private static JTextField[] dates;
			public static void editActionItem(ActionItem ai) {
				a=ai;
				JFrame f = new JFrame("Edit Item");
				JPanel pB = new JPanel(); //The radio buttons for priorities
				JPanel pD = new JPanel(); //The dates and checkboxes and buttons
				JPanel[] d = new JPanel[3]; //the date/checkbox combos
				
				for (int i=0;i<d.length;i++) { //date/checkbox panel setup
					d[i] = new JPanel();
					d[i].setLayout(new BoxLayout(d[i],BoxLayout.X_AXIS));
				}
				pB.setLayout(new BoxLayout(pB,BoxLayout.Y_AXIS));
				pD.setLayout(new BoxLayout(pD,BoxLayout.Y_AXIS));
				ButtonGroup prio = new ButtonGroup();
				f.setLayout(new FlowLayout());

				JTextField name = new JTextField(ai.getName(),30); //Component setup/initialization
				JRadioButton[] p = new JRadioButton[5];
				dates = new JTextField[3];
				JCheckBox[] datesEnabled = new JCheckBox[3];
				JButton comment = new JButton(COMMENT);
				JButton history = new JButton(HISTORY);
				JButton print = new JButton(PRINT);
				for (int i=0;i<dates.length;i++) { //setting up dates[]
					dates[i] = new JTextField("__/__/____");
					dates[i].setEnabled(false);
					datesEnabled[i] = new JCheckBox();
				}
				for (int i=0;i<p.length;i++) { //setting up radio buttons
					p[i] = new JRadioButton(Priority.values()[i].toString());
					prio.add(p[i]);
				}
				p[0].setSelected(true);
				
				name.addActionListener(new TextEdit()); //adding listeners
				for (JRadioButton r : p) {r.addActionListener(new PriorityEdit());}
				comment.addActionListener(new ButtonListener());
				history.addActionListener(new ButtonListener());
				print.addActionListener(new ButtonListener());
				
				f.add(name); //adding components
				for (JRadioButton r : p) { //adding radiobuttons
					pB.add(r);
				}
				for (int i=0;i<dates.length;i++) { //adding dates
					d[i].add(datesEnabled[i]);
					d[i].add(dates[i]);
					datesEnabled[i].addActionListener(new PDateCBox(i));
					dates[i].addTextListener(new PDateEdit(i));
					pD.add(d[i]);
				}
				pD.add(comment); //adding buttons
				pD.add(history);
				pD.add(print);
				
				f.add(pB); //adding priority radiobutton panel
				f.add(pD); //adding right panel
				
				f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //setting the frame's properties
				f.setSize(new Dimension(375,250));
				f.setResizable(false);
				f.setVisible(true);
			}
			private static class TextEdit implements ActionListener {
				public void actionPerformed(ActionEvent e) {
					a.changeName(((JTextField) e.getSource()).getText());
				}
			}
			private static class PriorityEdit implements ActionListener { //called when the radiobuttons are pressed to change priority
				public void actionPerformed(ActionEvent e) {
					String name = ((JRadioButton)e.getSource()).getText();
					a.changePriority(Priority.toPriority(name));
				}
			}
			private static class PDateCBox implements ActionListener {
				private int index;
				public PDateCBox(int index) {
					this.index=index;
				}
				public void actionPerformed(ActionEvent e) {
					if (((JCheckBox)e.getSource()).isSelected())
					dates[index].setEnabled(true);
					else dates[index].setEnabled(false);
				}
			}
			private static class PDateEdit implements TextListener {
				private int index;
				public PDateEdit(int index) {
					this.index=index;
				}
				public void textValueChanged(TextEvent e) {
					try {
						String messageText = ((JTextField)e.getSource()).getText().trim();
						String y = messageText.substring(0, messageText.indexOf("/"));
						System.out.println(y);
						LocalDate d = LocalDate.of(0, 0, 0);
					} catch (ClassCastException e2) {
						System.err.print(e2.getMessage());
					} catch (Exception e2) {}
				}
			}
			private static class ButtonListener implements ActionListener { //!!FLAG!! Work to be done here
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
		private static class mouseAdapter extends MouseInputAdapter{
			private boolean mouseDrag = false;
			private int dragSourceIndex;
			@Override
			public void mousePressed(MouseEvent e){
				dragSourceIndex = items.getSelectedIndex();
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if(mouseDrag){
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