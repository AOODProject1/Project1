package aoodp1.screens;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import aoodp1.item.ActionItem;
import aoodp1.item.Priority;

public class EditActionScreen {
	private static final String COMMENT="Comment",HISTORY="History",PRINT="Print to Console";
	private static ActionItem a;
	private static JTextField[] dates;
	public static void main(String[] args) {
		editActionItem(new ActionItem("лю",Priority.CURRENT));
	}
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
			datesEnabled[i] = new JCheckBox();
			if (a.getPDates()[i]==null) {
				dates[i] = new JTextField("YYYY-MM-DD");
				dates[i].setEnabled(false);
			} else {
				dates[i] = new JTextField(a.getPDates()[i].getDate().toString());
				dates[i].setEnabled(true);
				datesEnabled[i].setSelected(true);
			}
		}
		for (int i=0;i<p.length;i++) { //setting up radio buttons
			p[i] = new JRadioButton(Priority.values()[i].toString());
			prio.add(p[i]);
		}
		if (a.getPriority()!=null)
			p[a.getPriority().ordinal()].setSelected(true);
		
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
			dates[i].getDocument().addDocumentListener(new PDateEdit(i));
			pD.add(d[i]);
		}
		pD.add(comment); //adding buttons
		pD.add(history);
		pD.add(print);
		
		f.add(pB); //adding priority radiobutton panel
		f.add(pD); //adding right panel
		
		f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //setting the frame's properties
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					MainScreen.sortToDos();
				} catch (NullPointerException x) {
					System.err.println("Don't run the EditActionScreen without the MainScreen! It gets lonely.");
					System.exit(0);
				}
				f.dispose();
			}
		});
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
	private static class PDateEdit implements DocumentListener {
		private int index;
		public PDateEdit(int index) {
			this.index=index;
		}
		public void textValueChanged() {
			try {
				String messageText = dates[index].getText().trim();
				//System.out.println(messageText);
				int[] dmy = parseString(messageText,'-');
				if ((dmy[0]+"").length()<4) return; //only 4-digit years
				LocalDate d = LocalDate.of(dmy[0], dmy[1], dmy[2]); //YYYY-MM-DD - format for dates
				a.changePriorityDate(d, Priority.values()[index]);
				a.validateDates();
			} catch (ClassCastException e2) {
				System.err.println(e2.toString());
			} catch (Exception e2) {}
		}
		public void changedUpdate(DocumentEvent arg0) {textValueChanged();}
		public void insertUpdate(DocumentEvent arg0) {textValueChanged();}
		public void removeUpdate(DocumentEvent arg0) {textValueChanged();}
	}
	/**
	 * Parses a string into a bunch of int s (Example: {@code parseString("04/42/25",'/')} would return {@code "0,42,25"}
	 * @param toParse the string
	 * @param parseFor the character separator to search for
	 * @return
	 */
	private static int[] parseString(String toParse, char parseFor) {
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		int lastChar=0;
		for (int c=0;c<toParse.length();c++) {
			if (toParse.charAt(c) == parseFor) {
				numbers.add(Integer.parseInt(toParse.substring(lastChar, c)));
				lastChar=c+1;
			}
		}
		numbers.add(Integer.parseInt(toParse.substring(lastChar)));
		int[] n2 = new int[numbers.size()];
		int index=0;
		for (Integer i : numbers) {
			n2[index] = i;
			index++;
		}
		return n2;
	}
	private static class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton)e.getSource();
			switch (source.getText()) {
				case COMMENT:new CommentWindow().activate();break;
				case HISTORY:JOptionPane.showMessageDialog(null, a.getHistory());break;
				case PRINT:System.out.println(a.getFullInfo());break;
				default:
			}
		}
	}
	private static class CommentWindow extends JFrame {
		private static final long serialVersionUID = 3246864078201652807L;
		public void activate() {
			JTextArea window = new JTextArea(15,45);
			window.setText(a.getComment());
			setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
			JPanel buttons = new JPanel();
			JButton commit=new JButton("Commit");
			JButton clear = new JButton("Clear");
			
			commit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					a.changeComment(window.getText());
					close();
				}});
			clear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					window.setText(null);
				}});
			buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
			buttons.add(commit);
			buttons.add(clear);
			add(window);
			add(buttons);
			pack();
			setVisible(true);
		}
		private void close() {
			dispose();
		}
	}
}
