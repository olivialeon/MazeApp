package edu.wm.cs.cs301.soleonv.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import edu.wm.ed.cs301.soleonv.R;

/**
 * Created by solivialeonvitervo on 11/15/17.
 *
 * The ending screen for the game and informs  the user what happened and how to restart the game.
 * Shows the energy consumption and length of path.
 *
 * If user press back button app return to the State title.
 */

public class FinishActivity extends AppCompatActivity{

    private static final String LOG_TAG = "FinishActivity";
    private int pathLength;
    private float energyConsumption;
    private Intent prevIntent;
    private boolean exitFound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        //initialize objects

        prevIntent = getIntent();

        // displays the correct end message for user
        setFinishMessage();
        setBatteryLevelMessage();
        setPathLengthMessage();

    }


    /**
     * If users presses back button returns to the title screen
     */

    @Override
    public void onBackPressed(){
        Log.v(LOG_TAG, "Returning to the title screen");
        Intent intent = new Intent(this, AMazeActivity.class );
        startActivity(intent);
        finish();
    }


    /**
     * if users presses replay returns to the title screen to play maze again
     */
    public void toPlayAgain(View view){
        Log.v(LOG_TAG, "Play again button clicked");
        Intent intent = new Intent(this, AMazeActivity.class );
        startActivity(intent);
    }
    /**
     * method to change the message depending on whether the user won game or
     * used up all energy
     */
    public void setFinishMessage(){

        exitFound = prevIntent.getBooleanExtra("exitFound", true);
        TextView finishText = (TextView) findViewById(R.id.finishText);
        Log.v(LOG_TAG, "Exit found: " + exitFound);

        if (exitFound){
            finishText.setText("Congratulations! You won! You have found your familia!");
        }

        else{
            finishText.setText("You failed! Used up all energy.");
        }

    }

    /**
     * method to display the robot's battery level
     */
    public void setBatteryLevelMessage(){
        energyConsumption = prevIntent.getFloatExtra("energyused",0);

        Log.v(LOG_TAG, "Energy consumption: " + energyConsumption);
        TextView energyText = (TextView) findViewById(R.id.energyText);
        energyText.setText("Energy Consumption:" + energyConsumption );

    }


    /**
     * method to display the robot's path length
     */

    public void setPathLengthMessage(){
        pathLength = prevIntent.getIntExtra("pathlength", 0);
        Log.v(LOG_TAG, "pathlength: " + pathLength);
        TextView pathLengthText = (TextView) findViewById(R.id.pathLengthText);
        pathLengthText.setText("Path length: " + pathLength);

    }


}
