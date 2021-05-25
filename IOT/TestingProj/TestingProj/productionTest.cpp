#include "gtest/gtest.h"
#include "MockDeclaration.h"

extern "C" {
#include <stdio.h>
#include <stdlib.h>
#include <stddef.h>
#include <stdint.h>
	//simulated FreeRTOS files
#include "FreeRTOS.h"
#include "Task.h"
#include "message_buffer.h"
	//drivers
#include "../drivers/mh_z19.h"
#include "../drivers/hih8120.h"
#include "../drivers/sen14262.h"
#include "../drivers/rc_servo.h"
#include "../drivers/lora_driver.h"
#include "../drivers/status_leds.h"
	//Files from production code
#include <dataShare.h>
#include <downLink.h>
#include <configuration.h>
	//CO2
#include <CO2.h>
	//TempHum
#include <TempHum.h>
	//Sound
#include <Sound.h>
	//Servo
#include <Servo.h>
	//Lora
#include <Lora.h>
	//dataShare stuff
uint16_t ppm;
float temperature;
float humidity;
uint16_t lastSoundValue;
float servoMinTemperature;
float servoMaxTemperature;
float servoMinHumidity;
float servoMaxHumidity;
uint16_t servoMinPPM;
uint16_t servoMaxPPM;
MessageBufferHandle_t downLinkMessageBufferHandle;
}

// -- FreeRTOS function mocks --
// Mocked in MockDeclaration.h

// -- Mocks for CO2 --
FAKE_VOID_FUNC(mh_z19_initialise, serial_comPort_t);
FAKE_VALUE_FUNC(mh_z19_returnCode_t, mh_z19_takeMeassuring);
FAKE_VALUE_FUNC(mh_z19_returnCode_t, mh_z19_getCo2Ppm, uint16_t*);

// -- Mocks for TempHum --
FAKE_VALUE_FUNC(hih8120_driverReturnCode_t, hih8120_initialise);
FAKE_VALUE_FUNC(hih8120_driverReturnCode_t, hih8120_wakeup);
FAKE_VALUE_FUNC(hih8120_driverReturnCode_t, hih8120_measure);
FAKE_VALUE_FUNC(float, hih8120_getHumidity);
FAKE_VALUE_FUNC(float, hih8120_getTemperature);

// -- Mocks for Sound --
FAKE_VOID_FUNC(sen14262_initialise);
FAKE_VALUE_FUNC(uint16_t, sen14262_envelope);

// -- Mocks for Servo --
FAKE_VOID_FUNC(rc_servo_initialise);
FAKE_VOID_FUNC(rc_servo_setPosition, uint8_t, int8_t);

// -- Mocks for Lora --
FAKE_VALUE_FUNC(lora_driver_returnCode_t, lora_driver_setOtaaIdentity, char*, char*, char*);
FAKE_VALUE_FUNC(lora_driver_returnCode_t, lora_driver_configureToEu868);
FAKE_VALUE_FUNC(char*, lora_driver_mapReturnCodeToText, lora_driver_returnCode_t);
FAKE_VALUE_FUNC(lora_driver_returnCode_t, lora_driver_join, lora_driver_joinMode_t);
FAKE_VALUE_FUNC(lora_driver_returnCode_t, lora_driver_sendUploadMessage, bool, lora_driver_payload_t*);
FAKE_VALUE_FUNC(lora_driver_returnCode_t, lora_driver_setDeviceIdentifier, const char*);
FAKE_VALUE_FUNC(lora_driver_returnCode_t, lora_driver_setAdaptiveDataRate, lora_driver_adaptiveDataRate_t);
FAKE_VALUE_FUNC(lora_driver_returnCode_t, lora_driver_setReceiveDelay, uint16_t);
FAKE_VOID_FUNC(lora_driver_resetRn2483, uint8_t);
FAKE_VOID_FUNC(lora_driver_flushBuffers);
FAKE_VALUE_FUNC(lora_driver_returnCode_t, lora_driver_getRn2483Hweui, char*);
FAKE_VALUE_FUNC(lora_driver_returnCode_t, lora_driver_rn2483FactoryReset);
FAKE_VALUE_FUNC(lora_driver_returnCode_t, lora_driver_saveMac);

// -- Mocks for leds --
FAKE_VOID_FUNC(status_leds_longPuls, status_leds_t);
FAKE_VOID_FUNC(status_leds_shortPuls, status_leds_t);
FAKE_VOID_FUNC(status_leds_slowBlink, status_leds_t);
FAKE_VOID_FUNC(status_leds_fastBlink, status_leds_t);
FAKE_VOID_FUNC(status_leds_ledOn, status_leds_t);
FAKE_VOID_FUNC(status_leds_ledOff, status_leds_t);

