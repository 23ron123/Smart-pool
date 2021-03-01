package com.example.poolnk;

public class DataPh {

    private int max = 8;
    private int min = 4;
    private Pool p;
    private String phColor;

    public DataPh (Pool p){
        this.p=p;
        this.phColor = Check();
    }

    public DataPh (){
    }

    public String getPhColor(){
        return phColor;
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
        if (p.getPh() > max)
            return "red";
        if (p.getPh() < min)
            return "blue";
        return "green";
    }

}

