import java.io.DataInputStream;
import java.io.IOException;

import javax.swing.JFrame;

import lejos.pc.comm.NXTConnector;

public class ExplorerPCClient
{
	
    public static void main(String[] args)
    {
    	JFrame frame = new JFrame();
    	frame.setTitle("Robot Map Drawer");
    	frame.setSize(400, 400);
    	frame.setLocationRelativeTo(null);
    	MapComponent mc = new MapComponent();
    	frame.add(mc);
    	frame.setVisible(true);
	NXTConnector con = new NXTConnector();
	boolean connected = con.connectTo("btspp://");
	if (!connected)
	{
	    System.out.println("Failed to connect to any NXT");
	    System.exit(1);
	}
	System.out.println("Connected");
	System.out.println();

	DataInputStream dis = new DataInputStream(con.getInputStream());

	boolean done = false;
	while (!done)
	{
	    try
	    {
		float x = dis.readFloat();
	
		float y = dis.readFloat();
		
		float xp = dis.readFloat();
		
		float yp = dis.readFloat();
		System.out.printf("obstacle x= " + x + " " +"obstacle y= " + y);
		System.out.printf("robot pose xp= " + xp + " " +"robot pose yp= " + yp);
	
		mc.addPoseRobot(xp, yp);
		mc.addPosObstacle(x, y);
		
		System.out.println();
	    } catch (IOException e)
	    {
		e.printStackTrace();
		System.exit(0);
	    }
	}
    }
}
