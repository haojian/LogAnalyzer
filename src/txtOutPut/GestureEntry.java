package txtOutPut;

import java.util.ArrayList;

import util.GraphCalculator;
import util.WebviewTouchEntry;

public class GestureEntry {
	public ArrayList<WebviewTouchEntry> points;
	public GestureEntry(){
		points = new ArrayList<WebviewTouchEntry>();
	}
	
	public double GetLength(){
		return GraphCalculator.calGesture_length_pointtopoint(points);
	}
	
	public double GetSpeed(){
		double time =points.get(0).getTimeDifference(points.get(points.size()-1));
		return this.GetLength()/time;
	}
}
