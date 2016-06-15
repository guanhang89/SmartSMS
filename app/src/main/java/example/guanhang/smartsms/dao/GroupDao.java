package example.guanhang.smartsms.dao;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

import example.guanhang.smartsms.global.Constant;

/**
 * Created by guanhang on 2016/6/15.
 */
public class GroupDao {

    public static void insertGroup(ContentResolver resolver, String groupName){
        ContentValues values = new ContentValues();
        values.put("name", groupName);
        values.put("thread_count", 0);
        values.put("create_date", System.currentTimeMillis());
        resolver.insert(Constant.URI.URI_GROUP_INSERT, values);
    }

    public static void updateGroupName(ContentResolver resolver, String groupName, int _id){
        ContentValues values = new ContentValues();
        values.put("name", groupName);
        resolver.update(Constant.URI.URI_GROUP_UPDATE, values, "_id = " + _id, null);
    }

    public static void deleteGroup(ContentResolver resolver, int _id){
        resolver.delete(Constant.URI.URI_GROUP_DELETE, "_id = " + _id, null);
    }
    public static String getGroupNameByGroupId(ContentResolver resolver, int _id){
        String name = null;
        Cursor cursor = resolver.query(Constant.URI.URI_GROUP_QUERY, new String[]{"name"}, "_id = " + _id, null, null);
        if(cursor.moveToFirst()){
            name = cursor.getString(0);
        }
        return name;
    }

    public static int getThreadCount(ContentResolver resolver, int _id){
        int threadCount = -1;
        Cursor cursor = resolver.query(Constant.URI.URI_GROUP_QUERY, new String[]{"thread_count"}, "_id = " + _id, null, null);
        if(cursor.moveToNext()){
            threadCount = cursor.getInt(0);
        }
        return threadCount;
    }

    public static void updateThreadCount(ContentResolver resolver, int _id, int threadCount){
        ContentValues values = new ContentValues();
        values.put("thread_count", threadCount);
        resolver.update(Constant.URI.URI_GROUP_UPDATE, values, "_id = " + _id, null);
    }
}
