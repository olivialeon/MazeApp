package edu.wm.cs.cs301.soleonv.falstad;

import edu.wm.cs.cs301.soleonv.generation.MazeConfiguration;

/**
 * class to store static variables that are shared across class
 * Created by solivialeonvitervo on 11/29/17.
 */

public class MazeDataHolder {

    public static String mazeGenerationBuilder;
    public static int skillLevel;
    public static MazeController mazeController;

    public static String getMazeGenerationBuilder(){
        return mazeGenerationBuilder;
    }

    public static void setMazeGenerationBuilder( String builder){
        mazeGenerationBuilder = builder;
    }


    public static int getSkillLevel(){
        return skillLevel;

    }

    public static void setSkillLevel(int sl){
        skillLevel = sl;
    }

    public static void setMazeController(MazeController mc){
        mazeController = mc;
    }

    public static MazeController getMazeController(){
        return mazeController;
    }
}