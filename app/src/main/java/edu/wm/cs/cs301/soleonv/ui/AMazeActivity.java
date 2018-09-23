package edu.wm.cs.cs301.soleonv.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Spinner;
import android.content.Intent;
import android.widget.Toast;

import edu.wm.cs.cs301.soleonv.falstad.MazeDataHolder;
import edu.wm.ed.cs301.soleonv.R;


/**
 * This activity represents the title screen. It allows user to pick the skill level on the
 * seekBar, and spinners to pick drivers and maze configuration algorithm ( dfs, eller, prim).
 *
 * Once all options selected user presses start button to start generating the maze and move
 *to the generating activity.
 */

public class AMazeActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView textView, textView2;
    private Spinner builder_spinner, driver_spinner;
    private static final String LOG_TAG = "AMazeActivity";
    public String builder = "DFS";
    public String driver = "Manual";
    public int skillLevefl;
    public boolean loadMaze = false;
    private Button revisitButton, exploreButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amaze);


        //initialize the objects

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        builder_spinner = (Spinner) findViewById(R.id.mazespinner);
        driver_spinner = (Spinner) findViewById(R.id.driverspinner);
        revisitButton = (Button) findViewById(R.id.revisit);
        exploreButton = (Button) findViewById(R.id.explore);


        //set listeners
        setListeners();

    }

    public void setListeners(){

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressVal = 0;

            /**
             * When the SeekBar's progress changes, log it and print  saying that the progress changed.
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressVal = progress;
                textView.setText("Skill Level: " + progress + "/" + seekBar.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            /**
             * When the user stops touching the SeekBar, change the text above it to show the selected value.
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                skillLevel = progressVal;
                Log.v(LOG_TAG, "Selected skill level " + progressVal);
            }

        });


        //add listeners to driver and algorithm spinners

        builder_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            /**
             * print out on the logcat the selected algorithm
             */

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                builder = builder_spinner.getSelectedItem().toString();
                Log.v(LOG_TAG, "Maze generation algorithm selection: " + builder);

            }

            //does nothing if no item selected
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        driver_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            /**
             * print out on the logcat the selected driver
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                driver = driver_spinner.getSelectedItem().toString();
                Log.v(LOG_TAG, "Maze generation driver selection: " + driver);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        /**
         * adding listener to button
         */
        exploreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context Context = getApplicationContext();
                CharSequence Text = "Explore button has been pushed.";
                int Duration = Toast.LENGTH_SHORT;

                Toast revisitToast = Toast.makeText(Context, Text, Duration);
                revisitToast.show();
                loadMaze = false;
                generateMazeActivity(v);
            }
        });

        revisitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context revisitContext = getApplicationContext();
                CharSequence revisitText = "Revisit button has been pushed.";
                int revisitDuration = Toast.LENGTH_SHORT;

                Toast revisitToast = Toast.makeText(revisitContext, revisitText, revisitDuration);
                revisitToast.show();
                loadMaze = true;
                generateMazeActivity(v);
            }
        });
    }




    /**
     * An intent to move GenerationActivity and eventually give it the selected
     * skill level and maze generation algorithm.
     * called when the start button is clicked
     * @param view
     */
    public void generateMazeActivity( View view){
        Log.v(LOG_TAG, "Start button clicked to generate maze");
        Intent intent = new Intent(this, GeneratingActivity.class);
        intent.putExtra("loadMaze", loadMaze);


        //Pass the skill level, driver, builder to11 the generating state
        skillLevel = seekBar.getProgress();
        intent.putExtra("skillLevel", skillLevel);
        intent.putExtra("driver", driver);
        intent.putExtra("builder", builder);

        //add the global information to dataholder for future use
        MazeDataHolder.setSkillLevel(skillLevel);
        MazeDataHolder.setMazeGenerationBuilder(builder);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu( Menu menu){
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }





}
