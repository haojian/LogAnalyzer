package util;

public class TracePointEntry {
	float x, y;
	long timeStamp;
	float press_size;
	float pressure;
	
	public TracePointEntry(float _x, float _y, float _press_size, float _pressure, long _timeStamp)
	{
		x = _x;
		y = _y;
		press_size = _press_size;
		pressure = _pressure;
		timeStamp = _timeStamp;
	}
	
	public TracePointEntry(float _x, float _y)
	{
		x = _x;
		y = _y;
	}
	
	public String toString()
	{
		StringBuilder result= new StringBuilder("");
		result.append("<UData><X>" + x + "</X><Y>" + y + "</Y><TStamp>" + timeStamp + "</TStamp></UData>");
		return result.toString();
	}
	
	public float getX(){
		return x;
	}
	public float getY(){
		return y;
	}
	
	public float getPressure(){
		return pressure;
	}
	
	public float getPressSize(){
		return press_size;
	}
	
	public long getTimeStamp(){
		return timeStamp;
	}
}