class LoraTest : public::testing::Test {
protected:
	void SetUp() override {
		//Lora fake drivers
		RESET_FAKE(lora_driver_setOtaaIdentity);
		RESET_FAKE(lora_driver_configureToEu868);
		RESET_FAKE(lora_driver_mapReturnCodeToText);
		RESET_FAKE(lora_driver_join);
		RESET_FAKE(lora_driver_sendUploadMessage);
		RESET_FAKE(lora_driver_setDeviceIdentifier);
		RESET_FAKE(lora_driver_setAdaptiveDataRate);
		RESET_FAKE(lora_driver_setReceiveDelay);
		RESET_FAKE(lora_driver_resetRn2483);
		RESET_FAKE(lora_driver_flushBuffers);
		RESET_FAKE(lora_driver_getRn2483Hweui);
		RESET_FAKE(lora_driver_rn2483FactoryReset);
		RESET_FAKE(lora_driver_saveMac);
		//Leds fake drivers
		RESET_FAKE(status_leds_longPuls);
		RESET_FAKE(status_leds_shortPuls);
		RESET_FAKE(status_leds_slowBlink);
		RESET_FAKE(status_leds_fastBlink);
		RESET_FAKE(status_leds_ledOn);
		RESET_FAKE(status_leds_ledOff);
		FFF_RESET_HISTORY();
	}

	void TearDown() override {}
};

class CO2Test : public::testing::Test {
protected:
	void SetUp() override {
		RESET_FAKE(xTaskDelayUntil);
		RESET_FAKE(vTaskDelay);
		RESET_FAKE(xTaskGetTickCount);
		RESET_FAKE(mh_z19_initialise);
		RESET_FAKE(mh_z19_takeMeassuring);
		RESET_FAKE(mh_z19_getCo2Ppm);
		FFF_RESET_HISTORY();
	}

	void TearDown() override {}
};

class TempHumTest : public::testing::Test {
protected:
	void SetUp() override {
		RESET_FAKE(xTaskDelayUntil);
		RESET_FAKE(vTaskDelay);
		RESET_FAKE(xTaskGetTickCount);
		RESET_FAKE(hih8120_initialise);
		RESET_FAKE(hih8120_wakeup);
		RESET_FAKE(hih8120_measure);
		RESET_FAKE(hih8120_getHumidity);
		RESET_FAKE(hih8120_getTemperature);
	}
};

class SoundTest : public::testing::Test {
protected:
	void SetUp() override {
		RESET_FAKE(xTaskDelayUntil);
		RESET_FAKE(xTaskGetTickCount);
		RESET_FAKE(sen14262_initialise);
		RESET_FAKE(sen14262_envelope);
		FFF_RESET_HISTORY();
	}

	void TearDown() override {}
};

class ServoTest : public::testing::Test {
protected:
	void SetUp() override {
		RESET_FAKE(xTaskDelayUntil);
		RESET_FAKE(xTaskGetTickCount);
		RESET_FAKE(rc_servo_initialise);
		RESET_FAKE(rc_servo_setPosition);
		FFF_RESET_HISTORY();
	}

	void TearDown() override {}
};

// -- CO2 Tests --

TEST_F(CO2Test, CO2_init_successful) {
	//Arrange
	//Act
	co2_initCO2Sensor();
	//Assert
	EXPECT_EQ(1, mh_z19_initialise_fake.call_count);
}

TEST_F(CO2Test, CO2_xTaskDelayUntil_calledCorrectly) {
	//Arrange
	//Act
	co2_taskRun();
	//Assert
	EXPECT_EQ(1, xTaskDelayUntil_fake.call_count);
	EXPECT_TRUE(NULL != xTaskDelayUntil_fake.arg0_val);
}

TEST_F(CO2Test, CO2_vTaskDelay_calledCorrectly) {
	//Arrange
	//Act
	co2_taskRun();
	//Assert
	EXPECT_EQ(1, vTaskDelay_fake.call_count);
	EXPECT_EQ(pdMS_TO_TICKS(1000), vTaskDelay_fake.arg0_val);
}

TEST_F(CO2Test, CO2_takesCo2Meassuring_withSuccess) {
	//Arrange
	mh_z19_takeMeassuring_fake.return_val = MHZ19_OK;
	//Act
	co2_taskRun();
	//Assert
	EXPECT_EQ(1, mh_z19_takeMeassuring_fake.call_count);
	EXPECT_EQ(MHZ19_OK, mh_z19_takeMeassuring_fake.return_val);
}

TEST_F(CO2Test, CO2_ReturnsCO2MeassuringCorrectly) {
	//Arrange
	//Act
	co2_taskRun();
	//Assert
	EXPECT_EQ(1, mh_z19_getCo2Ppm_fake.call_count);
	EXPECT_TRUE(NULL != mh_z19_getCo2Ppm_fake.arg0_val);
}

// -- TempHum Tests --
TEST_F(TempHumTest, TempHum_init_successful) {
	//Arrange
	hih8120_initialise_fake.return_val = HIH8120_OK;
	//Act
	tempHum_init();
	//Assert
	EXPECT_EQ(1, hih8120_initialise_fake.call_count);
	EXPECT_EQ(HIH8120_OK, hih8120_initialise_fake.return_val);
}

