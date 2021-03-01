package com.example.poolnk;
public class DataWaterLevel {

    private int max = 24;
    private int min = 2;
    private Pool p;
    private String WaterlevelColor;

    public DataWaterLevel(Pool p){
        this.p=p;
        this.WaterlevelColor = Check();
    }

    public DataWaterLevel(){
    }

    public String getWaterlevelColor(){
        return WaterlevelColor;
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
        if (p.getWaterlevel() > max)
            return "red";
        if (p.getWaterlevel() < min)
            return "blue";
        return "green";
    }

}

