package edu.wm.cs.cs301.soleonv.falstad;

import edu.wm.cs.cs301.soleonv.generation.CardinalDirection;
import edu.wm.cs.cs301.soleonv.generation.Distance;
import edu.wm.cs.cs301.soleonv.falstad.Robot.Direction;
import edu.wm.cs.cs301.soleonv.falstad.Robot.Turn;
import edu.wm.cs.cs301.soleonv.ui.PlayActivity;

/**
 * CRC 
 * Responsibilities 
 * - implements the RobotDriver and drives the robot towards the exit by following a wall on it's side
 * Collaborators
 * -operates on top of the maze
 * - implements the RobotDriver
 * - receives  information from robot class 
 * PseudoCode
 * 1) move forward until you hit a wall 
 * 2) rotate to the right 
 * 3) step forward and check if there is a wall to the left and/or forward 
 * 4) if there is left wall and no forward wall then step forward 
 * 5) if there is no left wall then rotate left and step forward
 * 6) if there is left wall and a forward wall then rotate right and step forward 
 * @author solivialeonvitervo
 *
 */

public class WallFollower implements RobotDriver{
	
	private Robot robot;
	private MazeController maze;
	private Distance distance;
	private int width;
	private int height;
	private CardinalDirection currentDirection;
	public PlayActivity play;

	public WallFollower(){
		//currentMaze.showMaze = true;
		//currentMaze.showSolution = true; 
		//currentMaze.mapMode = true; 
		
	}


	@Override
	public void setRobot(Robot r){
		robot = r;
		((BasicRobot) robot).getMaze();
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
		
		//moves until it reaches a wall 
		while (robot.distanceToObstacle(Direction.FORWARD) != 0) {
			robot.move(1, false);
		}
		
		// checks if it reaches exit and is facing exit
		if (robot.isAtExit() && robot.canSeeExit(Direction.FORWARD)) 
			return true; 
		
		// turns and begins following the wall it has reached. It follows it on its left. 
		robot.rotate(Turn.RIGHT);
		
		//while robot has not reached exit 
		while (!(robot.isAtExit() && robot.canSeeExit(Direction.FORWARD))){
			
			//if there is left wall and no forward wall then step forward 
			if ( (robot.distanceToObstacle(Direction.LEFT) == 0)  && (robot.distanceToObstacle(Direction.FORWARD) != 0 ) ) {
				robot.move(1, false);
			}
			
			//if there is no left wall then rotate left and step forward
			if (robot.distanceToObstacle(Direction.LEFT) != 0){
				robot.rotate(Turn.LEFT);
				robot.move(1, false);
			}
			
			
			//if there is left wall and a forward wall then rotate right and step forward 
			if ( (robot.distanceToObstacle(Direction.LEFT) == 0)  && (robot.distanceToObstacle(Direction.FORWARD) == 0 ) ){
				robot.rotate(Turn.RIGHT);
				robot.move(1, false);
			}
			
			//one must consider the case of rooms but this algorithms does not :/ 
		
		}
		
	  // checks if it reaches exit and is facing exit
		if (robot.isAtExit() && robot.canSeeExit(Direction.FORWARD)) 
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


	public MazeController getMaze() {
		return maze;
	}


	public void setMaze(MazeController maze) {
		this.maze = maze;
	}


	public CardinalDirection getCurrentDirection() {
		return currentDirection;
	}


	public void setCurrentDirection(CardinalDirection currentDirection) {
		this.currentDirection = currentDirection;
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
