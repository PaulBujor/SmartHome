/*
* main.c
* Author : IHA
*
* Example main file including LoRaWAN setup
* Just for inspiration :)
*/

#include <stdio.h>
#include <avr/io.h>

#include <ATMEGA_FreeRTOS.h>
#include <task.h>
#include <semphr.h>

#include <stdio_driver.h>
#include <serial.h>

// Needed for LoRaWAN
#include <lora_driver.h>
#include <status_leds.h>
MessageBufferHandle_t downLinkMessageBufferHandle;

//Sensor drivers
#include <hih8120.h>
#include <mh_z19.h>
#include <sen14262.h>
#include <stdint.h>



// define the Tasks
void getDataFromTempHumSensorTask( void *pvParameters );
void getDataFromCO2SensorTask( void *pvParameters );
void getDataFromSoundSensorTask( void *pvParameters );
void turnServoTask( void *pvParameters );

//define sensor data

float temperature = 0.0;
float humidity = 0.0;

uint16_t ppm;

uint16_t lastSoundValue;

//servo thresholds



float servoMinTemperature = 20;
float servoMaxTemperature = 30;

float servoMinHumidity = 10;
float servoMaxHumidity = 300;

uint16_t servoMinPPM = 30;
uint16_t servoMaxPPM = 3900;



// define semaphore handle
SemaphoreHandle_t xTestSemaphore;

// Prototype for LoRaWAN handler
void lora_handler_initialise(UBaseType_t lora_handler_task_priority);

/*-----------------------------------------------------------*/
void create_tasks_and_semaphores(void)
{
	// Semaphores are useful to stop a Task proceeding, where it should be paused to wait,
	// because it is sharing a resource, such as the Serial port.
	// Semaphores should only be used whilst the scheduler is running, but we can set it up here.
	if ( xTestSemaphore == NULL )  // Check to confirm that the Semaphore has not already been created.
	{
		xTestSemaphore = xSemaphoreCreateMutex();  // Create a mutex semaphore.
		if ( ( xTestSemaphore ) != NULL )
		{
			xSemaphoreGive( ( xTestSemaphore ) );  // Make the mutex available for use, by initially "Giving" the Semaphore.
		}
	}
	
	xTaskCreate(
	getDataFromTempHumSensorTask
	,  "getDataFromTempHumSensorTask"  // A name just for humans
	,  configMINIMAL_STACK_SIZE  // This stack size can be checked & adjusted by reading the Stack Highwater
	,  NULL
	,  2  // Priority, with 3 (configMAX_PRIORITIES - 1) being the highest, and 0 being the lowest.
	,  NULL );
	
	
	xTaskCreate(
	getDataFromCO2SensorTask
	,  "getDataFromCO2SensorTask"  // A name just for humans
	,  configMINIMAL_STACK_SIZE  // This stack size can be checked & adjusted by reading the Stack Highwater
	,  NULL
	,  2  // Priority, with 3 (configMAX_PRIORITIES - 1) being the highest, and 0 being the lowest.
	,  NULL );
	
	
	xTaskCreate(
	getDataFromSoundSensorTask
	,  "getDataFromSoundSensorTask"  // A name just for humans
	,  configMINIMAL_STACK_SIZE  // This stack size can be checked & adjusted by reading the Stack Highwater
	,  NULL
	,  2  // Priority, with 3 (configMAX_PRIORITIES - 1) being the highest, and 0 being the lowest.
	,  NULL );
	
	xTaskCreate(
	turnServoTask
	,  "turnServoTask"  // A name just for humans
	,  configMINIMAL_STACK_SIZE  // This stack size can be checked & adjusted by reading the Stack Highwater
	,  NULL
	,  2  // Priority, with 3 (configMAX_PRIORITIES - 1) being the highest, and 0 being the lowest.
	,  NULL );
}


void getDataFromTempHumSensorTask( void *pvParameters )
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

/*-----------------------------------------------------------*/

