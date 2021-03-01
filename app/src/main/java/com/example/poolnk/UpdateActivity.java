package com.example.poolnk;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class UpdateActivity extends AppCompatActivity {

    EditText editTextPh;
    EditText editTextTemperature;
    EditText editTextWaterlevel;

    TextView tvId;

    Button btnsave;
    Button btndelete;
    PoolOpenHelper poh;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        poh=new PoolOpenHelper(this);
        init();
    }

    private void init() {
        editTextPh=(EditText) findViewById(R.id.edit_text_ph);
        editTextTemperature=(EditText) findViewById(R.id.edit_text_temperature);
        editTextWaterlevel=(EditText) findViewById(R.id.edit_text_salinity);

        btnsave=(Button) findViewById(R.id.ButtonSaveUpdate);
        btndelete=(Button) findViewById(R.id.ButtonDeleteUpdate);
        tvId=(TextView) findViewById(R.id.TitleUpdate);

        id=getIntent().getLongExtra("rowId", 0);

        if(id!=0)
        {
            poh.open();
            Pool c=poh.getPoolById(id);
            editTextPh.setText(String.valueOf(c.getPh()));
            editTextTemperature.setText(String.valueOf(c.getTemperature()));
            editTextWaterlevel.setText(String.valueOf(c.getWaterlevel()));

            tvId.setText("Details of spirulina: " + c.getPoolId());
            poh.close();

        }

        btnsave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Double temparature=Double.valueOf(editTextTemperature.getText().toString());
                Double ph=Double.valueOf(editTextPh.getText().toString());
                Double waterlevel=Double.valueOf(editTextWaterlevel.getText().toString());

                if (editTextTemperature.getText().length()>0 && editTextWaterlevel.getText().length()>0 &&editTextPh.getText().length()>0) {

                    Pool c = new Pool(id, temparature, waterlevel, ph);
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
                        Intent intent = new Intent(UpdateActivity.this, AllActivity.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(UpdateActivity.this, 0, intent, 0);
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

                    else {
                        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.cancel(1);
                    }
                    poh.open();
                    poh.updateByRow(c);
                    poh.close();
                    Intent i = new Intent();
                    setResult(RESULT_OK, i);
                    finish();

                }
                else {
                    Toast.makeText(UpdateActivity.this, "value incorrect", Toast.LENGTH_SHORT).show();
                }


            }
        });
        btndelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(v==btndelete) {
                    // TODO Auto-generated method stub
                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);

                    builder.setTitle("select");

                    builder.setMessage("Are you sure you want to delete?");

                    builder.setCancelable(true);

                    builder.setPositiveButton("Yes", new HandleAlertDialogClickListener());

                    builder.setNegativeButton("No", new HandleAlertDialogClickListener());

                    AlertDialog dialog = builder.create();// נפעיל את הבילדר ונחזיר רפרנס ל דיאלוג

                    dialog.show();
                }
            }
        });
    }

    public class HandleAlertDialogClickListener implements DialogInterface.OnClickListener
    {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(which==-1){
                poh.open();
                poh.deleteCustomerByRow(id);
                poh.close();
                Intent i = new Intent();
                setResult(RESULT_OK, i);
                finish();
            }
            if(which==-2){
                Toast.makeText(UpdateActivity.this, "you choose not to delete", Toast.LENGTH_LONG).show();
            }
        }
    }
}

