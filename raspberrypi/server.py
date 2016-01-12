import RPi.GPIO as GPIO  #GPIO package  
from socket import *       
import sys    
import string
import time    

IN1=11
IN2=12
IN3=13
IN4=15

def init():
    GPIO.setmode(GPIO.BOARD)
    GPIO.setup(IN1,GPIO.OUT)    
    GPIO.setup(IN2,GPIO.OUT)
    GPIO.setup(IN3,GPIO.OUT) 
    GPIO.setup(IN4,GPIO.OUT)

def forward():
    GPIO.output(IN1,GPIO.HIGH)    
    GPIO.output(IN2,GPIO.LOW)   
    GPIO.output(IN3,GPIO.HIGH)    
    GPIO.output(IN4,GPIO.LOW) 

def backward():
    GPIO.output(IN1,GPIO.LOW)    
    GPIO.output(IN2,GPIO.HIGH)   
    GPIO.output(IN3,GPIO.LOW)    
    GPIO.output(IN4,GPIO.HIGH) 

def left():
    GPIO.output(IN1,GPIO.HIGH)    
    GPIO.output(IN2,GPIO.LOW)   
    GPIO.output(IN3,False)    
    GPIO.output(IN4,False) 

def right():
    GPIO.output(IN1,False)    
    GPIO.output(IN2,False)     
    GPIO.output(IN3,GPIO.HIGH)    
    GPIO.output(IN4,GPIO.LOW)

def stop():
    GPIO.output(IN1,False)    
    GPIO.output(IN2,False)     
    GPIO.output(IN3,False)    
    GPIO.output(IN4,False)

######################################################### 
class Car:       
    @staticmethod    
    def init():    
        GPIO.setmode(GPIO.BOARD)
        GPIO.setup(IN1,GPIO.OUT)    
        GPIO.setup(IN2,GPIO.OUT)
        GPIO.setup(IN3,GPIO.OUT) 
        GPIO.setup(IN4,GPIO.OUT)

    @staticmethod    
    def forward(t):    
        init()
        GPIO.output(IN1,GPIO.HIGH)    
        GPIO.output(IN2,GPIO.LOW)   
        GPIO.output(IN3,GPIO.HIGH)    
        GPIO.output(IN4,GPIO.LOW) 
        time.sleep(t)
 
    @staticmethod    
    def back(t):
        init()
        GPIO.output(IN1,GPIO.LOW)    
        GPIO.output(IN2,GPIO.HIGH)   
        GPIO.output(IN3,GPIO.LOW)    
        GPIO.output(IN4,GPIO.HIGH) 
  
    @staticmethod    
    def left(t):
        init()
        GPIO.output(IN1,GPIO.HIGH)    
        GPIO.output(IN2,GPIO.LOW)   
        GPIO.output(IN3,False)    
        GPIO.output(IN4,False) 

    @staticmethod    
    def right(t): 
        init()
        GPIO.output(IN1,False)    
        GPIO.output(IN2,False)     
        GPIO.output(IN3,GPIO.HIGH)    
        GPIO.output(IN4,GPIO.LOW)   

    @staticmethod    
    def stop(): 
        init()
        GPIO.output(IN1,False)    
        GPIO.output(IN2,False)     
        GPIO.output(IN3,False)    
        GPIO.output(IN4,False)
#########################################################    



commands ={'forward':forward,    
  'back':back,     
  'stop':stop,    
  'left':left,    
  'right':right    
}    
    
def execute(command):       
    print command    
    if(len(command)<8):
        commands[command]()
    else:
        i=0;
        while(command[i:i+2]!="ed"):
            if(command[i:i+2]=="fd"):
                i=1+2
                Car.forward(string.atoi(command[i:i+2])/2)
            elif(command[i:i+2]=="td"):
                Car.left(3)
            elif(command[i:i+2]=="lt"):
                Car.left(1.5)
            elif(command[i:i+2]=="rt"):
                Car.right(1.5)
            i=i+2
            GPIO.cleanup()

    
HOST ='192.168.137.120' #the ip of rapberry pi    
PORT = 8888    
s= socket(AF_INET, SOCK_STREAM)    
s.bind((HOST, PORT))    
s.listen(1)    
print ('listening on 8888')
while 1:    
    conn, addr = s.accept()    
    print ('Connected by:', addr)    
    while 1:    
            command= conn.recv(1024).replace('\n','')    
            if not command:break    
            execute(command)    
    conn.close()  




