package util;

public class FrustrationEntry {
	
	private String userid;
	private int taskId;
	private long timestamp;
	private double frustrationlvl;
	
	public FrustrationEntry(String _userid, int _taskId, long _timestamp, double _frustrationlvl){
		userid = _userid;
		taskId = _taskId;
		timestamp = _timestamp;
		frustrationlvl = _frustrationlvl;
	}
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public double getFrustrationlvl() {
		return frustrationlvl;
	}
	public void setFrustrationlvl(double frustrationlvl) {
		this.frustrationlvl = frustrationlvl;
	}
}
