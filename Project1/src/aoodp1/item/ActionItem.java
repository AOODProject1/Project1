package aoodp1.item;

import java.util.ArrayList;
import java.sql.Timestamp;
import java.time.LocalDate;

public class ActionItem {
	private String s; //Name
	private Priority p;
	private String comment;
	private ArrayList<String> history;
	private PriorityDate dates[];
	public ActionItem(String s) {
		this.s=s;
		this.p=Priority.URGENT;
		history = new ArrayList<String>();
		dates = new PriorityDate[3];
	}
	public ActionItem(String s,Priority p) {
		this.s=s;
		this.p=p;
		history = new ArrayList<String>();
		dates = new PriorityDate[3];
	}
	public String getName() {
		return s;
	}
	public Priority getPriority() {
		return p;
	}
	/**
	 * Checks the dates to make sure the dates specified in dates[] haven't past.
	 */
	public void validateDates() {
		for (int i=0;i<3;i++) {
			if (dates[i]==null) continue;
			if (dates[i].getDate().isBefore(LocalDate.now())) {
				if (p.ordinal() < i) {
					addHistory("Priority changed from \"" + p + "\" to \"" + Priority.values()[i] + "\"");
					p = Priority.values()[i];
					
				}
			}
		}
	}
	public void addHistory(String message) {
		String time = new Timestamp(System.currentTimeMillis()).toString();
		history.add(0, "[" + time.substring(0, time.indexOf(".")) + "] " + message);
	}
	public void changeName(String name) {
		addHistory("Name changed from \"" + s + "\" to \"" +  name + "\"");
		s=name;
	}
	public void changeComment(String comment) {
		addHistory("Comment changed from \"" + this.comment + "\" to \"" + comment + "\"");
		this.comment=comment;
	}
	public void changePriority(Priority p) {
		addHistory("Priority changed from \"" + this.p + "\" to \"" + p + "\"");
		this.p=p;
	}
	public String getHistory() {
		String out="";
		for (String item : (String[])history.toArray()) {
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
		return (String[])history.toArray();
	}
	public void changePriorityDate(LocalDate d,Priority p) {
		dates[p.ordinal()] = new PriorityDate(d,p);
	}
	public String getDates() {
		String out="Dates at which the priority will change:\n";
		for (PriorityDate pd : dates) {
			out += pd + "\n";
		}
		return out;
	}
	public String toString() {
		return getHeader() +"\n" + getHistory();	
	}
	public String getHeader() {
		return getName() + "\nPriority: " + p.toString() +"\n"+ getDates();
	} 
}
