#include "Media.h"

//#define numButton  sizeof(button)

//int buttonState[numButton];
//int lastButtonState[numButton];

const int button[] = {7, 6, 5, 4, 3};


int buttonState[sizeof(button)];
int lastButtonState[sizeof(button)];

long lastDebounceTime = 0;
long debounceDelay = 50;

void setup() {
  for (int i = 0; i < sizeof(button); i++) {
    pinMode(button[i], INPUT);
    buttonState[i] = 0;
    lastButtonState[i] = 0;
  }
}

void checkButton() {
  for (int i = 0; i < sizeof(button); i ++) {
    buttonState[i] = digitalRead(button[i]);
  }
}

void loop() {
  checkButton();

  if (buttonState[0] == HIGH) {
    delay(50);
    Media.previous();
    Media.clear();
    delay(50);
  }

  if (buttonState[1] == HIGH) {
    delay(50);
    Media.play();
    Media.clear();
    delay(50);
  }

  if (buttonState[2] == HIGH) {
    delay(50);
    Media.next();
    Media.clear();
    delay(50);
  }

  if (buttonState[3] == HIGH) {
    delay(50);
    delay(50);
  }

  if (buttonState[4] == HIGH) {
    delay(50);
    Media.mute();
    Media.clear();
    delay(50);
  }

}
