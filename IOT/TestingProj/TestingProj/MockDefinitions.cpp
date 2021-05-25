#include "MockDeclaration.h"
DEFINE_FFF_GLOBALS

// -- Defining mocks for FreeRTOS functions --
//tasks
//xTaskCreate
DEFINE_FAKE_VALUE_FUNC(BaseType_t, xTaskCreate, TaskFunction_t, const char*, configSTACK_DEPTH_TYPE, void*, UBaseType_t, TaskHandle_t*);
//vTaskDelayUntil
DEFINE_FAKE_VOID_FUNC(xTaskDelayUntil, TickType_t*, TickType_t);
//vTaskDelay
DEFINE_FAKE_VOID_FUNC(vTaskDelay, TickType_t);
//xTaskGetTickCount
DEFINE_FAKE_VALUE_FUNC(TickType_t, xTaskGetTickCount);
//taskYIELD
DEFINE_FAKE_VOID_FUNC(taskYIELD);
//message buffer
//xMessageBufferReceive
DEFINE_FAKE_VALUE_FUNC(size_t, xMessageBufferReceive, MessageBufferHandle_t, void*, size_t, TickType_t);

