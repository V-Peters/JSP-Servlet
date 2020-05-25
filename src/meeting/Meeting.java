package meeting;

public class Meeting {
	
	private int id;
	private String name;
	private String date;
	private String time;
	private boolean display;
	
	public Meeting(int id, String name, String date, String time, boolean display) {
		this.id = id;
		this.name = name;
		this.date = date;
		this.time = time;
		this.display = display;
	}
	
	public Meeting(String name, String datum, String uhrzeit, boolean anzeigen) {
		this.name = name;
		this.setDate(datum);
		this.setTime(uhrzeit);
		this.display = anzeigen;

	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public boolean isDisplay() {
		return display;
	}
	
	public void setDisplay(boolean display) {
		this.display = display;
	}
	

}
