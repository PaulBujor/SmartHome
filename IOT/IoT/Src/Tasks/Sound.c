/*
 * Sound.c
 *
 * Created: 5/15/2021 3:21:06 PM
 *  Author: vasil
 */ 
#include <stdio.h>
#include <stdlib.h>
#include <sen14262.h>

#include "../Headers/Sound.h"
#include "../Headers/dataShare.h"

TickType_t xLastWakeTime;
TickType_t xFrequency;

void sound_taskRun(void) {
		xTaskDelayUntil( &xLastWakeTime, xFrequency );
		//puts("-getDataFromSoundSensorTask");
		
		lastSoundValue = sen14262_envelope();
		
		
		//printf("------Sound ----%u \n", (unsigned int)lastSoundValue);
		
		
		//PORTA ^= _BV(PA7);
}

void sound_getDataFromSoundSensorTask( void *pvParameters )
{
	xFrequency = 5000/portTICK_PERIOD_MS; // 1000 ms

	//Initialise the xLastWakeTime variable with the current time.
	xLastWakeTime = xTaskGetTickCount();

	for(;;)
	{
		sound_taskRun();
	}
}

void sound_init() {
	sen14262_initialise();
}
