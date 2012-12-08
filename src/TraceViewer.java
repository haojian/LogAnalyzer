import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import util.DataExtractor;
import util.WebviewTouchEntry;
import util.WebviewTouchEntry.TouchActionType;


public class TraceViewer extends Canvas implements KeyListener{
	
	private Color background_color, foreground_color;
	private int canvas_width, canvas_height;
	ArrayList<WebviewTouchEntry> dataset;
	BufferedImage offScreenImg;
	Graphics2D offScreenG2D;
	int touchTracker = -1;  //-1 means up, 0 means move, 1 means down.
	int displayIndex = 0;
	
	ArrayList<ArrayList<WebviewTouchEntry>> gestureList;

	public TraceViewer(int _width, int _height, Color _background_color, Color _foreground_color){
		background_color = _background_color;
		foreground_color = _foreground_color;
		canvas_width = _width;
		canvas_height = _height;
		this.setSize(_width, _height);
		this.setBackground(_background_color);
		this.addKeyListener(this);
		offScreenImg = new BufferedImage(canvas_width,canvas_height, BufferedImage.TYPE_INT_RGB);
		offScreenG2D = offScreenImg.createGraphics();

		gestureList = new ArrayList<ArrayList<WebviewTouchEntry>>();
		initialize();
	}
	
	public void initialize(){
		dataset= DataExtractor.extractWebview_touchtrace_log("test");
		//dataset= DataExtractor.extractWebview_touchtrace_log();
		extractGesture();
		repaint();
	}
	
	public void extractGesture(){
		ArrayList<WebviewTouchEntry> res = new ArrayList<WebviewTouchEntry>();
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
	}
	
	public void display(){
		
	}
	
	public void paint(Graphics g) {
		update(g);
	}


	public void update(Graphics g) {
		offScreenG2D.setBackground(background_color);
		offScreenG2D.clearRect(0, 0, canvas_width, canvas_height);
		WebviewTouchEntry lastTouchEntry = new WebviewTouchEntry();
		lastTouchEntry =  gestureList.get(displayIndex).get(0);
		//display the gesture at displayIndex
		for(int i=0; i<gestureList.get(displayIndex).size(); i++){
			WebviewTouchEntry curTouchEntry = gestureList.get(displayIndex)
					.get(i);
			for (int j = 0; j < curTouchEntry.getTouch_Point_Num(); j++) {
				if(j == 1)
				{
					System.out.println(j);
				}
				Shape tmpSpot = new Ellipse2D.Float(curTouchEntry
						.getPointList().get(j).getX()
						- curTouchEntry.getPointList().get(j).getPressSize()
						* 25, curTouchEntry.getPointList().get(j).getY()
						- curTouchEntry.getPointList().get(j).getPressSize()
						* 25, curTouchEntry.getPointList().get(j)
						.getPressSize() * 50, curTouchEntry.getPointList()
						.get(j).getPressSize() * 50);
				offScreenG2D.setPaint(foreground_color);
				
				offScreenG2D.draw(tmpSpot);
				offScreenG2D.fill(tmpSpot);
				if (j <= lastTouchEntry.getTouch_Point_Num() - 1) {
					if (lastTouchEntry.getTouch_Point_Num() <= curTouchEntry
							.getTouch_Point_Num()) {
						offScreenG2D.drawLine((int) lastTouchEntry
								.getPointList().get(j).getX(),
								(int) lastTouchEntry.getPointList().get(j)
										.getY(), (int) curTouchEntry
										.getPointList().get(j).getX(),
								(int) curTouchEntry.getPointList().get(j)
										.getY());
					}
				}
				offScreenG2D.setPaint(foreground_color);
				if (j == 0)
					offScreenG2D.drawString(
							String.valueOf(j) + String.valueOf(i),
							curTouchEntry.getPointList().get(j).getX()
									- curTouchEntry.getPointList().get(j)
											.getPressSize() * 25, curTouchEntry
									.getPointList().get(j).getY()
									- curTouchEntry.getPointList().get(j)
											.getPressSize() * 25);
			}
			lastTouchEntry = gestureList.get(displayIndex).get(i);
		}
		g.drawImage(offScreenImg, 0, 0, canvas_width, canvas_height, this);
		g.setColor(foreground_color);
	}

	
    public void keyReleased(KeyEvent e) {
    }
    
    public void keyTyped(KeyEvent e) {
    }
    
    public void keyPressed(KeyEvent e) {
    		displayIndex++;
    		if(displayIndex == gestureList.size())
    			displayIndex = 0;
    		repaint();
    		System.out.println("Entered");
    }
}
