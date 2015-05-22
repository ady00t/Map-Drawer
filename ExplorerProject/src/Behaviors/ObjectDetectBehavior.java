package Behaviors;

import java.io.DataOutputStream;
import java.io.IOException;

import lejos.geom.Point;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.comm.NXTConnection;
import lejos.robotics.RangeReading;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Pose;
import lejos.robotics.objectdetection.Feature;
import lejos.robotics.objectdetection.FeatureDetector;
import lejos.robotics.objectdetection.FeatureListener;
import lejos.robotics.objectdetection.RangeFeatureDetector;
import lejos.robotics.subsumption.Behavior;

public class ObjectDetectBehavior implements Behavior, FeatureListener
{
    private boolean suppressed = false;
    private boolean featureDetected = false;

    private DifferentialPilot pilot;
    private UltrasonicSensor us;

    private OdometryPoseProvider pose;
    private RangeFeatureDetector rfd;
    private Feature detectedFeature = null;
    private TouchSensor bumper ;
    
    private DataOutputStream out;

    public ObjectDetectBehavior(DifferentialPilot pilot, NXTConnection con)
    {
	this.pilot = pilot;
	bumper = new TouchSensor(SensorPort.S2);
	out = con.openDataOutputStream();
	pose = new OdometryPoseProvider(pilot);
	us = new UltrasonicSensor(SensorPort.S1);
	rfd = new RangeFeatureDetector(us, 25, 250);
	 rfd.setPoseProvider(pose);
	rfd.addListener(this);
    }

    @Override
    public boolean takeControl()
    {
	return featureDetected || bumper.isPressed() ;
    }

    @Override
    public void action()
    {
	suppressed = false;
	pilot.stop();
//	Sound.beepSequenceUp();
	synchronized (this)
	{
		RangeReading range = detectedFeature.getRangeReading();
		boolean checkRange = range.invalidReading();

	    while (!checkRange && us.getDistance() <26 || bumper.isPressed() )
	    {
	    	range = detectedFeature.getRangeReading();
		try
		{
				
			Pose position = pose.getPose();
			  
		  
	    	Point point = position.pointAt(range.getRange(), position.getHeading());    
		    out.writeFloat(point.x);
		    out.writeFloat(point.y);
		    out.writeFloat(position.getX());
		    out.writeFloat(position.getY());
		    pilot.rotate(1);
		    us.ping();
		    	    
		    try
		    {
			Thread.sleep(50);
		    } catch (InterruptedException e)
		    {}
		    
		    out.flush();
		    
		} catch (IOException e)
		{
		    Sound.beepSequence();
		}
	    }
	}

	rfd.enableDetection(false);
	pilot.travel(-20);
	pilot.rotate(30);
	featureDetected = false;
	rfd.enableDetection(true);
	 
	Sound.beepSequence();
	Thread.yield();
    }

    @Override
    public void suppress()
    {
	suppressed = true;
    }

    @Override
    public void featureDetected(Feature feature, FeatureDetector detector)
    {
	detectedFeature = feature;
	featureDetected = true;
    }

}
