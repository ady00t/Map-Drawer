import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;


public class MapComponent extends JComponent	
{
    private List<Point> points = new ArrayList<>(); 
    private List<Point> robotP = new ArrayList<>(); 
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
        g2d.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
       
        	for ( int i = 0; i <robotP.size(); i++)
        	{
        		
        		Point p  = robotP.get(i) ;
        				g2d.fillOval(getWidth() / 2 + p.x, getHeight() / 2 - p.y, 5, 5);
        	}
               
        	for ( int i = 0; i <points.size(); i++)
        	{
        		
        		Point p  = points.get(i) ;
        				g2d.fillOval(getWidth() / 2 + p.x, getHeight() / 2 - p.y, 3, 3);
        	}
               
       
    }
    
    public void addPosObstacle(float x, float y)
    {
	points.add(new Point(Math.round(x), Math.round(y)));
	repaint();
    }

	

	public void addPoseRobot(float xp, float yp) {
		robotP.add(new Point(Math.round(xp), Math.round(yp)));
		repaint();
		
	}
     
    
}
