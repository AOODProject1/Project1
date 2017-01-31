package aoodp1.item;

import java.time.LocalDate;
/**
 * Internal class to represent a date at which an ActionItem changes priority
 * @author Morgan
 *
 */
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
	public String toString() {
		return "["+dateToChangeAt+"] "+ priorityToChangeTo;
	}
}
