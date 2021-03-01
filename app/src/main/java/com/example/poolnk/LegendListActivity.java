package com.example.poolnk;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public class LegendListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legend_list);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.legend_next,menu);

        for(int i=0;i<menu.size();i++)
        {
            MenuItem item= menu.getItem(i);
            item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }

        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu:
                Intent intent = new Intent(LegendListActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.main_next:
                Intent i=new Intent(LegendListActivity.this,LegendListActivity2.class);
                startActivityForResult(i, 1);//Request code 1 is for ------>insert screen
            default:
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}
