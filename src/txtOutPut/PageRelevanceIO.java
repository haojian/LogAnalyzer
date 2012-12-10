package txtOutPut;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import util.DBUtil;
import util.ParametersCollection;
import util.ScrollEntry;
import util.TracePointEntry;
import util.WebviewTouchEntry;
import util.IOOperator;

public class PageRelevanceIO {
	private Map<String, PageRelevanceEntry> pageRelevanceData;
	
	private static String[] dataColumnName = {
		"pageurl_uid",
		//gesture association
		"gesture_pressure_total",
		"gesture_pressure_avg",
		"gesture_pressure_std",
		"gesture_pressszie_total",
		"gesture_presssize_avg",
		"gesture_presssize_std",
		
		"gesture_totalcount",
		"gesture_totalmovement_withoutmultitouch",
		"gesture_totalmovement_withmultitouch",
		"gesture_avg_speed",
		
		"gesture_zoom_count",
		"gesture_zoom_scale_total",
		"gesture_zoom_scale_std",
		"gesture_zoomin_count",
		"gesture_zoomin_scale_total",
		"gesture_zoomin_scale_std",
		"gesture_zoomout_count",
		"gesture_zoomout_scale_total",
		"gesture_zoomout_scale_std",
		
		"gesture_scrollX_count",
		"gesture_scrollX_length_total",
		"gesture_scrollX_length_avg",
		"gesture_scrollX_length_std",
		
		"gesture_scrollX_positive_count",
		"gesture_scrollX_positive_total",
		"gesture_scrollX_positive_length_avg",
		"gesture_scrollX_positive_length_std",
		
		"gesture_scrollX_negative_count",
		"gesture_scrollX_negative_total",
		"gesture_scrollX_negative_length_avg",
		"gesture_scrollX_negative_length_std",
		
		"gesture_scrollY_count",
		"gesture_scrollY_length_total",
		"gesture_scrollY_length_avg",
		"gesture_scrollY_length_std",
		
		"gesture_scrollY_positive_count",
		"gesture_scrollY_positive_total",
		"gesture_scrollY_positive_length_avg",
		"gesture_scrollY_positive_length_std",
		
		"gesture_scrollY_negative_count",
		"gesture_scrollY_negative_total",
		"gesture_scrollY_negative_length_avg",
		"gesture_scrollY_negative_length_std",
		
		
		//time association
		"page_viewing_time",
		"time_in_task",
		
		//search session association
		"query_length",
		"query_index",
		"index_in_SERP"
	};

	public static PageRelevanceIO singlton = null;
	
	public static PageRelevanceIO getInstance(){
		if(singlton == null)
			singlton = new PageRelevanceIO();
		return singlton;
	}

	public static void main(String[] args){
		PageRelevanceIO.getInstance().getRelevanceData();
	}
			
	
	public PageRelevanceIO(){
		pageRelevanceData = new HashMap<String, PageRelevanceEntry>();
	}
	
	public void getRelevanceData(){
		/*
		for(String tmpID : ParametersCollection.getInstance().getUserIDList())
			extractDataBasedOnID(tmpID);
			*/
		extractDataBasedOnID("091401");
		//Start output
		for(Map.Entry<String, PageRelevanceEntry> tmp : pageRelevanceData.entrySet()){
			 
			IOOperator.writeToFile("relevancepage.txt", "test", true);
		}
	}
	
	public boolean urlUpdate(){
		return true;
	} 
	
	public String generateKey(String uid, String taskid, String pageurl){
		//System.out.println(uid + "_" + taskid + "_" + pageurl);
		return uid + "_" + taskid + "_" + pageurl;
	}
	
