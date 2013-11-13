/*
  Joystick.h - Library for reading and parsing Serial data from Processing.
  Created by Anthony Lin, November 11, 2013.
*/

#include <Arduino.h>
#include "Joystick.h"

Joystick::Joystick()
{
  sync = '!';
}

int Joystick::getX1(){
  return x1;
}
int Joystick::getX2(){
  return x2;
}
int Joystick::getY1(){
  return y1;
}

int Joystick::getY2(){
  return y2;
}

int Joystick::getButton1(){
  return button1;
}

int Joystick::getButton2(){
  return button2;
}

boolean Joystick::buttonPressed(int bn){
  bn = bn-1;
  constrain(bn, 0, 15);
  if(bn < 8){
    if(bitRead(button1, map(bn, 0, 7, 7, 0)) == 1) return true;
    else return false;
  }
  else{
    if(bitRead(button2, map(bn, 8, 15, 7, 0)) == 1) return true;
    else return false;
  }
}

void Joystick::update(){
  byte temp[PACKETLENGTH];
  if(Serial.available() > PACKETLENGTH && Serial.read() == sync){
    for(int x = 0; x < PACKETLENGTH; x++) temp[x] = Serial.read();
    y1 = temp[0];
    x1 = temp[1];
    y2 = temp[2];
    x2 = temp[3];
    button1 = temp[4];
    button2 = temp[5];
  }
}