#include <SoftwareSerial.h>

SoftwareSerial mySerial(8, 9); // RX, TX

char myChar;

void setup()
{
    Serial.begin(9600);

    mySerial.begin(9600);
    delay(5000);
    mySerial.print("AT");
}

void loop() // run over and over
{
    while ( mySerial.available() )
    { 
        myChar = mySerial.read();
        Serial.print(myChar);
    }

    while ( Serial.available() )
    { 
        myChar = Serial.read();
        mySerial.print(myChar);
    }
}
