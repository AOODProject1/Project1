package aoodp1.screens;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BoxLayout;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.TransferHandler;
import javax.swing.event.MouseInputAdapter;
import javax.swing.filechooser.FileNameExtensionFilter;

import aoodp1.item.ActionItem;
import aoodp1.item.Priority;
import aoodp1.util.Constants;

//http://stackoverflow.com/questions/3804361/how-to-enable-drag-and-drop-inside-jlist
//http://codeidol.com/java/swing/Lists-and-Combos/Reorder-a-JList-with-Drag-and-Drop/
public class MainScreen {

	private static final JFrame f = new JFrame("ToDo List");

	private static ArrayList<ActionItem> toDos = new ArrayList<ActionItem>();
	private static File whereToSave = null;
	private static String username;
	private static JList<ActionItem> items;
	private static int compOption = Constants.SORTNOCARES;
	private static Priority dateOption = Priority.URGENT;

	public static void main(String[] args) {
		show("default");
	}

	public static void show(String user) {
		username = user;
		whereToSave = new File(Constants.FILEHEADER + username + "/ListData.tdl");
		JMenuItem save = new JMenuItem("Save");
		JMenuItem saveAs = new JMenuItem("Save As...");
		JMenuItem load = new JMenuItem("Load from File");
		JMenuItem print = new JMenuItem("Print To Console");
		JMenu sort = new JMenu("Sort");
		JMenuItem sn = new JMenuItem("...By Name");
		JMenu sd = new JMenu("...By Date");
		JMenuItem[] prioDates = new JMenuItem[3];
		for (int i = 0; i < prioDates.length; i++) {
			prioDates[i] = new JMenuItem(Priority.values()[i].toString());
			prioDates[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					compOption = Constants.SORTBYDATE;
					dateOption = Priority.toPriority((((JMenuItem) e.getSource()).getText()));
					// changes dateOption to the priority on the JMenuItems'
					// name
					sortToDos();
				}
			});
			sd.add(prioDates[i]);
		}
		sn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				compOption = Constants.SORTBYNAME;
				sortToDos();
			}
		});
		sort.add(sn);
		sort.add(sd);
		f.setSize(500, 500);
		f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		f.addWindowListener(new WindowManage());
		f.setLayout(new FlowLayout());
		JMenuBar bar = new JMenuBar();
		f.setLayout(new BorderLayout());
		mouseAdapter drag = new mouseAdapter();
		items = new JList<ActionItem>(toDos.toArray(new ActionItem[0]));
		items.addMouseListener(drag);
		items.addMouseMotionListener(drag);
		JMenu file = new JMenu("File");
		JMenuItem quit = new JMenuItem("Quit");
		JMenuItem closedActionItems = new JMenuItem("Closed Action Items");
		bar.add(file);
		bar.add(sort);
		bar.add(quit);
		bar.add(closedActionItems);
		file.add(save);
		file.add(saveAs);
		file.add(load);
		file.add(print);
		save.addActionListener(new SaveListener());
		saveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser getSaveFile = new JFileChooser(Constants.FILEHEADER);
				getSaveFile.addChoosableFileFilter(new FileNameExtensionFilter("ToDoList file",".tdl"));
				getSaveFile.showOpenDialog(f);
				//bring up a menu to select file
				//save file at selected place
			}
		});
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//set to-dos as a file chosen by user
			}
		});
		print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (ActionItem item : toDos.toArray(new ActionItem[0])) {
					System.out.println(item);
				}
			}
		});
		quit.addActionListener(new QuitListener());
		f.setJMenuBar(bar);
		JTextField newInputItem = new JTextField("Input An Item");
		f.add(newInputItem, BorderLayout.SOUTH);
		newInputItem.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER && ((JTextField) e.getSource()).getText().trim().length() != 0) {
					toDos.add(0, new ActionItem(newInputItem.getText(), Priority.URGENT));
					items.setListData(toDos.toArray(new ActionItem[0]));
					newInputItem.setText("");
				}
			}
		});
		newInputItem.addMouseListener(new MouseAdapter() { // Clears text field when clicked
			public void mousePressed(MouseEvent e) {
				((JTextField) e.getSource()).setText(null);
			}
		});
		items.setDragEnabled(true);
		items.setDropMode(DropMode.INSERT);
		items.setTransferHandler(new MainScreen.ListDrop());
		new MainScreen.DragListener();
		f.add(items, BorderLayout.CENTER);
		f.setVisible(true);
	}

	public static void sortToDos() {
		Collections.sort(toDos);
		items.setListData(toDos.toArray(new ActionItem[0]));
	}

	public static void sortToDosByPriority() {
		int prevPrio = compOption;
		compOption = Constants.SORTNOCARES;
		sortToDos();
		compOption = prevPrio;
	}

	public static void updateList() {
		items.setListData(toDos.toArray(new ActionItem[0]));
	}

	private static void close() {
		int confirm = JOptionPane.showConfirmDialog(f, "Are you sure you want to quit?", "Confirm Quit",
				JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.NO_OPTION) // person doesn't want to leave
			return;
		if (whereToSave == null)
			System.exit(0);
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
	}

	public static int getComparason() {
		return compOption;
	}

	public static Priority getDateOption() {
		return dateOption;
	}

	private static class WindowManage extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			close();
		}

		public void windowOpened(WindowEvent e) {
			File userdir = new File(Constants.FILEHEADER + username + "/ListData.tdl");
			try (ObjectInputStream o = new ObjectInputStream(new FileInputStream(userdir))) {
				ArrayList<ActionItem> userToDo = (ArrayList<ActionItem>)o.readObject();
				toDos = userToDo;
				updateList();
			} catch (FileNotFoundException x) {
				JOptionPane.showMessageDialog(f, "File not found", "Error", JOptionPane.ERROR_MESSAGE);
			} catch (IOException x) {
				JOptionPane.showMessageDialog(f, "Items not found (IOException)", "Error", JOptionPane.ERROR_MESSAGE);
			} catch (ClassNotFoundException x) {
				JOptionPane.showMessageDialog(f, "File Blank/Corrupted (ClassNotFoundException)", "Error", JOptionPane.ERROR_MESSAGE);
			} catch (ClassCastException x) {
				JOptionPane.showMessageDialog(f, "File Blank/Corrupted (ClassCastException)", "Error", JOptionPane.ERROR_MESSAGE);
			} catch (Exception x) {
				JOptionPane.showMessageDialog(f, "Unexpected Error", "Error", JOptionPane.ERROR_MESSAGE);
			}
			for (ActionItem a : toDos.toArray(new ActionItem[0])) {
				a.validateDates();
				sortToDosByPriority();
			}
		}
	}

	private static class SaveListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
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
			close();
			return;

		}

	}

	private static class mouseAdapter extends MouseInputAdapter {
		private boolean mouseDrag = false;
		private int dragSourceIndex;

		@Override
		public void mousePressed(MouseEvent e) {
			dragSourceIndex = items.getSelectedIndex();
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (items.contains(e.getPoint())) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					((JList)e.getSource()).setSelectedIndex(((JList)e.getSource()).locationToIndex(e.getPoint())); //select right-click point
					JDialog aiMenu = new JDialog();
					int selected = items.getSelectedIndex(); //so that user can't change selected index
					aiMenu.setTitle("Action Item Options");
					JLabel name = new JLabel("ActionItem: " + toDos.get(selected).toString());
					JButton delete = new JButton("Delete Action Item");
					JButton edit = new JButton("Edit Action Item");
					JButton complete = new JButton("Set to Completed");
					delete.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (JOptionPane.showConfirmDialog(aiMenu, "Are you sure?", "Confirmation of Deletion", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
								toDos.remove(items.getSelectedIndex());
								updateList();
								aiMenu.dispose();
							}
						}
					});
					complete.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							toDos.get(selected).changePriority(Priority.COMPLETED);
							sortToDosByPriority();
						}
					});
					JPanel buttons = new JPanel();
					buttons.add(delete);
					//buttons.add(edit);
					buttons.add(complete);
					buttons.setLayout(new BoxLayout(buttons,BoxLayout.X_AXIS));
					aiMenu.add(name);
					aiMenu.add(buttons);
					aiMenu.setLayout(new BoxLayout(aiMenu.getContentPane(), BoxLayout.X_AXIS));
					aiMenu.pack();
					aiMenu.setVisible(true);
				} else if (e.getClickCount() == 2) {
					EditActionScreen.editActionItem(items.getSelectedValue());
				}
			}
		}
	}

	private static class DragListener implements DragSourceListener, DragGestureListener {
		private DragSource ds = new DragSource();

		public DragListener() {
			ds.createDefaultDragGestureRecognizer(items, DnDConstants.ACTION_MOVE, this);
		}

		public void dragGestureRecognized(DragGestureEvent dge) {
			StringSelection transferable = new StringSelection(Integer.toString(items.getSelectedIndex()));
			ds.startDrag(dge, DragSource.DefaultCopyDrop, transferable, this);
		}

		public void dragEnter(DragSourceDragEvent dsde) {
		}

		public void dragExit(DragSourceEvent dse) {
		}

		public void dragOver(DragSourceDragEvent dsde) {
		}

		public void dragDropEnd(DragSourceDropEvent dsde) {
		}

		public void dropActionChanged(DragSourceDragEvent dsde) {
		}
	}

	private static class ListDrop extends TransferHandler {
		private static final long serialVersionUID = 1142577497098275366L;

		public boolean canImport(TransferSupport support) {
			return true;
		}

		public boolean importData(TransferSupport support) {
			Transferable transferable = support.getTransferable();
			String indexString = null;
			try {
				indexString = (String) transferable.getTransferData(DataFlavor.stringFlavor);
			} catch (ClassCastException e) {
				return false;
			} catch (IOException e) {
				e.printStackTrace();
			} catch (UnsupportedFlavorException e) {
				e.printStackTrace();
			}
			JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
			
			int sIndex = Integer.parseInt(indexString);
			int eIndex = dl.getIndex();
			int moveIndex = sIndex>=eIndex?eIndex:eIndex-1;
			ActionItem moved = toDos.remove(sIndex);
			toDos.add(moveIndex, moved);
			//change priority as necessary
			if (toDos.get(moveIndex).getPriority().ordinal() < toDos.get(moveIndex - 1).getPriority().ordinal()) {
				toDos.get(moveIndex).changePriority(toDos.get(moveIndex - 1).getPriority());
			} else if (toDos.get(moveIndex).getPriority().ordinal() > toDos.get(moveIndex + 1).getPriority().ordinal()) {
				toDos.get(moveIndex).changePriority(toDos.get(moveIndex + 1).getPriority());
			}
			updateList();
			return true;
		}
	}
}