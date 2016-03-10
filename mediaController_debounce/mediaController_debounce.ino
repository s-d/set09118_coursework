#include <Bounce2.h>
#include <Media.h>

//declare array of buttons
const int button[] = {7, 6, 5, 4, 3};
//declare array of debouncers
Bounce debounce[sizeof(button)];
//declare interval for debouncers
const int del = 5;

void setup() {
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
void loop() {

  checkDebounce();

  if (debounce[0].fell() == HIGH) {
    Media.previous();
    Media.clear();
  }

  if (debounce[1].fell() == HIGH) {
    Media.play();
    Media.clear();
  }

  if (debounce[2].fell() == HIGH) {
    Media.next();
    Media.clear();
  }

  if (debounce[3].read() == HIGH) {
    Media.increase();
    Media.clear();
    delay(100);
  }

  if (debounce[4].read() == HIGH) {
    Media.decrease();
    Media.clear();
    delay(100);
  }

}
