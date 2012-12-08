package util;

import java.util.ArrayList;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class GestureEntry {

	private String url;
	private String userid;
	private int taskIndex;
	private long time;
	private ArrayList<WebviewTouchEntry> gestureTouchEntries;
	
	private double length;
	private double averagespeed;
	private double averagetouchpress;
	private double averagetouchsize;
	private double sdtouchpress;
	private double sdtouchsize;
	
	public GestureEntry(String _url, String _userid, int _taskIndex, ArrayList<WebviewTouchEntry> _gestureTouchEntries){
		url = _url;
		userid = _userid;
		taskIndex = _taskIndex;
		gestureTouchEntries = _gestureTouchEntries;	
		statisticInit();
	}
	
	private void statisticInit(){
		if(gestureTouchEntries.size() != 0){
			length = GraphCalculator
					.calGesture_length_pointtopoint(gestureTouchEntries);
			averagespeed =  GraphCalculator.cal_singleTouchGesture_speed(gestureTouchEntries);
			if(averagespeed < 0 )
				averagespeed = 0;
			DescriptiveStatistics touchSizeStats = new DescriptiveStatistics();
			DescriptiveStatistics touchPressureStats = new DescriptiveStatistics();
			for(WebviewTouchEntry touchEntry:gestureTouchEntries){
				for(TracePointEntry point: touchEntry.getPointList()){
					touchSizeStats.addValue(point.getPressSize());
					touchPressureStats.addValue(point.getPressure());
				}
			}
			averagetouchsize = touchSizeStats.getMean();
			sdtouchsize = touchSizeStats.getStandardDeviation();
			averagetouchpress = touchPressureStats.getMean();
			sdtouchpress = touchPressureStats.getStandardDeviation();
			time = gestureTouchEntries.get(0).getPointList().get(0).timeStamp;
		}
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
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getAveragespeed() {
		return averagespeed;
	}

	public void setAveragespeed(double averagespeed) {
		this.averagespeed = averagespeed;
	}

	public double getAveragetouchpress() {
		return averagetouchpress;
	}

	public void setAveragetouchpress(double averagetouchpress) {
		this.averagetouchpress = averagetouchpress;
	}

	public double getAveragetouchsize() {
		return averagetouchsize;
	}

	public void setAveragetouchsize(double averagetouchsize) {
		this.averagetouchsize = averagetouchsize;
	}

	public double getSdtouchpress() {
		return sdtouchpress;
	}

	public void setSdtouchpress(double sdtouchpress) {
		this.sdtouchpress = sdtouchpress;
	}

	public double getSdtouchsize() {
		return sdtouchsize;
	}

	public void setSdtouchsize(double sdtouchsize) {
		this.sdtouchsize = sdtouchsize;
	}
}
