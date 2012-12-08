package util;

public class LoadingEntry {
	private boolean isFinished;
	private long timetakes;
	private String url;
	private long start_timeStamp;
	private long end_timeStamp;
	private int progress;
	
	
	private String userid;
	private int taskIndex;
	public LoadingEntry(){
		reset();
	}
	
	public void reset(){
		isFinished = false;
		timetakes = 0;
		url = "";
		start_timeStamp = 0;
		end_timeStamp = 0;
		userid = "";
		taskIndex = 0;
	}

	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}

	public long getTimetakes() {
		this.timetakes = end_timeStamp - start_timeStamp;
		return timetakes;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public long getStart_timeStamp() {
		return start_timeStamp;
	}

	public void setStart_timeStamp(long start_timeStamp) {
		this.start_timeStamp = start_timeStamp;
	}

	public void setEnd_timeStamp(long end_timeStamp) {
		this.end_timeStamp = end_timeStamp;
	}
	
	public long getEnd_timeStamp() {
		return end_timeStamp;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
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
