
#include "Keyboard.h"
#include "Remote.h"

//#define numButton  sizeof(button)

//int buttonState[numButton];
//int lastButtonState[numButton];


const int Button01 = 3;
const int Button02 = 4;
const int Button03 = 5;
const int Button04 = 6;
const int Button05 = 7;

bool play = 0;

int buttonState01 = 0;
int lastButtonState01 = 0;
int buttonState02 = 0;
int lastButtonState02 = 0;
int buttonState03 = 0;
int lastButtonState03 = 0;
int buttonState04 = 0;
int lastButtonState04 = 0;
int buttonState05 = 0;
int lastButtonState05 = 0;

long lastDebounceTime = 0;
long debounceDelay = 50;

void setup() {
  pinMode(Button01, INPUT);
  pinMode(Button02, INPUT);
  pinMode(Button03, INPUT);
  pinMode(Button04, INPUT);
  pinMode(Button05, INPUT);

  Serial.begin(9600);
  Keyboard.begin();
}
void loop() {

  buttonState01 = digitalRead(Button01);
  buttonState02 = digitalRead(Button02);
  buttonState03 = digitalRead(Button03);
  buttonState04 = digitalRead(Button04);
  buttonState05 = digitalRead(Button05);

  if ((buttonState01 != lastButtonState01) && (buttonState01 == HIGH)) {
    delay(50);
    Remote.mute();
    Remote.clear();
    delay(50);
  }
  lastButtonState01 = buttonState01;

  if ((buttonState02 != lastButtonState02) && (buttonState02 == HIGH)) {
    delay(50);
    Remote.next();
    Remote.clear();
    delay(50);
  }
  lastButtonState02 = buttonState02;

  if ((buttonState03 != lastButtonState03) && (buttonState03 == HIGH)) {
    delay(50);
    Remote.previous();
    Remote.clear();
    delay(50);

  }
  lastButtonState03 = buttonState03;

  if ((buttonState04 != lastButtonState04) && (buttonState04 == HIGH)) {
    delay(50);
    Remote.play();
    Remote.clear();
    delay(50);
  }
  lastButtonState04 = buttonState04;

  if ((buttonState05 != lastButtonState05) && (buttonState05 == HIGH)) {
    delay(50);
    Remote.pause();
    Remote.clear();
    delay(50);
  }
  lastButtonState05 = buttonState05;


}
