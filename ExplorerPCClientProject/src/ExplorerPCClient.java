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
	//float h = dis.readFloat();
		//float range = dis.readFloat();
		//float angle = dis.readFloat();
		mc.addPoseRobot(xp, yp);
		mc.addPosition(x, y);
		//System.out.printf(
			//"Position = (%4.2f, %4.2f), heading = %6.2f degrees\n",
			//x, y, h );
		//System.out.printf(
			//"Object found: Range = %6.2f cm. at angle = %6.2f\n",
			//range, angle );
		System.out.println();
		
		

	    } catch (IOException e)
	    {
		//System.out.println("ERROR - " + e.getMessage() );
		e.printStackTrace();
		System.exit(0);
	    }
	}
    }
}