TEST_F(TempHumTest, TempHum_xTaskDelayUntil_calledCorrectly) {
	//Arrange
	//Act
	tempHum_taskRun();
	//Assert
	EXPECT_EQ(1, xTaskDelayUntil_fake.call_count);
	EXPECT_TRUE(NULL != xTaskDelayUntil_fake.arg0_val);
}

TEST_F(TempHumTest, TempHum_vTaskDelay_calledCorrectly) {
	//Arrange
	//Act
	tempHum_taskRun();
	//Assert
	EXPECT_EQ(2, vTaskDelay_fake.call_count);
	EXPECT_EQ(pdMS_TO_TICKS(50), vTaskDelay_fake.arg0_history[0]);
	EXPECT_EQ(pdMS_TO_TICKS(500), vTaskDelay_fake.arg0_history[1]);
}


TEST_F(TempHumTest, TempHum_driver_wakeup_success) {
	//Arrange
	hih8120_wakeup_fake.return_val = HIH8120_OK;
	//Act
	tempHum_taskRun();
	//Assert
	EXPECT_EQ(1, hih8120_wakeup_fake.call_count);
	EXPECT_EQ(HIH8120_OK, hih8120_wakeup_fake.return_val);
}

TEST_F(TempHumTest, TempHum_getHumididty_success) {
	//Arrange
	hih8120_getHumidity_fake.return_val = 10.0;
	//Act
	tempHum_taskRun();
	//Assert
	EXPECT_EQ(1, hih8120_getHumidity_fake.call_count);
	EXPECT_EQ(10.0, humidity);
}

TEST_F(TempHumTest, TempHum_getTemperature_success) {
	//Arrange
	hih8120_getTemperature_fake.return_val = 10.0;
	//Act
	tempHum_taskRun();
	//Assert
	EXPECT_EQ(1, hih8120_getTemperature_fake.call_count);
	EXPECT_EQ(10.0, temperature);
}

// -- Sound Tests --
TEST_F(SoundTest, Sound_init_succesful) {
	//Arrange
	//Act
	sound_init();
	//Assert
	EXPECT_EQ(1, sen14262_initialise_fake.call_count);
}

TEST_F(SoundTest, Sound_xTaskDelayUntil_calledCorrectly) {
	//Arrange
	//Act
	sound_taskRun();
	//Assert
	EXPECT_EQ(1, xTaskDelayUntil_fake.call_count);
	EXPECT_TRUE(NULL != xTaskDelayUntil_fake.arg0_val);
}

TEST_F(SoundTest, Sound_GetSoundValue_Success) {
	//Arrange
	sen14262_envelope_fake.return_val = 1;
	//Act
	sound_taskRun();
	//Assert
	EXPECT_EQ(1, sen14262_envelope_fake.call_count);
	EXPECT_EQ(1, sen14262_envelope_fake.return_val);
}

// -- Servo Tests --
TEST_F(ServoTest, Servo_init_successful) {
	//Arrange
	//Act
	servo_init();
	//Assert
	EXPECT_EQ(1, rc_servo_initialise_fake.call_count);
}

TEST_F(ServoTest, Servo_xTaskDelayUntil_calledCorrectly) {
	//Arrange
	//Act
	servo_taskRun();
	//Assert
	EXPECT_EQ(1, xTaskDelayUntil_fake.call_count);
	EXPECT_TRUE(NULL != xTaskDelayUntil_fake.arg0_val);
}

TEST_F(ServoTest, Servo_setPositionOpen_called_success) {
	//Arrange
	temperature = 10.0;
	servoMaxTemperature = 5.0;
	//Act
	servo_taskRun();
	//Assert
	EXPECT_EQ(1, rc_servo_setPosition_fake.call_count);
	EXPECT_EQ(1, rc_servo_setPosition_fake.arg0_val);
	EXPECT_EQ(-100, rc_servo_setPosition_fake.arg1_val);
}

TEST_F(ServoTest, Servo_setPositionClosed_called_success) {
	//Arrange
	servoMaxTemperature = 10.0;
	servoMaxPPM = 10.0;
	servoMaxHumidity = 10.0;

	servoMinTemperature = 10.0;
	servoMinPPM = 15.0;
	servoMinHumidity = 10.0;

	temperature = 5.0;
	ppm = 10.0;
	humidity = 5.0;
	//Act
	servo_taskRun();
	//Assert
	EXPECT_EQ(1, rc_servo_setPosition_fake.call_count);
	EXPECT_EQ(1, rc_servo_setPosition_fake.arg0_val);
	EXPECT_EQ(100, rc_servo_setPosition_fake.arg1_val);
}

// -- Lora Tests --
TEST_F(LoraTest, Lora_init_successful) {
	//Arrange
	//Act
	lora_taskRun();
	//Assert
	EXPECT_EQ(1, lora_driver_sendUploadMessage_fake.call_count);
}