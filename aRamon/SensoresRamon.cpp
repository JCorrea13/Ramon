#include "Arduino.h"
#include "Wire.h"
#include "SensoresRamon.h"

//Acelerometro
const int pinAcelerometro = 12;
int16_t AcX,AcY,AcZ,Tmp,GyX,GyY,GyZ;


SensoresRamon :: SensoresRamon(){
        //GPS
	this->latitud = 0; 
	this->longitud = 0;
	this->altitud = 0;
	this->velocidad = 0;
	this->temperatura = 0;

        //ACELEROMETRO Y GIROSCOPIO
        Wire.begin();
        Wire.beginTransmission(pinAcelerometro);
        Wire.write(0x6B);  // PWR_MGMT_1 register
        Wire.write(0);     // set to zero (wakes up the pinAcelerometro-6050)
        Wire.endTransmission(true);
}

void SensoresRamon :: actualizaGPS(){
	if(Serial.available()){ 

		int c = Serial.read();

		if (gps.encode(c))
		{
		  long lat, lon;
		  this->altitud = gps.f_altitude();
		  this->velocidad = gps.f_speed_kmph(); 
		  gps.get_position(&lat, &lon);
	  	  this->latitud = lat;
		  this->longitud = lon;
	        }	
        }
}

void SensoresRamon :: acutualizaAcelerometroGiroscopio(){
    Wire.beginTransmission(pinAcelerometro);
    Wire.write(0x3B);  // starting with register 0x3B (ACCEL_XOUT_H)
    Wire.endTransmission(false);
    Wire.requestFrom(pinAcelerometro,14,true);  // request a total of 14 registers
    AcX=Wire.read()<<8|Wire.read();  // 0x3B (ACCEL_XOUT_H) & 0x3C (ACCEL_XOUT_L)    
    AcY=Wire.read()<<8|Wire.read();  // 0x3D (ACCEL_YOUT_H) & 0x3E (ACCEL_YOUT_L)
    AcZ=Wire.read()<<8|Wire.read();  // 0x3F (ACCEL_ZOUT_H) & 0x40 (ACCEL_ZOUT_L)
    Tmp=Wire.read()<<8|Wire.read();  // 0x41 (TEMP_OUT_H) & 0x42 (TEMP_OUT_L)
    GyX=Wire.read()<<8|Wire.read();  // 0x43 (GYRO_XOUT_H) & 0x44 (GYRO_XOUT_L)
    GyY=Wire.read()<<8|Wire.read();  // 0x45 (GYRO_YOUT_H) & 0x46 (GYRO_YOUT_L)
    GyZ=Wire.read()<<8|Wire.read();  // 0x47 (GYRO_ZOUT_H) & 0x48 (GYRO_ZOUT_L)

    Serial.print("AcX = "); Serial.print(AcX);
    Serial.print(" | AcY = "); Serial.print(AcY);
    Serial.print(" | AcZ = "); Serial.print(AcZ);
    Serial.print(" | Tmp = "); Serial.print(Tmp/340.00+36.53);  //equation for temperature in degrees C from datasheet
    Serial.print(" | GyX = "); Serial.print(GyX);
    Serial.print(" | GyY = "); Serial.print(GyY);
    Serial.print(" | GyZ = "); Serial.println(GyZ);   

}

char * SensoresRamon :: getCadenaSensores(){
	actualizaGPS();
        acutualizaAcelerometroGiroscopio();
        char paso[100] = "";
        char * separador = "|";
	char* resultado;
	
	resultado = "¥"; //comienzo del paquete
	//datos GPS
	resultado = resultado + strlen(dtostrf(this->latitud,4,3,paso))
        + strlen(separador) 
        + strlen(dtostrf(this->longitud,4,3,paso)) 
        + strlen(separador)
	+ strlen(dtostrf(this->altitud,4,3,paso)) 
        + strlen(separador) 
        + strlen(dtostrf(this->velocidad,4,3,paso)) 
        + strlen(separador)
	+ strlen(dtostrf(this->temperatura,4,3,paso));
		
	return resultado;
}



