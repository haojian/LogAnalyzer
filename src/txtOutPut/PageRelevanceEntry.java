package txtOutPut;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class PageRelevanceEntry {
	public DescriptiveStatistics pressureStats, pressszieStats;
	public DescriptiveStatistics gestureStatswithoutMultitouch, gestureStatswithMultitouch;
	public DescriptiveStatistics gestureZoomStats, gestureZoominStats, gestureZoomOutStats;
	public DescriptiveStatistics gestureScrollXStats, gestureScrollXPositiveStats, gestureScrollXNegativeStats;
	public DescriptiveStatistics gestureScrollYStats, gestureScrollYPositiveStats, gestureScrollYNegativeStats;
	
	public int page_viewing_time;
	public int time_in_task;
	public int query_length;
	public int query_index_in_task;
	public int index_in_SERP;
	public int relevance_report;
	
	public PageRelevanceEntry(){
		pressureStats = new DescriptiveStatistics();
		pressszieStats = new DescriptiveStatistics();
		gestureStatswithoutMultitouch = new DescriptiveStatistics();
		gestureStatswithMultitouch = new DescriptiveStatistics();
		gestureZoomStats = new DescriptiveStatistics();
		gestureZoominStats = new DescriptiveStatistics();
		gestureZoomOutStats = new DescriptiveStatistics();
		gestureScrollXStats = new DescriptiveStatistics();
		gestureScrollXPositiveStats = new DescriptiveStatistics();
		gestureScrollXNegativeStats = new DescriptiveStatistics();
		
		gestureScrollYStats = new DescriptiveStatistics();
		gestureScrollYPositiveStats = new DescriptiveStatistics();
		gestureScrollYNegativeStats = new DescriptiveStatistics();
		
		page_viewing_time = 0;
		time_in_task = 0;
		query_length = 0;
		query_index_in_task = 0;
		index_in_SERP = 0;
	}
}
