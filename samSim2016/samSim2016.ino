
#include "Keyboard.h"

const int Button01 = 2;
const int Button02 = 3;
const int Button03 = 4;
const int Button04 = 5;
const int Button05 = 6;

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
    int words = random(10, 50);
    for (int j = 0; j < words; j++) {
      int lines = random(1, 10);
      for (int i = 0; i < lines; i ++) {
        Keyboard.print("shoddy code ");
      }
      Keyboard.print(";\n");
    }
    Keyboard.print("\n");
    delay(50);
  }
  lastButtonState01 = buttonState01;

  if ((buttonState02 != lastButtonState02) && (buttonState02 == HIGH)) {
    delay(50);
    Keyboard.print("BM!\n");
    delay(50);
  }
  lastButtonState02 = buttonState02;

  if ((buttonState03 != lastButtonState03) && (buttonState03 == HIGH)) {
    delay(50);
    Keyboard.press(KEY_LEFT_GUI);
    Keyboard.press('l');
    delay(100);
    Keyboard.releaseAll();
    delay(50);
  }
  lastButtonState03 = buttonState03;

}
