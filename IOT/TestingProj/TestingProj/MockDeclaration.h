#pragma once
#include "../fff-master/fff.h"

extern "C" {
	//Simulated FreeRTOS header fies
#include "FreeRTOS.h"
#include "Task.h"
#include "message_buffer.h"
}

// -- Declare mock for FreeRTOS --
//tasks
//xTaskCreate
DECLARE_FAKE_VALUE_FUNC(BaseType_t, xTaskCreate, TaskFunction_t, const char*, configSTACK_DEPTH_TYPE, void*, UBaseType_t, TaskHandle_t*);
//xTaskDelayUntil
DECLARE_FAKE_VOID_FUNC(xTaskDelayUntil, TickType_t*, TickType_t);
//vTaskDelay
DECLARE_FAKE_VOID_FUNC(vTaskDelay, TickType_t);
//xTaskGetTickCount
DECLARE_FAKE_VALUE_FUNC(TickType_t, xTaskGetTickCount);
//taskYIELD
DECLARE_FAKE_VOID_FUNC(taskYIELD);
//message buffers
//xMessageBufferReceive
DECLARE_FAKE_VALUE_FUNC(size_t, xMessageBufferReceive, MessageBufferHandle_t, void*, size_t, TickType_t);