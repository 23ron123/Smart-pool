package com.example.poolnk;

public class Data {

    private Pool p;
    private DataPh dataPh;
    private DataTemperature dataTemperature;
    private DataWaterLevel dataWaterLevel;

    public Data(Pool p) {
        this.p = p;
        this. dataPh = new DataPh(p);
        this.dataTemperature  = new DataTemperature(p);
        this.dataWaterLevel  = new DataWaterLevel(p);    }

    public Data() {
        this.dataPh = new DataPh();
        this.dataTemperature  = new DataTemperature();
        this.dataWaterLevel  = new DataWaterLevel();
    }

    public Pool getP(){
        return p;
    }
    public DataTemperature getDataTemperature(){return dataTemperature;}
    public DataWaterLevel getDataWaterLevel(){return dataWaterLevel;}
    public DataPh getDataPh(){return dataPh;}

    public int Checklevel() {
        int Counter = 0;

        String temperature = dataTemperature.getTemperatureColor();
        String salinity = dataWaterLevel.getWaterlevelColor();
        String ph = dataPh.getPhColor();

        if(temperature.equals("green") && salinity.equals("green") && ph.equals("green"))
            return 0;

        if(temperature.equals("red") || temperature.equals("blue"))
            Counter++;

        if(salinity.equals("red") || salinity.equals("blue"))
            Counter++;

        if(ph.equals("red") || ph.equals("blue"))
            Counter++;

        return Counter;
    }

}
