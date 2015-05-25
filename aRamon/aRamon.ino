#include <SoftwareSerial.h>
#include <Servo.h>
#include <Wire.h>
#include "Piloto.h"
#include "SensoresRamon.h"

//Salida a motores
const int pinMotor1 = A0;
const int pinMotor2 = A1;
const int pinMotor3 = A2;
const int pinMotor4 = A3;
const int pinAterrizaje = 13;
//const int pinAcelerometro = 12;


SoftwareSerial GPSerial(2, 3); // RX, TX
Piloto piloto = Piloto(pinMotor1,pinMotor2,pinMotor3,pinMotor4,pinAterrizaje);
void setup() {
  GPSerial.begin(4800);
  Serial.begin(57600);
  
  //Establecemos los vaoltajes que manejaran los motores
  piloto.setVoltajes(10,5,1); 
}

void loop() {
   if(Serial.available() > 0){
     piloto.desplazar(Serial.read());
   }
}
