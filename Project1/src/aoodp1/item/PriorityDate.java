package aoodp1.item;

import java.time.LocalDate;

public class PriorityDate {
	private LocalDate dateToChangeAt;
	private Priority priorityToChangeTo;
	public PriorityDate(LocalDate d, Priority p) {
		dateToChangeAt=d;
		priorityToChangeTo=p;
	}
	public LocalDate getDate() {
		return dateToChangeAt;
	}
	public Priority getPriority() {
		return priorityToChangeTo;
	}

}
