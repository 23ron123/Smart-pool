package com.example.poolnk;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button ShowAllProducts;
    Button CheckButton;
    Button conect;

    EditText editTextString;

    BluetoothReceiver bluetoothReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //editTextString=(EditText) findViewById(R.id.editTextString);

        bluetoothReceiver = new BluetoothReceiver();

        /*CheckButton = (Button)findViewById(R.id.checkButton);
        CheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringCheck = String.valueOf(editTextString.getText());
                int NumCheck =stringCheck.indexOf("~");

                if ((NumCheck>0) && (editTextString.getText().length()>0)) {
                    String Num1= stringCheck.substring(0, NumCheck);
                    Toast.makeText(MainActivity.this, "nice ;) " + Num1, Toast.LENGTH_SHORT).show();

                    String replace = stringCheck.replace(stringCheck.substring(0, NumCheck) + '~',"");
                    NumCheck = replace.indexOf("~");

                    if ((NumCheck>0)) {
                        String Num2 = replace.substring(0, NumCheck);
                        Toast.makeText(MainActivity.this, "nice ;) <3 " + Num2, Toast.LENGTH_SHORT).show();

                        String Num3 = replace.replace(replace.substring(0, NumCheck) + '~', "");
                        Toast.makeText(MainActivity.this, "yess!!!!!!!!!!!!!!!!!!!" + Num3, Toast.LENGTH_SHORT).show();
                    }

                    else
                        Toast.makeText(MainActivity.this, "wrong", Toast.LENGTH_SHORT).show();

                }
                else
                    Toast.makeText(MainActivity.this, "wrong", Toast.LENGTH_SHORT).show();

                editTextString.setText("");
            }
        });*/

        ShowAllProducts = (Button)findViewById(R.id.ShowAllProducts);
        ShowAllProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AllActivity.class);
                startActivity(intent);
            }
        });

        conect = (Button)findViewById(R.id.conect);
        conect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DeviceActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(bluetoothReceiver, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(bluetoothReceiver);

    }

}
