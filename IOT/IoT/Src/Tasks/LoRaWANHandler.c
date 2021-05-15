/*
* loraWANHandler.c
*
* Created: 12/04/2019 10:09:05
*  Author: IHA
*/
#include <stddef.h>
#include <stdio.h>

#include <ATMEGA_FreeRTOS.h>

#include <lora_driver.h>
#include <status_leds.h>

#include "../Src/Headers/dataShare.h" 
#include "../Src/Headers/configuration.h"
#include "../Src/Headers/Lora.h"

void lora_handler_initialise(UBaseType_t lora_handler_task_priority)
{
	xTaskCreate(
	lora_handler_task
	,  "LRHand"  // A name just for humans
	,  configMINIMAL_STACK_SIZE+200  // This stack size can be checked & adjusted by reading the Stack Highwater
	,  NULL
	,  lora_handler_task_priority  // Priority, with 3 (configMAX_PRIORITIES - 1) being the highest, and 0 being the lowest.
	,  NULL );
}

static void _lora_setup(void)
{
	lora_driver_returnCode_t rc;
	status_leds_slowBlink(led_ST2); // OPTIONAL: Led the green led blink slowly while we are setting up LoRa

	// Factory reset the transceiver
	printf("FactoryReset >%s<\n", lora_driver_mapReturnCodeToText(lora_driver_rn2483FactoryReset()));
	
	// Configure to EU868 LoRaWAN standards
	printf("Configure to EU868 >%s<\n", lora_driver_mapReturnCodeToText(lora_driver_configureToEu868()));

	// Get the transceivers HW EUI
	rc = lora_driver_getRn2483Hweui(_out_buf);
	printf("Get HWEUI >%s<: %s\n",lora_driver_mapReturnCodeToText(rc), _out_buf);

	// Set the HWEUI as DevEUI in the LoRaWAN software stack in the transceiver
	printf("Set DevEUI: %s >%s<\n", _out_buf, lora_driver_mapReturnCodeToText(lora_driver_setDeviceIdentifier(_out_buf)));

	// Set Over The Air Activation parameters to be ready to join the LoRaWAN
	printf("Set OTAA Identity appEUI:%s appKEY:%s devEUI:%s >%s<\n", LORA_appEUI, LORA_appKEY, _out_buf, lora_driver_mapReturnCodeToText(lora_driver_setOtaaIdentity(LORA_appEUI,LORA_appKEY,_out_buf)));

	// Save all the MAC settings in the transceiver
	printf("Save mac >%s<\n",lora_driver_mapReturnCodeToText(lora_driver_saveMac()));

	// Enable Adaptive Data Rate
	printf("Set Adaptive Data Rate: ON >%s<\n", lora_driver_mapReturnCodeToText(lora_driver_setAdaptiveDataRate(LORA_ON)));

	// Set receiver window1 delay to 500 ms - this is needed if down-link messages will be used
	printf("Set Receiver Delay: %d ms >%s<\n", 500, lora_driver_mapReturnCodeToText(lora_driver_setReceiveDelay(500)));

	// Join the LoRaWAN
	uint8_t maxJoinTriesLeft = 100;
	
	do {
		rc = lora_driver_join(LORA_OTAA);
		printf("Join Network TriesLeft:%d >%s<\n", maxJoinTriesLeft, lora_driver_mapReturnCodeToText(rc));

		if ( rc != LORA_ACCEPTED)
		{
			// Make the red led pulse to tell something went wrong
			status_leds_longPuls(led_ST1); // OPTIONAL
			// Wait 5 sec and lets try again
			vTaskDelay(pdMS_TO_TICKS(5000UL));
		}
		else
		{
			break;
		}
		if(maxJoinTriesLeft == 0)
		{
			printf("Trying to reset");
			lora_driver_resetRn2483(1);
			
			vTaskDelay(pdMS_TO_TICKS(5000UL));
			
			lora_driver_resetRn2483(0);
			
			maxJoinTriesLeft = 100;
		}
	} while (--maxJoinTriesLeft);

	if (rc == LORA_ACCEPTED)
	{
		// Connected to LoRaWAN :-)
		// Make the green led steady
		status_leds_ledOn(led_ST2); // OPTIONAL
	}
	else
	{
		// Something went wrong
		// Turn off the green led
		status_leds_ledOff(led_ST2); // OPTIONAL
		// Make the red led blink fast to tell something went wrong
		status_leds_fastBlink(led_ST1); // OPTIONAL
		

		// Lets stay here
		while (1)
		{
			taskYIELD();
		}
	}
}

