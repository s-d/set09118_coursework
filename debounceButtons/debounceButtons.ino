#define debounce 10

const int button[] {3, 4, 5, 6, 7};
#define numButton  sizeof(button)

const int led = 13;

int buttonState[numButton];
int lastButtonState[numButton];

void checkButtons() {
  for (int i = 0; i < numButton; i ++) {
    buttonState[i] = digitalRead(button[i]);
  }
}

void setup() {
  pinMode(led, OUTPUT);

  for (int i = 0 ; i < numButton; i++) {
    pinMode(button[i], INPUT);
  }
}
void loop() {
  checkButtons();
  if (buttonState[4] == HIGH) {
    digitalWrite(led, HIGH);
  } else {
    digitalWrite(led, LOW);
  }
}
