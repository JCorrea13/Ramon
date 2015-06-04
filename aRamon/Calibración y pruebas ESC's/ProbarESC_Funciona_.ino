#include <Servo.h>

#define MAX_SIGNAL 2000
#define MIN_SIGNAL 700

Servo motor[4];

void setup() {
  Serial.begin(9600);
  
  motor[0].attach(6);
  motor[1].attach(9);
  motor[2].attach(10);
  motor[3].attach(11);
 
  Serial.write("Comienza en 22\n");
  for (int i = 0; i < 4; i++)
    motor[i].write(50);
  delay(8000);
  Serial.write("Probando 35\n");
  for (int i = 0; i < 4; i++){
    motor[i].write(35);
    delay(1000);
    motor[i].write(20);
}
  for (int i = 0; i < 4; i++)
    motor[i].write(22);
 Serial.write("Iniciando loop \n"); 
}

void loop() {
  Serial.write("22");
  motor[0].write(22);
  motor[1].write(22);
  motor[2].write(22);
  motor[3].write(22);
  delay(3000);
  Serial.write("30");
  motor[0].write(30);
  motor[1].write(30);
  motor[2].write(30);
  motor[3].write(30);
  delay(1000);
  Serial.write("40");
  motor[0].write(40);
  motor[1].write(40);
  motor[2].write(40);
  motor[3].write(40);
  delay(1000);/*
  Serial.write("50");
  motor[0].write(50);
  motor[1].write(50);
  motor[2].write(50);
  motor[3].write(50);
  delay(1000);
  Serial.write("60");
  motor[0].write(60);
  motor[1].write(60);
  motor[2].write(60);
  motor[3].write(60);
  delay(1000);/*
  Serial.write("70");
  motor[0].write(70);
  motor[1].write(70);
  motor[2].write(70);
  motor[3].write(70);
  delay(1000);
  Serial.write("80");
  motor[0].write(80);
  motor[1].write(80);
  motor[2].write(80);
  motor[3].write(80);
  delay(1000);
  Serial.write("90");
  motor[0].write(90);
  motor[1].write(90);
  motor[2].write(90);
  motor[3].write(90);
  delay(1000);
  Serial.write("100");
  motor[0].write(100);
  motor[1].write(100);
  motor[2].write(100);
  motor[3].write(100);
  delay(1000);
  Serial.write("110");
  motor[0].write(110);
  motor[1].write(110);
  motor[2].write(110);
  motor[3].write(110);
  delay(1000);
  Serial.write("120");
  motor[0].write(120);
  motor[1].write(120);
  motor[2].write(120);
  motor[3].write(120);
  delay(1000);
  Serial.write("130");
  motor[0].write(130);
  motor[1].write(130);
  motor[2].write(130);
  motor[3].write(130);
  delay(1000);
  Serial.write("140");
  motor[0].write(140);
  motor[1].write(140);
  motor[2].write(140);
  motor[3].write(140);
  delay(1000);*/
}
