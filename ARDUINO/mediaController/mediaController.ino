#include <Bounce2.h>
#include <Media.h>
#include <SoftwareSerial.h>

SoftwareSerial mySerial(8, 9); // RX, TX

char myChar;

//declare array of buttons
const int button[] = {3, 4, 5, 6, 7};
//declare array of debouncers
Bounce debounce[sizeof(button)];
//declare interval for debouncers
const int del = 5;
const int volumeDelay = 50;

void setup() {
  Serial.begin(9600);
  mySerial.begin(9600);

  //define buttons/bouncers attach a bouncer to each button
  for (int i = 0; i < sizeof(button); i++) {
    pinMode(button[i], INPUT);
    debounce[i] = Bounce();
    debounce[i].attach(button[i]);
    debounce[i].interval(del);
  }
}

void checkDebounce() {
  //update each debouncer i.e. check for presses
  for (int i = 0; i < sizeof(button); i ++) {
    debounce[i].update();
  }
}

void play() {
  Media.play();
  Media.clear();
}
void prev() {
  Media.previous();
  Media.clear();
}
void next() {
  Media.next();
  Media.clear();

}
void up() {
  Media.increase();
  Media.clear();
}
void down() {
  Media.decrease();
  Media.clear();
}

void loop() {

  //check all buttons
  checkDebounce();

  if (debounce[0].fell() == HIGH) {
    prev();
  }

  if (debounce[1].fell() == HIGH) {
    play();
  }

  if (debounce[2].fell() == HIGH) {
    next();
  }

  if (debounce[3].read() == HIGH) {
    up();
    delay(volumeDelay);
  }

  if (debounce[4].read() == HIGH) {
    down();
    delay(volumeDelay);
  }

  //check Bluetooth
  while (mySerial.available()) {
    //read char
    myChar = mySerial.read();
    Serial.print(myChar);

    //execute command
    switch (myChar) {
      case't':
        play();
        break;
      case'p':
        prev();
        break;
      case'n':
        next();
        break;
      case'u':
        up();
        break;
      case'd':
        down();
        break;
      default:
        break;
    }
    //reset char
    myChar = ' ';
  }

}
