package txtOutPut;

import java.util.ArrayList;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class PageRelevanceEntry {
	public DescriptiveStatistics pressureStats, pressszieStats;
	public DescriptiveStatistics gestureLengthStatswithoutMultitouch, gestureLengthStatswithMultitouch;
	public DescriptiveStatistics gestureSpeedStatswithoutMultitouch, gestureSpeedStatswithMultitouch;
	public DescriptiveStatistics gestureZoomStats, gestureZoominStats, gestureZoomOutStats;
	public DescriptiveStatistics gestureScrollSpeedXStats, gestureScrollSpeedXPositiveStats, gestureScrollSpeedXNegativeStats;
	public DescriptiveStatistics gestureScrollSpeedYStats, gestureScrollSpeedYPositiveStats, gestureScrollSpeedYNegativeStats;
	public DescriptiveStatistics gestureScrollDistXStats, gestureScrollDistYStats;
	
	public long page_viewing_time;
	public int time_in_task;
	public int query_length;
	public int query_index_in_task;
	public int index_in_SERP;
	public double relevance_report;
	
	//Debugging support
	public ArrayList<String> evList = new ArrayList<String>();
	
	public PageRelevanceEntry(){
		pressureStats = new DescriptiveStatistics();
		pressszieStats = new DescriptiveStatistics();
		gestureLengthStatswithoutMultitouch = new DescriptiveStatistics();
		gestureLengthStatswithMultitouch = new DescriptiveStatistics();
		gestureSpeedStatswithoutMultitouch = new DescriptiveStatistics();
		gestureSpeedStatswithMultitouch = new DescriptiveStatistics();
		
		gestureZoomStats = new DescriptiveStatistics();
		gestureZoominStats = new DescriptiveStatistics();
		gestureZoomOutStats = new DescriptiveStatistics();
		gestureScrollSpeedXStats = new DescriptiveStatistics();
		gestureScrollSpeedXPositiveStats = new DescriptiveStatistics();
		gestureScrollSpeedXNegativeStats = new DescriptiveStatistics();
		
		gestureScrollSpeedYStats = new DescriptiveStatistics();
		gestureScrollSpeedYPositiveStats = new DescriptiveStatistics();
		gestureScrollSpeedYNegativeStats = new DescriptiveStatistics();
		
		gestureScrollDistXStats = new DescriptiveStatistics();
		gestureScrollDistYStats = new DescriptiveStatistics();
		
		page_viewing_time = 0;
		time_in_task = 0;
		query_length = 0;
		query_index_in_task = 0;
		index_in_SERP = 0;
		
		evList = new ArrayList<String>();
	}
	
	public void clear(){
		pressureStats = new DescriptiveStatistics();
		pressszieStats = new DescriptiveStatistics();
		gestureLengthStatswithoutMultitouch = new DescriptiveStatistics();
		gestureLengthStatswithMultitouch = new DescriptiveStatistics();
		gestureSpeedStatswithoutMultitouch = new DescriptiveStatistics();
		gestureSpeedStatswithMultitouch = new DescriptiveStatistics();
		
		gestureZoomStats = new DescriptiveStatistics();
		gestureZoominStats = new DescriptiveStatistics();
		gestureZoomOutStats = new DescriptiveStatistics();
		gestureScrollSpeedXStats = new DescriptiveStatistics();
		gestureScrollSpeedXPositiveStats = new DescriptiveStatistics();
		gestureScrollSpeedXNegativeStats = new DescriptiveStatistics();
		
		gestureScrollSpeedYStats = new DescriptiveStatistics();
		gestureScrollSpeedYPositiveStats = new DescriptiveStatistics();
		gestureScrollSpeedYNegativeStats = new DescriptiveStatistics();
		
		gestureScrollDistXStats = new DescriptiveStatistics();
		gestureScrollDistYStats = new DescriptiveStatistics();
		
		page_viewing_time = 0;
		time_in_task = 0;
		query_length = 0;
		query_index_in_task = 0;
		index_in_SERP = 0;
		
		evList = new ArrayList<String>();
	}
	
	
	public void outputEvList(){
		for(String tmp : evList){
			System.out.print(tmp + "\t");
		}
	}
}
