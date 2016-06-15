package example.guanhang.smartsms.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by guanhang on 2016/6/13.
 */
public class GroupeOpenHelper extends SQLiteOpenHelper {

    private static GroupeOpenHelper instance;
    private GroupeOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static GroupeOpenHelper getInstance(Context context) {
        if (instance == null) {
            instance = new GroupeOpenHelper(context, "group.db", null, 1);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table groups(" +
                "_id integer primary key autoincrement, " +
                "name varchar, " +
                "create_date integer, " +
                "thread_count integer" +
                ")");
        //创建会话和群组的映射表
        db.execSQL("create table thread_group(" +
                "_id integer primary key autoincrement, " +
                "group_id integer, " +
                "thread_id integer" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
