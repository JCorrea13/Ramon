#include "Piloto.h"
#include "Arduino.h"
#include "Servo.h"

Piloto :: Piloto(const int pinMotor1,const int pinMotor2,const int pinMotor3,const int pinMotor4,const int pinTrenAterrizaje){
	this->pinMotor1 = pinMotor1;
	this->pinMotor2 = pinMotor2;
	this->pinMotor3 = pinMotor3;
	this->pinMotor4 = pinMotor4;
	this->pinTrenAterrrizaje = pinTrenAterrizaje;

        //Inicializamos motores
        this->motor1.attach(pinMotor1);
        this->motor2.attach(pinMotor2);
        this->motor3.attach(pinMotor3);
        this->motor4.attach(pinMotor4);
	
	//Inicializamos los pines de los motores como pines de salida
	pinMode(pinMotor1 , OUTPUT);
	pinMode(pinMotor2 , OUTPUT);
	pinMode(pinMotor3 , OUTPUT);
	pinMode(pinMotor4 , OUTPUT);
	pinMode(pinTrenAterrizaje , OUTPUT);
	
        //dejamos los voltajes en cero para obligar a cambiarlos
        setVoltajes(0,0,0);
}

void Piloto :: setVoltajes(int voltajeAlt, int voltajeMedio, int voltajeAlto){
  this->voltajeAlto = voltajeAlto;
  this->voltajeMedio = voltajeMedio;
  this->voltajeBajo = voltajeAlto;
}

void Piloto :: setVoltajeMotor1(int voltaje){
	this->voltajeMotor1 = voltaje;
}
void Piloto :: setVoltajeMotor2(int voltaje){
	this->voltajeMotor2 = voltaje;
}
void Piloto :: setVoltajeMotor3(int voltaje){
	this->voltajeMotor3 = voltaje;
}
void Piloto :: setVoltajeMotor4(int voltaje){
	this->voltajeMotor4 = voltaje;
}

//El valor que se puede escribir en los pines digitamles va desde 0 hasta 1023
void Piloto :: outputPines(){
	motor1.write(voltajeMotor1);
	motor2.write(voltajeMotor2);
	motor3.write(voltajeMotor3);
	motor4.write(voltajeMotor4);
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
	//insertar codigo para cambion tren aterrizaje
}

void Piloto :: on_off_Motores(){
	setVoltajeMotor1(1);
	setVoltajeMotor2(1);
	setVoltajeMotor3(1);
	setVoltajeMotor4(1);
}

void Piloto :: desplazar(int direccion){

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
	}
	outputPines();
}


