package com.example.poolnk;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PoolAdapter extends ArrayAdapter<Pool> {
    Context context;
    List<Pool> objects;

    public PoolAdapter(Context context, int resource, List<Pool> objects) {
        super(context, resource, objects);
        this.objects=objects;
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_row, parent, false);

        ImageView iv = (ImageView)view.findViewById(R.id.iv);
        ImageView ivtvTemperature = (ImageView)view.findViewById(R.id.ivtvTemperature);
        ImageView ivtvSalinity = (ImageView)view.findViewById(R.id.ivtvSalinity);
        ImageView ivPh = (ImageView)view.findViewById(R.id.ivPh);


        TextView tvTemperatureNum =(TextView) view.findViewById(R.id.tvTemperatureNum);
        TextView tvPhNum =(TextView) view.findViewById(R.id.tvPhNum);
        TextView tvSalinityNum =(TextView) view.findViewById(R.id.tvSalinityNum);
        TextView tdate = (TextView) view.findViewById(R.id.date);

        Pool temp = objects.get(position);

        Data data = new Data(temp);
        DataTemperature dataTemperature = new DataTemperature(temp);
        DataWaterLevel dataSalinity = new DataWaterLevel(temp);
        DataPh dataPh = new DataPh(temp);

        tvTemperatureNum.setText(String.valueOf(temp.getTemperature()));
        tvPhNum.setText(String.valueOf(temp.getPh()));
        tvSalinityNum.setText(String.valueOf(temp.getWaterlevel()));
        tdate.setText(temp.getDate());

        ///////////////////////////////////////////////////////////////////

        if(dataTemperature.getTemperatureColor().equals("red"))
            ivtvTemperature.setImageResource(R.drawable.kred);

        if(dataTemperature.getTemperatureColor().equals("blue"))
            ivtvTemperature.setImageResource(R.drawable.nblue2);

        if(dataTemperature.getTemperatureColor().equals("green"))
            ivtvTemperature.setImageResource(R.drawable.kgreen);

        ///////////////////////////////////////////////////////////////////

        if(dataSalinity.getWaterlevelColor().equals("red"))
            ivtvSalinity.setImageResource(R.drawable.kred);

        if(dataSalinity.getWaterlevelColor().equals("blue"))
            ivtvSalinity.setImageResource(R.drawable.nblue2);

        if(dataSalinity.getWaterlevelColor().equals("green"))
            ivtvSalinity.setImageResource(R.drawable.kgreen);

        ///////////////////////////////////////////////////////////////////

        if(dataPh.getPhColor().equals("red"))
            ivPh.setImageResource(R.drawable.kred);

        if(dataPh.getPhColor().equals("blue"))
            ivPh.setImageResource(R.drawable.nblue2);

        if(dataPh.getPhColor().equals("green"))
            ivPh.setImageResource(R.drawable.kgreen);

        ///////////////////////////////////////////////////////////////////

        iv.setImageResource(R.drawable.normal);

        if(data.Checklevel() == 1)
            iv.setImageResource(R.drawable.error);

        if (data.Checklevel() == 2)
            iv.setImageResource(R.drawable.error2);

        if (data.Checklevel() == 3)
            iv.setImageResource(R.drawable.kred);


        return view;
    }
}
