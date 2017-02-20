package com.myapplication1;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends Activity {
    /** A list containing the resource identifiers for all of our selectable backgrounds */
    private static final List<Integer> backgrounds = new ArrayList<Integer>();
    /** The total number of backgrounds in the list */
    private static final int TOTAL_IMAGES;
    /** Instantiate the list statically, so this will be done once on app load, also calculate the total number of backgrounds */
    static {
        backgrounds.add(R.drawable.background1);


        // We -1 as lists are zero indexed (0-2 is a size of 3) - we'll mke use of this to implement a browsing loop
        TOTAL_IMAGES = (backgrounds.size() - 1);
    }


    /** the state of what wallpaper is currently being previewed */
    private int currentPosition = 0;
    /** our image wallpaper preview */
    private ImageView backgroundPreview;
   

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backgroundPreview = (ImageView) findViewById(R.id.backgroundPreview);

        // Set the default image to be shown to start with
        changePreviewImage(currentPosition);

        
    }

    /**
     * Called from XML when the previous button is pressed
     * Decrement the current state position
     * If the position is now less than 0 loop round and show the last image (the array size)
     * @param v
     */
    public void gotoPreviousImage(View v) {
        int positionToMoveTo = currentPosition;
        positionToMoveTo--;
        if(positionToMoveTo < 0){
            positionToMoveTo = TOTAL_IMAGES;
        }
        changePreviewImage(positionToMoveTo);
    }

   
    public void setAsWallpaper(View v) {
        int resourceId = backgrounds.get(currentPosition);
        
    }

    /**
     * Called from XML when the next button is pressed
     * Increment the current state position
     * If the position is now greater than are array size loop round and show the first image again
     * @param v
     */
    public void gotoNextImage(View v) {
        int positionToMoveTo = currentPosition;
        positionToMoveTo++;
        if(currentPosition == TOTAL_IMAGES){
            positionToMoveTo = 0;
        }

        changePreviewImage(positionToMoveTo);
    }

    /**
     * Change the currently showing image on the screen
     * This is quite an expensive operation as each time the system
     * has to decode the image from our resources - alternatives are possible (a list of drawables created at app start)
     * @param pos the position in {@link MainActivity#backgrounds} to select the image from
     */
    public void changePreviewImage(int pos) {
        currentPosition = pos;
        backgroundPreview.setImageResource(backgrounds.get(pos));
        Log.d("Main", "Current position: "+pos);
    }

    

}
