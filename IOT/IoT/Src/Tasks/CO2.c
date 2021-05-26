/*
 * CO2.c
 *
 * Created: 5/15/2021 2:27:01 PM
 *  Author: vasil
 */ 
#include <stdio.h>
#include <stdlib.h>
#include <mh_z19.h>

#include "../Headers/CO2.h"
#include "../Headers/dataShare.h"

TickType_t xLastWakeTime;
TickType_t xFrequency;

void co2_taskRun(void) {
		xTaskDelayUntil( &xLastWakeTime, xFrequency );
		//puts("-getDataFromCO2SensorTask");
		
		mh_z19_returnCode_t rc;
		
		rc = mh_z19_takeMeassuring();
		
		vTaskDelay(pdMS_TO_TICKS(1000));
		
		if (rc != MHZ19_OK)
		{
			printf("Something went wrong with the CO2 sensor");
		}
		
		mh_z19_getCo2Ppm(&ppm);
		
		
		//printf("------CO2 ----%u \n", (unsigned int)ppm);
		
		
		//PORTA ^= _BV(PA7);
}

static void delayTask(void) {
	xFrequency = 5000 / portTICK_PERIOD_MS; // 1000 ms

	//Initialise the xLastWakeTime variable with the current time.
	xLastWakeTime = xTaskGetTickCount();
}


void co2_getDataFromCO2SensorTask( void *pvParameters )
{
	delayTask();

	for(;;)
	{
		co2_taskRun();
	}
}

void co2_initCO2Sensor() {
	mh_z19_initialise(ser_USART3); 
}