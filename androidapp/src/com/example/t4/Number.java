package com.example.t4;

import java.io.File;
import android.app.Application;
import android.os.Environment;

public class Number extends Application {
	public static int MAXMAP=4;
	public static int map=1;
	public static int savedpath[]=new int[MAXMAP];
	
	public static void Initialize(){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){//有sd卡
        	for(int i=1;i<MAXMAP+1;i++){
		  	   String path=Environment.getExternalStorageDirectory().toString()+"/raspberry/map"+i;   
		       File file=new File(path);   
		       if(!file.exists()) savedpath[i-1]=0;
		       else{
		    	   savedpath[i-1]=getSavedPath(file);        		
		       }
	       }       	
        }     
	}
	
	public static void AddSavedPath() {
		savedpath[map]++;
	}
	
	private static int getSavedPath(File root){  
        File files[] = root.listFiles();  
        int imgnum=0;
        if(files != null){  
            for (File f : files){  
                if(f.isDirectory()){  
                	getSavedPath(f);  
                }else{  
                	//输出所有文件
                    //System.out.println(f);  
                    imgnum++;
                }  
            }  
        } 
        return imgnum;
    } 
	
	
	public static void SetMap(int i){
		map=i;
	}
	
	public static int GetMap(){
		return map;
	}


}
