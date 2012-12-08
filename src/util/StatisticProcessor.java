package util;

import java.util.ArrayList;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import util.WebviewTouchEntry.TouchActionType;

public class StatisticProcessor {
	
	public static int[] cal_GestureLength_Statistic(){
		DescriptiveStatistics stats = new DescriptiveStatistics();
		int[] result = new int[6];
		ArrayList<ArrayList<WebviewTouchEntry>> gesture_list = DataExtractor
				.extractGesture();
		for (ArrayList<WebviewTouchEntry> singleGesture : gesture_list) {
			double length = GraphCalculator
					.calGesture_length_pointtopoint(singleGesture);
			stats.addValue(length);
			
			int index= (int)Math.log10(length);
			if(index>=0 && index<=5){
				result[index]++;
			}else{
				System.out.println(length);
				if(length == 0){
					result[0]++;
				}
			}
		}

		double mean = stats.getMean();
		double std = stats.getStandardDeviation();
		double median = stats.getPercentile(50);
		double biggest = stats.getMax();
		double smallest = stats.getMin();
		System.out.println("---------------------------------------");
		System.out.println(mean + "\t" + std + "\t" + median);
		System.out.println(biggest + "\t" + smallest);
		int sum=0;
		for(int i =0; i<6; i++){
			System.out.println(result[i]); 
			sum += result[i];
		}
		System.out.println(sum);
		return result;
	}	
	
	public static int[] cal_SingleTouchGestureSpeed_Statistic(){
		DescriptiveStatistics stats = new DescriptiveStatistics();
		int[] result = new int[6];
		ArrayList<ArrayList<WebviewTouchEntry>> gesture_list = DataExtractor
				.extractGesture();
		for (ArrayList<WebviewTouchEntry> singleGesture : gesture_list) {
			double speed = GraphCalculator.cal_singleTouchGesture_speed(singleGesture);
			System.out.println(speed);
			if(speed >=0 )
				stats.addValue(speed);
			
			int index= (int)Math.log10(speed);
			if(index>=0 && index<=5){
				result[index]++;
			}else{
				System.out.println(speed);
				if(speed == 0){
					result[0]++;
				}
			}
			
		}

		double mean = stats.getMean();
		double std = stats.getStandardDeviation();
		double median = stats.getPercentile(50);
		double biggest = stats.getMax();
		double smallest = stats.getMin();
		System.out.println("---------------------------------------");
		System.out.println(mean + "\t" + std + "\t" + median);
		System.out.println(biggest + "\t" + smallest);
		int sum=0;
		for(int i =0; i<6; i++){
			System.out.println(result[i]); 
			sum += result[i];
		}
		System.out.println(sum);
		return result;
	}	
	
