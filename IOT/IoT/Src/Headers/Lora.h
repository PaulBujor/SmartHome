/*
 * Lora.h
 *
 * Created: 5/15/2021 3:50:37 PM
 *  Author: vasil
 */ 
#include <ATMEGA_FreeRTOS.h>
#include "../Src/Headers/downLink.h"

static char _out_buf[100];
static lora_driver_payload_t _uplink_payload;
lora_driver_payload_t downlinkPayload;
static bool onOffSwitch = true;

void lora_taskRun(void);
void lora_handler_task( void *pvParameters );
void lora_handler_initialise(UBaseType_t lora_handler_task_priority);
