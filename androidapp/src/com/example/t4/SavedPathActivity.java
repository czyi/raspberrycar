package com.example.t4;

import java.io.File;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.Toast;

public class SavedPathActivity extends Activity {
	
    ImageView imageview1;
    ImageView imageview2;
    ImageView imageview3;
    ImageView imageview4;
    ImageView imageview5;
    ImageView imageview6;
    ImageView imglist[] = new ImageView[6];
    int curimg;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savedpath);
        
        imageview1=(ImageView) findViewById(R.id.image1);
        imageview2=(ImageView) findViewById(R.id.image2);
        imageview3=(ImageView) findViewById(R.id.image3);
        imageview4=(ImageView) findViewById(R.id.image4);
        
       imglist[0]=imageview1;imglist[1]=imageview2;imglist[2]=imageview3;
       imglist[3]=imageview4;imglist[4]=imageview5;imglist[5]=imageview6;
       curimg=0;
        
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){//有sd卡
	  	   String path=Environment.getExternalStorageDirectory().toString()+"/raspberry/map"+Number.GetMap();   
	       File file=new File(path);   
	       if(!file.exists()) Toast.makeText(SavedPathActivity.this, "没有已存储的路径！", Toast.LENGTH_LONG).show();
	       else{
	    	   getAllFiles(file);
	       }       	
        }
        else//无sd卡
        {
        	Toast.makeText(SavedPathActivity.this, "没有sd卡！", Toast.LENGTH_LONG).show();
        }
        
        findViewById(R.id.image1).setOnTouchListener(new OnTouchListener() {    		
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                	if(Number.savedpath[Number.map-1]>=1){
                		for(int i=0;i<Number.savedpath[Number.map-1];i++)
                			imglist[i].setBackgroundColor(android.graphics.Color.parseColor("#ffffff")); 
                		imageview1.setBackgroundColor(android.graphics.Color.parseColor("#6be986")); 
                		curimg=1;
                	}
                }
                return false;
            }
        });
        
        findViewById(R.id.image2).setOnTouchListener(new OnTouchListener() {    		
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                	if(Number.savedpath[Number.map-1]>=2){
                		for(int i=0;i<Number.savedpath[Number.map-1];i++)
                			imglist[i].setBackgroundColor(android.graphics.Color.parseColor("#ffffff")); 
                		imageview2.setBackgroundColor(android.graphics.Color.parseColor("#6be986")); 
                		curimg=2;
                	}
                }
                return false;
            }
        });
        
        findViewById(R.id.image3).setOnTouchListener(new OnTouchListener() {    		
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                	if(Number.savedpath[Number.map-1]>=3){
                		for(int i=0;i<Number.savedpath[Number.map-1];i++)
                			imglist[i].setBackgroundColor(android.graphics.Color.parseColor("#ffffff")); 
                		imageview3.setBackgroundColor(android.graphics.Color.parseColor("#6be986")); 
                		curimg=3;
                	}
                }
                return false;
            }
        });
        
        findViewById(R.id.image4).setOnTouchListener(new OnTouchListener() {    		
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                	if(Number.savedpath[Number.map-1]>=4){
                		for(int i=0;i<Number.savedpath[Number.map-1];i++)
                			imglist[i].setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));
                		imageview4.setBackgroundColor(android.graphics.Color.parseColor("#6be986")); 
                		curimg=4;
                	}
                }
                return false;
            }
        });
    }
        
    public void onClickToMain(View v){
    	if(curimg!=0){
    		String img=GetImg();
    		if(img!=null){
    			MainActivity.instance.SetSavedImage(img);
    		}
    	}
    	finish();
    }
    
    public void onClickToCancel(View v){
    	finish();
    }
    
    private String GetImg() {
		// TODO Auto-generated method stub
       int i=0;
       String chooseimg = null;
  	   String path=Environment.getExternalStorageDirectory().toString()+"/raspberry/map"+Number.GetMap();   
       File file=new File(path);   
       if(!file.exists()) Toast.makeText(SavedPathActivity.this, "没有已存储的路径！", Toast.LENGTH_LONG).show();
       else{
    	   //getAllFiles(file);
    	   for (File f : file.listFiles()){
    		   i++;
    		   if(i==curimg){
    			   chooseimg=f.getPath();
    		   }
    	   }
       }  
    	
     return chooseimg;	
	}

	private void getAllFiles(File root){  
        File files[] = root.listFiles();  
        int i=0;
        if(files != null){  
            for (File f : files){  
                if(f.isDirectory()){  
                    getAllFiles(f);  
                }else{  
                	//输出所有文件
                    System.out.println(f);  
                    String filepath=f.getPath();
                    Bitmap bitmap = BitmapFactory.decodeFile(filepath);
                    
                    if(i<6){
                    	imglist[i].setImageBitmap(bitmap);
		                i++;                   	
                    }
                    else{
                    	break;
                    }

                }  
            }  
        }  
    }  
}














