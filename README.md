# Raspberry Car
rasberry pi + small car

An Android app and a Raspberry Pi server software. Socket connection is established between two
ends to achieve remote control. An A* algorithm was implemented to find the shortest route and navigate.

### Description:
Raspberry pi car for transferring physic files or small objects, controlled by android app.

Firstly connect phone and raspberry by virtual LAN. User chooses map, selects start and end point. Use A star algorithm to find the shortest route, generate path instructions and send it to the raspberry.

Raspberry pi receives and parses instructions and sets different voltage on wheels to control the car.

The Mobile Phone and raspberry should be connnected with the same LAN.


