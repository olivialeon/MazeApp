package edu.wm.cs.cs301.soleonv.generation;



/**
 * @author solivialeonvitervo
 * This class has the responsibility to create a maze of given dimensions (width, height) 
 * together with a solution based on a distance matrix
 *
 */
public class MazeBuilderEller  extends MazeBuilder implements Runnable {
	/**
	 * Builds a new maze using Eller's algorithm 
	 */
	
	public MazeBuilderEller() {
		super();
		System.out.println("MazeBuilderPrim uses Eller's algorithm to generate maze.");
	}
	/**
	 * Builds a new maze deterministically using Eller's algorithm 
	 * @param deterministic if deterministic is true , then it will build the maze deterministically 
	 */
	public MazeBuilderEller(boolean deterministic) {
		super(deterministic);
		System.out.println("MazeBuilderPrim uses Eller's algorithm to generate maze.");
	}
	
	/**
	 * method to Override generatePathways 
	 * @return 
	 */
	@Override
	protected void generatePathways(){
		
		/**
		 * need cell set that creates and keeps track of the sets and their elements 
		 */
		int [][] cellSet = new int [width] [ height];
		int i;
		int row = 0;
		int col = 0;
		int randInt = 0;
		
		cells.initialize(); //creates maze with all walls up 
		
		
	//initialize each cell on the row to be its own set
		while ( row != height) {
			
			if ( row == 0) {
				//each cell in the first row initialized to be own set
				for ( i = 0; i< width-1; i++);
				cellSet[i][0] = i;	
			}
			
			//horizontal 
			while ( col != width) {
				
				//check for the last row of maze, if so break down all walls 
				if (row == ( height - 1)) {
					for ( i = 0; i < (width - 1); i++) {
						
						Wall wall = new Wall(i, row, CardinalDirection.East);
						
						if (cellSet[i][row] != cellSet[i+1][row])
							cells.deleteWall(wall);
				}
				
				if (col != (width -1)) {
					
					// if cells are not in the same set, randomly join by deleting wall 
					if  ( cellSet[col][row] != cellSet[col + 1][row] ){
						randInt = random.nextIntWithinInterval(0, 1);
						
						if ( randInt == 1) {
							
							Wall wall = new Wall(randInt, row, CardinalDirection.East);
							cells.deleteWall(wall);
							cellSet[col+1][row] = cellSet[col][row];
						}
						else col++;
					}
					else {
						col ++;
				}
		}
	}
				//one row finished
				
				
			//vertical 
			while ( row != width) {
				
				
				// iterate through each set and give each set at least one vertical connection
				
				//iterate through the elements in the set 
				// then randomly choose which bottom wall to delete 
				// check if adjacent cells are same set 
				
				int leftBoarder = 0;
				
				for ( row = 0; row < width-1; row++){
					for ( col = 0; col < height-1; col++){
						
						//in same set 
						if (cellSet[row][col] == cellSet[row+1][col]){
							
						}
						//reached a right board where you have the end of the set 
						else{
							randInt = random.nextIntWithinInterval(0, row);
							Wall wall = new Wall(randInt, row, CardinalDirection.South);
							cells.deleteWall(wall);
							
							//increment because you will start a new set
							leftBoarder = row + 1; 
							
							
							}
						}	
			
					}
				}
					 //flesh out the next row, assigning each remaining cell to its own set: 
					for ( i = 0; i < width ; i++){
						cellSet[i][0] = i;
							
						}
				
				}
		}
					
	}
} 
		
