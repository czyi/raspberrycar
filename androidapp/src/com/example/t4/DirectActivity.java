package com.example.t4;

import com.example.t4.R;
import com.example.t4.Commander;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DirectActivity extends Activity {
	
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct);
        
        button1=(Button) findViewById(R.id.button4);
        button2=(Button) findViewById(R.id.button5);
        button3=(Button) findViewById(R.id.button2);
        button4=(Button) findViewById(R.id.button3);
        button5=(Button) findViewById(R.id.button1);
        
        button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					Commander.send("forward");
				} catch (Exception e) {//¥¶¿Ì“Ï≥£
					e.printStackTrace();
				}
			}
		});
        button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					Commander.send("backward");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
        button3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					Commander.send("left");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
        button4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					Commander.send("right");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
        button5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					Commander.send("stop");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }
    
    public void onClickToMain(View view)
    {
        finish();
    }

}
