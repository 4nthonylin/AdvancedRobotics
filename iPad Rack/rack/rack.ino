#include <Max3421e.h>
#include <Usb.h>
#include <AndroidAccessory.h>

#define lock 2
#define led 13
#define limit 3

AndroidAccessory acc("TAS",
"9000",
"aweseom anthony",
"0.1",
"http://www.4nthonylin.com",
"0000000012345678");

boolean locked = false;

void setup(){
  Serial.begin(9600);
  pinMode(lock, OUTPUT);
  pinMode(led, OUTPUT);
  pinMode(limit, INPUT);
  digitalWrite(limit, HIGH);
  digitalWrite(led, LOW);
  digitalWrite(lock, HIGH);
  acc.powerOn();
  delay(1000);
}

void loop(){
  byte msg[1];
  if(acc.isConnected()){
    int len = acc.read(msg, sizeof(msg), 1);
    if(len > 0){
      Serial.println(msg[0]);
      if(msg[0] == 177){
        locked = true;
      }
      if(msg[0] == 176){
        locked = false;
      }
    }
  }
 if(locked){
    digitalWrite(lock, LOW);
    digitalWrite(led, LOW);
  }
  else{
    digitalWrite(lock, HIGH);
    digitalWrite(led, HIGH);
    delay(2000);
    while(digitalRead(limit) == 1);
    delay(300);
    locked = true;
  }
}

