package aoodp1.screens;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import aoodp1.item.ActionItem;
import aoodp1.item.Priority;

public class EditActionScreen {
	public static void main(String[] args) {
		new EditActionScreen().editActionItem(new ActionItem("EE",Priority.CURRENT));
	}
	public EditActionScreen() {
		
	}
	public ActionItem editActionItem(ActionItem ai) {
		JFrame f = new JFrame();
		JTextField name = new JTextField(ai.getName(),50);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setSize(500, 500);
		f.add(name);
		f.add(new JLabel("EEEEEEe"));
		//f.pack();
		JDialog d = new JDialog(f);
		d.pack();
		d.setVisible(true);
		return ai; //do changes first
	}

}
