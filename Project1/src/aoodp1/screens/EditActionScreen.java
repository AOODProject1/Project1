package aoodp1.screens;

import javax.swing.JFrame;
import javax.swing.JTextField;

import aoodp1.item.ActionItem;

public class EditActionScreen {
	public static ActionItem editActionItem(ActionItem ai) {
		JFrame f = new JFrame();
		JTextField name = new JTextField(ai.getName(),50);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setSize(500, 500);
		f.add(name);
		f.pack();
		f.setVisible(true);
		return ai; //do changes first
	}

}
