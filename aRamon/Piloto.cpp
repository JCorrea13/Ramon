#include "Arduino.h"
#include "Piloto.h"
#include "SensoresRamon.h"

Piloto :: Piloto(Motor motor1, Motor motor2, Motor motor3, Motor motor4, Servo aterrizaje){//const int PIN_TRENATERRIZAJE){

	tren_Aterrizaje = aterrizaje;
        estado_trenAterrizaje = true;        
        
        this->estado_motores = false;
        //dejamos los voltajes en cero para obligar a cambiarlos
        setVoltajes(0,0,0,0);
        
        //Inicializamos motores
        this->motor1 = motor1;
        this->motor2 = motor2;
        this->motor3 = motor3;
        this->motor4 = motor4;
}

void Piloto :: inicializaMotores(){
    motor1.write(22);
    motor2.write(22);
    motor3.write(22);
    motor4.write(22);   
    delay(7000);
    
    motor1.write(35);
    motor2.write(35);
    motor3.write(35);
    motor4.write(35);
    delay(1000);
    
    motor1.write(22);
    motor2.write(22);
    motor3.write(22);
    motor4.write(22);   
    
    setVoltajeMotor1(voltajeApagado);
    setVoltajeMotor2(voltajeApagado);
    setVoltajeMotor3(voltajeApagado);
    setVoltajeMotor4(voltajeApagado);
}

void Piloto :: setVoltajes(int voltajeAlto, int voltajeMedio, int voltajeBajo, int voltajeApagado){
        this->voltajeAlto = voltajeAlto;
        this->voltajeMedio = voltajeMedio;  
        this->voltajeBajo = voltajeBajo;	
        this->voltajeApagado = voltajeApagado;
}

void Piloto :: setVoltajeMotor1(int voltaje){
	motor1.setVoltaje(voltaje);
}
void Piloto :: setVoltajeMotor2(int voltaje){
	motor2.setVoltaje(voltaje);
}
void Piloto :: setVoltajeMotor3(int voltaje){
	motor3.setVoltaje(voltaje);
}
void Piloto :: setVoltajeMotor4(int voltaje){
	motor4.setVoltaje(voltaje);
}

//El valor que se puede escribir en los pines digitamles va desde 0 hasta 1023
void Piloto :: outputPines(){
	/*motor1.actulizaVoltaje();
	motor2.actulizaVoltaje();
	motor3.actulizaVoltaje();
	motor4.actulizaVoltaje();*/
        
        motor1.write(motor1.getVoltaje());
        motor2.write(motor2.getVoltaje());
        motor3.write(motor3.getVoltaje());
        motor4.write(motor4.getVoltaje());
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
        if(estado_trenAterrizaje){
            Serial.println("True");
            tren_Aterrizaje.write(17);            
        }else{
            Serial.println("False");          
            tren_Aterrizaje.write(180);          
        }
        estado_trenAterrizaje = (!estado_trenAterrizaje);
}

void Piloto :: on_off_Motores(){
  //if(estado_motores){
    motor1.setVoltaje(voltajeApagado);
    motor2.setVoltaje(voltajeApagado);
    motor3.setVoltaje(voltajeApagado);
    motor4.setVoltaje(voltajeApagado);
  //estado_motores = (!estado_motores);
  outputPines();
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
                                Serial.println("Modo estatico");
				break;
		case DIR_IZQUIERDA:
				setConfi_DirIzquierda();
                                Serial.println("Izquierda");
				break;
		case DIR_DERECHA:
				setConfi_DirDerecha();
                                Serial.println("Derecha");
				break;
		case DIR_ADELANTE:
				setConfi_DirAdelante();
                                Serial.println("Adelante");
				break;
		case DIR_ATRAS:
				setConfi_DirAtras();
                                Serial.println("Atras");
				break;
		case DIR_BAJAR:
				setConfi_DirBajar();
                                Serial.println("Bajar");
				break;
		case DIR_SUBIR:
				setConfi_DirSubir();
                                Serial.println("Subir");
				break;
		case ATERRIZA:
				setConfi_DirAterrizar();
                                Serial.println("Aterrizar");
				break;
		case TREN_ATERRIZAJE_CAMBIOESTADO:
				cambioEstado_trenAterrizaje();
                                Serial.println("Tren Aterrizaje");
				break;
		case ON_OFF_MOTORES:
				on_off_Motores();
                                Serial.println("On off motores");
				break;
		case CONFIMACION_CONEXION:
				confirmaConexion();
                                Serial.println("Confima coneixon");  
				break;
	}
	outputPines();
}
