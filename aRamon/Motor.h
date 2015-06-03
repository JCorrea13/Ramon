#include "Arduino.h"
#include "Servo.h"
/**
 * Esta clase tiene como objetivo modelar y
 * controlar los motores
 * */
class Motor : public Servo{	
	private:
          int pinMotor;	//Pin de arduino en el que esta conectado el motor
          int voltaje;	//voltaje que se estara actualizando
          int voltajeTR;	//voltaje en tiempo real
          void sumVoltajeTR();
          void resVoltajeTR();

	public:
          Motor();
  	  //Motor(int);
       	  void setVoltaje(int);
          int getVoltaje();
          int getVoltajeTR();
          void actulizaVoltaje();
};
