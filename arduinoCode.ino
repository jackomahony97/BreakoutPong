
#include <SoftwareSerial.h>

int BUTTON = 8;
int BUTTON1 = 6;
// int BUTTON2 = 4;
// int BUTTON3 = 2;


SoftwareSerial mySerial(10, 11); // RX, TX

void setup(){
  pinMode(BUTTON, INPUT);
  pinMode(BUTTON1, INPUT);
  //pinMode(BUTTON2, INPUT);
  //pinMode(BUTTON3, INPUT);
  
  // Open serial communications and wait for port to open:


  // set the data rate for the SoftwareSerial port
  mySerial.begin(9600);
}
                                                                                      
void loop() // run over and over
{
// if(mySerial.available()){
    if(digitalRead(BUTTON) == HIGH){mySerial.println(0);}

    if(digitalRead(BUTTON1) == HIGH){mySerial.println(1);}

    //if(digitalRead(BUTTON2) == HIGH){mySerial.println(2);}

    //if(digitalRead(BUTTON3) == HIGH){mySerial.println(3);}

    
    

//}
}
