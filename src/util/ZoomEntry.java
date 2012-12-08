package util;

public class ZoomEntry {
	private long time;
	private double oldZoomScale;
	private double newZoomScale;
	private String url;
	
	private String userid;
	private int taskIndex;
	
	public ZoomEntry(double _oldZoomScale, double _newZoomScale, long _time){
		oldZoomScale = _oldZoomScale;
		newZoomScale = _newZoomScale;
		time = _time;
	}
	
	public ZoomEntry(double _oldZoomScale, double _newZoomScale, long _time, String _url){
		oldZoomScale = _oldZoomScale;
		newZoomScale = _newZoomScale;
		time = _time;
		url = _url;
	}
	
	public ZoomEntry(double _oldZoomScale, double _newZoomScale, long _time, String _url, String _uid, int _taskIndex){
		oldZoomScale = _oldZoomScale;
		newZoomScale = _newZoomScale;
		time = _time;
		url = _url;
		
		userid = _uid;
		taskIndex = _taskIndex;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public double getOldZoomScale() {
		return oldZoomScale;
	}

	public void setOldZoomScale(double oldZoomScale) {
		this.oldZoomScale = oldZoomScale;
	}

	public double getNewZoomScale() {
		return newZoomScale;
	}

	public void setNewZoomScale(double newZoomScale) {
		this.newZoomScale = newZoomScale;
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
