package aoodp1.item;

import java.util.ArrayList;
import java.sql.Timestamp;

public class ActionItem {
	private String s; //Name
	private Priority p;
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
	public void addHistory(String message) {
		String time = new Timestamp(System.currentTimeMillis()).toString();
		history.add(0, "[" + time.substring(0, time.indexOf(".")) + "] " + message);
	}
	public String getHistory() {
		String out="";
		for (String item : history.toArray(new String[0])) {
			out += item + "\n";
		}
		return out;
	}
	public String toString() {
		return getName() + "\n" + p.toString() + "\n" + getHistory();
		
	}
}
