package util;

import java.util.ArrayList;

import util.WebviewTouchEntry.TouchActionType;

public class GraphCalculator {
	public static ArrayList<TracePointEntry> getPointsinCircle(int centerX,
			int centerY, int radius) {
		ArrayList<TracePointEntry> result = new ArrayList<TracePointEntry>();
		int minX = centerX - radius;
		int minY = centerY - radius;
		int maxX = centerX + radius;
		int maxY = centerY + radius;

		for (int i = 0; i < radius; i++) {
			int x = (int) Math.sqrt(radius * radius - i * i);
			for (int j = 0; j < x; j++) {
				result.add(new TracePointEntry((centerX - j), (centerY - i)));
				result.add(new TracePointEntry((centerX - j), (centerY + i)));
				result.add(new TracePointEntry((centerX + j), (centerY - i)));
				result.add(new TracePointEntry((centerX + j), (centerY + i)));
			}
		}
		return result;
	}

	public static double calLine_length(float x1, float y1, float x2, float y2) {
		return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
	}

	// need a patch to calculate some special gesture data.
	public static double calGesture_length_pointtopoint(
			ArrayList<WebviewTouchEntry> singleGesture) {
		//System.out.println(singleGesture.size()); 
		double length = 0;
		WebviewTouchEntry lastTracePoint = new WebviewTouchEntry();
		
		for (WebviewTouchEntry tracePoint : singleGesture) {
			if (lastTracePoint.getAction_Type() == TouchActionType.ERROR) {
				lastTracePoint = tracePoint;
			} else {
				for (int i = 0; i < tracePoint.getTouch_Point_Num(); i++) {
					if (i <= lastTracePoint.getTouch_Point_Num() - 1) {
						if (lastTracePoint.getTouch_Point_Num() <= tracePoint
								.getTouch_Point_Num()) {
							length += calLine_length(tracePoint.getPointList()
									.get(i).getX(), tracePoint.getPointList()
									.get(i).getY(), lastTracePoint
									.getPointList().get(i).getX(),
									lastTracePoint.getPointList().get(i).getY());
						}
					}
				}
			}
			lastTracePoint = tracePoint;

		}
		//System.out.println(length);  
		return length;
	}
	
	public static double cal_singleTouchGesture_speed(
			ArrayList<WebviewTouchEntry> singleGesture) {
		//System.out.println(singleGesture.size()); 
		double length = 0;
		WebviewTouchEntry lastTracePoint = new WebviewTouchEntry();
		long time = 0;
		for (WebviewTouchEntry tracePoint : singleGesture) {
			if (lastTracePoint.getAction_Type() == TouchActionType.ERROR) {
				lastTracePoint = tracePoint;
			} else {
				if(tracePoint.getTouch_Point_Num() > 1){
					return -1;
				}
				length += calLine_length(tracePoint.getPointList().get(0).getX(), tracePoint.getPointList().get(0).getY(),
						lastTracePoint
						.getPointList().get(0).getX(),
						lastTracePoint.getPointList().get(0).getY());
			}
			time += tracePoint.getTimeDifference(lastTracePoint);
			
			lastTracePoint = tracePoint;
		}
		//System.out.println(length);  
		
		double speed = length/time* 1000;
		if(speed < 0)
			System.out.println(length + " | " + time);
		//System.out.println(time + " | " + speed);
		//System.out.println(speed);
		return speed;
	}
}
