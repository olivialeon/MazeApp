package edu.wm.cs.cs301.soleonv.falstad;

import edu.wm.cs.cs301.soleonv.falstad.Robot.Direction;
import edu.wm.cs.cs301.soleonv.falstad.Robot.Turn;
import edu.wm.cs.cs301.soleonv.generation.CardinalDirection;
import edu.wm.cs.cs301.soleonv.generation.Distance;
import edu.wm.cs.cs301.soleonv.ui.PlayActivity;

/**
 * CRC
 * Responsibilities 
 * - implements the RobotDriver and drives the robot towards the exit by picking a main direction and
 * following that direction until it reaches a wall.  It will then apply wall following until the total
 * value of its turns, assigned left (-1) and right (+1) reaches zero
 * Collaborators 
 * -operates on top of the maze
 * - implements the RobotDriver
 * -  receives  information from robot class 
 * pseudocode 
 * 1) Randomly produce main direction and assign to a variable 
 * 2) Initiate a counter for the turns
 * 3) Take the implementation from the Wall Follower and add values -1 or +1 every time it turns
 * 4) We then add a condition where when the counter is at 0 it does not wall follow and instead goes in the main direction
 * 
 * @author solivialeonvitervo
 */

public class Pledge extends ManualDriver implements RobotDriver{
	
	private Robot robot;
	private Distance distance;
	private int width;
	private int height;
	protected MazeController currentMaze;

	public PlayActivity play;
	
	public Pledge(){
		//currentMaze.showMaze = true;
		//currentMaze.showSolution = true; 
		//currentMaze.mapMode = true; 
		
	}
	
	@Override
	public void setRobot(Robot r){
		robot = r;
		((BasicRobot) robot).getMaze();
	}

	public void setMaze(MazeController m){
		currentMaze = m;
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
		
		// stores the current direction of robot as the main direction for the driver to follow
		CardinalDirection mainDirection = robot.getCurrentDirection();
		int turnCounter = 0;
		
		
		//Not wall following state.  Goes in main direction
		while (robot.distanceToObstacle(Direction.FORWARD) != 0) {
			robot.move(1, false);
		}
		
	// checks if it reaches exit and is facing exit
		if (robot.isAtExit() && robot.canSeeExit(Direction.FORWARD) )
			return true; 
		
		robot.rotate(Turn.RIGHT);
		turnCounter +=1;
		
		
		
		//Wall following algorithm
		while (!(robot.isAtExit() && robot.canSeeExit(Direction.FORWARD))){
			while(turnCounter!=0){
				if ( (robot.distanceToObstacle(Direction.LEFT) == 0)  && (robot.distanceToObstacle(Direction.FORWARD) != 0 ) ) {
					robot.move(1, false);
				}

				if (robot.distanceToObstacle(Direction.LEFT) != 0){
					robot.rotate(Turn.LEFT);
					turnCounter -=1;
					robot.move(1, false);
				}

				if ( (robot.distanceToObstacle(Direction.LEFT) == 0)  && (robot.distanceToObstacle(Direction.FORWARD) == 0 ) ){
					robot.rotate(Turn.RIGHT);
					turnCounter +=1;
					robot.move(1, false);
				}	
			}
			while (robot.distanceToObstacle(Direction.FORWARD) != 0) {
				robot.move(1, false);
				if (robot.isAtExit() && robot.canSeeExit(Direction.FORWARD) )
					return true; 
			}
			
			robot.rotate(Turn.RIGHT);
			turnCounter +=1;
		}
		
	// checks if it reaches exit and is facing exit
		if (robot.isAtExit() && robot.canSeeExit(Direction.FORWARD) )
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


	public Robot getRobot() {
		return robot;
	}

	public Distance getDistance() {
		return distance;
	}

	public void setPlayingActivity(PlayActivity play){
		this.play = play;
	}
	
	

}
