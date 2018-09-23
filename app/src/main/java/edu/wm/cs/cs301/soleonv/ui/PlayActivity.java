package edu.wm.cs.cs301.soleonv.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;


import edu.wm.cs.cs301.soleonv.falstad.BasicRobot;
import edu.wm.cs.cs301.soleonv.falstad.Constants;
import edu.wm.cs.cs301.soleonv.falstad.FirstPersonDrawer;
import edu.wm.cs.cs301.soleonv.falstad.ManualDriver;
import edu.wm.cs.cs301.soleonv.falstad.MapDrawer;
import edu.wm.cs.cs301.soleonv.falstad.MazeController;
import edu.wm.cs.cs301.soleonv.falstad.MazeDataHolder;
import edu.wm.cs.cs301.soleonv.falstad.NewMazePanel;
import edu.wm.cs.cs301.soleonv.falstad.Pledge;
import edu.wm.cs.cs301.soleonv.falstad.RangeSet;
import edu.wm.cs.cs301.soleonv.falstad.WallFollower;
import edu.wm.cs.cs301.soleonv.falstad.Wizard;
import edu.wm.ed.cs301.soleonv.R;

/**
 * Created by solivialeonvitervo on 11/15/17.
 * Displays the maze and lets the user either watch a robot exploring the maze or allows the
 * user to manually navigate the robot through the maze.
 * If  in manual exploration mode: screen provides navigation buttons (up, down, left,
 * right) for user.
 * If in robot exploration mode: screen provides a start/pause button for user to start the
 * exploration and to pause the animation.
 *
 * Displays the remaining energy. Allows user to show the whole maze from top or not,
 * show the solution in the maze or not, and  show the currently visible walls or not.
 *
 *  If the robot stops (no energy, at exit) the screen switches to the finish screen.
 *  If user press back button, app returns to State Title to allow the user to choose different
 parameter settings and restart.
 */

public class PlayActivity extends AppCompatActivity {

    private static final String LOG_TAG = "PlayActivity";
    private ProgressBar batteryLevelBar;
    private ToggleButton showMaze, showWall, showSolution, pauseButton;
    private NewMazePanel newMazePanel;
    private boolean exitFound = true;
    boolean paused = false;
    boolean cancel = false;
    public Intent prevIntent;
    public String driver = "Manual";
    public ManualDriver manualDriver;
    public WallFollower wallFollower;
    public Pledge pledge;
    public Wizard wizard;
    public MazeController mazeController;
    public MapDrawer mapDrawer;
    public BasicRobot robot;
    public RelativeLayout mazeLayout;
    public RangeSet rangeSet;
    public Handler robotHandler;
    public MediaPlayer mediaPlayer;

