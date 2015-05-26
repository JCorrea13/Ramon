#include "Servo.h"
/**
 * Esta clase tiene como objetivo modelar y
 * controlar los motores
 * */
class Motor{	
	private:
		int pinMotor;	
                Servo motor;
                int voltaje;
                int voltajeTR;

	public:
		Motor(int,int);
		void setVoltaje(int);
                int getVoltaje();
                void sumVoltajeTR();
                void resVoltajeTR();
                int getVoltajeTR();
                void actulizaVoltaje();
                void setVoltajes(int, int, int); // este metodo setea los valores para voltajeAlto,voltajeMedio, voltajeBajo
};
