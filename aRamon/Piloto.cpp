#include "Arduino.h"
#include "Piloto.h"
#include "SensoresRamon.h"

Piloto :: Piloto(const int PIN_MOTOR1,const int PIN_MOTOR2,const int PIN_MOTOR3,const int PIN_MOTOR4,const int PIN_TRENATERRIZAJE){

	this->tren_Aterrizaje = new Servo();
        this->tren_Aterrizaje->attach(PIN_TRENATERRIZAJE);	
	//Inicializamos los pines de los motores como pines de salida
	pinMode(PIN_MOTOR1 , OUTPUT);
	pinMode(PIN_MOTOR2 , OUTPUT);
	pinMode(PIN_MOTOR3 , OUTPUT);
	pinMode(PIN_MOTOR4 , OUTPUT);
	pinMode(PIN_TRENATERRIZAJE , OUTPUT);

        //Inicializamos motores
        this->motor1 = new Motor(PIN_MOTOR1, 22);
        this->motor2 = new Motor(PIN_MOTOR2, 22);
        this->motor3 = new Motor(PIN_MOTOR3, 22);
        this->motor4 = new Motor(PIN_MOTOR4, 22);
    	
        //dejamos los voltajes en cero para obligar a cambiarlos
        setVoltajes(0,0,0,0);
}

void Piloto :: setVoltajes(int voltajeAlto, int voltajeMedio, int voltajeBajo, int voltajeApagado){
        this->voltajeAlto = voltajeAlto;
        this->voltajeMedio = voltajeMedio;
        this->voltajeBajo = voltajeBajo;	
        this->voltajeApagado = voltajeApagado;
}

void Piloto :: setVoltajeMotor1(int voltaje){
	((Motor*)this->motor1)->setVoltaje(voltaje);
}
void Piloto :: setVoltajeMotor2(int voltaje){
	((Motor*)this->motor2)->setVoltaje(voltaje);
}
void Piloto :: setVoltajeMotor3(int voltaje){
	((Motor*)this->motor3)->setVoltaje(voltaje);
}
void Piloto :: setVoltajeMotor4(int voltaje){
	((Motor*)this->motor4)->setVoltaje(voltaje);
}

//El valor que se puede escribir en los pines digitamles va desde 0 hasta 1023
void Piloto :: outputPines(){
	((Motor*)this->motor1)->actulizaVoltaje();
	((Motor*)this->motor2)->actulizaVoltaje();
	((Motor*)this->motor3)->actulizaVoltaje();
	((Motor*)this->motor4)->actulizaVoltaje();
}

void Piloto :: setConfi_DirIzquierda(){
    setVoltajeMotor1(voltajeAlto); 
    setVoltajeMotor2(voltajeBajo); 
    setVoltajeMotor3(voltajeAlto); 
    setVoltajeMotor4(voltajeBajo);
}
void Piloto :: setConfi_DirDerecha(){
    setVoltajeMotor1(voltajeBajo); 
    setVoltajeMotor2(voltajeAlto); 
    setVoltajeMotor3(voltajeBajo); 
    setVoltajeMotor4(voltajeAlto);
}
void Piloto :: setConfi_DirAdelante(){
    setVoltajeMotor1(voltajeAlto); 
    setVoltajeMotor2(voltajeAlto); 
    setVoltajeMotor3(voltajeBajo); 
    setVoltajeMotor4(voltajeBajo);
}
void Piloto :: setConfi_DirAtras(){
    setVoltajeMotor1(voltajeBajo); 
    setVoltajeMotor2(voltajeBajo); 
    setVoltajeMotor3(voltajeAlto); 
    setVoltajeMotor4(voltajeAlto);
}
void Piloto :: setConfi_DirSubir(){
    setVoltajeMotor1(voltajeAlto); 
    setVoltajeMotor2(voltajeAlto); 
    setVoltajeMotor3(voltajeAlto); 
    setVoltajeMotor4(voltajeAlto);
}
void Piloto :: setConfi_DirBajar(){
    setVoltajeMotor1(voltajeBajo); 
    setVoltajeMotor2(voltajeBajo); 
    setVoltajeMotor3(voltajeBajo); 
    setVoltajeMotor4(voltajeBajo);	
}
void Piloto :: setConfi_DirModoEstatico(){
    setVoltajeMotor1(voltajeMedio); 
    setVoltajeMotor2(voltajeMedio); 
    setVoltajeMotor3(voltajeMedio); 
    setVoltajeMotor4(voltajeMedio);	
}
void Piloto :: setConfi_DirAterrizar(){
	//Insertar codigo para el aterrizaje
}

void Piloto :: cambioEstado_trenAterrizaje(){
        if(this->estado_trenAterrizaje){
            this->tren_Aterrizaje->write(0);            
            this->estado_trenAterrizaje = false;
        }else{
            this->tren_Aterrizaje->write(100);          
            this->estado_trenAterrizaje = true;
        }
}

void Piloto :: on_off_Motores(){
	
	if(((Motor*)this->motor1)->getVoltaje() > this->voltajeApagado)
		((Motor*)this->motor1)->setVoltaje(this->voltajeApagado);
		((Motor*)this->motor2)->setVoltaje(this->voltajeApagado);
		((Motor*)this->motor3)->setVoltaje(this->voltajeApagado);
		((Motor*)this->motor4)->setVoltaje(this->voltajeApagado);
		
	if(((Motor*)this->motor1)->getVoltaje() <= this->voltajeApagado)
		((Motor*)this->motor1)->setVoltaje(this->voltajeMedio);
		((Motor*)this->motor2)->setVoltaje(this->voltajeMedio);
		((Motor*)this->motor3)->setVoltaje(this->voltajeMedio);
		((Motor*)this->motor4)->setVoltaje(this->voltajeMedio);
		
}

void Piloto :: confirmaConexion(){
      SensoresRamon::enciendeBuzzer();
      delay(2000);
      SensoresRamon::apagaBuzzer();
      Serial.write(CONFIMACION_CONEXION);
}

void Piloto :: desplazar(char direccion){
	switch(direccion){
		case MODO_ESTATICO:
				setConfi_DirModoEstatico();
				break;
		case DIR_IZQUIERDA:
				setConfi_DirIzquierda();
				break;
		case DIR_DERECHA:
				setConfi_DirDerecha();
				break;
		case DIR_ADELANTE:
				setConfi_DirAdelante();
				break;
		case DIR_ATRAS:
				setConfi_DirAtras();
				break;
		case DIR_BAJAR:
				setConfi_DirBajar();
				break;
		case DIR_SUBIR:
				setConfi_DirSubir();
				break;
		case ATERRIZA:
				setConfi_DirAterrizar();
				break;
		case TREN_ATERRIZAJE_CAMBIOESTADO:
				cambioEstado_trenAterrizaje();
				break;
		case ON_OFF_MOTORES:
				on_off_Motores();
				break;
		case CONFIMACION_CONEXION:
				confirmaConexion();
				break;
	}
	outputPines();
}
