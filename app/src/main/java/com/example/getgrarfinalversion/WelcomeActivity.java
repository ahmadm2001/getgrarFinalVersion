package com.example.getgrarfinalversion;

import androidx.annotation.IntRange;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {
    Button driver,customer;
    Intent t;
    @Override
    /**
     * @author		Ahmad mashal
     * @version	    V1.0
     * @since		7/4/2020
     * user will   Choose who he is Customer/driver.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        driver=(Button) findViewById(R.id.driver);
        customer=(Button)findViewById(R.id.customer);
        /**
         * Lisner for driver button.
         * <p>
         *
         * @param	view Button	on click operate the action.
         */
        driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * moveing to the next driver activity.
                 * <p>
                 *
                 * @param	view Button	on click operate the action.
                 */
                t=new Intent(WelcomeActivity.this , LoginDriver .class);
                startActivity(t);
            }
        });
        /**
         * Lisner for customer button.
         * <p>
         *
         * @param	view Button	on click operate the action.
         */
        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * moveing to the next customer activity.
                 * <p>
                 *
                 * @param	view Button	on click operate the action.
                 */
                t=new Intent(WelcomeActivity.this , LoginForCusromer .class);
                startActivity(t);
            }
        });
    }
}
