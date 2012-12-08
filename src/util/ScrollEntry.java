package util;

public class ScrollEntry {
	private int scrollX;
	private int scrollY;
	
	private double scrollSpeed;
	private long time;
	
	private String url;
	
	private String userid;
	private int taskIndex;
	
	public ScrollEntry(int _scrollX, int _scrollY, long _time){
		scrollX = _scrollX;
		scrollY = _scrollY;
		time = _time;
	}
	
	public ScrollEntry(int _scrollX, int _scrollY, long _time, String _url, String _userid, int _taskid){
		scrollX = _scrollX;
		scrollY = _scrollY;
		time = _time;
		
		url = _url;
		userid = _userid;
		taskIndex = _taskid;
	}
	
	
	public int getScrollX() {
		return scrollX;
	}
	public void setScrollX(int scrollX) {
		this.scrollX = scrollX;
	}
	public int getScrollY() {
		return scrollY;
	}
	public void setScrollY(int scrollY) {
		this.scrollY = scrollY;
	}
	public double getScrollSpeed() {
		return scrollSpeed;
	}
	public void setScrollSpeed(double scrollSpeed) {
		this.scrollSpeed = scrollSpeed;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
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
