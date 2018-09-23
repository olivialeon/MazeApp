package edu.wm.cs.cs301.soleonv.generation;

import edu.wm.cs.cs301.soleonv.generation.Order;

//helper class to testing, stub object gets the values we need for testing

public class StubOrder implements Order{
	
	private int skillLevel;
	private Builder builder;
	private boolean perfect; 
	private MazeConfiguration mazeConfiguration;
	int percentage;
	
	public StubOrder( int skillLevel, Builder builder, boolean perfect){
		this.skillLevel = skillLevel;
		this.builder = builder;
		this.perfect = perfect;
		
	}
	
	@Override
	public int getSkillLevel(){
	return skillLevel;
	}
	
	@Override
	public Builder getBuilder(){
	return builder;
	}
	
	@Override
	public boolean isPerfect() {
		return perfect;
	}
	
	@Override
	public void deliver(MazeConfiguration mazeConfig){
		this.mazeConfiguration = mazeConfig;
	}
	
	@Override
	public void updateProgress(int percentage){
		this.percentage = percentage; 
		
	}
	
	public MazeConfiguration getConfiguration(){
		return mazeConfiguration;
	}
	

}
