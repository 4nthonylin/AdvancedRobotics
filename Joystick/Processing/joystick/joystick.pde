import controlP5.*;
import procontroll.*;
import net.java.games.input.*;
import processing.serial.*;
PImage bg;

Serial port;

ControllIO controllIO;
ControllDevice joypad;
ControllStick stick1;
ControllStick stick2;

ControlP5 controlP5;
DropdownList ports;
int Ss;
String[] comList;
boolean serialSet;
boolean Comselected;

int button1;
int button2;


int[] sticks = new int[4];

void setup() {
  size(640, 480);
  bg = loadImage("joystick.png");
  background(bg);
  ellipseMode(CENTER);
  rectMode(CENTER);
  controllIO = ControllIO.getInstance(this);
  joypad = controllIO.getDevice("Logitech Dual Action");
  stick1 = joypad.getStick(0);
  stick1.setMultiplier(127);
  stick2 = joypad.getStick(1);
  stick2.setMultiplier(127);
  joypad.getCoolieHat(0).setMultiplier(255);
  joypad.printSliders();

  controlP5 = new ControlP5(this);
  ports = controlP5.addDropdownList("list-1", 10, 25, 100, 84);
  customize(ports);


  // port = new Serial(this, "COM10", 9600);
}



void draw() {
  while (Comselected == true && serialSet == false)
  {
    startSerial(comList);
  }

  background(bg);
  button1 = 0;
  button2 = 0;
  buts();
  joys();
  if(serialSet) transmit();
  /*
  if (serialSet) {
    port.write('!');
    port.write((byte)(stick1.getY() + 127));
    port.write((byte)(stick1.getX() + 127));
    port.write((byte)(stick2.getY() + 127));
    port.write((byte)(stick2.getX() + 127));
    println("STICK 1: " + (int)stick1.getY() + " " + (int)stick1.getX());
    println("STICK 2: " + (int)stick2.getY() + " " + (int)stick2.getX());
  }
  */
  //if(port.available()>0) print(port.readChar());
}

void transmit() {
  byte bButton1 = (byte)button1;
  byte bButton2 = (byte)button2;
  sticks[0] = (int)map(stick2.getY(), -127, 127, 255, 0);
  sticks[1] = (int)map(stick2.getX(), -127, 127, 0, 255);
  sticks[2] = (int)map(stick1.getY(), -127, 127, 255, 0);
  sticks[3] = (int)map(stick1.getX(), -127, 127, 0, 255);
  //println((sticks[0]) + " " + (sticks[1]) + " " + (sticks[2]) + " " + (sticks[3]) + " " +" Button1: " + binary(bButton1) + " Button2: "  + binary(bButton2));
  
  port.write('!');
   for(int x = 0; x < 4; x++) port.write((byte)sticks[x]);
   port.write(bButton1);
   port.write(bButton2);
   
} 

void joys() {
  if (joypad.getButton(11).pressed()) {
    button2 = button2 << 1;
    button2 = button2 | 1;
    fill(255, 0, 0);
  }
  else {
    button2 = button2 << 1;
    fill(50);
  }
  ellipse(240+map(stick2.getX(), -127, 127, -15, 15), 280+map(stick2.getY(), -127, 127, -15, 15), 70, 70);
  if (joypad.getButton(12).pressed()) {
    button2 = button2 << 1;
    button2 = button2 | 1;
    fill(255, 0, 0);
  }
  else {
    button2 = button2 << 1;
    fill(50);
  }
  ellipse(395+map(stick1.getX(), -127, 127, -15, 15), 280+map(stick1.getY(), -127, 127, -15, 15), 70, 70);
}

