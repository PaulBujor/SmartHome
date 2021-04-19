package com.example.myhomeapplication.Remote.Response;

import com.example.myhomeapplication.Models.Measurement;
import com.example.myhomeapplication.Remote.CustomDateAndTimeDeserialize;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


import java.util.Date;

public class MeasurementResponse {
    @JsonProperty("measurementId")
    private long measurementId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = JsonFormat.DEFAULT_TIMEZONE)
    @JsonProperty("timestamp")
    private Date timestamp;
    @JsonProperty("value")
    private double value;


    public Measurement getMeasurement()
    {
        return new Measurement(measurementId, timestamp, value);
    }
}
