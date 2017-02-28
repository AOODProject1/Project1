package aoodp1.item;

import java.time.LocalDate;

public class InactiveItem extends ActionItem{
	private static final long serialVersionUID = -1808405248761724916L;
	private LocalDate dateActive;
	public InactiveItem(ActionItem oldItem,LocalDate active) {
		super(oldItem.getName(),Priority.INACTIVE);
		this.dateActive=active;
		oldItem.changePriority(Priority.INACTIVE);
		appendHistory(oldItem.getHistoryAsArray());
		dates = oldItem.dates.clone();
		comment = oldItem.comment;
	}
	public LocalDate getDateActive() {
		return dateActive;
	}
	public void validateDates() {
		super.validateDates();
		if (dateActive.isBefore(LocalDate.now())) {
			this.changePriority(Priority.CURRENT);
		}
	}
	public String toString() {
		return dateActive + " | " + super.toString();
	}
}
