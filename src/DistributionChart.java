import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import util.DataExtractor;
import util.StatisticProcessor;
import util.TracePointEntry;
import util.WebviewTouchEntry;

public class DistributionChart extends JFrame {
	public DistributionChart(String applicationTitle, String chartTitle) {
		super(applicationTitle);
		// This will create the dataset
		DefaultCategoryDataset dataset = createScrollDataset();
		// based on the dataset we create the chart
		JFreeChart chart = createChart(dataset, chartTitle);
		// we put the chart into a panel
		ChartPanel chartPanel = new ChartPanel(chart);
		// default size
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		// add it to our application
		setContentPane(chartPanel);
	}

	private DefaultCategoryDataset createPressureSizeDataset() {
		DefaultCategoryDataset result = new DefaultCategoryDataset();
		ArrayList<WebviewTouchEntry> dataset = DataExtractor.extractWebview_touchtrace_log();
		HashMap<Float, Integer> map = new HashMap<Float, Integer>();
		
		for(WebviewTouchEntry singleEntry : dataset){
			for(TracePointEntry singlePoint : singleEntry.getPointList()){
				System.out.println(singlePoint.getPressSize());
				if(map.containsKey(singlePoint.getPressSize())){
					map.put( singlePoint.getPressSize(), map.get(singlePoint.getPressSize())+ 1);
					
				}
				else{
					map.put( singlePoint.getPressSize(), 1);
				}
			}
		}
		Object[]  key   = map.keySet().toArray();   
		Arrays.sort(key);  
		System.out.println("------------------------------------------");  
        for (int   i   =   0;   i   <   key.length;   i++)   {  
                System.out.println(map.get(key[i]) + "\t" + key[i]); 
                result.addValue(map.get(key[i]), "PressureSize", Double.valueOf(key[i].toString()) );
        }   
		return result;
	}
	
	private DefaultCategoryDataset createPressureDataset() {
		DefaultCategoryDataset result = new DefaultCategoryDataset();
		
		ArrayList<WebviewTouchEntry> dataset = DataExtractor.extractWebview_touchtrace_log();
		HashMap<Float, Integer> map = new HashMap<Float, Integer>();
		
		for(WebviewTouchEntry singleEntry : dataset){
			for(TracePointEntry singlePoint : singleEntry.getPointList()){
				System.out.println(singlePoint.getPressure());
				if(map.containsKey(singlePoint.getPressure())){
					map.put( singlePoint.getPressure(), map.get(singlePoint.getPressure())+ 1);
					
				}
				else{
					map.put( singlePoint.getPressure(), 1);
				}
			}
		}
		Object[]  key   = map.keySet().toArray();   
		Arrays.sort(key);  
		System.out.println("------------------------------------------");  
        for (int   i   =   0;   i   <   key.length;   i++)   {  
                System.out.println(map.get(key[i]) + "\t" + key[i]); 
                result.addValue(map.get(key[i]), "Pressure", Double.valueOf(key[i].toString()) );
        }   
		return result;

	}
	
	
	private DefaultCategoryDataset createGestureLengthDataset() {
		DefaultCategoryDataset result = new DefaultCategoryDataset();
		int[] stat = StatisticProcessor.cal_GestureLength_Statistic();
		for(int i=0; i< stat.length; i++){
			result.addValue(stat[i], "Gesture Length", Double.valueOf(i));
		}
		return result;

	}

	
	private DefaultCategoryDataset createSingleTouchGestureSpeedDataset() {
		DefaultCategoryDataset result = new DefaultCategoryDataset();
		int[] stat = StatisticProcessor.cal_SingleTouchGestureSpeed_Statistic();
		for(int i=0; i< stat.length; i++){
			result.addValue(stat[i], "Gesture Speed", Double.valueOf(i));
		}
		return result;

	}
	
	private DefaultCategoryDataset createScrollDataset() {
		DefaultCategoryDataset result = new DefaultCategoryDataset();
		int[] stat = StatisticProcessor.getScrollStatistic();
		for(int i=0; i< stat.length; i++){
			result.addValue(stat[i], "Zoom", Double.valueOf(i));
		}
		return result;

	}
	
	private DefaultCategoryDataset createZoomDataset() {
		DefaultCategoryDataset result = new DefaultCategoryDataset();
		int[] stat = StatisticProcessor.getZoomStatistic();
		for(int i=0; i< stat.length; i++){
			result.addValue(stat[i], "Zoom", Double.valueOf(i));
		}
		return result;

	}
	
	
	private JFreeChart createChart(DefaultCategoryDataset dataset, String title) {
	        JFreeChart chart = ChartFactory.createBarChart3D("Bar Chart Demo", // chart title
	        		"Category", // domain axis label
	        		"Value", // range axis label
	        		dataset, // data
	        		PlotOrientation.VERTICAL, // orientation
	        		true, // include legend
	        		true, // tooltips?
	        		false // URLs?
	        );
	        CategoryPlot plot = (CategoryPlot) chart.getPlot();
	        //plot.setStartAngle(290);
	        plot.setForegroundAlpha(0.5f);
	        return chart;
	        
	    }
}
