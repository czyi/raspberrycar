package com.example.t4;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;

public class AstarPathFinder {
	   // 前四个是上下左右，后四个是斜角
   public final static int[] dx = { 0, -1, 0, 1 };
   public final static int[] dy = { -1, 0, 1, 0 };
   public enum Direction {
       up, down, left, right, zero;
   }
   public int turnum;
   
   public final static int WIDTH=100;
   public final static int HEIGHT=100;
   
   public Point turnpoint[]=new Point[20];
   public String sendstr;
 
   static int[][] map = new int[WIDTH][HEIGHT];
   Bitmap imgpath = Bitmap.createBitmap(1000,1000,Bitmap.Config.ARGB_8888);
   
   public int FindPath(Bitmap imgsrc, Bitmap imghb, int startxsrc, int startysrc, int endxsrc, int endysrc)
   {	
	   int N=10;
	   int startx=startxsrc/N;
	   int starty=startysrc/N;
	   int endx=endxsrc/N;
	   int endy=endysrc/N;
	   
	   //初始化地图
   	   int[] pixels = new int[imghb.getWidth()*imghb.getHeight()];//保存所有的像素的数组，图片宽×高

   	   imghb.getPixels(pixels,0,imghb.getWidth(),0,0,imghb.getWidth(),imghb.getHeight());
       for(int i = 0; i < pixels.length; i++){
           int clr = pixels[i];
              int  red   = (clr & 0x00ff0000) >> 16;  //取高两位
              int  green = (clr & 0x0000ff00) >> 8; //取中两位
              int  blue  =  clr & 0x000000ff; //取低两位
              //System.out.println("x="+i/WIDTH+",y="+(i%WIDTH)+",r="+red+",g="+green+",b="+blue);
              
              if(red<100 & green<100 & blue<100) map[i%WIDTH][i/WIDTH]=1;
              else map[i%WIDTH][i/WIDTH]=0;
       }
       for(int i=0;i<WIDTH;i++){
    	   map[0][i]=1;
    	   map[HEIGHT-1][i]=1;
    	   map[i][0]=1;
    	   map[i][WIDTH-1]=1;
       }
	   
       Point start = new Point(startx, starty);
       Point end = new Point(endx, endy);
       
       //寻路
       Stack<Point> stack = printPath(start, end);
       if(null==stack) {//没有路径连通起点和终点
    	 return 0;
       }else {
     	   Paint paint1, paint2, paint3;
    	   
           paint1 = new Paint(); //设置一个笔刷大小是3的红色的画笔   
           paint1.setColor(Color.RED);   
           paint1.setStrokeJoin(Paint.Join.ROUND);   
           paint1.setStrokeCap(Paint.Cap.ROUND);   
           paint1.setStrokeWidth(8); 
            
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
    	   
           Canvas canvas = new Canvas(imgpath);
           canvas.drawBitmap(imgsrc,0,0,null);
           Point curpoint;
           Direction predir=Direction.zero;
           Direction curdir=Direction.zero;
           turnum=0;
           
           Point prepoint=stack.pop();//起点
	       while(!stack.isEmpty()) {
	    	   //虚线绘路
	    	   curpoint=stack.pop();
	    	   //canvas.drawPoint(curpoint.getX()*N, curpoint.getY()*N, paint1); 
	    	   
	    	 //记录转折点 turnpoint
	    	   if(curpoint.getX()-prepoint.getX()==0){
	    		   if(curpoint.getY()-prepoint.getY()==-1) curdir=Direction.up;
	    		   else curdir=Direction.down;
	    	   }
	    	   else if (curpoint.getX()-prepoint.getX()==1) curdir=Direction.right;
	    	   else curdir=Direction.left;
	    	   
	    	   if(curdir!=predir){
	    		   turnpoint[turnum]=prepoint;
	    		   //MainActivity.instance.SetTextView("");
	    		   System.out.println("num "+turnum+": x "+ turnpoint[turnum].getX()*N+", y "+turnpoint[turnum].getY()*N);
	    		   turnum++;
	    	   }
	    	   
	    	   prepoint=curpoint;
	    	   predir=curdir;
	       }

           turnpoint[turnum]=end;
           System.out.println("num "+turnum+": x "+ turnpoint[turnum].getX()*N+", y "+turnpoint[turnum].getY()*N);
           turnum++;
           
           for(int i=1;i<turnum;i++){
        	   canvas.drawLine(turnpoint[i].getX()*N, turnpoint[i].getY()*N, turnpoint[i-1].getX()*N, turnpoint[i-1].getY()*N, paint1);
           }
           
           canvas.drawPoint(startx*N, starty*N, paint2);
           canvas.drawPoint(endx*N, endy*N, paint3); 
           
           return 1;
       }

        //return 1;
     } 
   
