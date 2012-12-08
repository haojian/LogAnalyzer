package util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class IOOperator {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// InitializeSetting();
		//writeGestureSummerizationToFile();

	}

	public static void writeZoomDataToFile() {
		String title = "user id \t task id \t event name \t time \t url \t oldscale \t newscale";
		String event = "Zoom";
		String filepath = event + ".txt";
		writeToFile(filepath, title + "\n", true);
		ArrayList<ArrayList<ZoomEntry>> dataSet = DataExtractor
				.extractIOZoomEventByPage();
		for (ArrayList<ZoomEntry> dataInsamepage : dataSet) {
			for (ZoomEntry curEntry : dataInsamepage) {
				if (curEntry.getUserid() == "")
					curEntry.setUserid("munch");

				if (curEntry.getUserid().equals("twu37")
						&& curEntry.getTaskIndex() >= 5)
					continue;
				if (curEntry.getUserid().equals("twu372")
						&& curEntry.getTaskIndex() >= 5)
					curEntry.setUserid("twu37");

				String content = curEntry.getUserid() + "\t"
						+ curEntry.getTaskIndex() + "\t" + event + "\t"
						+ curEntry.getTime() + "\t" + curEntry.getUrl() + "\t"
						+ curEntry.getOldZoomScale() + "\t"
						+ curEntry.getNewZoomScale();
				writeToFile(filepath, content + "\n", true);
			}
		}
		System.out.println("Done");
	}

	public static void writeScollDataToFile() {
		String title = "user id \t task id \t event name \t time \t url \t targetscrollX \t targetscrollY";
		String event = "Scroll";
		String filepath = event + ".txt";
		writeToFile(filepath, title + "\n", true);

		ArrayList<ArrayList<ScrollEntry>> dataSet = DataExtractor
				.extractIOScrollEventByPage();
		for (ArrayList<ScrollEntry> dataInsamepage : dataSet) {
			for (ScrollEntry curEntry : dataInsamepage) {
				if (curEntry.getUserid() == "")
					curEntry.setUserid("munch");

				if (curEntry.getUserid().equals("twu37")
						&& curEntry.getTaskIndex() >= 5)
					continue;
				if (curEntry.getUserid().equals("twu372")
						&& curEntry.getTaskIndex() >= 5)
					curEntry.setUserid("twu37");

				String content = curEntry.getUserid() + "\t"
						+ curEntry.getTaskIndex() + "\t" + event + "\t"
						+ curEntry.getTime() + "\t" + curEntry.getUrl() + "\t"
						+ curEntry.getScrollX() + "\t" + curEntry.getScrollY();
				writeToFile(filepath, content + "\n", true);
			}
		}
		System.out.println("Done");

	}
	
	public static void writeWebviewTouchDetails(){
		String title = "user id \t task id \t event_name \t actiontype \t time \t url \t TouchPointNum \t pressure0 \t touchsize0 \t vx0 \t vy0 \t \t pressure1 \t touchsize1 \t vx1 \t vy1 \t pressure2 \t touchsize2 \t vx2 \t vy2 \t pressure2 \t touchsize2 \t vx2 \t vy2 \t pressure3 \t touchsize3 \t vx3 \t vy3 \t pressure4 \t touchsize4 \t vx4 \t vy4";
		
		//String title = "user id \t task id \t event name \t time \t url \t length \t averagespeed \t averagetouchpress \t averagetouchsize \t sdtouchpress \t sdtouchsize";
		String event = "Touch data";
		String filepath =  event + ".txt";
		writeToFile(filepath, title + "\n", true);
		
		ArrayList<ArrayList<WebviewTouchEntry>> touches_in_gestures  = DataExtractor.extractGesture();
		boolean flag = false;
		for(ArrayList<WebviewTouchEntry> curEntryList : touches_in_gestures){
			for(WebviewTouchEntry curEntry : curEntryList){
				if(curEntry.getUid().equals(""))
					curEntry.setUid("munch");
	
				 if(curEntry.getUid().equals("twu372") )
					 curEntry.setUid("twu37");

				 if(curEntry.getUid().equals("twu37") || curEntry.getUid().equals("munch") || curEntry.getUid().equals("1720689")){
				 String content = curEntry.getUid() + "\t" + curEntry.getTaskIndex() + "\t" + event + "\t" + curEntry.getAction_Type() + "\t" + curEntry.getPointList().get(0).timeStamp
						 + "\t" + curEntry.getUrl() + "\t" + curEntry.getTouch_Point_Num() + "\t";
				 for(int i =0 ;i<curEntry.getTouch_Point_Num(); i++){
					 content += curEntry.getPointList().get(i).getPressure() + "\t" + curEntry.getPointList().get(i).getPressSize() + "\t" + curEntry.getPointList().get(i).getX() + "\t" + curEntry.getPointList().get(i).getY() + "\t";
				 }
				 writeToFile(filepath, content + "\n", true);
				 }
				
			}
			writeToFile(filepath, "#############New Gesture Started##############\n", true);
			
		}
		System.out.println("Done");
	}

	public static void writeGestureSummerizationToFile(){
		String title = "user id \t task id \t event name \t time \t url \t length \t averagespeed \t averagetouchpress \t averagetouchsize \t sdtouchpress \t sdtouchsize";
		String event = "GestureSummerization";
		String filepath =  event + ".txt";
		writeToFile(filepath, title + "\n", true);
		
		ArrayList<GestureEntry> gestures = DataExtractor.extractIOGesture();
		
		for(GestureEntry curEntry : gestures){
			if(curEntry.getUserid().equals("") )
				 curEntry.setUserid("munch");
			 
			 if(curEntry.getUserid().equals( "twu37") && curEntry.getTaskIndex()>= 5)
				 continue;
			 if(curEntry.getUserid().equals("twu372") && curEntry.getTaskIndex()>= 5)
				 curEntry.setUserid("twu37");

			 String content = curEntry.getUserid() + "\t" + curEntry.getTaskIndex() + "\t" + event + "\t" + curEntry.getTime() + "\t" + curEntry.getUrl() + "\t" + curEntry.getLength() + "\t" + curEntry.getAveragespeed() + "\t" + curEntry.getAveragetouchpress() + "\t" + curEntry.getAveragetouchsize() + "\t" + curEntry.getSdtouchpress() + "\t" + curEntry.getSdtouchsize();
			 writeToFile(filepath, content + "\n", true);
		}
		System.out.println("Done");
	}

	public static void writeGestureCoordination() {
		String title = "user id \t task id \t event name \t time \t url \t startX \t startY \t endX \t endY \t isVertical";
		String event = "GestureCoordination";
		String filepath = event + ".txt";
		//writeToFile(filepath, title, true);
	}

	public static void writeTextUpdate() {
		String title = "user id \t task id \t event name \t time \t longest text \t backspace counter";
		String event = "TextUpdate";
		String filepath = event + ".txt";
		writeToFile(filepath, title + "\n", true);
		ArrayList<TextInputEntry> dataset = DataExtractor.TextSequenceAnalysisWithUID();
		for(TextInputEntry curEntry : dataset){
			if(curEntry.getUserid() == null || curEntry.getUserid().equals(""))
				 curEntry.setUserid("munch");
			 
			 if(curEntry.getUserid().equals( "twu37") && curEntry.getTaskIndex()>= 5)
				 continue;
			 if(curEntry.getUserid().equals("twu372") && curEntry.getTaskIndex()< 5)
				 continue;
			 if(curEntry.getUserid().equals("twu372") && curEntry.getTaskIndex()>= 5)
				 curEntry.setUserid("twu37");

			 String content = curEntry.getUserid() + "\t" + curEntry.getTaskIndex() + "\t" + event + "\t" +  curEntry.getTime() + "\t" + curEntry.getLongest_text() +"\t" + curEntry.getBackspace_count();
			 writeToFile(filepath, content + "\n", true);
		}
		System.out.println("Done");
	}

	public static void writeLoadingData() {
		String title = "user id \t task id \t event name \t time \t url \t loaded percentage \t time takes";
		String event = "LoadingTime";
		String filepath = event + ".txt";
		writeToFile(filepath, title + "\n", true);
		
		ArrayList<LoadingEntry> dataset = DataExtractor.extractProcessedLoadingTimeData_withuid();
		for(LoadingEntry curEntry : dataset){
			if(curEntry.getUserid().equals(""))
				 curEntry.setUserid("munch");
			 
			 if(curEntry.getUserid().equals( "twu37") && curEntry.getTaskIndex()>= 5)
				 continue;
			 if(curEntry.getUserid().equals("twu372") && curEntry.getTaskIndex()< 5)
				 continue;
			 if(curEntry.getUserid().equals("twu372") && curEntry.getTaskIndex()>= 5)
				 curEntry.setUserid("twu37");

			 String content = curEntry.getUserid() + "\t" + curEntry.getTaskIndex() + "\t" + event + "\t" +  curEntry.getStart_timeStamp() + "\t" + curEntry.getUrl() +"\t" + curEntry.getProgress() + "\t" + curEntry.getTimetakes();
			 writeToFile(filepath, content + "\n", true);
		}
		System.out.println("Done");
	}

	public static void writeFrustrationData() {
		String title = "user id \t task id \t event name \t time \t frustrationrate";
		String event = "Frustration";
		String filepath =  event + ".txt";
		writeToFile(filepath, title + "\n", true);
		ArrayList<FrustrationEntry> dataset = DataExtractor.extract_frustrationData();
		for(FrustrationEntry curEntry : dataset){
			if(curEntry.getUserid().equals(""))
				 curEntry.setUserid("munch");
			 
			 if(curEntry.getUserid().equals( "twu37") && curEntry.getTaskId()>= 5)
				 continue;
			 if(curEntry.getUserid().equals("twu372") && curEntry.getTaskId()< 5)
				 continue;
			 if(curEntry.getUserid().equals("twu372") && curEntry.getTaskId()>= 5)
				 curEntry.setUserid("twu37");

			 String content = curEntry.getUserid() + "\t" + curEntry.getTaskId() + "\t" + event + "\t" +  curEntry.getTimestamp()  +"\t" + curEntry.getFrustrationlvl();
			 writeToFile(filepath, content + "\n", true);
		}
	}

	public static void writeToFile(String filename, String content,
			boolean isappend) {
		FileWriter writer;
		try {
			writer = new FileWriter(filename, isappend);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
