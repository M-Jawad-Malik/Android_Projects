package com.example.shockrecorder;

public class MPU6050 {
    private Double Real_Time_Acceleration_X;
    private Double Real_Time_Acceleration_Y;
    private Double Real_Time_Acceleration_Z;
    private Double Real_Time_Temperature;
    public MPU6050() {

    }


    public Double getReal_Time_Acceleration_X() {
        return Real_Time_Acceleration_X;
    }

    public void setReal_Time_Acceleration_X(Double real_Time_Acceleration_X) {
        this.Real_Time_Acceleration_X = real_Time_Acceleration_X;
    }
    public Double getReal_Time_Acceleration_Y() {
        return Real_Time_Acceleration_Y;
    }

    public void setReal_Time_Acceleration_Y(Double real_Time_Acceleration_Y) {
        this.Real_Time_Acceleration_Y = real_Time_Acceleration_Y;
    }
    public Double getReal_Time_Acceleration_Z() {
        return Real_Time_Acceleration_Z;
    }

    public void setReal_Time_Acceleration_Z(Double real_Time_Acceleration_Z) {
        this.Real_Time_Acceleration_Z = real_Time_Acceleration_Z;
    }
    public Double getReal_Time_Temperature() {
        return Real_Time_Temperature;
    }

    public void setReal_Time_Temperature(Double real_time_temperature) {
        this.Real_Time_Temperature = real_time_temperature;
    }

}
