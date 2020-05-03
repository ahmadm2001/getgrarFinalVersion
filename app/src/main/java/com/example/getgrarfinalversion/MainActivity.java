package com.example.getgrarfinalversion;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
public class MainActivity extends AppCompatActivity {
    ProgressBar splashProgress;
    int SPLASH_TIME = 3000; //This is 3 seconds
/**
 * @author		Ahmad mashal
 * @version	    V1.0
 * @since		7/4/2020
 * This activity will show a backgroong for this app and take the user to the next activity.
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         *This is additional feature, used to run a progress bar
         * <p>
         * @param
         */
        splashProgress = findViewById(R.id.splashProgress);
        playProgress();
        /**
         *Code to start timer and take action after the timer ends
         * <p>
         * @param
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /**
                 * Method Do any action here. Now we are moving to next page
                 * <p>
                 *
                 * @param
                 */
                Intent mySuperIntent = new Intent(MainActivity.this, WelcomeActivity.class);
                startActivity(mySuperIntent);
                /**
                 * This 'finish()' is for exiting the app when back button pressed from Home page which is ActivityHome
                 */
                finish();

            }
        }, SPLASH_TIME);
    }
    /**
     * Method to run progress bar for 5 seconds
     * <p>
     *
     * @param
     */
    private void playProgress() {
        ObjectAnimator.ofInt(splashProgress, "progress", 100)
                .setDuration(5000)
                .start();
    }
}
