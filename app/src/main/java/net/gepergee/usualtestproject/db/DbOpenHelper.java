package net.gepergee.usualtestproject.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * db 帮助类
 *
 * @author petergee
 * @date 2018/7/6
 */
public class DbOpenHelper extends SQLiteOpenHelper {
    public static final String CREATE_TABLE = "create table mytable (id integer primary key ,name text,age integer)";
    public static final String CREATE_NEW_TABLE="create table newtable (id integer primary key autoincrement,name text,age integer)";
    public static final String SELECT_SQL="select * from newtable where age>10 or id>100";
    public static final String SELECT_SQL_TWO="select * from newtable where age in(10,100)";
    public static final String SELECT_SQL_THREE="select * from newtable where age between 10 and 100";
    private static final String DB_NAME = "newdb.db";
    private static final int DB_VERSION = 1;
    private static DbOpenHelper dbOpenHelperInstance;

    private DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static synchronized DbOpenHelper getDbHelperInstance(Context context) {
        if (dbOpenHelperInstance == null) {
            dbOpenHelperInstance = new DbOpenHelper(context.getApplicationContext());
        }
        return dbOpenHelperInstance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建表
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_NEW_TABLE);
        db.execSQL(SELECT_SQL);
        db.execSQL(SELECT_SQL_TWO);
        db.execSQL(SELECT_SQL_THREE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
