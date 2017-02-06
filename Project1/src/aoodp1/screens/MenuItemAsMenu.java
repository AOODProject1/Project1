package aoodp1.screens;

import java.awt.Graphics;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class MenuItemAsMenu extends JMenuItem {
	private static final long serialVersionUID = 1L;

	public MenuItemAsMenu(String s) {
		super(s);
	}
	@Override
	public void paint(Graphics g) {
		new JMenu().paint(g);
	}

}
