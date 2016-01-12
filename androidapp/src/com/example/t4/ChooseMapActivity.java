package com.example.t4;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ChooseMapActivity extends Activity {
	public int map;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosemap);
        map=0;
        
        final ImageView imageview1=(ImageView) findViewById(R.id.image1);
        final ImageView imageview2=(ImageView) findViewById(R.id.image2);
        final ImageView imageview3=(ImageView) findViewById(R.id.image3);
        final ImageView imageview4=(ImageView) findViewById(R.id.image4);
       
        final Bitmap src1 = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.map1x500);
        final Bitmap src2 = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.map2x500);
        final Bitmap src3 = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.map3x500);
        final Bitmap src4 = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.map4x500);
        
        final Bitmap choose1 = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.choose1x500);
        final Bitmap choose2 = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.choose2x500);
        final Bitmap choose3 = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.choose3x500);
        final Bitmap choose4 = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.choose4x500);
        
        findViewById(R.id.image1).setOnTouchListener(new OnTouchListener() {
    		TextView textView = (TextView) findViewById(R.id.textView1); 
    		
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                	map=1;
                	textView.setText("已选择第一张"+map);
                	
                    imageview1.setImageBitmap(choose1); 
                    imageview2.setImageBitmap(src2);
                    imageview3.setImageBitmap(src3);
                    imageview4.setImageBitmap(src4);
                }
                return false;
            }
        });
        
        findViewById(R.id.image2).setOnTouchListener(new OnTouchListener() {
    		TextView textView = (TextView) findViewById(R.id.textView1); 
    		
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                	map=2;
                	textView.setText("已选择第二张"+map);
                	            	
                    imageview1.setImageBitmap(src1); 
                    imageview2.setImageBitmap(choose2);
                    imageview3.setImageBitmap(src3);
                    imageview4.setImageBitmap(src4);
                }
                return false;
            }
        });
        
        findViewById(R.id.image3).setOnTouchListener(new OnTouchListener() {
    		TextView textView = (TextView) findViewById(R.id.textView1); 
    		
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                  	map=3;              	
                	textView.setText("已选择第三张"+map);
                	
                    imageview1.setImageBitmap(src1); 
                    imageview2.setImageBitmap(src2);
                    imageview3.setImageBitmap(choose3);
                    imageview4.setImageBitmap(src4);
                }
                return false;
            }
        });
        
        findViewById(R.id.image4).setOnTouchListener(new OnTouchListener() {
    		TextView textView = (TextView) findViewById(R.id.textView1); 
    		
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                  	map=4;              	
                	textView.setText("已选择第四张"+map);
                	
                    imageview1.setImageBitmap(src1); 
                    imageview2.setImageBitmap(src2);
                    imageview3.setImageBitmap(src3);
                    imageview4.setImageBitmap(choose4);
                }
                return false;
            }
        });    
    }
    
    public void onClickToMain(View v){
    	Number.SetMap(map);
    	MainActivity.instance.SetSrcImage();
    	finish();
    }
}
