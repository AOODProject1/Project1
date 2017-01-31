package aoodp1.item;

import java.awt.Color;
import java.awt.Font;
import java.util.Date;

public enum Priority {

	URGENT(Color.red),
	CURRENT(Color.yellow),
	EVENTUAL(Color.cyan),
	INACTIVE(Color.lightGray),
	COMPLETED(Color.green);
	
	private Color c;
	private Date d;
	Priority(Color c) {
		this.c=c;
	}
	void setDate(Date d) {
		this.d=d;
	}
	Date getDate() {
		if (d!=null)
			return d;
		return null;
	}
	Color getColor() {
		return c;
	}
	static Font TranslatePriority(Priority p) {
		return null;
	}
	public String toString() {
		switch (this) {
		case URGENT:return "Urgent";
		case CURRENT:return "Current";
		case INACTIVE:return "Inactive";
		case COMPLETED:return "Completed";
		default:return "Unknown Priority";
		}
	}
}
