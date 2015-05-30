#include <SoftwareSerial.h>
#include <Servo.h>
#include <Wire.h>
#include "Piloto.h"
#include "SensoresRamon.h"

//Salida a motores
const int NULLL = -1;
const int PIN_MOTOR1 = 6;
const int PIN_MOTOR2 = 9;
const int PIN_MOTOR3 = 10;
const int PIN_MOTOR4 = 11;
const int PIN_ATERRIZAJE = 3;
const int PIN_BUZZER = A2;
const int PIN_GPSRX = -2;
const int PIN_GPSTX = 5;
const int PIN_ACELEROMETRO = 0x68;

Piloto *piloto = new Piloto(PIN_MOTOR1,PIN_MOTOR2,PIN_MOTOR3,PIN_MOTOR4,PIN_ATERRIZAJE);
//SensoresRamon *sensores = new SensoresRamon(PIN_ACELEROMETRO,PIN_BUZZER,PIN_GPSRX, PIN_GPSTX); 
void setup() {
  //NOTA: FALTA DETERMINAR EL GIRO DE LOS MOTORES(VALOR DE CERRADOY VALOR DE ABIERTO)
  //PARA EL MECANISMO DE ATERRIZAJE
  Serial.begin(57600);
   //Establecemos los vaoltajes que manejaran los motores
  //piloto->setVoltajes(80,60,22,22); 
  Serial.println("Inicio");
}

char p;
void loop() {
   p = (char)Serial.read();
   if(Serial.available()){
     Serial.println(p);
     piloto->desplazar(p);
   }else{
     //Serial.write("...");
     piloto->desplazar(NULLL);
   }
}
