package com.example.poolnk;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AllActivity extends AppCompatActivity {



    PoolOpenHelper poh;
    ArrayList<Pool> listOfPool;
    ListView lv;

    PoolAdapter poolAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);

        lv=(ListView)findViewById(R.id.lv);

        poh=new PoolOpenHelper(this);
        listOfPool= new ArrayList<Pool>();
        poh.open();
        listOfPool=poh.getAllPool();
        poh.close();

        Log.i("data", "list size is " + listOfPool.size());

        if(listOfPool.size()==0)
        {
            createPool();
        }

        poolAdapter=new PoolAdapter(this,0,listOfPool);
        lv.setAdapter(poolAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pool p = poolAdapter.getItem(position);
                Toast.makeText(getBaseContext(), p.getPoolId() + " touched", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(AllActivity.this, UpdateActivity.class);
                i.putExtra("rowId", p.getPoolId());
                startActivityForResult(i, 0);
            }
        });
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    public void createPool(){
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy\nhh-mm-ss a");
        String Date = sdf.format(date);

        poh.open();
        Pool p1 = new Pool(0, 13.65, 25.0, 2.4, Date);
        p1=poh.createPool(p1);
        listOfPool.add(p1);

        Pool p2 = new Pool(0, 5.7, 1.2, 5.9, Date);
        p2=poh.createPool(p2);
        listOfPool.add(p2);
        poh.close();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);

        MenuItem item= menu.getItem(0);
        item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        item= menu.getItem(5);
        item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);


        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        poh.open();
        switch (item.getItemId()) {
            case R.id.main_menu:
                Intent intent = new Intent(AllActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_settings:
                Intent intent2 = new Intent(AllActivity.this, SettingsActivity.class);
                startActivity(intent2);
                break;
            case R.id.menu_allPools:
                listOfPool=poh.getAllPool();
                Log.i("filter", "list count is " + listOfPool.size());
                refreshMyAdapter();
                break;
            case R.id.menu_hottest:
                listOfPool=poh.getAllCustomersByFIlter("temperature>25", "temperature ASC");
                Log.i("filter", "list count is " + listOfPool.size());
                refreshMyAdapter();
                break;
            case R.id.menu_most_cold :
                listOfPool=poh.getAllCustomersByFIlter("temperature<18", "temperature DESC");
                Log.i("filter", "list count is " + listOfPool.size());
                refreshMyAdapter();
                break;
            case R.id.menu_new:
                Intent i=new Intent(AllActivity.this,InsertActivity.class);
                startActivityForResult(i, 1);//Request code 1 is for ------>insert screen
            default:
                break;
        }
        poh.close();

        return super.onOptionsItemSelected(item);

    }
    public void refreshMyAdapter()
    {

        poolAdapter=new PoolAdapter(this,0,listOfPool);
        lv.setAdapter(poolAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK)
        {
            poh.open();
            listOfPool=poh.getAllPool();
            refreshMyAdapter();
            poh.close();

            if(requestCode==0)
            {
                Toast.makeText(getBaseContext(), "Database updated", Toast.LENGTH_SHORT).show();

            }
            else if (requestCode==1)
            {
                Toast.makeText(getBaseContext(), "New Pool add to database", Toast.LENGTH_SHORT).show();
            }


        }
    }

}
