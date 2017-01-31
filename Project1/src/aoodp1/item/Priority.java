package aoodp1.item;

import java.awt.Color;
import java.awt.Font;
import java.util.Date;

import javax.swing.UIManager;

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
	Font TranslatePriority(Priority p) {
		switch (this) {
		case URGENT:return new Font("SansSerif",Font.BOLD,12);
		case CURRENT:return new Font("SansSerif",Font.PLAIN,12);
		case EVENTUAL:return new Font("SansSerif",Font.ITALIC,12);
		case INACTIVE:return new Font("SansSerif",Font.ITALIC,12);
		case COMPLETED:return new Font("SansSerif",Font.PLAIN,12);
		default:return new Font("SansSerif",Font.PLAIN,12);
		}
	}
	public String toString() {
		switch (this) {
		case URGENT:return "Urgent";
		case CURRENT:return "Current";
		case EVENTUAL:return "Eventual";
		case INACTIVE:return "Inactive";
		case COMPLETED:return "Completed";
		default:return "Unknown Priority";
		}
	}
}
