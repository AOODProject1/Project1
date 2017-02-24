package aoodp1.item;

import java.time.LocalDate;

public class InactiveItem extends ActionItem{
	private static final long serialVersionUID = -1808405248761724916L;
	private LocalDate dateActive;
	public InactiveItem(String name,LocalDate active) {
		super(name,Priority.INACTIVE);
		dateActive = active;
	}
	public InactiveItem(ActionItem oldItem) {
		super(oldItem.getName(),Priority.INACTIVE);
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
		return dateActive + super.toString();
	}
}
