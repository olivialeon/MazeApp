package edu.wm.cs.cs301.soleonv.falstad;

import edu.wm.cs.cs301.soleonv.generation.CardinalDirection;
import edu.wm.cs.cs301.soleonv.generation.Distance;
import edu.wm.cs.cs301.soleonv.falstad.Robot.Direction;
import edu.wm.cs.cs301.soleonv.falstad.Robot.Turn;
import edu.wm.cs.cs301.soleonv.ui.PlayActivity;

/**
 * CRC 
 * Responsibilities 
 * - implements the RobotDriver and drives robot to the exist by checking the relative distance to exist of neighbor cells 
 * - moves robot to the cell that has shorter distance to the exist 
 * Collaborators 
 * - operates on top of maze
 * - implements RobotDriver
 * - receives  information from robot class 
 * Pseudocode
 * 1) assign current distance to exit to variable 
 * 2) check neighbors' distance to exit and compare to current distance
 * 3) If cell in certain direction has shorter distance then move robot in that direction 
 * 4) repeat until robot is at exit 
 * @author solivialeonvitervo
 */

public class Wizard implements RobotDriver {
	
	private Robot robot;
	private Distance distance;
	private int width;
	private int height;
	protected MazeController currentMaze;
	public  PlayActivity play;
	
	public Wizard(){
//		currentMaze.showMaze = true;
//		currentMaze.showSolution = true; 
//		currentMaze.mapMode = true; 
		
	}

	@Override
	public void setRobot(Robot r){
		robot = r;
		currentMaze = ((BasicRobot) robot).getMaze();
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
			this.distance = distance;
	
	}
	
	@Override
	public boolean drive2Exit() throws Exception {
		if (robot.getBatteryLevel() == 0)
			throw new Exception();
		
		//gets the distance at the current position in int form to compare by adding or subtracting 
		int [] position = robot.getCurrentPosition();
		int currDistance = distance.getDistance(position[0], position[1]);
		
		CardinalDirection currDir = robot.getCurrentDirection();
		CardinalDirection goalDir = null;

		int minDis = currDistance;
		
		while(! (robot.isAtExit() && robot.canSeeExit(Direction.FORWARD)) ){
			
			position = robot.getCurrentPosition();
			currDistance = distance.getDistance(position[0], position[1]);
			
			if(!currentMaze.getMazeConfiguration().hasWall(position[0], position[1], CardinalDirection.North)){
				if (minDis > distance.getDistance(position[0], position[1]+1)){
					minDis = distance.getDistance(position[0], position[1]+1);
					goalDir = CardinalDirection.North;

				}
			}
			if(!this.currentMaze.getMazeConfiguration().hasWall(position[0], position[1], CardinalDirection.South)){
				if (minDis > distance.getDistance(position[0], position[1]-1)){
					minDis = distance.getDistance(position[0], position[1]-1);
					goalDir = CardinalDirection.South;
				}
			}

			if(!this.currentMaze.getMazeConfiguration().hasWall(position[0], position[1], CardinalDirection.East)){
				if (minDis> distance.getDistance(position[0]+1, position[1])){
					minDis = distance.getDistance(position[0]+1, position[1]);
					goalDir = CardinalDirection.East;
				}
			}

			if(!this.currentMaze.getMazeConfiguration().hasWall(position[0], position[1], CardinalDirection.West)){
				if (minDis > distance.getDistance(position[0]-1, position[1])){
					minDis = distance.getDistance(position[0]-1, position[1]);
					goalDir = CardinalDirection.West;
				}
			}
			
			// robot is facing north 
			if (currDir == CardinalDirection.North && goalDir == CardinalDirection.South)
				robot.rotate(Turn.AROUND);
			if (currDir == CardinalDirection.North && goalDir == CardinalDirection.East)
				robot.rotate(Turn.RIGHT);
			if (currDir == CardinalDirection.North && goalDir == CardinalDirection.West)
				robot.rotate(Turn.LEFT);

			//robot is facing south 
			if (currDir == CardinalDirection.South && goalDir == CardinalDirection.North)
				robot.rotate(Turn.AROUND);
			if (currDir == CardinalDirection.South && goalDir == CardinalDirection.East)
				robot.rotate(Turn.LEFT);
			if (currDir == CardinalDirection.South && goalDir == CardinalDirection.West)
				robot.rotate(Turn.RIGHT);

			//robot is facing east
			if (currDir == CardinalDirection.East && goalDir == CardinalDirection.North)
				robot.rotate(Turn.LEFT);
			if (currDir == CardinalDirection.East && goalDir == CardinalDirection.West)
				robot.rotate(Turn.AROUND);
			if (currDir == CardinalDirection.East && goalDir == CardinalDirection.South)
				robot.rotate(Turn.RIGHT);

			//robot is facing west
			if (currDir == CardinalDirection.West && goalDir == CardinalDirection.North)
				robot.rotate(Turn.RIGHT);
			if (currDir == CardinalDirection.West && goalDir == CardinalDirection.East)
				robot.rotate(Turn.AROUND);
			if (currDir == CardinalDirection.West && goalDir == CardinalDirection.South)
				robot.rotate(Turn.LEFT);

			robot.move(1, false);
			
			
		// checks if it reaches exit and is facing exit
			if (robot.isAtExit() && robot.canSeeExit(Direction.FORWARD) ) 
				return true; 
		}
		
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

	public MazeController getCurrentMaze() {
		return currentMaze;
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
