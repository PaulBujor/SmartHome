/*
 * dataShare.h
 *
 * Created: 23-Apr-21 3:14:33 PM
 *  Author: dobre
 */ 


#ifndef DATASHARE_H_
#define DATASHARE_H_

extern float temperature;
extern float humidity;

extern uint16_t ppm;

extern uint16_t lastSoundValue;

extern float servoTemperature;
extern float servoHumidity;

extern uint16_t servoPpm;

extern MessageBufferHandle_t downLinkMessageBufferHandle;





#endif /* DATASHARE_H_ */