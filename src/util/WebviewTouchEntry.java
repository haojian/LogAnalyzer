  package util;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class WebviewTouchEntry {
	private int touch_point_num ;
	private ArrayList<TracePointEntry> point_list;
	private TouchActionType act_type;
	public enum TouchActionType{
		DOWN, MOVE, UP, ERROR
	}
	
	private String uid;
	private int taskIndex;
	private String url;
	
	public WebviewTouchEntry(){
		touch_point_num = 0;
		point_list = new ArrayList<TracePointEntry>();
		act_type = TouchActionType.ERROR;
	}
	public WebviewTouchEntry(int _touch_point_num, String _act_type, ArrayList<TracePointEntry> _point_list){
		touch_point_num = _touch_point_num;
		if(_act_type.equals("DOWN"))
			act_type = TouchActionType.DOWN;
		else if(_act_type.equals("MOVE"))
			act_type = TouchActionType.MOVE;
		else if(_act_type.equals("UP"))
			act_type = TouchActionType.UP;
		else 
			act_type = TouchActionType.ERROR;
		this.point_list = _point_list;
	}
	
	public WebviewTouchEntry(int _touch_point_num, String _act_type, ArrayList<TracePointEntry> _point_list, String _uid){
		touch_point_num = _touch_point_num;
		if(_act_type.equals("DOWN"))
			act_type = TouchActionType.DOWN;
		else if(_act_type.equals("MOVE"))
			act_type = TouchActionType.MOVE;
		else if(_act_type.equals("UP"))
			act_type = TouchActionType.UP;
		else 
			act_type = TouchActionType.ERROR;
		this.point_list = _point_list;
		uid = _uid;
	}
	
	public WebviewTouchEntry(int _touch_point_num, String _act_type, ArrayList<TracePointEntry> _point_list, String _uid, int _taskIndex, String _url){
		touch_point_num = _touch_point_num;
		if(_act_type.equals("DOWN"))
			act_type = TouchActionType.DOWN;
		else if(_act_type.equals("MOVE"))
			act_type = TouchActionType.MOVE;
		else if(_act_type.equals("UP"))
			act_type = TouchActionType.UP;
		else 
			act_type = TouchActionType.ERROR;
		this.point_list = _point_list;
		uid = _uid;
		taskIndex = _taskIndex;
		url = _url;
	}
	
	public int getTouch_Point_Num(){
		return touch_point_num;
	}
	
	public TouchActionType getAction_Type(){
		return act_type;
	}
	
	public ArrayList<TracePointEntry> getPointList(){
		return point_list;
	}
	
	public long getTimeDifference(WebviewTouchEntry target){
		long res = Math.abs(target.getPointList().get(0).getTimeStamp() - this.getPointList().get(0).getTimeStamp());
		return res;
	}
	public int getTaskIndex() {
		return taskIndex;
	}
	public void setTaskIndex(int taskIndex) {
		this.taskIndex = taskIndex;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
