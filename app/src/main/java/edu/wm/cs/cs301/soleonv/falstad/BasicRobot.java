package edu.wm.cs.cs301.soleonv.falstad;

import edu.wm.cs.cs301.soleonv.falstad.Constants.StateGUI;
import edu.wm.cs.cs301.soleonv.generation.CardinalDirection;
import edu.wm.cs.cs301.soleonv.generation.Cells;
import edu.wm.cs.cs301.soleonv.generation.MazeFactory;
import edu.wm.cs.cs301.soleonv.generation.MazeConfiguration;

/**
 * CRC Card for BasicRobot
- Responsibilities 
    - keep track of battery level , keep track of current position, whether it has stopped
    - provides methods to move robot  
- Collaboration 
    - this class works with Maze and ManuelDriver
        - basic robot operates on top of maze and operated by the robot driver 
 * @author solivialeonvitervo
 *
 */

public class BasicRobot implements Robot {
	
	public static float batteryLevel = 3000;
	public MazeController currentMaze; 
	private static int odometerCount;
	private boolean stopped = false;
	
	public BasicRobot() {
	
		
	}
		
		@Override
		public void rotate(Turn turn){
			
			hasStopped();
			
			if ( batteryLevel >= 3){
			
				if (turn == Turn.LEFT) {
					this.currentMaze.rotate(-1);

					this.batteryLevel -= 3; 
				}

				if (turn == Turn.RIGHT){
					this.currentMaze.rotate(1);

					this.batteryLevel -= 3;
				}

				if (turn == Turn.AROUND) {
					this.currentMaze.rotate(-1);
					this.currentMaze.rotate(1);
					this.batteryLevel -= 6;
				}
		 }
			else{
				stopped = true;
				//printExitScreen();
				
				
			}
		}
		
		@Override
		public void move(int distance, boolean manual){
			
			//print the exist screen if any of the conditions in has stopped at met
			hasStopped();
			
			if (batteryLevel >= 5){
				for (int i= 1; i <=  distance; i++) {
					currentMaze.walk(1);
					batteryLevel -= 5;
					odometerCount++;
				}
		 }
			else{
				stopped = true;
				//printExitScreen();
			}
	 }
		
		@Override
		public int[] getCurrentPosition() throws Exception {
			int [] position;
			position = currentMaze.getCurrentPosition(); 
			
			if ( (position[0] > currentMaze.getMazeConfiguration().getWidth()  ||position[0] < 0)  || 
					( position[1] > currentMaze.getMazeConfiguration().getHeight() || position[1] < 0))
				throw new IndexOutOfBoundsException();
			else return position;
			
		}
		
		@Override
		public void setMaze(MazeController maze){
			if (maze!= null)
			this.currentMaze = maze; 
		}
		
		@Override
		public boolean isAtExit() {
			int [] position;
			position = currentMaze.getCurrentPosition();
			
			int [] exitPosition;
			exitPosition = currentMaze.getMazeConfiguration().getMazedists().getExitPosition();
			
			//if (currentMaze.getMazeConfiguration().getDistanceToExit(position[0], position[1]) == 1)
				//return true;
			if (position[0] == exitPosition[0] && exitPosition[1]== exitPosition[1])
				return true;
			else return false;
			
		}
		
		@Override
		public boolean canSeeExit(Direction direction) throws UnsupportedOperationException{
			if (!hasDistanceSensor(direction))
				throw new UnsupportedOperationException();
			
			if (distanceToObstacle(direction) == Integer.MAX_VALUE){
				return true;
			}
			return false;
			
		}
		
		@Override
		public boolean isInsideRoom() throws UnsupportedOperationException{
			int [] position;
			position = currentMaze.getCurrentPosition();
			
			if ( !hasRoomSensor())
				throw new UnsupportedOperationException();
				
			else if  ( ( currentMaze.getMazeConfiguration().hasWall(position[0], position[1], CardinalDirection.East ) )  && 
					( currentMaze.getMazeConfiguration().hasWall(position[0], position[1], CardinalDirection.West )) &&
					( currentMaze.getMazeConfiguration().hasWall(position[0], position[1], CardinalDirection.South )) &&
					( currentMaze.getMazeConfiguration().hasWall(position[0], position[1], CardinalDirection.North ) ))
					return true;
			else
				return false;
		}
		
		@Override
		public boolean hasRoomSensor(){
				return true; 
		}

		@Override
		public CardinalDirection getCurrentDirection(){
			return currentMaze.getCurrentDirection();
			
		}
		
		@Override 
		public float getBatteryLevel(){
			return this.batteryLevel;
				
		}
		
		public MazeController getMaze(){
			return this.currentMaze;
		}
		@Override 
		public void setBatteryLevel(float level) {
			this.batteryLevel = level; 
		}
		
		@Override
		public int getOdometerReading(){
			return this.odometerCount;
			
		}
		
		@Override
		public void resetOdometer(){
			this.odometerCount = 0;
		}
		
		@Override
		public float getEnergyForFullRotation(){
			return 12;
		}
		
		@Override
		public float getEnergyForStepForward(){
			return 5;
		}
		
