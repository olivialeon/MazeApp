package edu.wm.cs.cs301.soleonv.falstad;

import edu.wm.cs.cs301.soleonv.generation.CardinalDirection;
import edu.wm.cs.cs301.soleonv.generation.Distance;
import edu.wm.cs.cs301.soleonv.generation.StubMazeController;
import edu.wm.cs.cs301.soleonv.falstad.MazeController.UserInput;
import edu.wm.cs.cs301.soleonv.falstad.Robot.Turn;
import edu.wm.cs.cs301.soleonv.ui.PlayActivity;


/**
 * CRC Card for Manual Driver
- Responsibilities 
    -  
- Collaboration 
    - operates on basic robot
    - Receives board input 
    
 * @author solivialeonvitervo
 *
 */

public class ManualDriver implements RobotDriver{
	
	Robot robot;
	Distance distance;
	int width;
	int height;
	private CardinalDirection currentDirection;
	public PlayActivity play;
	
	public ManualDriver(){
	}

	@Override
	public void setRobot(Robot r){
		robot = r;
	}
	
	@Override
	public void setDimensions(int width, int height){
		if (width >= 0)
			this.width = width;
		if (width >= 0)
			this.height = height;
	}
	
	@Override
	public void setDistance(Distance distance){
		if (distance != null)
			this.distance = distance;
	
	}
	
	@Override
	public boolean drive2Exit() throws Exception {
		if (robot.getBatteryLevel() == 0)
			throw new Exception();
		
		else if (robot.isAtExit()) 
			return true; 
		
		else
			return false;
	}
	
	
	@Override
	public float getEnergyConsumption() {
		return 3000 - robot.getBatteryLevel();
		
	}
	
	@Override 
	public int getPathLength(){
		return robot.getOdometerReading(); 
	}
	
	/*
	 * called in simpleKeyListener, used to interpret keys from user and make useful movements 
	 */
	public void keyDown(UserInput uikey){
		if (uikey == UserInput.Down)

			robot.move(-1, true);
		if (uikey ==UserInput.Up)

			robot.move(1, true);
		if (uikey ==UserInput.Left)

			robot.rotate(Turn.LEFT);

		if (uikey ==UserInput.Right)

			robot.rotate(Turn.RIGHT);
		
	}

	public void setPlayingActivity(PlayActivity play){
		this.play = play;
	}

}
