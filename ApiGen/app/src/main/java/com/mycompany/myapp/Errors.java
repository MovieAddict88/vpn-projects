package com.mycompany.myapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.mycompany.mygen.R;


public class Errors extends AppCompatActivity {
	
    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.errors);
		error = (TextView) findViewById(R.id.errors);
        
        error.setText(getIntent().getStringExtra("error"));
    }
}