   public Bitmap GetPathImg()
   {
	   return imgpath;
   }
   
   public String ChangeToString(int dxy){
	   String intostr;
	   
	   if(dxy>0){
		   if((dxy)>=10) intostr=Integer.toString(dxy);
		   else intostr="0"+Integer.toString(dxy);
	   }
	   else{
		   if(dxy<=-10) intostr=Integer.toString(-dxy);
		   else intostr="0"+Integer.toString(-dxy);
	   }
	   
	   return intostr;
   }
 
   public void CalculatePath(){
	   int dx, dy;
	   Direction predir=Direction.zero;
	   Direction curdir=Direction.zero;
	   
	   dx=turnpoint[1].getX()-turnpoint[0].getX();
	   dy=turnpoint[1].getY()-turnpoint[0].getY();
	   
	   if(dx==0){
		   if(dy<0){
			   predir=Direction.up;
			   sendstr="fd"+ChangeToString(dx+dy);
		   }
		   else {
			   predir=Direction.down;
			   sendstr="tdfd"+ChangeToString(dx+dy);
		   }
	   }
	   else if(dx>0) {
		   predir=Direction.right;
		   sendstr="rtfd"+ChangeToString(dx+dy);
	   }
	   else {
		   predir=Direction.left;
		   sendstr="ltfd"+ChangeToString(dx+dy);
	   }

	   for(int i=2;i<turnum;i++){
		   dx=turnpoint[i].getX()-turnpoint[i-1].getX();
		   dy=turnpoint[i].getY()-turnpoint[i-1].getY();

		   if(dx==0){
			   if(dy<0) curdir=Direction.up;
			   else curdir=Direction.down;
		   }
		   else if(dx>0) curdir=Direction.right;
		   else curdir=Direction.left;
		   
		   if((predir==Direction.up && curdir==Direction.right) | (predir==Direction.right && curdir==Direction.down) |
				   (predir==Direction.down && curdir==Direction.left) | (predir==Direction.left && curdir==Direction.up)) sendstr+="rt";
		   else sendstr+="lt";
		   
		   sendstr+="fd"+ChangeToString(dx+dy);
		   predir=curdir;
	   }
	   
	   sendstr+="ed";
	   
	   //System.out.println(sendstr);
	   //MainActivity.instance.SetTextView(sendstr);
   }
   
