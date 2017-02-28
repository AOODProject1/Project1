package aoodp1.item;

import java.util.ArrayList;

import aoodp1.screens.MainScreen;
import aoodp1.util.Constants;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;

public class ActionItem implements Comparable<ActionItem>, Serializable {
	private static final long serialVersionUID = 5418447856094847813L;
	protected String name;
	protected Priority p;
	protected String comment;
	protected ArrayList<String> history;
	protected PriorityDate dates[];
	public ActionItem(String name) {
		this.name=name;
		this.p=Priority.URGENT;
		history = new ArrayList<String>();
		dates = new PriorityDate[3];
	}
	public ActionItem(String name,Priority p) {
		this.name=name;
		this.p=p;
		history = new ArrayList<String>();
		dates = new PriorityDate[3];
	}
	public String getName() {
		return name;
	}
	public Priority getPriority() {
		return p;
	}
	public String getComment() {
		return comment;
	}
	/**
	 * Checks the dates to make sure the dates specified in dates[] haven't past.
	 */
	public void validateDates() {
		for (int i=0;i<3;i++) {
			if (dates[i]==null) continue;
			if (dates[i].getDate().isBefore(LocalDate.now())) {
				if (p.ordinal() > i) {
					addHistory("Priority changed from \"" + p + "\" to \"" + Priority.values()[i] + "\" because date passed");
					p = Priority.values()[i];
					
				}
			}
		}
	}
	public void addHistory(String message) {
		String time = new Timestamp(System.currentTimeMillis()).toString();
		history.add(0, "[" + time.substring(0, time.indexOf(".")) + "] " + message);
	}
	public void appendHistory(String[] message) {
		for (String h : message) {
			history.add(h);
		}
	}
	public void changeName(String name) {
		addHistory("Name changed from \"" + this.name + "\" to \"" +  name + "\"");
		this.name=name;
	}
	public void changeComment(String comment) {
		addHistory("Comment changed from \"" + this.comment + "\" to \"" + comment + "\"");
		this.comment=comment;
	}
	public void changePriority(Priority p) {
		addHistory("Priority changed from \"" + this.p + "\" to \"" + p + "\"");
		this.p=p;
	}
	public void changePriorityDate(LocalDate d,Priority p) {
		addHistory("Date to change priority changed from \"" + this.dates[p.ordinal()] + "\" to \"" + new PriorityDate(d,p) + "\"");
		dates[p.ordinal()] = new PriorityDate(d,p);
	}
	public String getHistory() {
		String out="";
		for (String item : history.toArray(new String[0])) {
			out += item + "\n";
		}
		return out;
	}
	public void editHistory(int i,String text) {
		history.set(i, text);
	}
	public String getHistoryItem(int i) {
		return history.get(i);
	}
	public String[] getHistoryAsArray() {
		return history.toArray(new String[0]);
	}
	public void changeHistory(int index, String newHistory) {
		history.set(index, newHistory);
	}
	public String getDates() {
		String out="Dates at which the priority will change:\n";
		for (PriorityDate pd : dates) {
			out += pd + "\n";
		}
		return out;
	}
	public PriorityDate[] getPDates() {
		return dates;
	}
	public String getFullInfo() {
		return getHeader() + "\nComment: " + getComment() + "\n" + getDates() + "\nHistory:\n" + getHistory();
	}
	public String toString() {
		return getHeader();
	}
	public String getHeader() {
		return getName() + " | Priority: " + p.toString();
	}
	/**
	 * Compares ActionItems' priorities
	 */
	public int compareTo(ActionItem o) {
		if (p!=o.getPriority())
			return p.ordinal() - o.getPriority().ordinal();
		if (MainScreen.getComparason() == Constants.SORTBYNAME) {
			return compareName(o);
		} else if (MainScreen.getComparason() == Constants.SORTBYDATE) {
			return compareDates(o,MainScreen.getDateOption());
		} else {
			return 0;
		}
	}
	/**
	 * Compares ActionItems' names
	 * @param o The second ActionItem
	 * @return The result of {@code this.getName().compareToIgnoreCase(o.getName);}
	 */
	public int compareName(ActionItem o) {
		return getName().compareToIgnoreCase(o.getName());
	}
	/**
	 * Compares the dates for the ActionItems {@code this} and {@code o}
	 * @param o the other ActionItem
	 * @param p the Priority whose date is checked
	 * @throws ArrayIndexOutOfBoundsException when p is not Urgent, Current, or Eventual
	 * @return the result of the comparison in LocalDate for the ActionItems' dates, or 0 if one doesn't have the PriorityDate set.
	 * @see PriorityDate
	 */
	public int compareDates(ActionItem o,Priority p) {
		if (p.ordinal()>2) throw new ArrayIndexOutOfBoundsException("Priority Dates only apply to Urgent, Current, and Eventual");
		try {
			return dates[p.ordinal()].getDate().compareTo(o.getPDates()[p.ordinal()].getDate());
		} catch (NullPointerException e){ //Either date doesn't have the PriorityDate set
			return 0;
		}
	}
}