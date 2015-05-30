#include <SoftwareSerial.h>
#include <Servo.h>
#include <Wire.h>
#include "Piloto.h"
#include "SensoresRamon.h"

//Salida a motores
const int NULLL = -1;
const int PIN_MOTOR1 = A0;
const int PIN_MOTOR2 = A1;
const int PIN_MOTOR3 = A2;
const int PIN_MOTOR4 = A3;
const int PIN_ATERRIZAJE = 13;
const int PIN_BUZZER = 1;
const int PIN_GPSRX = 2;
const int PIN_GPSTX = 3;
const int PIN_ACELEROMETRO = 12;

Piloto piloto = Piloto(PIN_MOTOR1,PIN_MOTOR2,PIN_MOTOR3,PIN_MOTOR4,PIN_ATERRIZAJE);
SensoresRamon sensores = SensoresRamon(PIN_ACELEROMETRO,PIN_BUZZER,PIN_GPSRX, PIN_GPSTX); 
void setup() {
  //NOTA: FALTA DETERMINAR EL GIRO DE LOS MOTORES(VALOR DE CERRADOY VALOR DE ABIERTO)
  //PARA EL MECANISMO DE ATERRIZAJE
  Serial.begin(57600);
   //Establecemos los vaoltajes que manejaran los motores
  piloto.setVoltajes(10,5,1,0); 
}

void loop() {
   if(Serial.available() > 0){
     piloto.desplazar(Serial.read());
   }else{
     piloto.desplazar(NULLL);
   }
}