/*-----------------------------------------------------------*/
void lora_handler_task( void *pvParameters )
{
	// Hardware reset of LoRaWAN transceiver
	lora_driver_resetRn2483(1);
	vTaskDelay(2);
	lora_driver_resetRn2483(0);
	vTaskDelay(2);
	lora_driver_flushBuffers();
	// Give it a chance to wakeup
	vTaskDelay(150);

	lora_driver_flushBuffers(); // get rid of first version string from module after reset!

	_lora_setup();

	_uplink_payload.len = 13;
	_uplink_payload.portNo = 2;

	TickType_t xLastWakeTime;
	const TickType_t xFrequency = pdMS_TO_TICKS(300000UL); // Upload message every 5 minutes (300000 ms)
	xLastWakeTime = xTaskGetTickCount();
	
	for(;;)
	{
		//xTaskDelayUntil( &xLastWakeTime, xFrequency );
		
		//float temperature = 26.5;
		//float humidity = 132.2;

		//uint16_t ppm = 100;

		//uint16_t lastSoundValue = 15;

	

		//bool motion = false;



	
		
		if(!onOffSwitch)
		{
			int i;
			for(i = 0; i < 8; i++)
				_uplink_payload.bytes[i] = 0;
		}
		else 
		{
			_uplink_payload.bytes[0] = (int)humidity >> 8;
			_uplink_payload.bytes[1] = (int)humidity & 0xFF;
			
			_uplink_payload.bytes[2] = (int)temperature >> 8;
			_uplink_payload.bytes[3] = (int)temperature & 0xFF;
			
			_uplink_payload.bytes[4] = ppm >> 8;
			_uplink_payload.bytes[5] = ppm & 0xFF;
			
			_uplink_payload.bytes[6] = lastSoundValue >> 8;
			_uplink_payload.bytes[7] = lastSoundValue & 0xFF;
		}
		
		
		
		printf("---Hum = %d Temp = %d PPm = %d Sound = %d\n", (int)humidity, (int)temperature, ppm, lastSoundValue);

		status_leds_shortPuls(led_ST4);  // OPTIONAL
		
		
		lora_driver_returnCode_t rc;
		
		if ((rc = lora_driver_sendUploadMessage(false, &_uplink_payload)) == LORA_MAC_TX_OK )
		{
			// The uplink message is sent and there is no downlink message received
		}
		else if (rc == LORA_MAC_RX)
		{
			xMessageBufferReceive(downLinkMessageBufferHandle, &downlinkPayload, sizeof(lora_driver_payload_t), portMAX_DELAY);
			if (13 == downlinkPayload.len) // Check that we have got the expected 4 bytes
			{
				// decode the payload into our variables
				servoMinHumidity = (downlinkPayload.bytes[0] << 8) + downlinkPayload.bytes[1];
				servoMaxHumidity = (downlinkPayload.bytes[2] << 8) + downlinkPayload.bytes[3];
				
				
				servoMinTemperature = (downlinkPayload.bytes[4] << 8) + downlinkPayload.bytes[5];
				servoMaxTemperature = (downlinkPayload.bytes[6] << 8) + downlinkPayload.bytes[7];
				
				servoMinPPM = (downlinkPayload.bytes[8] << 8) + downlinkPayload.bytes[9];
				servoMaxPPM = (downlinkPayload.bytes[10] << 8) + downlinkPayload.bytes[11];
				
				printf("RECEIVED---Hum = %d Temp = %d PPm = %d \n", (int)servoMaxHumidity, (int)servoMaxTemperature, servoMaxPPM);
				
				if(downlinkPayload.bytes[12] == 1)
					onOffSwitch = true;
					else onOffSwitch = false;
			}
			
			// The uplink message is sent and a downlink message is received
		}
		//printf("---Upload Message >%s<\n", lora_driver_mapReturnCodeToText(lora_driver_sendUploadMessage(false, &_uplink_payload)));
		
		xTaskDelayUntil( &xLastWakeTime, xFrequency );
	}
}
