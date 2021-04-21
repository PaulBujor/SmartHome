package com.example.myhomeapplication.View;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

public class XAxisValueFormatter extends ValueFormatter {


    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        /*axis.setLabelCount(3, true);*/
        return convertFloatToDate(value);

    }

    private String convertFloatToDate(float value)
    {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
        date.setTime((long) value);
        String strDate = formatter.format(date);

        return strDate;
    }
}
