package txtOutPut;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import util.DBUtil;

public class PageRelevanceIO {
	DescriptiveStatistics pressureStats, pressszieStats;
	
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
		
		"gesture_scroll_count",
		"gesture_scroll_length_total",
		"gesture_scroll_length_avg",
		"gesture_scroll_length_std",
		
		"gesture_scroll_positive_count",
		"gesture_scroll_positive_total",
		"gesture_scroll_positive_length_avg",
		"gesture_scroll_positive_length_std",
		
		"gesture_scroll_negative_count",
		"gesture_scroll_negative_total",
		"gesture_scroll_negative_length_avg",
		"gesture_scroll_negative_length_std",
		
		"gesture_scroll_count",
		"gesture_scroll_length_total",
		"gesture_scroll_length_avg",
		"gesture_scroll_length_std",
		
		"gesture_scroll_positive_count",
		"gesture_scroll_positive_total",
		"gesture_scroll_positive_length_avg",
		"gesture_scroll_positive_length_std",
		
		"gesture_scroll_negative_count",
		"gesture_scroll_negative_total",
		"gesture_scroll_negative_length_avg",
		"gesture_scroll_negative_length_std",
		//time association
		"page_viewing_time",
		"time_in_task",
		
		//search session association
		"query_length",
		"query_index"
	};

	public static PageRelevanceIO singlton = null;
	
	public PageRelevanceIO getInstance(){
		if(singlton == null)
			singlton = new PageRelevanceIO();
		return singlton;
	}

	public PageRelevanceIO(){
		pressureStats = new DescriptiveStatistics();
		pressszieStats = new DescriptiveStatistics();
		
		
	}
	
	public void getPressureData(){
		
	}
	
	public static void extractDataBasedOnID(String uid){
		
	}
	
	public static double[][] extract_heatmap_data_onlyEndTrace(int _width, int _height){
		double[][] data= new double[_width][_height];
		DBUtil db1 = new DBUtil();
		String sql = "select * from emu_android_success where (uid = '091401') ";
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
}