		@Override
		public boolean hasStopped(){
			int [] position;
			position = currentMaze.getCurrentPosition();
			
			if (batteryLevel <= 0){
		
				stopped = true;
				printExitScreen();
				return stopped;
				
			}
			if (currentMaze.getMazeConfiguration().hasWall(position[0], position[1], currentMaze.getCurrentDirection())){
				stopped = true;
				return stopped;
		}
			else if (isAtExit()){
				printExitScreen();
			}
		stopped = false;
		return stopped;
		}
		
		@Override
		public int distanceToObstacle(Direction direction) throws UnsupportedOperationException{
			
			CardinalDirection facing = currentMaze.getCurrentDirection();
			CardinalDirection pointSensor= null;
			int distance = 0;
			int[] position;
			position = currentMaze.getCurrentPosition();
			
			if (!hasDistanceSensor(direction))
				throw new UnsupportedOperationException();

			if(facing == CardinalDirection.North && direction == Direction.LEFT)
				pointSensor = CardinalDirection.West;

			if(facing == CardinalDirection.North && direction == Direction.FORWARD)
				pointSensor = CardinalDirection.North;

			if(facing == CardinalDirection.North && direction == Direction.RIGHT)
				pointSensor = CardinalDirection.East;

			if(facing == CardinalDirection.North && direction == Direction.BACKWARD)
				pointSensor = CardinalDirection.South;

			if(facing == CardinalDirection.East && direction == Direction.LEFT)
				pointSensor = CardinalDirection.North;

			if(facing == CardinalDirection.East && direction == Direction.FORWARD)
				pointSensor = CardinalDirection.East;

			if(facing == CardinalDirection.East && direction == Direction.RIGHT)
				pointSensor = CardinalDirection.South;

			if(facing == CardinalDirection.East && direction == Direction.BACKWARD)
				pointSensor = CardinalDirection.West;

			if(facing == CardinalDirection.South && direction == Direction.LEFT)
				pointSensor = CardinalDirection.East;

			if(facing == CardinalDirection.South && direction == Direction.FORWARD)
				pointSensor = CardinalDirection.South;

			if(facing == CardinalDirection.South && direction == Direction.RIGHT)
				pointSensor = CardinalDirection.West;

			if(facing == CardinalDirection.South && direction == Direction.BACKWARD)
				pointSensor = CardinalDirection.North;

			if(facing == CardinalDirection.West && direction == Direction.LEFT)
				pointSensor = CardinalDirection.South;

			if(facing == CardinalDirection.West && direction == Direction.FORWARD)
				pointSensor = CardinalDirection.West;

			if(facing == CardinalDirection.West && direction == Direction.RIGHT)
				pointSensor = CardinalDirection.North;
			
			if (facing == CardinalDirection.West && direction == Direction.BACKWARD)
				pointSensor = CardinalDirection.East;
			
			if(currentMaze.getMazeConfiguration().hasWall(position[0], position[1], pointSensor)){
				this.batteryLevel -= 1;
				return distance;
			}
			
		  boolean wall = currentMaze.getMazeConfiguration().getMazecells().hasNoWall(position[0], position[1], pointSensor); 
			
			if (pointSensor == CardinalDirection.East){
				for (int i = position[0]; wall; i++){
					if (!currentMaze.getMazeConfiguration().isValidPosition(i, position[1]) ){
						this.batteryLevel -= 1;
						return Integer.MAX_VALUE;
					}
					wall = currentMaze.getMazeConfiguration().getMazecells().hasNoWall(i, position[1], pointSensor);
					distance ++;
				}
			} 
			
			if (pointSensor == CardinalDirection.South){
				for (int i = position[1]; wall; i--){
					if (!currentMaze.getMazeConfiguration().isValidPosition(position[0], i) ){
						this.batteryLevel -= 1;
						return Integer.MAX_VALUE;
					}
					wall = currentMaze.getMazeConfiguration().getMazecells().hasNoWall(position[0], i, pointSensor);
					distance ++;
				}
			}
			
			if (pointSensor == CardinalDirection.West){
				for (int i = position[0]; wall; i--){
					if (!currentMaze.getMazeConfiguration().isValidPosition(i, position[1]) ){
						this.batteryLevel -= 1;
						return Integer.MAX_VALUE;
					}
					wall = currentMaze.getMazeConfiguration().getMazecells().hasNoWall(i, position[1], pointSensor);
					distance ++;
				}
			}
			if (pointSensor == CardinalDirection.North){
				
				for (int i = position[1]; wall; i++){
					if (!currentMaze.getMazeConfiguration().isValidPosition(position[0],i) ){
						this.batteryLevel -= 1;
						return Integer.MAX_VALUE;
					}
					distance ++;
					wall = currentMaze.getMazeConfiguration().getMazecells().hasNoWall(position[0],i, pointSensor);
				}
			}
			
			this.batteryLevel -= 1; 
			return distance;
	 }
		
		@Override 
		public boolean hasDistanceSensor(Direction direction){
			return true;
			
		}
		
		public static int getStaticPathLength(){
			return odometerCount;
		}
		
		public static float getStaticBatteryLevel(){
			return batteryLevel;
		}
		
		public void printExitScreen(){
			//currentMaze.switchToFinishScreen();
			
		}
		
	

 }
