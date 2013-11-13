/*
  Example sketch for Joystick library, used in conjunction with Processing Sketch

  Author: Anthony Lin
*/

#include <Joystick.h>                  //import Joystick library

Joystick controller;                   //initialized a new joystick object

int x1, x2, y1, y2;                    //Variables for holding stick values
byte button1, button2;                 //Variables for holding button bytes

void setup(){
  Serial.begin(9600);                  //Have to call Serial.begin()
  Serial.print("Initialized");
  pinMode(13, OUTPUT);
}

void loop(){
  controller.update();                 //Must call this in void loop() to update Joystick values
  y1 = controller.getY1();             //Gets stick values
  x1 = controller.getX1();
  y2 = controller.getY2();
  x2 = controller.getX2();
  button1 = controller.getButton1();   //Gets button bytes
  button2 = controller.getButton2();
  
  if(controller.buttonPressed(0)) digitalWrite(13, HIGH); //Returns true of button is pressed
  else digitalWrite(13, LOW);
}
