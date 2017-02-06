package aoodp1.screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import aoodp1.item.ActionItem;

public class CommentScreen extends JFrame {
	
	public String ShowCommentScreen(ActionItem ai) {
		add(new JTextField(ai.getComment()),50,50);
		JButton commit = new JButton("Commit");
		JButton delete = new JButton("Delete");
		return "";
	}
	private static class CommitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
		}
		
	}
}
