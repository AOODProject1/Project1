package aoodp1.screens;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import aoodp1.item.ActionItem;
/**
 * Screen where history comments can be edited
 *
 */
public class HistoryScreen extends JFrame {
	private static final long serialVersionUID = 6086501999008126333L;
	private static JList<String> historyItems = new JList<String>();
	public void showCommentScreen(ActionItem ai) {
		historyItems.setListData(ai.getHistoryAsArray());
		//historyItems.setListData(new String[] {"yr","a","gd","boi"});
		historyItems.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int selected = historyItems.getSelectedIndex();
					String newComment = JOptionPane.showInputDialog(historyItems, "Input a new Comment", historyItems.getSelectedValue());
					ai.changeHistory(selected, newComment);
					historyItems.setListData(ai.getHistoryAsArray());
				}
			}
		});
		add(historyItems);
		pack();
		setVisible(true);
	}
}
