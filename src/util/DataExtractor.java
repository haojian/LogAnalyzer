package util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import util.LoadingTimeEntry.ev_type;
import util.WebviewTouchEntry.TouchActionType;


public class DataExtractor {
	private static DataExtractor sSingleton;
	
	private static String[] userid_list= {"chenchen", "tunan", "james", "xiaofeng", "haojian"};
	
	static DataExtractor getInstance() {
		if (sSingleton == null) {
			sSingleton = new DataExtractor();
		}
		return sSingleton;
	}
	
	public DataExtractor(){
		//DBUtil db1 = new DBUtil();
	}
	
	
	public static double[][] extract_heatmap_data_onlyStartTrace(int _width, int _height){
		double[][] data= new double[_width][_height];
		DBUtil db1 = new DBUtil();
		String sql = "select * from emu_android_success where (ev = 'WebViewOnTouch') and action = 'DOWN' ";
		ResultSet rs1 = db1.executeQuerySQL(sql);
		try{
			while(rs1.next()){
				long time = rs1.getLong("time");
				int point_num = rs1.getInt("touchpointnum");
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
				
				for(int i=0; i<point_num; i++)
				{
					if(vx[i] >= 0 && vy[i] >= 0 && vx[i] < _width && vy[i]<_height){
						ArrayList<TracePointEntry> tmpres = GraphCalculator.getPointsinCircle((int)vx[i], (int)vy[i], 20);
						for(TracePointEntry point:tmpres){
							if(point.getX() >= 0 && point.getY() >= 0 && point.getX() < _width && point.getY()<_height)
								data[(int)point.getX()][(int)point.getY()]++;
						}
					}
					System.out.println(String.valueOf(vx[i]) + "\t" + String.valueOf(vy[i]));
				}
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db1.rundown();
		return data;
	}
	
	public static double[][] extract_heatmap_data_onlyStartTrace(int _width, int _height, int orientation){
		double[][] data= new double[_width][_height];
		DBUtil db1 = new DBUtil();
		String sql = "select * from emu_android_success where ev = 'OrientationChange' or ((ev = 'WebViewOnTouch') and action = 'DOWN') ";
		ResultSet rs1 = db1.executeQuerySQL(sql);
		boolean orientationflag = false;
		try{
			while(rs1.next()){
				String ev = rs1.getString("ev");
				if(ev.equals("OrientationChange")){
					int neworient = rs1.getInt("neworient");
					if(orientationflag == false && neworient == orientation){
						orientationflag = true;
					}
					if(orientationflag == true && neworient != orientation){
						orientationflag = false;
					}
				}
				if(ev.equals("WebViewOnTouch") && orientationflag == true){
					long time = rs1.getLong("time");
					int point_num = rs1.getInt("touchpointnum");
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
					
					for(int i=0; i<point_num; i++)
					{
						if(vx[i] >= 0 && vy[i] >= 0 && vx[i] < _width && vy[i]<_height){
							ArrayList<TracePointEntry> tmpres = GraphCalculator.getPointsinCircle((int)vx[i], (int)vy[i], 20);
							for(TracePointEntry point:tmpres){
								if(point.getX() >= 0 && point.getY() >= 0 && point.getX() < _width && point.getY()<_height)
									data[(int)point.getX()][(int)point.getY()]++;
							}
						}
						System.out.println(String.valueOf(vx[i]) + "\t" + String.valueOf(vy[i]));
					}
				}
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db1.rundown();
		return data;

	}
	
	public static ArrayList<FrustrationEntry> extract_frustrationData(){
		ArrayList<FrustrationEntry> res = new ArrayList<FrustrationEntry>();
		DBUtil db1 = new DBUtil();
		String sql = "select * from emu_android_success where ((ev = 'FrustrationReport') or (ev = 'StartNewTask')) and ((uid = 'twu37') or (uid = 'munch')  or (uid = 'twu372') or (uid = '1720689'))";
		ResultSet rs1 = db1.executeQuerySQL(sql);
		String currentuid = "";
		int currenttaskid = 0;
		try{
			while(rs1.next()){
				String ev = rs1.getString("ev");
				if(ev.equals("StartNewTask")){
					currentuid = rs1.getString("uid");

					int index = rs1.getString("taskid").indexOf(currentuid)
							+ currentuid.length() + 1;
					System.out.println(rs1.getString("taskid"));
					System.out
							.println(rs1.getString("taskid").substring(index));
					currenttaskid = Integer.valueOf(rs1.getString("taskid")
							.substring(index));
				}
				else{
					long time = rs1.getLong("time");
					double frustrationrate = rs1.getDouble("frustrationlvl");
					String userid = rs1.getString("uid");
					
					FrustrationEntry frsEntry = new FrustrationEntry(userid, currenttaskid, time, frustrationrate);
					res.add(frsEntry);
				} 
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db1.rundown();
		return res;
	}
	
	
	public static double[][] extract_heatmap_data_onlyEndTrace(int _width, int _height){
		double[][] data= new double[_width][_height];
		DBUtil db1 = new DBUtil();
		String sql = "select * from emu_android_success where (ev = 'WebViewOnTouch') and action = 'UP' ";
		ResultSet rs1 = db1.executeQuerySQL(sql);
		try{
			while(rs1.next()){
				long time = rs1.getLong("time");
				int point_num = rs1.getInt("touchpointnum");
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
				
				
				for(int i=0; i<point_num; i++)
				{
					if(vx[i] >= 0 && vy[i] >= 0 && vx[i] < _width && vy[i]<_height)
						data[(int)vx[i]][(int)vy[i]]++;
					System.out.println(String.valueOf(vx[i]) + "\t" + String.valueOf(vy[i]));
				}
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db1.rundown();
		return data;

	}
	
	public static ArrayList<WebviewTouchEntry> extractWebview_touchtrace_log(String _userid){
		ArrayList<WebviewTouchEntry> result = new ArrayList<WebviewTouchEntry>();
		DBUtil db1 = new DBUtil();
		String sql = "select * from emu_android_success where (ev = 'WebViewOnTouch') AND uid = '" +  _userid + "';";
		if(_userid == null)
			return null;
		ResultSet rs1 = db1.executeQuerySQL(sql);
		try {
			while(rs1.next()){
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
				result.add(new WebviewTouchEntry(point_num, act_type, point_list));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db1.rundown();
		return result;
	}
	
	public static ArrayList<double[]> extractZoomScale(){
		ArrayList<double[]> result = new ArrayList<double[]>();
		
		DBUtil db1 = new DBUtil();
		String sql = "select * from emu_android_success where (ev = 'WebClientScaleChange');";
		ResultSet rs1 = db1.executeQuerySQL(sql);
		try {
			while(rs1.next()){
				double [] zoomscale = new double [2];
				zoomscale[0] = rs1.getDouble("newscale");
				zoomscale[1] = rs1.getDouble("oldscale");
				result.add(zoomscale);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db1.rundown();
		return result;
	}
	
	
	
	public static ArrayList<WebviewTouchEntry> extractWebview_touchtrace_log(){
		ArrayList<WebviewTouchEntry> result = new ArrayList<WebviewTouchEntry>();
		DBUtil db1 = new DBUtil();
		String sql = "select * from emu_android_success where (ev = 'WebViewOnTouch') ;";

		ResultSet rs1 = db1.executeQuerySQL(sql);
		try {
			while(rs1.next()){
				long time = rs1.getLong("time");
				int point_num = rs1.getInt("touchpointnum");
				String act_type = rs1.getString("action");
				String uid = rs1.getString("uid");
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
				result.add(new WebviewTouchEntry(point_num, act_type, point_list, uid));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db1.rundown();
		return result;
	}
	
	public static ArrayList<WebviewTouchEntry> extractWebview_touchtrace_log_with_uid(){
		ArrayList<WebviewTouchEntry> result = new ArrayList<WebviewTouchEntry>();
		String currenturl = "";
		String currentuid = "";
		int currenttaskid = 0;
		DBUtil db1 = new DBUtil();
		String sql = "select * from emu_android_success where ((ev = 'WebViewOnTouch') or (ev = 'WebClientPageStart') or (ev = 'StartNewTask')) and ((uid = 'twu37') or (uid = 'munch')  or (uid = 'twu372') or (uid = '1720689'))";

		ResultSet rs1 = db1.executeQuerySQL(sql);
		try {
			while(rs1.next()){
				String ev = rs1.getString("ev");
				if (ev.equals("WebClientPageStart")) {
					currenturl = rs1.getString("starturl");
				} else if (ev.equals("WebViewOnTouch")) {

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
					for (int i = 0; i < point_num; i++) {
						point_list.add(new TracePointEntry(vx[i], vy[i],
								press_size_list[i], pressure_list[i], time));
					}
					result.add(new WebviewTouchEntry(point_num, act_type,
							point_list, currentuid, currenttaskid, currenturl));
				} else if (ev.equals("StartNewTask")) {
					currentuid = rs1.getString("uid");

					int index = rs1.getString("taskid").indexOf(currentuid)
							+ currentuid.length() + 1;
					System.out.println(rs1.getString("taskid"));
					System.out
							.println(rs1.getString("taskid").substring(index));
					currenttaskid = Integer.valueOf(rs1.getString("taskid")
							.substring(index));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db1.rundown();
		return result;
	}
	
	
	public static ArrayList<GestureEntry> extractIOGesture(){
		ArrayList<GestureEntry> results = new ArrayList<GestureEntry>();
		ArrayList<WebviewTouchEntry> res = new ArrayList<WebviewTouchEntry>();
		ArrayList<WebviewTouchEntry> dataset= DataExtractor.extractWebview_touchtrace_log_with_uid();
		for(int i =0; i<dataset.size(); i++){
			if(dataset.get(i).getAction_Type() ==TouchActionType.DOWN){
				res.clear();
				res.add(dataset.get(i));
			}else if(dataset.get(i).getAction_Type() ==TouchActionType.MOVE){
				res.add(dataset.get(i));
			}else if(dataset.get(i).getAction_Type() ==TouchActionType.UP){
				res.add(dataset.get(i));
				ArrayList<WebviewTouchEntry> tmp = new ArrayList<WebviewTouchEntry>();
				for(int j = 0; j< res.size(); j++)
					tmp.add(res.get(j));
				//gestureList.add(tmp);
				results.add(new GestureEntry(tmp.get(0).getUrl(), tmp.get(0).getUid(), tmp.get(0).getTaskIndex(), tmp));
			}
		}
		return results;
	}
	
	public static ArrayList<ArrayList<WebviewTouchEntry>> extractGesture(){
		ArrayList<ArrayList<WebviewTouchEntry>> gestureList = new ArrayList<ArrayList<WebviewTouchEntry>>();
		ArrayList<WebviewTouchEntry> res = new ArrayList<WebviewTouchEntry>();
		ArrayList<WebviewTouchEntry> dataset= DataExtractor.extractWebview_touchtrace_log();
		for(int i =0; i<dataset.size(); i++){
			if(dataset.get(i).getAction_Type() ==TouchActionType.DOWN){
				res.clear();
				res.add(dataset.get(i));
			}else if(dataset.get(i).getAction_Type() ==TouchActionType.MOVE){
				res.add(dataset.get(i));
			}else if(dataset.get(i).getAction_Type() ==TouchActionType.UP){
				res.add(dataset.get(i));
				ArrayList<WebviewTouchEntry> tmp = new ArrayList<WebviewTouchEntry>();
				for(int j = 0; j< res.size(); j++)
					tmp.add(res.get(j));
				gestureList.add(tmp);
			}
		}
		System.out.println(gestureList.size());
		return gestureList;
	}
	
	public static ArrayList<LoadingTimeEntry> extractLoadingTimeData(){
		ArrayList<LoadingTimeEntry> result = new ArrayList<LoadingTimeEntry>();
		DBUtil db1 = new DBUtil();
		String sql = "select * from emu_android_success where (ev = 'WebClientPageFinish') or (ev = 'WebClientPageStart') or (ev = 'OnProgressChange');";

		ResultSet rs1 = db1.executeQuerySQL(sql);
		try {
			while(rs1.next()){
				long time = rs1.getLong("time");
				int progress = rs1.getInt("progress");
				String act_type = rs1.getString("ev");
				String url = rs1.getString("starturl");
				result.add(new LoadingTimeEntry(progress, time, act_type, url));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db1.rundown();
		return result;
	}
	
	public static ArrayList<LoadingTimeEntry> extractIOLoadingTimeData(){
		ArrayList<LoadingTimeEntry> result = new ArrayList<LoadingTimeEntry>();
		DBUtil db1 = new DBUtil();
		String sql = "select * from emu_android_success where ((ev = 'WebClientPageFinish') or (ev = 'WebClientPageStart') or (ev = 'OnProgressChange') or (ev = 'StartNewTask')) and ((uid = 'twu37') or (uid = 'munch')  or (uid = 'twu372') or (uid = '1720689'));";
		int curtaskIndex = 0;
		ResultSet rs1 = db1.executeQuerySQL(sql);
		try {
			while(rs1.next()){
				String ev = rs1.getString("ev");
				if(ev.equals("StartNewTask"))
				{
					//currenttaskid = Integer.valueOf(rs1.getString("taskid").split("|")[1]);
					String currentuid = rs1.getString("uid");
					int index = rs1.getString("taskid").indexOf(currentuid) + currentuid.length()+1;
					System.out.println(index);
					System.out.println(rs1.getString("taskid"));
					System.out.println(rs1.getString("taskid").substring(index));
					curtaskIndex = Integer.valueOf(rs1.getString("taskid").substring(index));
				}
				else
				{
					String currentuid = rs1.getString("uid");
					long time = rs1.getLong("time");
					int progress = rs1.getInt("progress");
					String act_type = rs1.getString("ev");
					String url = rs1.getString("starturl");
					result.add(new LoadingTimeEntry(progress, time, act_type, url, currentuid, curtaskIndex));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db1.rundown();
		return result;
	}
	
	
	public static ArrayList<LoadingEntry> extractProcessedLoadingTimeData_withuid(){
		int counter=0;
		ArrayList<LoadingEntry> result = new ArrayList<LoadingEntry>();
		ArrayList<LoadingTimeEntry> dataset= DataExtractor.extractIOLoadingTimeData();
		LoadingEntry tmpEntry = new LoadingEntry();
		int flag = 0;
		int start_count =0;
		for (int i = 0; i < dataset.size(); i++) {
			if (dataset.get(i).getEv_actiontype() == ev_type.WebClientPageStart) {
				if (tmpEntry.getProgress() != 0) {
					if (tmpEntry.getProgress() == 100) {
						tmpEntry.setFinished(true);
					} else
						tmpEntry.setFinished(false);
					result.add(tmpEntry);
				} else {
					counter++;
					//System.out.println(dataset.get(i).getTimeStamp() + "\t" + dataset.get(i).getUrl());
				}
				start_count++;
				tmpEntry = new LoadingEntry();
				tmpEntry.setStart_timeStamp(dataset.get(i).getTimeStamp());
				tmpEntry.setUrl(dataset.get(i).getUrl());
			} else if (dataset.get(i).getEv_actiontype() == ev_type.OnProgressChange) {
				tmpEntry.setEnd_timeStamp(dataset.get(i).getTimeStamp());
				tmpEntry.setProgress(dataset.get(i).getProgress());
				tmpEntry.setTaskIndex(dataset.get(i).getTaskIndex());
				tmpEntry.setUserid(dataset.get(i).getUserid());
			}
			else if (dataset.get(i).getEv_actiontype() == ev_type.WebClientPageFinish) {
				tmpEntry.setEnd_timeStamp(dataset.get(i).getTimeStamp());
				tmpEntry.setProgress(100);
			}
		}
		System.out.println(start_count);
		System.out.println(counter);
		System.out.println(result.size());
		return result;
	}
	
	
	
	public static ArrayList<LoadingEntry> extractProcessedLoadingTimeData(){
		int counter=0;
		ArrayList<LoadingEntry> result = new ArrayList<LoadingEntry>();
		ArrayList<LoadingTimeEntry> dataset= DataExtractor.extractLoadingTimeData();
		LoadingEntry tmpEntry = new LoadingEntry();
		int flag = 0;
		int start_count =0;
		for (int i = 0; i < dataset.size(); i++) {
			if (dataset.get(i).getEv_actiontype() == ev_type.WebClientPageStart) {
				if (tmpEntry.getProgress() != 0) {
					if (tmpEntry.getProgress() == 100) {
						tmpEntry.setFinished(true);
					} else
						tmpEntry.setFinished(false);
					result.add(tmpEntry);
				} else {
					counter++;
					//System.out.println(dataset.get(i).getTimeStamp() + "\t" + dataset.get(i).getUrl());
				}
				start_count++;
				tmpEntry = new LoadingEntry();
				tmpEntry.setStart_timeStamp(dataset.get(i).getTimeStamp());
				tmpEntry.setUrl(dataset.get(i).getUrl());
			} else if (dataset.get(i).getEv_actiontype() == ev_type.OnProgressChange) {
				tmpEntry.setEnd_timeStamp(dataset.get(i).getTimeStamp());
				tmpEntry.setProgress(dataset.get(i).getProgress());

			}
			else if (dataset.get(i).getEv_actiontype() == ev_type.WebClientPageFinish) {
				tmpEntry.setEnd_timeStamp(dataset.get(i).getTimeStamp());
				tmpEntry.setProgress(100);
			}
		}
		System.out.println(start_count);
		System.out.println(counter);
		System.out.println(result.size());
		return result;
	}
	
	public static ArrayList<String> extractTextSequence(){
		ArrayList<String> result = new ArrayList<String>();
		DBUtil db1 = new DBUtil();
		String sql = "select * from emu_android_success where (ev = 'UpdatedText')";

		ResultSet rs1 = db1.executeQuerySQL(sql);
		try {
			while(rs1.next()){
				long time = rs1.getLong("time");
				String text = rs1.getString("edittext");
				result.add(text);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db1.rundown();
		return result;
	}
	
	
	public static ArrayList<TextInputEntry> extractTextSequence_withuid(){
		ArrayList<TextInputEntry> result = new ArrayList<TextInputEntry>();
		DBUtil db1 = new DBUtil();
		String sql = "select * from emu_android_success where ((ev = 'UpdatedText') or (ev = 'StartNewTask')) and ((uid = 'twu37') or (uid = 'munch')  or (uid = 'twu372') or (uid = '1720689'))";
		int curtaskIndex = 0;
		ResultSet rs1 = db1.executeQuerySQL(sql);
		try {
			while(rs1.next()){
				String ev = rs1.getString("ev");
				if(ev.equals("StartNewTask"))
				{
					//currenttaskid = Integer.valueOf(rs1.getString("taskid").split("|")[1]);
					String currentuid = rs1.getString("uid");
					int index = rs1.getString("taskid").indexOf(currentuid) + currentuid.length()+1;
					System.out.println(index);
					System.out.println(rs1.getString("taskid"));
					System.out.println(rs1.getString("taskid").substring(index));
					curtaskIndex = Integer.valueOf(rs1.getString("taskid").substring(index));
				}else{
					String currentuid = rs1.getString("uid");
					long time = rs1.getLong("time");
					String text = rs1.getString("edittext");
					result.add(new TextInputEntry(time, text, currentuid, curtaskIndex));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db1.rundown();
		return result;
	}
	
	public static ArrayList<TextInputEntry> TextSequenceAnalysisWithUID(){
		ArrayList<TextInputEntry> res = new ArrayList<TextInputEntry>();
		ArrayList<TextInputEntry> textSequence = DataExtractor.extractTextSequence_withuid();
		int backspace_counter =0 ;
		String lastString ="";
		TextInputEntry tie = new TextInputEntry();
		String longestString = "";
		for(TextInputEntry entry : textSequence){
			if(entry.getLongest_text().contains(lastString) && (entry.getLongest_text().length() == lastString.length() +1) || entry.getLongest_text().equals(lastString)){
				tie.setLongest_text(entry.getLongest_text());
				tie.setTime(entry.getTime());
			}
			else if (lastString.contains(entry.getLongest_text()) && (lastString.length() == entry.getLongest_text().length() +1)){
				tie.setBackspace_count(tie.getBackspace_count()+1);
				tie.setTime(entry.getTime());
			}
			else{
				if(tie.getLongest_text() != null && tie.getLongest_text().length() != 0)
					res.add(tie);
				
				tie = new TextInputEntry();
				tie.setTaskIndex(entry.getTaskIndex());
				tie.setUserid(entry.getUserid());
				
				longestString ="";
			}
			lastString = entry.getLongest_text();
			
		}
		return res;
	}
	
	
	public static ArrayList<ScrollEntry> extractScrollEvent(){
		ArrayList<ScrollEntry> result = new ArrayList<ScrollEntry>();
		DBUtil db1 = new DBUtil();
		String sql = "select * from emu_android_success where (ev = 'Scroll')";

		ResultSet rs1 = db1.executeQuerySQL(sql);
		try {
			while(rs1.next()){
				long time = rs1.getLong("time");
				int scrollx = rs1.getInt("scrollx");
				int scrolly = rs1.getInt("scrolly");
				result.add(new ScrollEntry(scrollx, scrolly, time));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db1.rundown();
		return result;
	}
	
	public static ArrayList<ArrayList<ScrollEntry>> extractScrollEventGroup(){
		 ArrayList<ArrayList<ScrollEntry>> res = new  ArrayList<ArrayList<ScrollEntry>>();
		 
		 ArrayList<ScrollEntry> dataset = DataExtractor.extractScrollEvent();
		 ArrayList<ScrollEntry> scrollInPageLevel = new ArrayList<ScrollEntry>();
		 for(ScrollEntry tmpEntry : dataset){
			 if(tmpEntry.getScrollX() == 0 && tmpEntry.getScrollY() == 0){
				 if(scrollInPageLevel.size() != 0){
					 res.add(scrollInPageLevel);
					 scrollInPageLevel = new ArrayList<ScrollEntry>();
				 }
			 }else{
				 scrollInPageLevel.add(tmpEntry);
			 }
		 }
		 
		 for(ArrayList<ScrollEntry> test : res){
			 System.out.println(test.size());
		 }
		 
		 return res;
	}
	
	public static ArrayList<ArrayList<ScrollEntry>> extractScrollEventByPage(){
		ArrayList<ArrayList<ScrollEntry>> result = new ArrayList<ArrayList<ScrollEntry>>();
		ArrayList<ScrollEntry> scrollInPage = new ArrayList<ScrollEntry>();
		
		
		DBUtil db1 = new DBUtil();
		String sql = "select * from emu_android_success where (ev = 'Scroll') or (ev = 'WebClientPageStart')";
		ResultSet rs1 = db1.executeQuerySQL(sql);
		try {
			while(rs1.next()){
				String ev = rs1.getString("ev");
				if(ev.equals("WebClientPageStart")){
					if(scrollInPage.size() != 0)
						result.add(scrollInPage);
					
					scrollInPage = new ArrayList<ScrollEntry>();
				}else{
					long time = rs1.getLong("time");
					int scrollx = rs1.getInt("scrollx");
					int scrolly = rs1.getInt("scrolly");
					scrollInPage.add(new ScrollEntry(scrollx, scrolly, time));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db1.rundown();
		 for(ArrayList<ScrollEntry> test : result){
			 System.out.println(test.size());
		 }
		return result;
	}
	
	
	
	public static ArrayList<ArrayList<ScrollEntry>> extractIOScrollEventByPage(){
		ArrayList<ArrayList<ScrollEntry>> result = new ArrayList<ArrayList<ScrollEntry>>();
		ArrayList<ScrollEntry> scrollInPage = new ArrayList<ScrollEntry>();
		String currenturl ="";
		String currentuid = "";
		int currenttaskid = 0;
		DBUtil db1 = new DBUtil();
		String sql = "select * from emu_android_success where ((ev = 'Scroll') or (ev = 'WebClientPageStart') or (ev = 'StartNewTask')) and ((uid = 'twu37') or (uid = 'munch')  or (uid = 'twu372') or (uid = '1720689')) ";
		ResultSet rs1 = db1.executeQuerySQL(sql);
		try {
			while(rs1.next()){
				String ev = rs1.getString("ev");
				if(ev.equals("WebClientPageStart")){
					if(scrollInPage.size() != 0)
						result.add(scrollInPage);
					
					scrollInPage = new ArrayList<ScrollEntry>();
					currenturl =  rs1.getString("starturl");
				}else if(ev.equals("Scroll")){
					long time = rs1.getLong("time");
					int scrollx = rs1.getInt("scrollx");
					int scrolly = rs1.getInt("scrolly");
					scrollInPage.add(new ScrollEntry(scrollx, scrolly, time, currenturl, currentuid, currenttaskid));
				}else if(ev.equals("StartNewTask")){
					currentuid = rs1.getString("uid");
					//currenttaskid = Integer.valueOf(rs1.getString("taskid").split("|")[1]);
					int index = rs1.getString("taskid").indexOf(currentuid) + currentuid.length()+1;
					System.out.println(rs1.getString("taskid"));
					System.out.println(rs1.getString("taskid").substring(index));
					currenttaskid = Integer.valueOf(rs1.getString("taskid").substring(index));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db1.rundown();
		 for(ArrayList<ScrollEntry> test : result){
			 System.out.println(test.size());
		 }
		return result;
	}
	
	public static ArrayList<ArrayList<ZoomEntry>> extractIOZoomEventByPage(){
		ArrayList<ArrayList<ZoomEntry>> result = new ArrayList<ArrayList<ZoomEntry>>();
		ArrayList<ZoomEntry> zoomEntryInPage = new ArrayList<ZoomEntry>();
		String currenturl ="";
		String currentuid = "";
		int currenttaskid = 0;
		DBUtil db1 = new DBUtil();
		String sql = "select * from emu_android_success where ((ev = 'WebClientScaleChange') or (ev = 'WebClientPageStart') or (ev = 'StartNewTask')) and ((uid = 'twu37') or (uid = 'munch')  or (uid = 'twu372') or (uid = '1720689'))";
		ResultSet rs1 = db1.executeQuerySQL(sql);
		try {
			while(rs1.next()){
				String ev = rs1.getString("ev");
				if(ev.equals("WebClientPageStart")){
					if(zoomEntryInPage.size() != 0)
						result.add(zoomEntryInPage);
					zoomEntryInPage = new ArrayList<ZoomEntry>();
					currenturl =  rs1.getString("starturl");
				}else if(ev.equals("WebClientScaleChange")){
					long time = rs1.getLong("time");
					Double oldscale = rs1.getDouble("oldscale");
					Double newscale = rs1.getDouble("newscale");
					zoomEntryInPage.add(new ZoomEntry(oldscale, newscale, time, currenturl, currentuid, currenttaskid));
				}else if(ev.equals("StartNewTask")){
					currentuid = rs1.getString("uid");
					//currenttaskid = Integer.valueOf(rs1.getString("taskid").split("|")[1]);
					int index = rs1.getString("taskid").indexOf(currentuid) + currentuid.length()+1;
					System.out.println(rs1.getString("taskid"));
					System.out.println(rs1.getString("taskid").substring(index));
					currenttaskid = Integer.valueOf(rs1.getString("taskid").substring(index));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db1.rundown();
		return result;
	}
	
	public static ArrayList<ArrayList<ZoomEntry>> extractZoomEventByPage(){
		ArrayList<ArrayList<ZoomEntry>> result = new ArrayList<ArrayList<ZoomEntry>>();
		ArrayList<ZoomEntry> zoomEntryInPage = new ArrayList<ZoomEntry>();
		
		DBUtil db1 = new DBUtil();
		String sql = "select * from emu_android_success where (ev = 'WebClientScaleChange') or (ev = 'WebClientPageStart')";
		ResultSet rs1 = db1.executeQuerySQL(sql);
		try {
			while(rs1.next()){
				String ev = rs1.getString("ev");
				if(ev.equals("WebClientPageStart")){
					if(zoomEntryInPage.size() != 0)
						result.add(zoomEntryInPage);
					zoomEntryInPage = new ArrayList<ZoomEntry>();
				}else{
					long time = rs1.getLong("time");
					Double oldscale = rs1.getDouble("oldscale");
					Double newscale = rs1.getDouble("newscale");
					zoomEntryInPage.add(new ZoomEntry(oldscale, newscale, time));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db1.rundown();
		return result;
	}
}
