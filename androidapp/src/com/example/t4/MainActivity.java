package com.example.t4;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private float startx, starty, endx, endy, currentx, currenty;
	private int startpoint, endpoint, path;
	
	private Paint paint1, paint2, paint3;
	private static int N=10;//大图小图缩放比例
	
	private Bitmap bg1, bg2, bg3, bg4, imghb1, imghb2, imghb3, imghb4;
	private Bitmap bg[] =new Bitmap[Number.MAXMAP];
	private Bitmap imghb[] =new Bitmap[Number.MAXMAP];
	
	private ImageView imageview;
	
	public static MainActivity instance = null; 
	AstarPathFinder astar;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); 
        instance = this;
        
        Number.Initialize();
        startpoint=0;endpoint=0;startx=0;starty=0;endx=0;endy=0;path=0;
        imageview=(ImageView) findViewById(R.id.imageView1);
        //textView = (TextView) findViewById(R.id.textView1);
        astar = new AstarPathFinder();
         
        bg1 = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.map1x1000);
        bg2 = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.map2x1000);
        bg3 = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.map3x1000);
        bg4 = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.map4x1000);
        bg[0]=bg1; bg[1]=bg2; bg[2]=bg3; bg[3]=bg4; 
        imageview.setImageBitmap(bg[Number.GetMap()-1]); 

        imghb1 = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.map1x100);
        imghb2 = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.map2x100);
        imghb3 = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.map3x100);
        imghb4 = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.map4x100);
        imghb[0]=imghb1; imghb[1]=imghb2; imghb[2]=imghb3; imghb[3]=imghb4;
        
        paint1 = new Paint(); //设置一个笔刷大小是3的红色的画笔   
        paint1.setColor(Color.RED);   
        paint1.setStrokeJoin(Paint.Join.ROUND);   
        paint1.setStrokeCap(Paint.Cap.ROUND);   
        paint1.setStrokeWidth(20); 
        
        paint2 = new Paint(); //起点画笔
        paint2.setColor(Color.YELLOW);   
        paint2.setStrokeJoin(Paint.Join.ROUND);   
        paint2.setStrokeCap(Paint.Cap.ROUND);   
        paint2.setStrokeWidth(30); 
        
        paint3 = new Paint(); //终点画笔
        paint3.setColor(Color.BLUE);   
        paint3.setStrokeJoin(Paint.Join.ROUND);   
        paint3.setStrokeCap(Paint.Cap.ROUND);   
        paint3.setStrokeWidth(30); 
        
        findViewById(R.id.imageView1).setOnTouchListener(new OnTouchListener() {
    		//触摸地图
    		
            @Override
            public boolean onTouch(View v, MotionEvent event) {
            //当按下时获取到屏幕中的xy位置
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                	currentx=event.getX();
                	currenty=event.getY();
                    //textView.setText(event.getX() +","+event.getY());
                    SetNewImage();                    
                }
                return false;
            }
        });
        
    }
    
    
    public void SetSrcImage()
    {//设置地图
        imageview.setImageBitmap(bg[Number.GetMap()-1]); 
        startpoint=0;
        endpoint=0;
    }
    
    public void SetSavedImage(String file){
    	 Bitmap bitmap = BitmapFactory.decodeFile(file);
    	 imageview.setImageBitmap(bitmap); 
    	 //设置sendstr
    	 astar.SetSendMsg(getFileName(file));
    	 path=1;
    }
    
    public String getFileName(String pathandname)
    {//从路径中获取文件名
        int start=pathandname.lastIndexOf("/");  
        int end=pathandname.lastIndexOf(".");  
        if(start!=-1 && end!=-1){  
            return pathandname.substring(start+1,end);    
        }else{  
            return null;  
        }    
    }  
    
    public void SetNewImage()
    {
        Bitmap bitmap = Bitmap.createBitmap(imageview.getWidth(),imageview.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(bg[Number.GetMap()-1],0,0,null);
        canvas.drawPoint(currentx, currenty, paint1);
        if(startpoint==1) canvas.drawPoint(startx, starty, paint2);
        if(endpoint==1) canvas.drawPoint(endx, endy, paint3);
        
        imageview.setImageBitmap(bitmap); 
    }
    
    public void SetStartPoint(View v)  
    {//设置起点
    	int pixel=imghb[Number.GetMap()-1].getPixel((int)Math.floor(currentx/N), (int)Math.floor(currenty/N));
    	int red = Color.red(pixel); 
    	int green = Color.green(pixel); 
    	int blue = Color.blue(pixel); 
    	
    	if(red<100 & green<100 & blue<100) Toast.makeText(MainActivity.this, "障碍物！不能设为起点", Toast.LENGTH_SHORT).show();
    	else{
	    	startx=currentx;
	    	starty=currenty;
	    	startpoint=1;
	    	SetNewImage();		
    	}
    }  
    
    public void SetEndPoint(View v)  
    {//设置终点
    	int pixel=imghb[Number.GetMap()-1].getPixel((int)Math.floor(currentx/N), (int)Math.floor(currenty/N));
    	int red = Color.red(pixel);
    	int green = Color.green(pixel); 
    	int blue = Color.blue(pixel);
    	
    	if(red<100 & green<100 & blue<100) Toast.makeText(MainActivity.this, "障碍物！不能设为终点", Toast.LENGTH_SHORT).show();
    	else{  	
	    	endx=currentx;
	    	endy=currenty;
	    	endpoint=1;
	    	SetNewImage(); 		
    	}
    } 
    
    public void FindPath(View v)
    {
    	if(startpoint==1&endpoint==1){
	    	//textView.setText("start"+(int)Math.floor(startx) +","+(int)Math.floor(starty)+" end,"+(int)Math.floor(endx) +","+(int)Math.floor(endy));
	    	
	    	int road=astar.FindPath(bg[Number.GetMap()-1], imghb[Number.GetMap()-1], (int)Math.floor(startx+N/2), (int)Math.floor(starty+N/2), (int)Math.floor(endx+N/2), (int)Math.floor(endy+N/2));
	    	if(road==0) Toast.makeText(MainActivity.this, "没有路径连通起点和终点", Toast.LENGTH_LONG).show();
	    	else{           
	            imageview.setImageBitmap(astar.GetPathImg());
	            path=1;
	    	}  		
    	}
    	else{
    		Toast.makeText(MainActivity.this, "未设置起点或终点！", Toast.LENGTH_SHORT).show();
    	}
    	

    }
    
    public void SendPath(View v){
    	if(path==1){
    		astar.SendPath();
    	}
    	else{
          	Toast.makeText(MainActivity.this, "请先计算路径！", Toast.LENGTH_SHORT).show();
        }
    	
    }
    
    public void onClickToMap(View view)
    {
        startActivity(new Intent("com.example.t4.DirectActivity"));
    }
    
    public void onClickToChooseMap(View v){
    	startActivity(new Intent("com.example.t4.ChooseMapActivity"));
    }
    
    public void SavePath(View v){
    	if(path==1) astar.SavePath();
    	else Toast.makeText(MainActivity.this, "请先计算路径！", Toast.LENGTH_SHORT).show();
    }
    
    public void ChooseSavedPath(View v)
    {   
    	startActivity(new Intent("com.example.t4.SecondActivity"));
    }
    public static void showToast(String text) {
        Toast.makeText(MainActivity.instance, text, Toast.LENGTH_SHORT).show();
    }
}
