#include "Arduino.h"
#include "Piloto.h"
#include "SensoresRamon.h"

Piloto :: Piloto(Motor motor1, Motor motor2, Motor motor3, Motor motor4, Servo aterrizaje){//const int PIN_TRENATERRIZAJE){

	this->tren_Aterrizaje = aterrizaje;
        this->estado_trenAterrizaje = true;
        
        //dejamos los voltajes en cero para obligar a cambiarlos
        setVoltajes(0,0,0,0);
        
        //Inicializamos motores
        this->motor1 = motor1;
        this->motor2 = motor2;
        this->motor3 = motor3;
        this->motor4 = motor4;
        this->desplazar(MODO_ESTATICO);
}

void Piloto :: setVoltajes(int voltajeAlto, int voltajeMedio, int voltajeBajo, int voltajeApagado){
        this->voltajeAlto = voltajeAlto;
        this->voltajeMedio = voltajeMedio;  
        this->voltajeBajo = voltajeBajo;	
        this->voltajeApagado = voltajeApagado;
}

void Piloto :: setVoltajeMotor1(int voltaje){
	this->motor1.setVoltaje(voltaje);
}
void Piloto :: setVoltajeMotor2(int voltaje){
	this->motor2.setVoltaje(voltaje);
}
void Piloto :: setVoltajeMotor3(int voltaje){
	this->motor3.setVoltaje(voltaje);
}
void Piloto :: setVoltajeMotor4(int voltaje){
	this->motor4.setVoltaje(voltaje);
}

//El valor que se puede escribir en los pines digitamles va desde 0 hasta 1023
void Piloto :: outputPines(){
	this->motor1.actulizaVoltaje();
	this->motor2.actulizaVoltaje();
	this->motor3.actulizaVoltaje();
	this->motor4.actulizaVoltaje();
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
        Serial.print("Cambio tren Aterrizaje: ");
        if(this->estado_trenAterrizaje){
            Serial.println("True");
            this->tren_Aterrizaje.write(0);            
            this->estado_trenAterrizaje = false;
        }else{
            Serial.println("False");          
            this->tren_Aterrizaje.write(150);          
            this->estado_trenAterrizaje = true;
        }
}

void Piloto :: on_off_Motores(){
	
	if(this->motor1.getVoltaje() > this->voltajeApagado)
		this->motor1.setVoltaje(this->voltajeApagado);
		this->motor2.setVoltaje(this->voltajeApagado);
		this->motor3.setVoltaje(this->voltajeApagado);
		this->motor4.setVoltaje(this->voltajeApagado);
		
	if(this->motor1.getVoltaje() <= this->voltajeApagado)
		this->motor1.setVoltaje(this->voltajeMedio);
		this->motor2.setVoltaje(this->voltajeMedio);
		this->motor3.setVoltaje(this->voltajeMedio);
		this->motor4.setVoltaje(this->voltajeMedio);
		
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