	public static int[] cal_loadingtime_stat(){
		DescriptiveStatistics stats = new DescriptiveStatistics();
		ArrayList<LoadingEntry> loadingEntries = DataExtractor.extractProcessedLoadingTimeData();
		System.out.println(loadingEntries.size());
		int unfinishedCounter = 0;
		int finishedCounter =0;
		int[] result = new int[11];
		for(LoadingEntry loadingEntry : loadingEntries){
			//System.out.println(loadingEntry.isFinished() + "\t" + loadingEntry.getTimetakes() + "\t" + loadingEntry.getUrl());
			if(loadingEntry.getTimetakes() < 0){
				System.out.println("<0 : "+ loadingEntry.getTimetakes());
				continue;
			}
			if(loadingEntry.getTimetakes() > 60000)
			{
				System.out.println(">10 : "+ loadingEntry.getTimetakes());
				continue;
			}
			if(loadingEntry.getProgress() == 0){
				System.out.println("no progress : "+ loadingEntry.getTimetakes());
				continue;
			}
			if(loadingEntry.isFinished()){
				finishedCounter++;
				//stats.addValue(loadingEntry.getTimetakes());
			}
			else{
				System.out.println("progress : "+ loadingEntry.getProgress());
				stats.addValue(loadingEntry.getProgress());
				int index = (int)loadingEntry.getProgress()/10;
				result[index]++;
				unfinishedCounter++;
			}
		}
		System.out.println(finishedCounter + "||" + unfinishedCounter);
		
		
		double mean = stats.getMean();
		double std = stats.getStandardDeviation();
		double median = stats.getPercentile(50);
		double biggest = stats.getMax();
		double smallest = stats.getMin();
		System.out.println("---------------------------------------");
		System.out.println(mean + "\t" + std + "\t" + median);
		System.out.println(biggest + "\t" + smallest);
		int sum=0;
		for(int i =0; i<10; i++){
			System.out.println(result[i]); 
			sum += result[i];
		}
		System.out.println(sum);
		return result;
	}
	
	
	public static int[] getTextInputStatistic(){
		int[] res = new int[10];
		ArrayList<TextInputEntry> tmpResults = TextSequenceAnalysis();
		int backspace_counter = 0;
		for(TextInputEntry entry: tmpResults){
			//System.out.println(entry.getLongest_text() + "\t" + entry.getBackspace_count());
			backspace_counter += entry.getBackspace_count();
		}
		System.out.println(tmpResults.size() + "\t" +  backspace_counter);
		return res;
	}
	
	public static ArrayList<TextInputEntry> TextSequenceAnalysis(){
		ArrayList<TextInputEntry> res = new ArrayList<TextInputEntry>();
		ArrayList<String> textSequence = DataExtractor.extractTextSequence();
		int backspace_counter =0 ;
		String lastString ="";
		TextInputEntry tie = new TextInputEntry();
		String longestString = "";
		for(String text: textSequence){
			if(text.contains(lastString) && (text.length() == lastString.length() +1) || text.equals(lastString)){
				tie.setLongest_text(text);
			}
			else if (lastString.contains(text) && (lastString.length() == text.length() +1)){
				tie.setBackspace_count(tie.getBackspace_count()+1);
			}
			else{
				if(tie.getLongest_text() != null && tie.getLongest_text().length() != 0)
					res.add(tie);
				
				tie = new TextInputEntry();
				longestString ="";
			}
			lastString = text;
		}
		
		
		return res;
	}
	
	public static int[] getZoomStatistic(){
		ArrayList<ArrayList<ZoomEntry>> dataset = DataExtractor.extractIOZoomEventByPage();
		int[] res = new int[dataset.size()];
		System.out.println(dataset.size());
		int index = 0;
		for(ArrayList<ZoomEntry> tmp:dataset){
			int lastScrollX =0, lastScrollY = 0;
			for(ZoomEntry singleEntry : tmp){
				double zoom = 0;
				if(singleEntry.getOldZoomScale() != 0)
					zoom = singleEntry.getNewZoomScale()/singleEntry.getOldZoomScale();
				if(zoom <1 && zoom != 0)
					zoom = 1/zoom;
				res[index]+= zoom;
			}
			index++;
		}
		return res;
	}
	
	public static int[] getScrollStatistic(){
		ArrayList<ArrayList<ScrollEntry>> dataset = DataExtractor.extractScrollEventByPage();
		int[] resX = new int[dataset.size()];
		int[] resY = new int[dataset.size()];
		System.out.println(dataset.size());
		int index = 0;
		for(ArrayList<ScrollEntry> tmp:dataset){
			int lastScrollX =0, lastScrollY = 0;
			for(ScrollEntry singleEntry : tmp){
				resX[index]+= Math.abs(singleEntry.getScrollX()-lastScrollX);
				resY[index]+= Math.abs(singleEntry.getScrollY()-lastScrollY);
				lastScrollX = singleEntry.getScrollX();
				lastScrollY = singleEntry.getScrollY();
			}
			index++;
		}
		for(int i=0; i<dataset.size(); i++){
			//System.out.println(resY[i]);
		}
		return resY;
	}
}
