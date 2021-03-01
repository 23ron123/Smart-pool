package com.example.poolnk;

public class Pool {

    private long poolId;
    private Double temperature;
    private Double ph;
    private Double Waterlevel;
    private  String Date;

    public Pool(long poolId, Double temperature, Double Waterlevel, Double ph) {
        this.poolId = poolId;
        this.temperature = temperature;
        this.Waterlevel = Waterlevel;
        this.ph = ph;
    }

    public Pool( Double temperature, Double Waterlevel, Double ph) {
        this.temperature = temperature;
        this.Waterlevel = Waterlevel;
        this.ph = ph;
    }

    public Pool(long poolId, Double temperature, Double Waterlevel, Double ph, String Date) {
        this.poolId = poolId;
        this.temperature = temperature;
        this.Waterlevel = Waterlevel;
        this.ph = ph;
        this.Date = Date;
    }

    public Pool( Double temperature, Double Waterlevel, Double ph, String Date) {
        this.temperature = temperature;
        this.Waterlevel = Waterlevel;
        this.ph = ph;
        this.Date = Date;
    }



    public long getPoolId(){
        return poolId;
    }
    public void  setPoolId(long poolId){
        this.poolId = poolId;
    }

    public Double getTemperature(){
        return  temperature;
    }
    public  void setTemperature(){
        this.temperature = temperature;
    }

    public Double getWaterlevel(){
        return Waterlevel;
    }
    public void setWaterlevel(){
        this.Waterlevel = Waterlevel;
    }

    public  Double getPh(){
        return ph;
    }
    public void setPh(){
        this.ph = ph;
    }

    public  String getDate(){
        return Date;
    }
    public void setDate(){
        this.Date = Date;
    }

}