   public void SendPath() {
	   if(sendstr==null) CalculatePath();
	   //MainActivity.instance.SetTextView(sendstr);
	   
		try {
			Commander.send(sendstr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
   }
   
   @SuppressLint("SdCardPath") 
   public void SavePath() {//保存指定路径在sd卡上
	   if(sendstr==null){
		   CalculatePath();//计算路径
	   }
	   
	   String path=getRootPath()+"/raspberry/map"+Number.GetMap()+"/";   
       File file=new File(path);   
       if(!file.exists())   
        file.mkdir();    
	   
	   File f = new File(path+sendstr+".png");
	   //MainActivity.instance.SetTextView(path+sendstr+".png");
	   if (f.exists()) { 
		   f.delete(); 
	   } 
	   
       if(isHavedSDcard()){
           try {
        	   FileOutputStream fos = new FileOutputStream(f);
        	   imgpath.compress(Bitmap.CompressFormat.PNG, 90, fos); 
        	   fos.flush(); 
        	   fos.close(); 
               MainActivity.showToast("保存成功！");
               Number.AddSavedPath();
           } catch (Exception e) {
           	MainActivity.showToast("保存失败！");
               e.printStackTrace();
           }
       }
       else{
           MainActivity.showToast("您的手机没有内存卡哦!");
       }
	}
   
   public static boolean isHavedSDcard() {
       if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
           return true;
       else
           return false;
   }
    
   public static String getRootPath() {
       return Environment.getExternalStorageDirectory().toString();
   }
    
    
   
   public void SetSendMsg(String str) {
		// TODO Auto-generated method stub
		sendstr=str;
	}
   
   
   
   
   
   
   
   
   
 
   public static Stack<Point> printPath(Point start, Point end) {
     
     /*
      * 不用PriorityQueue是因为必须取出存在的元素
      */
     ArrayList<Point> openTable = new ArrayList<Point>();
     ArrayList<Point> closeTable = new ArrayList<Point>();
     openTable .clear();
     closeTable.clear();
     Stack<Point> pathStack = new Stack<Point>();
     start.parent = null;
     //该点起到转换作用，就是当前扩展点
     Point currentPoint = new Point(start.x, start.y);
     //closeTable.add(currentPoint);
     boolean flag = true;
     
     while(flag) {
       for (int i = 0; i < 4; i++) {
         int fx = currentPoint.x + dx[i];
         int fy = currentPoint.y + dy[i];
         Point tempPoint = new Point(fx,fy);
         if (map[fx][fy] == 1) {
           // 由于边界都是1中间障碍物也是1，，这样不必考虑越界和障碍点扩展问题
           //如果不设置边界那么fx >=map.length &&fy>=map[0].length判断越界问题
           continue;
         } else {
           if(end.equals(tempPoint)) {
             flag = false;
             //不是tempPoint，他俩都一样了此时
             end.parent = currentPoint;
             break;
           }
           if(i<4) {
             tempPoint.G = currentPoint.G + 10;
           }else {
           tempPoint.G = currentPoint.G + 14;
           }
           tempPoint.H = Point.getDis(tempPoint,end);
           tempPoint.F = tempPoint.G + tempPoint.H;
           //因为重写了equals方法，所以这里包含只是按equals相等包含
           //这一点是使用java封装好类的关键
           if(openTable.contains(tempPoint)) {
             int pos = openTable.indexOf(tempPoint );
             Point temp = openTable.get(pos);
             if(temp.F > tempPoint.F) {
               openTable.remove(pos);
               openTable.add(tempPoint);
               tempPoint.parent = currentPoint;
             }
           }else if(closeTable.contains(tempPoint)){
             int pos = closeTable.indexOf(tempPoint );
             Point temp = closeTable.get(pos);
             if(temp.F > tempPoint.F) {
               closeTable.remove(pos);
               openTable.add(tempPoint);
               tempPoint.parent = currentPoint;
             }
           }else {
             openTable.add(tempPoint);
             tempPoint.parent = currentPoint;
           }
         }
       }//end for
       
       if(openTable.isEmpty()) {
         return null;
       }//无路径
       if(false==flag) {
         break;
       }//找到路径
       openTable.remove(currentPoint);
       closeTable.add(currentPoint);
       Collections.sort(openTable);
       /*奇怪的错误，加了if好了*/
       if(!openTable.isEmpty()) currentPoint = openTable.get(0);
      
     }//end while
     Point node = end;
     while(node.parent!=null) {
       pathStack.push(node);
       node = node.parent;
     }    
     return pathStack;
   }

 }
 
 class Point implements Comparable<Point>{
   int x;
   int y;
   Point parent;
   int F, G, H;
 
   public Point(int x, int y) {
     super();
     this.x = x;
     this.y = y;
     this.F = 0;
     this.G = 0;
     this.H = 0;
   }
 
   public int compareTo(Point o) {
     // TODO Auto-generated method stub
     return this.F  - o.F;
   }
 
   @Override
   public boolean equals(Object obj) {
     Point point = (Point) obj;
     if (point.x == this.x && point.y == this.y)
       return true;
     return false;
   }
 
   public static int getDis(Point p1, Point p2) {
     int dis = Math.abs(p1.x - p2.x) * 10 + Math.abs(p1.y - p2.y) * 10;
     return dis;
   }
 
   public int getX(){
	   return x;
   }
   
   public int getY(){
	   return y;
   }
   
   @Override
   public String toString() {
     return "(" + this.x + "," + this.y + ")";
   }
}