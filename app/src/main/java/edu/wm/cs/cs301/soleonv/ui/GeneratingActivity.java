package edu.wm.cs.cs301.soleonv.ui;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;

import edu.wm.cs.cs301.soleonv.falstad.MazeController;
import edu.wm.cs.cs301.soleonv.falstad.MazeDataHolder;
import edu.wm.cs.cs301.soleonv.falstad.MazeFileReader;
import edu.wm.cs.cs301.soleonv.falstad.MazeFileWriter;
import edu.wm.cs.cs301.soleonv.generation.MazeBuilder;
import edu.wm.cs.cs301.soleonv.generation.MazeContainer;
import edu.wm.cs.cs301.soleonv.generation.MazeFactory;
import edu.wm.cs.cs301.soleonv.generation.Order;
import edu.wm.cs.cs301.soleonv.generation.StubOrder;
import edu.wm.cs.cs301.soleonv.generation.MazeConfiguration;
import edu.wm.cs.cs301.soleonv.generation.Order.Builder;
import edu.wm.ed.cs301.soleonv.R;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;

/**
 * Created by solivialeonvitervo on 11/14/17.
 * Intermediate activity that shows progress of the maze creation through a progress bar.
 * Once the generation algorithm is finished the screen switches to State Play.
 * Pressing the back button will stop the maze generation and return to State Title
 *
 */

public class GeneratingActivity extends AppCompatActivity {

    private static final String LOG_TAG = "GeneratingActivity";
    private ProgressBar mazeProgress;
    private TextView loadingText;
    public boolean loadMaze;
    public int skillLevel;
    public String builder;
    public String driver;
    private int progressStatus = 0;
    private Handler progressHandler = new Handler();
    private MazeFactory mazeFactory = new MazeFactory();
    public MazeController mazeController;
    private MazeFileWriter writer;
    private MazeConfiguration container;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generating);


        //initialize objects used
        mazeProgress = (ProgressBar) findViewById(R.id.maze_progressBar);
        mazeProgress.setProgress(0);


        //Get the intent that start this activity and the information it passes

        Intent prevIntent = getIntent();
        loadMaze = prevIntent.getBooleanExtra("loadMaze", false);
        driver = prevIntent.getStringExtra("driver");
        skillLevel = prevIntent.getIntExtra("skillLevel", 0);
        builder = prevIntent.getStringExtra( "builder");


        File f = new File(getApplicationContext().getFilesDir() + "/maze" + skillLevel + ".xml");

          //figure out whether need to load xml or generate new maze
        if (loadMaze == true && skillLevel < 3 && f.exists()){


            mazeController = new MazeController();
            container = (MazeConfiguration)mazeController.getMazeConfiguration();
            mazeController.setFilename(getApplicationContext().getFilesDir() + "/maze" + skillLevel + ".xml");
            mazeController.init();
            if ( skillLevel <=3) {
                Log.v(LOG_TAG, "Generating maze from file");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        writer = new MazeFileWriter();
                        writer.store(getApplicationContext().getFilesDir()+ "/maze" + skillLevel + ".xml", container.getWidth(), container.getHeight(), 0,0, container.getRootnode(), container.getMazecells(),container.getMazedists().getDists(), container.getStartingPosition()[0], container.getStartingPosition()[1]);

                    }
                }).start();
            }
        }

           //generate maze using the given builder and skillLevel
        else  {


            //build maze
            mazeController = new MazeController();
            mazeController.setBuilder(builder);
            mazeController.setSkillLevel(skillLevel);
            mazeController.setGeneratingActivity(this);
            mazeFactory = new MazeFactory();
            mazeFactory.setGenerating(this);
            mazeFactory.order(mazeController);

            MazeDataHolder.setMazeController(mazeController);
        }

        loadingText = (TextView) findViewById(R.id.loadingtext);

        //toast message for user
        Context generationContext = getApplicationContext();
        CharSequence generationText = "Maze is being generated with " + builder + " and " + driver;
        int generationDuration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(generationContext, generationText, generationDuration);
        toast.show();


    }

    /**
     * Handles the population of the actions in the action bar.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Handles usage of the actions in the action bar.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        finish();
        return super.onOptionsItemSelected(item);
    }



    /**
     * An Intent to move on to PlayActivity and  pass the information needed to play
     *
     */
    public void toPlayScreen(){
        Log.v( LOG_TAG, "Moving to play screen");
        Intent intent = new Intent(GeneratingActivity.this, PlayActivity.class);
        intent.putExtra("driver", driver);

        startActivity(intent);
        finish();
    }


    /**
     * go back to title screen when the back button is pressed
     */
    @Override
    public void onBackPressed(){
        Log.v(LOG_TAG, "Returning to the title screen");
        Intent intent = new Intent(this, AMazeActivity.class);
        startActivity(intent);
        finish();
    }

    public void updateProgress( int percent){
        mazeProgress.setProgress(percent);
    }

    public int getProgress(){
        return mazeProgress.getProgress();
    }


 }

