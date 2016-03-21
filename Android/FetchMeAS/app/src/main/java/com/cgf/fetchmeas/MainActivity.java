package com.cgf.fetchmeas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

	@Bind(R.id.MainTextView) TextView mMainTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		// for test the butter knife to simply the code of the findViewById and so on
		ButterKnife.bind(this);
		mMainTextView.setText("Hello FetchMe");
        
		Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
