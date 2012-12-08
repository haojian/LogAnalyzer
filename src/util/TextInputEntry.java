package util;

public class TextInputEntry {
	private long time;
	private String longest_text;
	private int backspace_count;
	
	private String userid;
	private int taskIndex;
	
	public TextInputEntry(){
		
	}
	
	public TextInputEntry(long _time, String _longest_text, String _userid,
			int _taskIndex) {
		time = _time;
		longest_text = _longest_text;
		userid = _userid;
		taskIndex = _taskIndex;
	}
	
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getLongest_text() {
		return longest_text;
	}
	public void setLongest_text(String longest_text) {
		this.longest_text = longest_text;
	}
	public int getBackspace_count() {
		return backspace_count;
	}
	public void setBackspace_count(int backspace_count) {
		this.backspace_count = backspace_count;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public int getTaskIndex() {
		return taskIndex;
	}

	public void setTaskIndex(int taskIndex) {
		this.taskIndex = taskIndex;
	}
}
