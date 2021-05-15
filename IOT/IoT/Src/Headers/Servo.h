/*
 * Servo.h
 *
 * Created: 5/15/2021 3:30:51 PM
 *  Author: vasil
 */ 
#pragma once

#include "ATMEGA_FreeRTOS.h"

void servo_turnServoTask( void *pvParameters );
void servo_init();
