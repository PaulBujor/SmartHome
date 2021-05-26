/*
 * Servo.c
 *
 * Created: 5/15/2021 3:32:03 PM
 *  Author: vasil
 */ 
#include <stdio.h>
#include <stdlib.h>
#include <rc_servo.h>

#include "../Headers/Servo.h"
#include "../Headers/dataShare.h"

TickType_t xLastWakeTime;
TickType_t xFrequency;

void servo_taskRun(void) {
		xTaskDelayUntil( &xLastWakeTime, xFrequency );
		//puts("-getDataFromMotionSensorTask");
		
		//printf("Servotask---Hum = %d Temp = %d PPm = %d Sound = %d\n", (int)humidity, (int)temperature, ppm, lastSoundValue);
		
		//printf("Servotask---Hum = %d Temp = %d PPm = %d \n Hum = %d Temp = %d PPm = %d Sound = %d\n", (int)servoHumidity, (int)servoTemperature, servoPpm, );
		if(servoMaxTemperature + 1 < temperature || servoMaxPPM + 20 < ppm || servoMaxHumidity + 1 < humidity)
		{
			rc_servo_setPosition(1, -100); //OPEN
		}
		else if(servoMinTemperature > temperature && servoMinPPM > ppm && servoMinHumidity > humidity)
		rc_servo_setPosition(1, 100); //CLOSE	
}

void servo_turnServoTask( void *pvParameters )
{
	xFrequency = 5000/portTICK_PERIOD_MS; // 1000 ms

	//Initialise the xLastWakeTime variable with the current time.
	xLastWakeTime = xTaskGetTickCount();

	for(;;)
	{
		servo_taskRun();
	}
}

void servo_init() {
	rc_servo_initialise();
}
