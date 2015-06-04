#include <Servo.h>

#define MAX_SIGNAL 2000
#define MIN_SIGNAL 700

Servo motor[4];

void setup() {
  Serial.begin(9600);
  Serial.println("Inicio");

  motor[0].attach(6);
  motor[1].attach(9);
  motor[2].attach(10);
  motor[3].attach(11);

  Serial.println("Escribiendo valor maximo.");
  Serial.println("Prender baterías y esperar a que comiencen a pitar os esc, pulsar cualquier tecla para programar la funcion: ");
  
  motor[0].writeMicroseconds(MAX_SIGNAL);
  motor[1].writeMicroseconds(MAX_SIGNAL);
  motor[2].writeMicroseconds(MAX_SIGNAL);
  motor[3].writeMicroseconds(MAX_SIGNAL);
  
  while (!Serial.available());
  Serial.read();

  Serial.println("Escribiendo valor minimo.");
  
  motor[0].writeMicroseconds(MIN_SIGNAL);
  motor[1].writeMicroseconds(MIN_SIGNAL);
  motor[2].writeMicroseconds(MIN_SIGNAL);
  motor[3].writeMicroseconds(MIN_SIGNAL);
    
}

void loop() {/*
  LOOP
  */
}
