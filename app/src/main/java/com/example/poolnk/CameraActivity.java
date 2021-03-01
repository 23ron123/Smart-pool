package com.example.poolnk;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView iv;
    Button btnTakePic;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        iv=(ImageView)findViewById(R.id.iv);
        btnTakePic=(Button)findViewById(R.id.btnTakePic);
        btnTakePic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if(v==btnTakePic)
        {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,0);
        }
    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0)//coming from camera
        {
            if(resultCode==RESULT_OK)
            {
                bitmap= (Bitmap)data.getExtras().get("data");
                iv.setImageBitmap(bitmap);
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main,menu);

        MenuItem item= menu.getItem(0);
        item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);


        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu:
                Intent i=new Intent(CameraActivity.this,MainActivity.class);
                startActivityForResult(i, 1);//Request code 1 is for ------>insert screen
            default:
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}
