package com.example.poolnk;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.text.SimpleDateFormat;

public class InsertActivity extends AppCompatActivity {

    Button insert;

    EditText editTextPh;
    EditText editTextTemperature;
    EditText editTextWaterlevel;

    PoolOpenHelper cds;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        cds=new PoolOpenHelper(this);
        init();

    }

    private void init() {
        editTextPh=(EditText) findViewById(R.id.edit_text_ph);
        editTextTemperature=(EditText) findViewById(R.id.edit_text_temperature);
        editTextWaterlevel=(EditText) findViewById(R.id.edit_text_salinity);
        insert=(Button) findViewById(R.id.insertBtnSave);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (editTextTemperature.getText().length()>0 && editTextWaterlevel.getText().length()>0 &&editTextPh.getText().length()>0) {
                    long date = System.currentTimeMillis();
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy\nhh-mm-ss a");
                    String Date = sdf.format(date);

                    Double temperature=Double.valueOf(editTextTemperature.getText().toString());
                    Double ph=Double.valueOf(editTextPh.getText().toString());
                    Double waterlevel=Double.valueOf(editTextWaterlevel.getText().toString());
                    Pool c = new Pool(0, temperature, waterlevel, ph, Date);

                    if(c.getTemperature() > 25 || c.getTemperature() < 18 || c.getWaterlevel()>36 || c.getWaterlevel() < 34 || c.getPh() >8 || c.getPh() <4)
                    {
                        //phase 1
                        int icon = android.R.drawable.star_on;
                        String ticket = " this is ticket message";
                        long when = System.currentTimeMillis();
                        String title ="something wrong";
                        String ticker = "ticker";
                        String text="text";
                        //phase 2
                        Intent intent = new Intent(InsertActivity.this, AllActivity.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(InsertActivity.this, 0, intent, 0);
                        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "M_CH_ID");
//https://stackoverflow.com/questions/47480732/what-is-the-purpose-of-the-condition-if-build-version-sdk-int-build-version
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            String channelId = "YOUR_CHANNEL_ID";
                            NotificationChannel channel = new NotificationChannel(channelId,
                                    "Channel human readable title",
                                    NotificationManager.IMPORTANCE_DEFAULT);
                            notificationManager.createNotificationChannel(channel);
                            builder.setChannelId(channelId);
                        }

                        //phase 3
                        Notification notification = builder.setContentIntent(pendingIntent)
                                .setSmallIcon(icon).setTicker(ticker).setWhen(when)
                                .setAutoCancel(true).setContentTitle(title)
                                .setContentText("Tap here to check what happened.").build();
                        notificationManager.notify(1, notification);

                    }
                    cds.open();
                    c = cds.createPool(c);
                    cds.close();
                    Intent i = new Intent();
                    setResult(RESULT_OK, i);
                    finish();
                }

                else {
                    Toast.makeText(InsertActivity.this, "value incorrect", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}