#include "Arduino.h"
#include "Servo.h"
#include "Motor.h"

Motor :: Motor(){
}

void Motor :: setVoltaje(int v){
	voltaje = v;
}

int Motor :: getVoltaje(){
	return voltaje;
}

void Motor :: sumVoltajeTR(){
	voltajeTR ++;
}

void Motor :: resVoltajeTR(){
	voltajeTR --;	
}

int Motor :: getVoltajeTR(){
	return voltajeTR;
}

/**
 *Este metodo actualiza los motores com el voltaje de tiempo real
 * (VoltajeTR) la finalida de este metodo es que al cambiar la 
 * configuracion del motor (voltaje) para manipular la velocidad a la 
 * que gira el voltaje valla acercandose al deseado y no cambie drasticamente.
 * 
 */
void Motor :: actulizaVoltaje(){
	if(voltajeTR < voltaje)
	  sumVoltajeTR();
	
	if(voltajeTR > voltaje)
          resVoltajeTR();
		
	write(voltajeTR);
}
