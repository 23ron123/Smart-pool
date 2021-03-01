package com.example.poolnk;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.UUID;


public class ConnectionActivity extends Activity implements OnClickListener {

    Button button0, button1, button2, button3, button4, buttonM, buttonA;
    Handler bluetoothIn;

    ImageView iv;
    ImageView ivtvTemperature;
    ImageView ivtvSalinity;
    ImageView ivPh;

    TextView tvTemperatureNum;
    TextView tvPhNum;
    TextView tvSalinityNum;

    String Num1;
    String Num2;
    String Num3;

    PoolOpenHelper cds;

    final int handlerState = 0;//used to identify handler message
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private ConnectedThread mConnectedThread;

    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // String for MAC address
    private static String address;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cds=new PoolOpenHelper(this);

        setContentView(R.layout.activity_connection);

        //Link the buttons and textViews to respective views
        button0 = (Button)findViewById(R.id.button0);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);
        buttonM = (Button)findViewById(R.id.buttonM);
        buttonA = (Button)findViewById(R.id.buttonA);

        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        buttonM.setOnClickListener(this);
        buttonA.setOnClickListener(this);

        bluetoothIn = new Handler() {
            public void handleMessage(Message msg) {
                if(msg.what == handlerState){
                    String readMessage = (String) msg.obj;
                    /////////////////////////פירוק הסטרינג///////////////////////
                    int NumCheck =readMessage.indexOf("~");

                    if ((NumCheck>0) && (readMessage.length()>0)) {
                        Num1= readMessage.substring(0, NumCheck);
                        Toast.makeText(ConnectionActivity.this, "Num 1:  " + Num1, Toast.LENGTH_SHORT).show();

                        String replace = readMessage.replace(readMessage.substring(0, NumCheck) + '~',"");
                        NumCheck = replace.indexOf("~");

                        if ((NumCheck>0)) {
                            Num2 = replace.substring(0, NumCheck);
                            Toast.makeText(ConnectionActivity.this, "Num 2: " + Num2, Toast.LENGTH_SHORT).show();

                            Num3 = replace.replace(replace.substring(0, NumCheck) + '~', "");
                            Toast.makeText(ConnectionActivity.this, "Num 3: " + Num3, Toast.LENGTH_SHORT).show();
                        }

                        else
                            Toast.makeText(ConnectionActivity.this, "wrong", Toast.LENGTH_SHORT).show();

                    }
                    else
                        Toast.makeText(ConnectionActivity.this, "wrong", Toast.LENGTH_SHORT).show();

                    ///////////////////////////פירוק הסטרינג/////////////////////

                    ///////////////////////////הוספה למאגר נתונים///////////////////////
                    long date = System.currentTimeMillis();
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy\nhh-mm-ss a");
                    String Date = sdf.format(date);

                    Double temperature2=Double.valueOf(Num1);
                    Double ph2=Double.valueOf(Num2);
                    Double waterlevel2=Double.valueOf(Num3);
                    Pool c = new Pool(0, temperature2, waterlevel2, ph2, Date);

                    cds.open();
                    c = cds.createPool(c);
                    cds.close();
                    /////////////////////////סוף הוספה מאגר נתונים//////////////////////



                    /////////////////////////עיצוב////////////////////
                    iv = (ImageView)findViewById(R.id.iv);
                    ivtvTemperature = (ImageView)findViewById(R.id.ivtvTemperature);
                    ivtvSalinity = (ImageView)findViewById(R.id.ivtvSalinity);
                    ivPh = (ImageView)findViewById(R.id.ivPh);


                    tvTemperatureNum =(TextView)findViewById(R.id.tvTemperatureNum);
                    tvPhNum =(TextView)findViewById(R.id.tvPhNum);
                    tvSalinityNum =(TextView)findViewById(R.id.tvSalinityNum);

                    if(Num1.length()>0 && Num2.length()>0 && Num3.length()>0) {
                        Double temperature = Double.valueOf(String.valueOf(Num1));
                        Double waterlevel = Double.valueOf(Num2);
                        Double ph = Double.valueOf(Num3);

                        Pool temp = new Pool(temperature, waterlevel, ph);
                        Data data = new Data(temp);
                        DataTemperature dataTemperature = new DataTemperature(temp);
                        DataWaterLevel dataWaterLevel = new DataWaterLevel(temp);
                        DataPh dataPh = new DataPh(temp);

                        tvTemperatureNum.setText(String.valueOf(temp.getTemperature()));
                        tvPhNum.setText(String.valueOf(temp.getPh()));
                        tvSalinityNum.setText(String.valueOf(temp.getWaterlevel()));

                        ///////////////////////////////////////////////////////////////////

                        if (dataTemperature.getTemperatureColor().equals("red"))
                            ivtvTemperature.setImageResource(R.drawable.kred);

                        if (dataTemperature.getTemperatureColor().equals("blue"))
                            ivtvTemperature.setImageResource(R.drawable.nblue2);

                        if (dataTemperature.getTemperatureColor().equals("green"))
                            ivtvTemperature.setImageResource(R.drawable.kgreen);

                        ///////////////////////////////////////////////////////////////////

                        if (dataWaterLevel.getWaterlevelColor().equals("red"))
                            ivtvSalinity.setImageResource(R.drawable.kred);

                        if (dataWaterLevel.getWaterlevelColor().equals("blue"))
                            ivtvSalinity.setImageResource(R.drawable.nblue2);

                        if (dataWaterLevel.getWaterlevelColor().equals("green"))
                            ivtvSalinity.setImageResource(R.drawable.kgreen);

                        ///////////////////////////////////////////////////////////////////

                        if (dataPh.getPhColor().equals("red"))
                            ivPh.setImageResource(R.drawable.kred);

                        if (dataPh.getPhColor().equals("blue"))
                            ivPh.setImageResource(R.drawable.nblue2);

                        if (dataPh.getPhColor().equals("green"))
                            ivPh.setImageResource(R.drawable.kgreen);

                        ///////////////////////////////////////////////////////////////////

                        iv.setImageResource(R.drawable.normal);

                        if (data.Checklevel() == 1)
                            iv.setImageResource(R.drawable.error);

                        if (data.Checklevel() == 2)
                            iv.setImageResource(R.drawable.error2);

                        if (data.Checklevel() == 3)
                            iv.setImageResource(R.drawable.kred);

                        //////////////////////////////סוף עיצוב///////////////////
                    }
                }

            }
        };
        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();
    }


    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException
    {

        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }

    @Override
    public void onResume() {
        super.onResume();

        //Get MAC address from DeviceListActivity via intent
        Intent intent = getIntent();

        //Get the MAC address from the DeviceListActivty via EXTRA
        address = intent.getStringExtra(DeviceActivity.EXTRA_DEVICE_ADDRESS);

        //create device and set the MAC address
        BluetoothDevice device = btAdapter.getRemoteDevice(address);
        Toast.makeText(ConnectionActivity.this,device+"",Toast.LENGTH_LONG).show();
        if(device!=null)
        {
            try {
                btSocket = createBluetoothSocket(device);
            } catch (IOException e) {
                Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_LONG).show();
            }
            // Establish the Bluetooth socket connection.
            try {
                btSocket.connect();
            } catch (IOException e) {
                try {
                    btSocket.close();
                } catch (IOException e2) {
                    //insert code to deal with this
                }
            }
            mConnectedThread = new ConnectedThread(btSocket);
            mConnectedThread.start();

        }

        //I send a character when resuming.beginning transmission to check device is connected
        //If it is not an exception will be thrown in the write method and finish() will be called
        //mConnectedThread.write("x");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        try
        {
            //Don't leave Bluetooth sockets open when leaving activity
            btSocket.close();
        } catch (IOException e2) {
            //insert code to deal with this
        }
    }

    //Checks that the Android device Bluetooth is available and prompts to be turned on if off
    private void checkBTState() {

        if(btAdapter==null) {
            Toast.makeText(getBaseContext(), "Device does not support bluetooth", Toast.LENGTH_LONG).show();
        } else {
            if (btAdapter.isEnabled())
            {

            }
            else
            {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    @Override
    public void onClick(View view) {

        Button btn = (Button) view;
        String str = btn.getText().toString();
        char ch = str.charAt(0);
        if (ch >= '0' && ch <= '9') {
            mConnectedThread.write(str);    // Send "1-5,M,A" via Bluetooth
        }
        if (str.equals("M")) {
            mConnectedThread.write(str);
        }
        if (str.equals("A")) {
            mConnectedThread.write(str);
        }
        Toast.makeText(getBaseContext(), str, Toast.LENGTH_SHORT).show();
    }

    //create new class for connect thread
    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        //creation of the connect thread
        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                //Create I/O streams for connection
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run()
        {
            byte[] buffer = new byte[256];
            int bytes=0;

            // Keep looping to listen for received messages
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);        	//read bytes from input buffer

                    Log.d("asaf", "x=" + bytes);
                    String readMessage = new String(buffer, 0, bytes);
                    // Send the obtained bytes to the UI Activity via handler
                    // Message msg = new Message();
                    // msg.obj = readMessage;

                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                }
                catch (IOException e)
                {
                    Log.d("asaf", "x=" + 100);

                    // this.start();
                    break;
                }
            }
        }
        //write method
        public void write(String input) {
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            } catch (IOException e) {
                //if you cannot write, close the application
                Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();

                finish();

            }
        }




    }
}