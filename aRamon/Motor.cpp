#include "Arduino.h"
#include "Servo.h"
#include "Motor.h"

Motor :: Motor(int pin, int vinicial){
	this->attach(pin);
	setVoltaje(vinicial);
}

void Motor :: setVoltaje(int voltaje){
	this->voltaje = voltaje;
}

int Motor :: getVoltaje(){
	return this-> voltaje;
}

void Motor :: sumVoltajeTR(){
	this->voltajeTR ++;
}

void Motor :: resVoltajeTR(){
	this->voltajeTR --;	
}

int Motor :: getVoltajeTR(){
	return this->voltajeTR;
}

/**
 *Este metodo actualiza los motores com el voltaje de tiempo real
 * (VoltajeTR) la finalida de este metodo es que al cambiar la 
 * configuracion del motor (voltaje) para manipular la velocidad a la 
 * que gira el voltaje valla acercandose al deseado y no cambie drasticamente.
 * 
 */
void Motor :: actulizaVoltaje(){
	if(this->voltajeTR < this->voltaje)
		sumVoltajeTR();
	
	if(this->voltajeTR > this->voltaje)
		resVoltajeTR();
		
	this->write(this->voltajeTR);
}
