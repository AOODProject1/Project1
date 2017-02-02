package aoodp1.screens;

import javax.swing.JFrame;

import aoodp1.item.ActionItem;

public class EditActionScreen {
	public static ActionItem editActionItem(ActionItem ai) {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(500, 500);
		f.pack();
		f.setVisible(true);
		return ai; //do changes first
	}

}
