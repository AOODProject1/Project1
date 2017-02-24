package aoodp1.item;

import java.time.LocalDate;

public class CompletedItem extends ActionItem {
	private static final long serialVersionUID = -1480906812633904166L;
	
	private LocalDate completed;
	public CompletedItem(String s,LocalDate timeDeactivated) {
		super(s,Priority.COMPLETED);
		completed = timeDeactivated;
	}
	public CompletedItem(ActionItem oldItem) {
		super(oldItem.getName(),Priority.COMPLETED);
		appendHistory(oldItem.getHistoryAsArray());
		dates = oldItem.dates.clone();
		comment = oldItem.comment;
	}
	public LocalDate getDateCompleted() {
		return completed;
	}
	public String toString() {
		return completed + super.toString();
	}
}
