import java.awt.Color;
import java.util.Random;

import javax.swing.JFrame;

import util.DataExtractor;
import visualizationUtil.Gradient;
import visualizationUtil.HeatMap;

public class HeatMapViewer extends JFrame {
	
	HeatMap panel;
	public HeatMapViewer(int _width, int _height){
		super("Heat Map Frame");
        //double[][] data = HeatMap.generateRampTestData();
        
        Random abc = new Random();
        /*
        double[][] data = new double[_width][_height];
        for (int x = 0; x < _width; x++)
        {
            for (int y = 0; y < _height; y++)
            {
                data[x][y] = abc.nextInt(1000);
            }
        }
        */
        double[][] data = DataExtractor.extract_heatmap_data_onlyStartTrace(_width, _height, 0);
        
        boolean useGraphicsYAxis = true;
        
        // you can use a pre-defined gradient:
        panel = new HeatMap(data, useGraphicsYAxis, Gradient.GRADIENT_BLUE_TO_RED);
        
        // or you can also make a custom gradient:
        Color[] gradientColors = new Color[]{Color.blue, Color.green, Color.yellow};
        Color[] customGradient = Gradient.createMultiGradient(gradientColors, 500);
        panel.updateGradient(customGradient);
        
        // set miscelaneous settings
        panel.setDrawLegend(true);

        panel.setTitle("Touch Trace");
        panel.setDrawTitle(true);

        panel.setXAxisTitle("X (pixel)");
        panel.setDrawXAxisTitle(true);

        panel.setYAxisTitle("Y (pixel)");
        panel.setDrawYAxisTitle(true);

        panel.setCoordinateBounds(0, 1000, 0, 1000);
        panel.setDrawXTicks(true);
        panel.setDrawYTicks(true);

        panel.setColorForeground(Color.black);
        panel.setColorBackground(Color.white);

        this.getContentPane().add(panel);
	}
}
