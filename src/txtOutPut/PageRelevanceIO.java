package txtOutPut;

public class PageRelevanceIO {
	
	private static String[] dataColumnName = {
		"pageurl_uid",
		//gesture association
		"gesture_totalcount",
		"gesture_totalmovement_withoutmultitouch",
		"gesture_totalmovement_withmultitouch",
		"gesture_avg_speed",
		"gesture_zoom_count",
		"gesture_zoom_scale_total",
		"gesture_scroll_count",
		"gesture_scroll_length_total",

		//time association
		"page_viewing_time",
		"time_in_task",
		""
		
		//search session association
		
	};

	public static PageRelevanceIO singlton = null;
	
	public PageRelevanceIO getInstance(){
		if(singlton == null)
			singlton = new PageRelevanceIO();
		return singlton;
	}

	public PageRelevanceIO(){
		
	}
}
