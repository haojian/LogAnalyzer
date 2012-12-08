import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import util.DataExtractor;
import util.StatisticProcessor;
import util.TracePointEntry;
import util.WebviewTouchEntry;
import util.IOOperator;


public class visualizer extends JApplet implements ActionListener, EventListener {
	//UI parameters
	int FONT_SIZE = 16;
	
	//Default parameters setting
	private int frameWidth = 1000;
	private int frameHeight = 480;
	private boolean b_titlebar = true;
	private Color background_color = Color.BLACK;
	private Color foreground_color = Color.WHITE;
	private JButton btn_TouchTrace, btn_WebView_ActTrace, btn_OverView, btn_HeatMap, btn_consoleoutput, btn_IOOutput;
	private JButton btn_JChart;
	private JLabel lbl_result;
	JPanel centerArea;
	GraphicsEnvironment ge;
	GraphicsDevice gs;
	JFrame fullscreen_frame;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//InitializeSetting();
	}
	
	public void init() {
		InitializeSetting();
		//DataExtractor.extractWebview_touchtrace_log("hj");
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_TouchTrace) {
			popup_touchtrace_view();
		}else if(e.getSource() == btn_WebView_ActTrace){
			popup_acttrace_view();
		}else if(e.getSource() == btn_HeatMap){
			popup_heatmap_view();
		}else if(e.getSource() == btn_consoleoutput){
			//console_presssize_output();
			//console_press_output();
			//console_zoomdata_output();
			//StatisticProcessor.cal_SingleTouchGestureSpeed_Statistic();
			//DataExtractor.extractProcessedLoadingTimeData();
			//StatisticProcessor.cal_loadingtime_stat();
			//StatisticProcessor.getTextInputStatistic();
			//DataExtractor.extractZoomEventByPage();
			StatisticProcessor.getZoomStatistic();
		}else if(e.getSource() == btn_JChart){
			visualize_distribution();
		}else if(e.getSource() == btn_IOOutput){
			//IOOperator.writeTextUpdate();
			IOOperator.writeWebviewTouchDetails();
		}
	}
	
	public void visualize_distribution(){
		DistributionChart demo = new DistributionChart("Comparison", "Which operating system are you using?");
        demo.pack();
        demo.setVisible(true);
	}
	
	public void console_zoomdata_output(){
		DescriptiveStatistics stats = new DescriptiveStatistics();
		 ArrayList<double[]> dataset = DataExtractor.extractZoomScale();
		 for(double[] singleZoom:dataset){
			 if(singleZoom[1] >= 0 && singleZoom[0]> 0){
				 //if(singleZoom[0]/singleZoom[1] < 1)
					 stats.addValue(singleZoom[0]/singleZoom[1]);
				 System.out.println(singleZoom[0]/singleZoom[1]);
			 }
		 }
		
		double mean = stats.getMean();
		double std = stats.getStandardDeviation();
		double median = stats.getPercentile(50);
		double biggest = stats.getMax();
		double smallest = stats.getMin();
		System.out.println(mean + "\t" + std + "\t" + median );
		System.out.println(biggest + "\t" + smallest);
	}
	
	public void console_presssize_output(){
		DescriptiveStatistics stats = new DescriptiveStatistics();
		
		ArrayList<WebviewTouchEntry> dataset = DataExtractor.extractWebview_touchtrace_log();
		for(WebviewTouchEntry singleEntry : dataset){
			for(TracePointEntry singlePoint : singleEntry.getPointList()){
				stats.addValue(singlePoint.getPressSize());
				System.out.println(singlePoint.getPressSize());
			}
		}
		
		double mean = stats.getMean();
		double std = stats.getStandardDeviation();
		double median = stats.getPercentile(50);
		double biggest = stats.getMax();
		double smallest = stats.getMin();
		System.out.println(mean + "\t" + std + "\t" + median );
		System.out.println(biggest + "\t" + smallest);
	}
	
	public void console_press_output(){
		DescriptiveStatistics stats = new DescriptiveStatistics();
		
		ArrayList<WebviewTouchEntry> dataset = DataExtractor.extractWebview_touchtrace_log();
		for(WebviewTouchEntry singleEntry : dataset){
			for(TracePointEntry singlePoint : singleEntry.getPointList()){
				stats.addValue(singlePoint.getPressure());
				System.out.println(singlePoint.getPressure());
			}
		}
		
		double mean = stats.getMean();
		double std = stats.getStandardDeviation();
		double median = stats.getPercentile(50);
		double biggest = stats.getMax();
		double smallest = stats.getMin();
		System.out.println(mean + "\t" + std + "\t" + median );
		System.out.println(biggest + "\t" + smallest);
	}
	
	public void popup_heatmap_view(){
		HeatMapViewer hmf = new HeatMapViewer(1000, 1000);
        hmf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        hmf.setSize(1000, 1000);
        hmf.setVisible(true);
	}
	
	public void popup_acttrace_view(){
		fullscreen_frame = new JFrame("TouchTrace View");
		fullscreen_frame.addWindowListener(new WindowAdapter(){
			 public void windowClosing(WindowEvent e) {
			        System.out.println("Window Closing Event");
			      }
		});
		
		fullscreen_frame.setSize(frameWidth, frameHeight);
		
		if(!b_titlebar)
			fullscreen_frame.setUndecorated(true);
		fullscreen_frame.setEnabled(true);
		fullscreen_frame.setVisible(true);
		
		//fullscreen_frame.add();
	}
	
	public void popup_touchtrace_view(){
		fullscreen_frame = new JFrame("TouchTrace View");
		fullscreen_frame.addWindowListener(new WindowAdapter(){
			 public void windowClosing(WindowEvent e) {
			        System.out.println("Window Closing Event");
			      }
		});
		
		fullscreen_frame.setSize(frameWidth, frameHeight);
		
		if(!b_titlebar)
			fullscreen_frame.setUndecorated(true);
		fullscreen_frame.setEnabled(true);
		fullscreen_frame.setVisible(true);
		
		fullscreen_frame.add(new TraceViewer(frameWidth, frameHeight, background_color, foreground_color));
	}
	
	public void InitializeSetting() {
		JPanel centerArea = new JPanel();
		centerArea.setPreferredSize(new Dimension(700, 700));
		centerArea.setBackground(Color.BLACK);
		centerArea.setForeground(Color.WHITE);

		centerArea.setLayout(new BoxLayout(centerArea, BoxLayout.PAGE_AXIS));

		String browserEndText = "Please click the button below to view the trace";
		JTextArea ia = new JTextArea(browserEndText);
		ia.setBackground(Color.BLACK);
		ia.setForeground(Color.WHITE);
		ia.setLineWrap(true);
		ia.setEditable(false);
		ia.setWrapStyleWord(true);
		ia.setAlignmentX(JTextArea.CENTER_ALIGNMENT);

		centerArea.add(ia, JPanel.CENTER_ALIGNMENT);

		btn_OverView = new JButton("Overview");
		btn_TouchTrace = new JButton("Touch Trace");
		btn_WebView_ActTrace = new JButton("Action Trace");
		btn_HeatMap = new JButton("Touch HeatMap");
		btn_consoleoutput = new JButton("Console Output");
		btn_JChart = new JButton("Distribution");
		btn_IOOutput = new JButton("IO Output");
		
		JPanel midPanel = new JPanel();
		midPanel.setBackground(Color.BLACK);
		midPanel.setForeground(Color.WHITE);
		midPanel.setPreferredSize(new Dimension(100, 700));
		midPanel.setMaximumSize(new Dimension(100, 700));
		midPanel.add(btn_OverView);
		midPanel.add(btn_TouchTrace);
		midPanel.add(btn_WebView_ActTrace);
		midPanel.add(btn_HeatMap);
		midPanel.add(btn_consoleoutput);
		midPanel.add(btn_JChart);
		midPanel.add(btn_IOOutput);
		centerArea.add(midPanel);

		btn_TouchTrace.addActionListener(this);
		btn_WebView_ActTrace.addActionListener(this);
		btn_HeatMap.addActionListener(this);
		btn_consoleoutput.addActionListener(this);
		btn_JChart.addActionListener(this);
		btn_IOOutput.addActionListener(this);
		
		JPanel botPanel = new JPanel();
		botPanel.setBackground(Color.BLACK);
		botPanel.setForeground(Color.WHITE);
		botPanel.setPreferredSize(new Dimension(400, 40));
		botPanel.setMaximumSize(new Dimension(400, 40));

		centerArea.add(botPanel);
		
		this.add(centerArea);
		this.setSize(700, 700);
	}
}
