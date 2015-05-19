import Behaviors.*;
import lejos.nxt.*;
import lejos.nxt.comm.*;
import lejos.robotics.navigation.*;
import lejos.robotics.subsumption.*;


public class Explorer
{
    private static DifferentialPilot pilot;

    /**
     * @param args
     */
    public static void main(String[] args)
    {
	NXTConnection con = Bluetooth.waitForConnection();

	pilot = new DifferentialPilot(DifferentialPilot.WHEEL_SIZE_NXT2, 15.5,
		Motor.A, Motor.B);
	pilot.setTravelSpeed(5);

	Behavior b1 = new ExploreBehavior(pilot);
	Behavior b2 = new ObjectDetectBehavior(pilot, con);
	Behavior b3 = new ExitBehavior();
	Behavior[] behaviorList = { b1, b2, b3 };
	Arbitrator arbitrator = new Arbitrator(behaviorList);
	
	LCD.drawString("Explorer", 0, 1);
	Button.waitForAnyPress();
	
	arbitrator.start();
    }
}
