/*
 * TempHum.h
 *
 * Created: 5/15/2021 3:24:46 PM
 *  Author: vasil
 */ 
#pragma once

#include "ATMEGA_FreeRTOS.h"
#include <stdint.h>

void tempHum_taskRun(void);
void tempHum_getDataFromTempHumSensorTask( void *pvParameters );
void tempHum_init();
