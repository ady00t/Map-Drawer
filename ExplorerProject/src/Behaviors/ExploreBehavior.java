package Behaviors;

import lejos.nxt.Sound;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.subsumption.Behavior;

public class ExploreBehavior implements Behavior
{
    private DifferentialPilot pilot;
    private boolean _suppressed = false;
    private Navigator nav ;

    public ExploreBehavior(DifferentialPilot pilot)
    {
	this.pilot = pilot;
	this.nav = new Navigator(pilot);
    }

    @Override
    public boolean takeControl()
    {
	return true; // always try to take control
    }

    @Override
    public void action()
    {
	_suppressed = false;
	Sound.twoBeeps();
	//if(/*some integer value for encountered objects is more than max encounters*/)
	pilot.forward();
	while (! _suppressed)
	    Thread.yield();
	pilot.stop();
    }

    @Override
    public void suppress()
    {
	_suppressed = true;
    }
}
