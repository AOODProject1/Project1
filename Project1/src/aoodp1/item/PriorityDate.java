package aoodp1.item;

import java.io.Serializable;
import java.time.LocalDate;
/**
 * Internal class to represent a date at which an ActionItem changes priority
 *
 */
public class PriorityDate implements Serializable {
	private static final long serialVersionUID = -8772439116209023324L;
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
