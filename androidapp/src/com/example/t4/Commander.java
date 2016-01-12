package com.example.t4;
import java.io.PrintWriter;
import java.net.Socket;

import android.annotation.SuppressLint;
import android.os.StrictMode;

public class Commander {
    public static String HOST ="192.168.137.120"; //the ip of raspberry pi   
    public static int PORT =8888;
    
    @SuppressLint("NewApi") 
    public static void send(String command) throws Exception {
    	//网络连接线程
    	StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        Socket socket = new Socket(HOST, PORT);
        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        
        //发送command字符串
        writer.println(command.toString());  
        writer.flush();  
        socket.close();  
    }  
}






