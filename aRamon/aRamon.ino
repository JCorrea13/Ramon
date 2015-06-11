#include <SoftwareSerial.h>
#include <Servo.h>
#include <Wire.h>
#include "Piloto.h"
#include "SensoresRamon.h"

//Salida a motores
const int NULLL = 'X';
const int PIN_MOTOR1 = 6; //adelante izquierda
const int PIN_MOTOR2 = 10; //adelante derecha
const int PIN_MOTOR3 = 11; //atras izquierda
const int PIN_MOTOR4 = 9;// atras derecha
const int PIN_ATERRIZAJE = 3;
const int PIN_BUZZER = A2;
const int PIN_GPSRX = -2;
const int PIN_GPSTX = 5;
const int PIN_ACELEROMETRO = 0x68;

//Declaramos servo para tren de aterrizaje
Servo trenAterrizaje;
//Declaramos motores
Motor motor1;
Motor motor2;
Motor motor3;
Motor motor4;

Piloto piloto = Piloto(motor1,motor2,motor3,motor4,trenAterrizaje);
SensoresRamon sensores = SensoresRamon(PIN_ACELEROMETRO,PIN_BUZZER,PIN_GPSRX, PIN_GPSTX); 

void setup() {
  //Inicializamos el serial
  Serial.begin(57600);
  
  //inicializamos el tren de aterrizaje
  trenAterrizaje.attach(PIN_ATERRIZAJE);  
  trenAterrizaje.write(0);
  
  //Inicializamos los motores
  motor1.attach(PIN_MOTOR1);
  motor2.attach(PIN_MOTOR2);
  motor3.attach(PIN_MOTOR3);
  motor4.attach(PIN_MOTOR4);
  piloto.inicializaMotores();
  
  pinMode(PIN_BUZZER, OUTPUT);
  //Establecemos los vaoltajes que manejaran los motores
  piloto.setVoltajes(50,38,30,22); 
  Serial.println("Inicio");
}

char dato;
void loop() {
   if(Serial.available()){
     dato = Serial.read();
     piloto.desplazar(dato);
   }else{
     //piloto.desplazar(NULLL);
   }
}

