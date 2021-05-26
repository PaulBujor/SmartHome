/*
 * Sound.h
 *
 * Created: 5/15/2021 3:20:14 PM
 *  Author: vasil
 */ 

#pragma once

#include "ATMEGA_FreeRTOS.h"
#include <stdint.h>

void sound_taskRun(void);
void sound_getDataFromSoundSensorTask( void *pvParameters );
void sound_init();