	public void extractDataBasedOnID(String uid){
		DBUtil db1 = new DBUtil();
		String sql = "select * from emu_android_success where (uid = '"+ uid + "') ";
		ResultSet rs1 = db1.executeQuerySQL(sql);
		
		String url = "";
		String userid = "";
		String taskid = "";
		
		String curStartUrl = "";
		String curFinishedUrl = "";
		PageRelevanceEntry tmpRelevanceEntry= new PageRelevanceEntry();
		int counter = 0;
		GestureEntry gesture = new GestureEntry();
		
		long last_scrolltime = -1;
		int last_scrollx = -1;
		int last_scrolly = -1;
		
		long pageStartTime = 0;
		try{
			while(rs1.next())
			{	
				userid = rs1.getString("uid");
				String taskuid = rs1.getString("taskuid");
				if(taskuid != null && taskuid.length() != 0){
					int index = taskuid.indexOf("|");
					taskid = taskuid.substring(index+1);
				}
				if(rs1.getString("starturl") != null &&  !rs1.getString("starturl").isEmpty() && rs1.getString("starturl").length() !=0
						&& !rs1.getString("starturl").startsWith("http%3A%2F%2Fwww.google.com%2Fsearch%3Fhl%3Den%26client%3Dms-android-")){
					curStartUrl = rs1.getString("starturl");
					//System.out.println(counter + "\t"+ curStartUrl);
					counter = 0;
					last_scrolltime = -1;
					last_scrollx = -1;
					last_scrolly = -1;
					pageStartTime = rs1.getLong("time");
					tmpRelevanceEntry= new PageRelevanceEntry();
					
					
				}
				if(rs1.getString("finishurl") != null &&  rs1.getString("finishurl").isEmpty()){
					curFinishedUrl = rs1.getString("finishurl");
					if(curFinishedUrl != curStartUrl)
					{
						System.out.println("unvalidated");
					}
					counter = 0;
				}
				String ev = rs1.getString("ev");
				tmpRelevanceEntry.evList.add(rs1.getString("ev"));
				
				if(ev.equals("WebViewOnTouch")){
					double pressure = rs1.getDouble("pressure0");
					tmpRelevanceEntry.pressureStats.addValue(pressure);
					double presssize = rs1.getDouble("pressure0");
					tmpRelevanceEntry.pressszieStats.addValue(presssize);
					
					long time = rs1.getLong("time");
					int point_num = rs1.getInt("touchpointnum");
					String act_type = rs1.getString("action");
					float[] vx = new float[5];
					float[] vy = new float[5];
					float[] press_size_list = new float[5];
					float[] pressure_list = new float[5];
					vx[0] = rs1.getFloat("vx0");
					vx[1] = rs1.getFloat("vx1");
					vx[2] = rs1.getFloat("vx2");
					vx[3] = rs1.getFloat("vx3");
					vx[4] = rs1.getFloat("vx4");
					vy[0] = rs1.getFloat("vy0");
					vy[1] = rs1.getFloat("vy1");
					vy[2] = rs1.getFloat("vy2");
					vy[3] = rs1.getFloat("vy3");
					vy[4] = rs1.getFloat("vy4");
					press_size_list[0] = rs1.getFloat("touchsize0");
					press_size_list[1] = rs1.getFloat("touchsize1");
					press_size_list[2] = rs1.getFloat("touchsize2");
					press_size_list[3] = rs1.getFloat("touchsize3");
					press_size_list[4] = rs1.getFloat("touchsize4");
					pressure_list[0] = rs1.getFloat("pressure0");
					pressure_list[1] = rs1.getFloat("pressure1");
					pressure_list[2] = rs1.getFloat("pressure2");
					pressure_list[3] = rs1.getFloat("pressure3");
					pressure_list[4] = rs1.getFloat("pressure4");
					
					ArrayList<TracePointEntry> point_list = new ArrayList<TracePointEntry>();
					for(int i=0; i<point_num; i++)
					{
						point_list.add(new TracePointEntry(vx[i], vy[i], press_size_list[i], pressure_list[i], time));
					}
					
					if(rs1.getString("action").equals("DOWN")){
						gesture = new GestureEntry();
						gesture.points.add(new WebviewTouchEntry(point_num, act_type, point_list));
						
					}
					if(rs1.getString("action").equals("MOVE")){
						gesture.points.add(new WebviewTouchEntry(point_num, act_type, point_list));
						
					}
					if(rs1.getString("action").equals("UP")){
						gesture.points.add(new WebviewTouchEntry(point_num, act_type, point_list));
						tmpRelevanceEntry.gestureLengthStatswithoutMultitouch.addValue(gesture.GetLength());
						tmpRelevanceEntry.gestureSpeedStatswithoutMultitouch.addValue(gesture.GetSpeed());
					}
				}
				
				if(ev.equals("WebClientScaleChange")){
					double [] zoomscale = new double [2];
					zoomscale[0] = rs1.getDouble("newscale");
					zoomscale[1] = rs1.getDouble("oldscale");
					double change = zoomscale[0] / zoomscale[1];
					tmpRelevanceEntry.gestureZoomStats.addValue(change);
					if(zoomscale[1]>zoomscale[0])
						tmpRelevanceEntry.gestureZoominStats.addValue(change);
					else
						tmpRelevanceEntry.gestureZoomOutStats.addValue(change);
				}
				
				if(ev.equals("Scroll")){
					long time = rs1.getLong("time");
					int scrollx = rs1.getInt("scrollx");
					int scrolly = rs1.getInt("scrolly");
					tmpRelevanceEntry.gestureScrollDistXStats.addValue(scrollx);
					tmpRelevanceEntry.gestureScrollDistYStats.addValue(scrolly);
					
					if(last_scrolltime!=-1 && last_scrollx != -1 && last_scrolly != -1){
						int scrollx_dist = scrollx-last_scrollx;
						int scrolly_dist = scrolly-last_scrolly;
						int time_dur = (int) (time - last_scrolltime);
						double speedX =  scrollx_dist/time_dur;
						double speedY =  scrolly_dist/time_dur;
						tmpRelevanceEntry.gestureScrollSpeedXStats.addValue(speedX);
						tmpRelevanceEntry.gestureScrollSpeedYStats.addValue(speedY);
						if(speedX>0)
							tmpRelevanceEntry.gestureScrollSpeedXPositiveStats.addValue(speedX);
						else
							tmpRelevanceEntry.gestureScrollSpeedXNegativeStats.addValue(speedX);
						
						if(speedY>0)
							tmpRelevanceEntry.gestureScrollSpeedYPositiveStats.addValue(speedY);
						else
							tmpRelevanceEntry.gestureScrollSpeedYNegativeStats.addValue(speedY);
					}
					last_scrolltime = time;
					last_scrollx = scrollx;
					last_scrolly = scrolly;	
				}
				
				if(!curStartUrl.startsWith("http%3A%2F%2Fwww.google.com%2Fsearch%3Fhl%3Den%26client%3Dms-android-")){
					counter++;
					if(rs1.getFloat("relevancerate") != 0){
						tmpRelevanceEntry.page_viewing_time = rs1.getLong("time")-pageStartTime;
						double relevance = rs1.getFloat("relevancerate");
						String key = generateKey(userid, taskid, curStartUrl);
						tmpRelevanceEntry.relevance_report = relevance;
						if(pageRelevanceData.containsKey(key)){
							//merge data
							
						}
						else{
							pageRelevanceData.put(key, tmpRelevanceEntry);
						}
						System.out.println(counter+ "\t" + relevance + "\t"+ curStartUrl );
						//tmpRelevanceEntry.outputEvList();
						//System.out.println("\n");
						counter=0;
					}
				}
			}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db1.rundown();
		return;
	}
}
