/*
 * TempHum.c
 *
 * Created: 5/15/2021 3:25:44 PM
 *  Author: vasil
 */ 
#include <stdio.h>
#include <stdlib.h>
#include <hih8120.h>

#include "../Headers/TempHum.h"
#include "../Headers/dataShare.h"


void tempHum_getDataFromTempHumSensorTask( void *pvParameters )
{
	TickType_t xLastWakeTime;
	const TickType_t xFrequency = 5000/portTICK_PERIOD_MS; // 1000 ms

	//Initialise the xLastWakeTime variable with the current time.
	xLastWakeTime = xTaskGetTickCount();

	for(;;)
	{
		xTaskDelayUntil( &xLastWakeTime, xFrequency );
		//puts("-getDataFromTempHumSensorTask");
		
		if (HIH8120_OK != hih8120_wakeup())
		{
			vTaskDelay(pdMS_TO_TICKS(100));
			printf("-Temp/humidity sensor couldn't wake up trying again");
			while(HIH8120_OK != hih8120_wakeup())
			{
				vTaskDelay(pdMS_TO_TICKS(100));
			}
		}
		
		vTaskDelay(pdMS_TO_TICKS(50));
		
		if (HIH8120_OK !=  hih8120_measure() )
		{
			vTaskDelay(pdMS_TO_TICKS(100));
			printf("-Temp/humidity sensor couldn't measure trying again");
			while(HIH8120_OK !=  hih8120_measure())
			{
				vTaskDelay(pdMS_TO_TICKS(100));
			}
		}
		
		vTaskDelay(pdMS_TO_TICKS(500));
		
		
		humidity =  hih8120_getHumidity();
		temperature = hih8120_getTemperature();
		
		
		//printf("------VALUES FOUND TEMP: %d  ----- HUM: %d\n", (int)temperature, (int)humidity );
		
		
		//PORTA ^= _BV(PA7);
	}
}

void tempHum_init() {
		if ( HIH8120_OK == hih8120_initialise() )
		{
			// Driver initialised OK
			// Always check what hih8120_initialise() returns
		}	else printf("Driver doesn't start");
}
