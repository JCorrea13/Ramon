#include "Arduino.h"
#include "Servo.h"
#include "Motor.h"

#define MODO_ESTATICO '0'
#define DIR_IZQUIERDA '1'
#define DIR_DERECHA '2'
#define DIR_ADELANTE '3'
#define DIR_ATRAS '4'
#define DIR_BAJAR '5'
#define DIR_SUBIR '6'
#define ATERRIZA '7'
#define TREN_ATERRIZAJE_CAMBIOESTADO '8'
#define ON_OFF_MOTORES '9'
#define CONFIMACION_CONEXION 'A'

/**
 * Esta clase tiene como objetivo unicamente pilotear a Ramon
 * da soporte a las configuraciones de los motores de Ramon, solo se debe inicializar
 * pasando como parametro 5 pines, los referentes a los 4 motores y el referente al tren de aterrizaje
 * y de desearse la configuraicon inicial de los motores
 * 
 * El unico metod que se debe llamar para indicar un movimiento es ´desplazar´ que recibe como parametro
 * un entero que indica la configuaraicon de los motores que se establecera
 * */
class Piloto{	
	private:

		Servo *tren_Aterrizaje;	
                boolean estado_trenAterrizaje;	
		int voltajeMotor1;
		int voltajeMotor2;
		int voltajeMotor3;
		int voltajeMotor4;
                		
		Servo *motor1;
		Servo *motor2;
		Servo *motor3;
		Servo *motor4;


                int voltajeAlto;
                int voltajeMedio;
                int voltajeBajo;
                int voltajeApagado;

		void setVoltajeMotor1(int);
		void setVoltajeMotor2(int);
		void setVoltajeMotor3(int);
		void setVoltajeMotor4(int);
		
		void setConfi_DirIzquierda();
		void setConfi_DirDerecha();
		void setConfi_DirAdelante();
		void setConfi_DirAtras();
		void setConfi_DirSubir();
		void setConfi_DirBajar();
		void setConfi_DirModoEstatico();
		void setConfi_DirAterrizar();
		void cambioEstado_trenAterrizaje();
		void on_off_Motores();
		void outputPines();
                void confirmaConexion();
	public:
		Piloto(const int,const int,const int,const int,const int);
		void desplazar(int);
                void setVoltajes(int, int, int, int); // este metodo setea los valores para voltajeAlto,voltajeMedio, voltajeBajo, voltajeApagado
};