    Button upButton, downButton, leftButton, rightButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.un_poco_loco);
        mediaPlayer.start();


        batteryLevelBar = (ProgressBar) findViewById(R.id.energyLevelBar);
        batteryLevelBar.setMax(3000);
        batteryLevelBar.setProgress(3000);

        //toggle buttons
        showMaze = ( ToggleButton) findViewById(R.id.showMaze);
        showWall = ( ToggleButton) findViewById(R.id.showWall);
        showSolution = ( ToggleButton) findViewById(R.id.showSolution);

        //arrows
        upButton = (Button) findViewById(R.id.up);
        downButton = (Button) findViewById(R.id.down);
        leftButton = (Button) findViewById(R.id.left);
        rightButton = (Button) findViewById(R.id.right);
        pauseButton = (ToggleButton) findViewById(R.id.pausebutton);
        showArrows();


        prevIntent = getIntent();
        driver = prevIntent.getStringExtra("driver");

        //get the maze from maze generating activity
        mazeController = MazeDataHolder.getMazeController();

        //Views
        mazeLayout = (RelativeLayout) findViewById(R.id.mazeLayout);
        newMazePanel = new NewMazePanel(this);
        mazeController.setPlayingActivity(this);
        mazeController.setPanel(newMazePanel);
        mazeController.setDriver(driver);


        robot = new BasicRobot();
        robot.setMaze(mazeController);
        mazeController.notifyViewerRedraw();
        robotHandler = new Handler();

        //sets the robots
        if (driver.equals("Manual")){
            Log.v(LOG_TAG, "Maze is using Manual Driver");
            manualDriver = new ManualDriver();
            manualDriver.setRobot(robot);
            manualDriver.setDistance(mazeController.getMazeConfiguration().getMazedists());
            manualDriver.setPlayingActivity(this);
        }
        else if (driver.equals("Wall Follower")){
            Log.v(LOG_TAG, "Maze is using Wall Follower Driver");
            wallFollower = new WallFollower();
            wallFollower.setRobot(robot);
            wallFollower.setMaze(mazeController);
            wallFollower.setDistance(mazeController.getMazeConfiguration().getMazedists());
            wallFollower.setPlayingActivity(this);


        }
        else if (driver.equals("Pledge")) {
            Log.v(LOG_TAG, "Maze is using Pledge Driver");
            pledge = new Pledge();
            pledge.setRobot(robot);
            pledge.setMaze(mazeController);
            pledge.setDistance(mazeController.getMazeConfiguration().getMazedists());
            pledge.setPlayingActivity(this);

        }

        else if (driver.equals("Wizard")) {
            Log.v(LOG_TAG, "Maze is using Wizard Driver");
            wizard = new Wizard();
            wizard.setRobot(robot);
            wizard.setMaze(mazeController);
            wizard.setDistance(mazeController.getMazeConfiguration().getMazedists());
            wizard.setPlayingActivity(this);
        }

        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** moves up when the button has been pushed.
                 **/
                mazeController.keyDown('8');


            }
        });

        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** moves down when the button has been pushed.
                 **/
                mazeController.keyDown('2');
            }
        });

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** moves left when the button has been pushed.
                 **/

                mazeController.keyDown('4');
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** moves right when the button has been pushed.
                 **/
                mazeController.keyDown('6');

            }
        });

        mapDrawer = new MapDrawer(Constants.VIEW_WIDTH, Constants.VIEW_WIDTH, Constants.MAP_UNIT,Constants.STEP_SIZE, mazeController.getSeenCells(), 13,mazeController);
        rangeSet = new RangeSet();

        //setup toogle buttons

        showWall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mazeController.mapMode = true;
                    mapDrawer.redraw(newMazePanel, Constants.StateGUI.STATE_PLAY, mazeController.px, mazeController.py, mazeController.dx, mazeController.dy, 1, 2, rangeSet,0);
                    //mazeController.keyDown('1');
                }
                else{

                }
            }
        });

        showMaze.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    mazeController.mapMode = true;
                    mazeController.showMaze = true;
                    mazeController.showSolution = false;
                    mapDrawer.redraw(newMazePanel, Constants.StateGUI.STATE_PLAY, mazeController.px, mazeController.py, mazeController.dx, mazeController.dy, 1, 2, rangeSet,0);
                    //mazeController.keyDown('3');
                }
                else{

                }
            }
        });

        showSolution.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mazeController.mapMode = true;
                    mazeController.showMaze = true;
                    mazeController.showSolution = true;
                    //mazeController.keyDown('5');
                    mapDrawer.redraw(newMazePanel, Constants.StateGUI.STATE_PLAY, mazeController.px, mazeController.py, mazeController.dx, mazeController.dy, 1, 2, rangeSet,0);

                }
                else{

                }
            }
        });


        pauseButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Context pauseContext = getApplicationContext();
                CharSequence pauseText = "Button has been pushed.";
                int pauseDuration = Toast.LENGTH_SHORT;

                Toast pausetoast = Toast.makeText(pauseContext, pauseText, pauseDuration);
                pausetoast.show();
                if (isChecked){
                    paused = false;
                }
                else{
                    paused = true;
                }
                    //play button pushed so run the driver
                    Thread t = new Thread(new Runnable(){
                        @Override
                        public void run(){
                            while (!mazeController.isOutside(mazeController.px, mazeController.py) && !cancel){
                                try {
                                    if (!paused) {

                                        if (driver.equals("Wall Follower")) {
                                            wallFollower.drive2Exit();
                                        } else if (driver.equals("Pledge"))
                                            pledge.drive2Exit();
                                        else if (driver.equals("Wizard"))
                                            wizard.drive2Exit();
                                    }

                                } catch (Exception e) {
                                    resetMap();
                                    toFinishPlay();

                                }
                                newMazePanel.invalidate();
                            }
                            robotHandler.postDelayed(this, 15);
                        }

                    });
                    t.start();

            }
        });

    }


    /**
     * go back to title screen when the back button is pressed
     */
    @Override
    public void onBackPressed(){
        Log.v(LOG_TAG, "Returning to the title screen");
        Intent intent = new Intent(this, AMazeActivity.class );
        cancel = true;
        startActivity(intent);
        mediaPlayer.stop();
        finish();
    }


    /**
     * if wins goes to finish screen
     */
    public void toFinishPlay(){
        Log.v(LOG_TAG, "Moving to the finish screen");

        Intent intent = new Intent(this, FinishActivity.class);
        intent.putExtra("exitFound",exitFound);
        intent.putExtra("energyused", 3000 - robot.getBatteryLevel());
        intent.putExtra("pathlength", robot.getOdometerReading());
        startActivity(intent);
        mediaPlayer.stop();
        finish();

    }

    /**
     * show arrow keys if in manual mode, pause button if automatic
     */

    public void showArrows(){

        Bundle extras = getIntent().getExtras();{
            if (extras != null){
                driver = extras.getString("driver");
            }
        }

        if (driver.equals("Manual")){
            //show arrows, do not show pause button
            Log.v(LOG_TAG, "Showing the manual keys");
            upButton.setVisibility(View.VISIBLE);
            downButton.setVisibility(View.VISIBLE);
            leftButton.setVisibility(View.VISIBLE);
            rightButton.setVisibility(View.VISIBLE);
            pauseButton.setVisibility(View.INVISIBLE);

        }
        else{
            Log.v(LOG_TAG, "Showing the pause/play button");
            //do not show arrows, show pause button
            upButton.setVisibility(View.INVISIBLE);
            downButton.setVisibility(View.INVISIBLE);
            leftButton.setVisibility(View.INVISIBLE);
            rightButton.setVisibility(View.INVISIBLE);
            pauseButton.setVisibility(View.VISIBLE);

        }
    }

    public void resetMap(){
        final Resources resource = this.getResources();
        if(this.mazeController.getDriver() != null){
            this.updateBattery(((int) this.mazeController.robot.getBatteryLevel()));
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = newMazePanel.getBitmap();
                Drawable background = new BitmapDrawable(resource, bitmap);
                mazeLayout.setBackground(background);
            }
        });

    }


    public void updateBattery( int a){
        if(a<0){
            this.batteryLevelBar.setProgress(0);
        }
        this.batteryLevelBar.setProgress(a);
    }




}
