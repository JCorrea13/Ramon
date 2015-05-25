#include "TinyGPS.h"
#include "Wire.h"


//Constantes de Acelerometro y Giroscopio 
#define MPU 0x68
#define A_R 16384.0
#define G_R 131.0
#define RAD_A_DEG = 57.295779

class SensoresRamon{
	
	
	private:
                //GPS
		TinyGPS gps;
		double latitud; 
		double longitud;
		float altitud;
		float velocidad ;
		float temperatura;
		void actualizaGPS();

                //Acelerometro y Giroscopio
	        int16_t AcX, AcY, AcZ, GyX, GyY, GyZ;
                float Acc[2];
                float Gy[2];
                float Angle[2];
                void acutualizaAcelerometroGiroscopio();
                        
      
	public:
                SensoresRamon();
		char * getCadenaSensores();
	
};
