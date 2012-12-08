package util;

import util.WebviewTouchEntry.TouchActionType;

public class LoadingTimeEntry {
	public enum ev_type{
		OnProgressChange, WebClientPageFinish, WebClientPageStart, ERROR
	}
	private int progress;
	private long timeStamp;
	private ev_type ev_actiontype;
	private String url;
	
	private String userid;
	private int taskIndex;
	
	public LoadingTimeEntry(){
		setTimeStamp(0);
		setProgress(0);
		setEv_actiontype(ev_type.ERROR);
	}

	public LoadingTimeEntry(int _progress, long _timeStamp, String _act_type, String _start_url){
		setProgress(_progress);
		setTimeStamp(_timeStamp);
		setUrl(_start_url);
		if(_act_type.equals("OnProgressChange"))
			setEv_actiontype(ev_type.OnProgressChange);
		else if(_act_type.equals("WebClientPageFinish"))
			setEv_actiontype(ev_type.WebClientPageFinish);
		else if(_act_type.equals("WebClientPageStart"))
			setEv_actiontype(ev_type.WebClientPageStart);
		else
			setEv_actiontype(ev_type.ERROR);
	}
	
	public LoadingTimeEntry(int _progress, long _timeStamp, String _act_type, String _start_url, String _uid, int _taskIndex){
		setProgress(_progress);
		setTimeStamp(_timeStamp);
		setUrl(_start_url);
		if(_act_type.equals("OnProgressChange"))
			setEv_actiontype(ev_type.OnProgressChange);
		else if(_act_type.equals("WebClientPageFinish"))
			setEv_actiontype(ev_type.WebClientPageFinish);
		else if(_act_type.equals("WebClientPageStart"))
			setEv_actiontype(ev_type.WebClientPageStart);
		else
			setEv_actiontype(ev_type.ERROR);
		
		userid = _uid;
		taskIndex = _taskIndex;
	}
	
	public void reset(){
		setTimeStamp(0);
		setProgress(0);
		setEv_actiontype(ev_type.ERROR);
	}
	
	public ev_type getEv_actiontype() {
		return ev_actiontype;
	}

	public void setEv_actiontype(ev_type ev_actiontype) {
		this.ev_actiontype = ev_actiontype;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	public int getTaskIndex() {
		return taskIndex;
	}

	public void setTaskIndex(int taskIndex) {
		this.taskIndex = taskIndex;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
}
