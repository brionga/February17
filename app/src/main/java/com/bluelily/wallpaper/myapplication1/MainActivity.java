package com.bluelily.wallpaper.myapplication1;

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

import static com.bluelily.wallpaper.myapplication1.HeavyLifter.FAIL;
import static com.bluelily.wallpaper.myapplication1.HeavyLifter.SUCCESS;

public class MainActivity extends Activity {
    /** A list containing the resource identifiers for all of our selectable backgrounds */
    private static final List<Integer> backgrounds = new ArrayList<Integer>();
    /** The total number of backgrounds in the list */
    private static final int TOTAL_IMAGES;
    /** Instantiate the list statically, so this will be done once on app load, also calculate the total number of backgrounds */
    static {
        backgrounds.add(R.drawable.background1);
        backgrounds.add(R.drawable.background2);
        backgrounds.add(R.drawable.background3);
        backgrounds.add(R.drawable.roosid1);
        backgrounds.add(R.drawable.seahorse);
        backgrounds.add(R.drawable.lilled);

        // We -1 as lists are zero indexed (0-2 is a size of 3) - we'll mke use of this to implement a browsing loop
        TOTAL_IMAGES = (backgrounds.size() - 1);
    }


    /** the state of what wallpaper is currently being previewed */
    private int currentPosition = 0;
    /** our image wallpaper preview */
    private ImageView backgroundPreview;
    /** A helper class that will do the heavy work of decoding images and actually setting the wallpaper */
    private HeavyLifter chuckNorris;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backgroundPreview = (ImageView) findViewById(R.id.backgroundPreview);

        // Set the default image to be shown to start with
        changePreviewImage(currentPosition);

        // Load are heavy lifter (goes and does work on another thread), to get a response after the lifters thread
        // has finished we pass in a Handler that will be notified when it completes
        chuckNorris = new HeavyLifter(this, chuckFinishedHandler);
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

    /**
     * Called from XML when the set wallpaper button is pressed
     * Thie retrieves the id of the current image from our list
     * It then asks chuck to set it as a wallpaper!
     * The chuckHandler will be called when this operation is complete
     * @param v
     */
    public void setAsWallpaper(View v) {
        int resourceId = backgrounds.get(currentPosition);
        chuckNorris.setResourceAsWallpaper(resourceId);
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

    /**
     * This is the handler that is notified when are HeavyLifter is finished doing an operation
     */
    private Handler chuckFinishedHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case SUCCESS:
                    Toast.makeText(MainActivity.this, "Wallpaper set", Toast.LENGTH_SHORT).show();
                    break;
                case FAIL:
                    Toast.makeText(MainActivity.this, "Uh oh, can't do that right now", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

}