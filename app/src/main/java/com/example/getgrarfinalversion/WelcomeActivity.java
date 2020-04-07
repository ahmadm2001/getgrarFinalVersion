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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        driver=(Button) findViewById(R.id.driver);
        customer=(Button)findViewById(R.id.customer);
        driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t=new Intent(WelcomeActivity.this , LoginDriver .class);
                startActivity(t);
            }
        });
        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t=new Intent(WelcomeActivity.this , LoginForCusromer .class);
                startActivity(t);
            }
        });
    }
}