void buts() {
  if (joypad.getButton(1).pressed()) {
    button1 = button1 << 1;
    button1 = button1 | 1;
    fill(255, 0, 0);
  }
  else {
    button1 = button1 << 1;
    fill(10);
  }
  ellipse(430, 204, 35, 35);
  if (joypad.getButton(2).pressed()) {
    button1 = button1 << 1;
    button1 = button1 | 1;
    fill(255, 0, 0);
  }
  else {
    button1 = button1 << 1;
    fill(10);
  }
  ellipse(470, 240, 35, 35);
  if (joypad.getButton(3).pressed()) {
    button1 = button1 << 1;
    button1 = button1 | 1;
    fill(255, 0, 0);
  }
  else {
    button1 = button1 << 1;
    fill(10);
  }
  ellipse(510, 204, 35, 35);
  if (joypad.getButton(4).pressed()) {
    button1 = button1 << 1;
    button1 = button1 | 1;
    fill(255, 0, 0);
  }
  else {
    button1 = button1 << 1;
    fill(10);
  }
  ellipse(470, 167, 35, 35);

  if (joypad.getButton(5).pressed()) {
    button1 = button1 << 1;
    button1 = button1 | 1;
    fill(255, 0, 0);
  }
  else {
    button1 = button1 << 1;
    fill(10);
  }
  rect(163, 73, 60, 30, 5);
  if (joypad.getButton(6).pressed()) {
    button1 = button1 << 1;
    button1 = button1 | 1;
    fill(255, 0, 0);
  }
  else {
    button1 = button1 << 1;
    fill(10);
  }
  rect(476, 73, 60, 30, 5);
  if (joypad.getButton(7).pressed()) {
    button1 = button1 << 1;
    button1 = button1 | 1;
    fill(255, 0, 0);
  }
  else {
    button1 = button1 << 1;
    fill(10);
  }
  rect(163, 32, 60, 30, 10);
  if (joypad.getButton(8).pressed()) {
    button1 = button1 << 1;
    button1 = button1 | 1;
    fill(255, 0, 0);
  }
  else {
    button1 = button1 << 1;
    fill(10);
  }
  rect(476, 32, 60, 30, 10);
  if (joypad.getButton(9).pressed()) {
    button2 = button2 << 1;
    button2 = button2 | 1;
    fill(255, 0, 0);
  }
  else {
    button2 = button2 << 1;
    fill(10);
  }
  rect(265, 171, 30, 20, 10);
  if (joypad.getButton(10).pressed()) {
    button2 = button2 << 1;
    button2 = button2 | 1;
    fill(255, 0, 0);
  }
  else {
    button2 = button2 << 1;
    fill(10);
  }
  rect(374, 171, 30, 20, 10);

  if (joypad.getCoolieHat(0).getX() < -100) {
    button2 = button2 << 1;
    button2 = button2 | 1;
  }
  else button2 = button2 << 1;
  if (joypad.getCoolieHat(0).getY() > 100) {
    button2 = button2 << 1;
    button2 = button2 | 1;
  }
  else button2 = button2 << 1;


  if (joypad.getCoolieHat(0).getX() > 100) {
    button2 = button2 << 1;
    button2 = button2 | 1;
  }

  else button2 = button2 << 1;

  if (joypad.getCoolieHat(0).getY() < -100) {
    button2 = button2 << 1;
    button2 = button2 | 1;
  }
  else button2 = button2 << 1;

  fill(map(joypad.getCoolieHat(0).getX(), 0, -127, 0, 255), 0, 0);
  ellipse(129, 202, 20, 20);
  fill(map(joypad.getCoolieHat(0).getY(), 0, 127, 0, 255), 0, 0);
  ellipse(164, 234, 20, 20);
  fill(map(joypad.getCoolieHat(0).getX(), 0, 127, 0, 255), 0, 0);
  ellipse(202, 202, 20, 20);
  fill(map(joypad.getCoolieHat(0).getY(), 0, -127, 0, 255), 0, 0);
  ellipse(164, 168, 20, 20);
}

void controlEvent(ControlEvent theEvent) {
  if (theEvent.isGroup()) {
    float S = theEvent.group().value();
    try {
      port.stop();
    }
    catch(Exception e) {
    }
    Ss= int(S);
    Comselected = true;
    serialSet = false;
  }
}

void customize(DropdownList ddl) {
  //Set the background color of the list (you wont see this though).
  ddl.setBackgroundColor(color(200));
  //Set the height of each item when the list is opened.
  ddl.setItemHeight(20);
  //Set the height of the bar itself.
  ddl.setBarHeight(15);
  //Set the lable of the bar when nothing is selected.
  ddl.captionLabel().set("Select COM port");
  //Set the top margin of the lable.
  ddl.captionLabel().style().marginTop = 3;
  //Set the left margin of the lable.
  ddl.captionLabel().style().marginLeft = 3;
  //Set the top margin of the value selected.
  ddl.valueLabel().style().marginTop = 3;
  //Store the Serial ports in the string comList (char array).
  comList = port.list();
  //We need to know how many ports there are, to know how many items to add to the list, so we will convert it to a String object (part of a class).
  String comlist = join(comList, ",");
  //We also need how many characters there is in a single port name, we´ll store the chars here for counting later.
  String COMlist = comList[0];
  //Here we count the length of each port name.
  int size2 = COMlist.length();
  //Now we can count how many ports there are, well that is count how many chars there are, so we will divide by the amount of chars per port name.
  int size1 = comlist.length() / size2;
  //Now well add the ports to the list, we use a for loop for that. How many items is determined by the value of size1.
  for (int i=0; i< size1; i++)
  {
    //This is the line doing the actual adding of items, we use the current loop we are in to determin what place in the char array to access and what item number to add it as.
    ddl.addItem(comList[i], i);
  }
  //Set the color of the background of the items and the bar.
  ddl.setColorBackground(color(60));
  //Set the color of the item your mouse is hovering over.
  ddl.setColorActive(color(255, 128));
}

void startSerial(String[] theport)
{
  //When this function is called, we setup the Serial connection with the accuried values. The int Ss acesses the determins where to accsess the char array. 
  port = new Serial(this, theport[Ss], 9600);
  //Since this is a one time setup, we state that we now have set up the connection.
  serialSet = true;
}
