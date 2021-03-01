package com.example.poolnk;

public class DataTemperature {

    private int max = 25;
    private int min = 18;
    private Pool p;
    private String temperatureColor;

    public DataTemperature (Pool p){
        this.p=p;
        this.temperatureColor = Check();
    }

    public DataTemperature (){
    }

    public String getTemperatureColor(){
        return this.temperatureColor;
    }

    public int getMax(){
        return max;
    }
    public void  setMax(int max){
        this.max = max;
    }

    public int getMin(){
        return min;
    }
    public void  setMin(int min){
        this.min = min;
    }

    public String Check() {
        if (p.getTemperature() > max)
            return "red";
        if (p.getTemperature() < min)
            return "blue";
        return "green";
    }

}

