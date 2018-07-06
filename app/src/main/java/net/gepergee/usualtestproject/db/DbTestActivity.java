package net.gepergee.usualtestproject.db;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import net.gepergee.usualtestproject.R;

/**
 * @author petergee
 * @date 2018/7/6
 */
public class DbTestActivity extends Activity {

    private SQLiteDatabase database;
    private TextView tvText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_test);
        initDb();
        initView();
    }

    private void initView() {
        tvText = findViewById(R.id.tv_text);
    }

    private void initDb() {
        // 构建dbopenHelper实例
        DbOpenHelper openHelper = DbOpenHelper.getDbHelperInstance(this);
        // 获取readableDatabase
        database = openHelper.getReadableDatabase();

        // 增加方式一：
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", 166);
        contentValues.put("name", "张三");
        contentValues.put("age", 19);
        database.insert("newtable", null, contentValues);

        // 增加方式二
        String addSpl2 = "insert into newtable(id,name,age) values(167,\"李四\",36)";
        database.execSQL(addSpl2);

        // 删除
        String delete = "delete from newtable where id=2";
        database.execSQL(delete);

        // 更新数据
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("id", 1);
        contentValues2.put("name", "王五");
        contentValues2.put("age", 19);
        database.update("newtable",contentValues2,"name=?",new String[]{"张三"});

        // 删除id小于100的数据
        database.execSQL("delete from newtable where id!=167");

        // 查询
        Cursor cursor = database.query("newtable", new String[]{"id", "age"}, null, null,
                null, null, "age");
        while (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex("id");
            int id = cursor.getInt(idIndex);

            // int nameIndex = cursor.getColumnIndex("name");
            // String name = cursor.getString(nameIndex);

            int ageIndex = cursor.getColumnIndex("age");
            int age = cursor.getInt(ageIndex);

            // String result = id + "  name=" + name + "   age=" + age;
            String result = id +"   age=" + age;
            System.out.println(result);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }
}
