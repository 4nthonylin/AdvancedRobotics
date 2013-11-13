/*
  Joystick.h - Library for reading and parsing Serial data from Processing.
  Created by Anthony Lin, November 11, 2013.
*/

#ifndef Joystick_h
#define Joystick_h
#include <Arduino.h>


#define PACKETLENGTH 6

class Joystick{
  public:
    Joystick();
    int getX1();
    int getX2();
    int getY1();
    int getY2();
    int getButton1();
    int getButton2();
    boolean buttonPressed(int bn);
    void update();
  public:
  	int x1;
  	int x2;
  	int y1;
  	int y2;
  	byte button1;
  	byte button2;
  private:
  	byte sync;
};

#endif