/*
 * CO2.h
 *
 * Created: 5/15/2021 2:12:52 PM
 *  Author: vasil
 */ 
#pragma once

#include "ATMEGA_FreeRTOS.h"
#include <stdint.h>

void co2_taskRun(void);
void co2_getDataFromCO2SensorTask( void *pvParameters );
void co2_initCO2Sensor();
