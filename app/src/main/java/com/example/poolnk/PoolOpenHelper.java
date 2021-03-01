package com.example.poolnk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PoolOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASENAME="poolWithDate.db";
    public static final String TABLE_SIRULINA="tblpoolwithdate";
    public static final int DATABASEVERSION=1;

    public static final String COLUMN_ID="poolId";
    public static final String COLUMN_TEMPERATURE="temperature";
    public static final String COLUMN_WATERLEVEL="waterlevel";
    public static final String COLUMN_PH="ph";
    public static final String COLUMN_DATE="date";

    private static final String CREATE_TABLE_SPIRULINA="CREATE TABLE IF NOT EXISTS " +
            TABLE_SIRULINA + "(" + COLUMN_ID +  " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_TEMPERATURE + " INTEGER,"  + COLUMN_WATERLEVEL + " INTEGER," + COLUMN_PH + " INTEGER," + COLUMN_DATE +   " VARCHAR "  +   ");";

    String []allColumns={PoolOpenHelper.COLUMN_ID, PoolOpenHelper.COLUMN_TEMPERATURE, PoolOpenHelper.COLUMN_WATERLEVEL, PoolOpenHelper.COLUMN_PH, PoolOpenHelper.COLUMN_DATE};

    SQLiteDatabase database;

    public PoolOpenHelper(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SPIRULINA);
        Log.i("data", "Table pool created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SIRULINA);
        onCreate(db);
    }

    public void open()
    {
        database=this.getWritableDatabase();
        Log.i("data", "Database connection open");
    }


    public Pool createPool(Pool c)
    {
        ContentValues values=new ContentValues();
        values.put(PoolOpenHelper.COLUMN_TEMPERATURE, c.getTemperature());
        values.put(PoolOpenHelper.COLUMN_WATERLEVEL, c.getWaterlevel());
        values.put(PoolOpenHelper.COLUMN_PH, c.getPh());
        values.put(PoolOpenHelper.COLUMN_DATE, c.getDate());


        long insertId=database.insert(TABLE_SIRULINA, null, values);
        Log.i("data", "pool " + insertId + "insert to database");
        c.setPoolId(insertId);
        return c;
    }

    public ArrayList<Pool> getAllPool() {

        ArrayList<Pool> l = new ArrayList<Pool>();
        Cursor cursor=database.query(PoolOpenHelper.TABLE_SIRULINA, allColumns, null, null, null, null, null);

        if(cursor.getCount()>0)
        {
            while(cursor.moveToNext())
            {
                long id=cursor.getLong(cursor.getColumnIndex(PoolOpenHelper.COLUMN_ID));
                Double  temperature=cursor.getDouble(cursor.getColumnIndex(PoolOpenHelper.COLUMN_TEMPERATURE));
                Double  waterlevel=cursor.getDouble(cursor.getColumnIndex(PoolOpenHelper.COLUMN_WATERLEVEL));
                Double  ph=cursor.getDouble(cursor.getColumnIndex(PoolOpenHelper.COLUMN_PH));
                String  Date=cursor.getString(cursor.getColumnIndex(PoolOpenHelper.COLUMN_DATE));
                Pool c=new Pool(id,temperature,waterlevel,ph,Date);
                l.add(c);
            }
        }
        return l;
    }

    public Pool getPoolById(long rowId)
    {
        Cursor cursor=database.query(PoolOpenHelper.TABLE_SIRULINA, allColumns, PoolOpenHelper.COLUMN_ID + "=" +rowId, null, null, null, null);
        cursor.moveToFirst();
        if(cursor.getCount()>0)
        {
            long id=cursor.getLong(cursor.getColumnIndex(PoolOpenHelper.COLUMN_ID));
            Double  temperature=cursor.getDouble(cursor.getColumnIndex(PoolOpenHelper.COLUMN_TEMPERATURE));
            Double  waterlevel=cursor.getDouble(cursor.getColumnIndex(PoolOpenHelper.COLUMN_WATERLEVEL));
            Double  ph=cursor.getDouble(cursor.getColumnIndex(PoolOpenHelper.COLUMN_PH));
            String  Date=cursor.getString(cursor.getColumnIndex(PoolOpenHelper.COLUMN_DATE));
            Pool c=new Pool(id,temperature,waterlevel,ph,Date);
            return c;
        }
        return null;
    }

    public long deleteAll()
    {
        return database.delete(PoolOpenHelper.TABLE_SIRULINA, null, null);

    }
    public long deleteCustomerByRow(long rowId)
    {
        return database.delete(PoolOpenHelper.TABLE_SIRULINA, PoolOpenHelper.COLUMN_ID + "=" + rowId, null);
    }

    public long updateByRow(Pool c)
    {
        ContentValues values=new ContentValues();
        values.put(PoolOpenHelper.COLUMN_ID, c.getPoolId());
        values.put(PoolOpenHelper.COLUMN_TEMPERATURE, c.getTemperature());
        values.put(PoolOpenHelper.COLUMN_WATERLEVEL, c.getWaterlevel());
        values.put(PoolOpenHelper.COLUMN_PH, c.getPh());

        return database.update(PoolOpenHelper.TABLE_SIRULINA, values, PoolOpenHelper.COLUMN_ID +"=" + c.getPoolId(), null);
    }

    public ArrayList<Pool>getAllCustomersByFIlter(String selection,String OrderBy)
    {
        Cursor cursor=database.query(PoolOpenHelper.TABLE_SIRULINA, allColumns, selection, null, null, null, OrderBy);
        ArrayList<Pool>l=convertCurserToList(cursor);
        return  l;
    }


    private ArrayList<Pool> convertCurserToList(Cursor cursor) {
        ArrayList<Pool>l=new ArrayList<Pool>();

        if(cursor.getCount()>0)
        {
            while(cursor.moveToNext())
            {
                long id=cursor.getLong(cursor.getColumnIndex(PoolOpenHelper.COLUMN_ID));
                Double  temperature=cursor.getDouble(cursor.getColumnIndex(PoolOpenHelper.COLUMN_TEMPERATURE));
                Double  waterlevel=cursor.getDouble(cursor.getColumnIndex(PoolOpenHelper.COLUMN_WATERLEVEL));
                Double  ph=cursor.getDouble(cursor.getColumnIndex(PoolOpenHelper.COLUMN_PH));
                String  Date=cursor.getString(cursor.getColumnIndex(PoolOpenHelper.COLUMN_DATE));
                Pool c=new Pool(id,temperature,waterlevel,ph,Date);
                l.add(c);

            }

        }
        return l;
    }

}
