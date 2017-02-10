package aoodp1.item;

import java.awt.Color;
import java.awt.Font;
import java.time.LocalDate;

import aoodp1.util.PriorityException;

public enum Priority {

	URGENT(Color.red),
	CURRENT(Color.yellow),
	EVENTUAL(Color.cyan),
	INACTIVE(Color.lightGray),
	COMPLETED(Color.green);
	
	private Color c;
	private LocalDate d;
	Priority(Color c) {
		this.c=c;
	}
	void setDate(LocalDate d) {
		this.d=d;
	}
	LocalDate getDate() {
		if (d!=null)
			return d;
		return null;
	}
	Color getColor() {
		return c;
	}
	/**
	 * Translates the priority represented in an instance of {@code Priority} into a font to display on the main screen
	 * @return a font that can be displayed on the main screen
	 */
	Font TranslatePriority() {
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
	public static Priority toPriority(String s) {
		switch (s.toLowerCase()) {
		case "urgent":return URGENT;
		case "current":return CURRENT;
		case "eventual":return EVENTUAL;
		case "inactive":return INACTIVE;
		case "completed":return COMPLETED;
			default: throw new PriorityException("Non-existant priority");
		}
	}
}
