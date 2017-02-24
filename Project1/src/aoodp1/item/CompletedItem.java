package aoodp1.item;

import java.time.LocalDate;

public class CompletedItem extends ActionItem {
	private static final long serialVersionUID = -1480906812633904166L;
	
	private LocalDate completed;
	public CompletedItem(ActionItem oldItem, LocalDate timeDeactivated) {
		super(oldItem.getName(),Priority.COMPLETED);
		appendHistory(oldItem.getHistoryAsArray());
		dates = oldItem.dates.clone();
		comment = oldItem.comment;
		completed = timeDeactivated;
	}
	public LocalDate getDateCompleted() {
		return completed;
	}
	public String toString() {
		return completed +" " + super.toString();
	}
}