void getDataFromCO2SensorTask( void *pvParameters )
{
	TickType_t xLastWakeTime;
	const TickType_t xFrequency = 5000/portTICK_PERIOD_MS; // 1000 ms

	//Initialise the xLastWakeTime variable with the current time.
	xLastWakeTime = xTaskGetTickCount();

	for(;;)
	{
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
}

void getDataFromSoundSensorTask( void *pvParameters )
{
	TickType_t xLastWakeTime;
	const TickType_t xFrequency = 5000/portTICK_PERIOD_MS; // 1000 ms

	//Initialise the xLastWakeTime variable with the current time.
	xLastWakeTime = xTaskGetTickCount();

	for(;;)
	{
		xTaskDelayUntil( &xLastWakeTime, xFrequency );
		//puts("-getDataFromSoundSensorTask");
		
		lastSoundValue = sen14262_envelope();
		
		
		//printf("------Sound ----%u \n", (unsigned int)lastSoundValue);
		
		
		//PORTA ^= _BV(PA7);
	}
}

void turnServoTask( void *pvParameters )
{
	TickType_t xLastWakeTime;
	const TickType_t xFrequency = 5000/portTICK_PERIOD_MS; // 1000 ms

	//Initialise the xLastWakeTime variable with the current time.
	xLastWakeTime = xTaskGetTickCount();

	for(;;)
	{
		xTaskDelayUntil( &xLastWakeTime, xFrequency );
		//puts("-getDataFromMotionSensorTask");
		
		//printf("Servotask---Hum = %d Temp = %d PPm = %d Sound = %d\n", (int)humidity, (int)temperature, ppm, lastSoundValue);
		
		//printf("Servotask---Hum = %d Temp = %d PPm = %d \n Hum = %d Temp = %d PPm = %d Sound = %d\n", (int)servoHumidity, (int)servoTemperature, servoPpm, );
		if(servoMaxTemperature + 1 < temperature || servoMaxPPM + 20 < ppm || servoMaxHumidity + 1 < humidity)
		{
			rc_servo_setPosition(1, -100); //OPEN
		}
		else if(servoMinTemperature > temperature && servoMinPPM >ppm && servoMinHumidity > humidity) 
			rc_servo_setPosition(1, 100); //CLOSE
		
	}
}

/*-----------------------------------------------------------*/
void initialiseSystem()
{
	//Initialise sensor drivers
	
	if ( HIH8120_OK == hih8120_initialise() )
	{
		// Driver initialised OK
		// Always check what hih8120_initialise() returns
	}	else printf("Driver doesn't start");
	
	mh_z19_initialise(ser_USART3); 
	
	sen14262_initialise();
	
	rc_servo_initialise(); 
	
	
	
	
	// Set output ports for leds used in the example
	DDRA |= _BV(DDA0) | _BV(DDA7);

	// Make it possible to use stdio on COM port 0 (USB) on Arduino board - Setting 57600,8,N,1
	stdio_initialise(ser_USART0);
	// Let's create some tasks
	create_tasks_and_semaphores();

	// vvvvvvvvvvvvvvvvv BELOW IS LoRaWAN initialisation vvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
	// Status Leds driver
	status_leds_initialise(5); // Priority 5 for internal task
	// Initialise the LoRaWAN driver without down-link buffer
	//lora_driver_initialise(1, NULL);
	
	downLinkMessageBufferHandle = xMessageBufferCreate(sizeof(lora_driver_payload_t)*2); // Here I make room for two downlink messages in the message buffer
	lora_driver_initialise(ser_USART1, downLinkMessageBufferHandle); // The parameter is the USART port the RN2483 module is connected to - in this case USART1 - here no message buffer for down-link messages are defined
	
	
	// Create LoRaWAN task and start it up with priority 3
	lora_handler_initialise(3);
	

	

}

/*-----------------------------------------------------------*/
int main(void)
{
	initialiseSystem(); // Must be done as the very first thing!!
	printf("-Program Started!!\n");
	vTaskStartScheduler(); // Initialise and run the freeRTOS scheduler. Execution should never return from here.

	/* Replace with your application code */


	while (1)
	{
	}
	
	return 0;
}