package example.guanhang.smartsms.utils;

import android.database.Cursor;

/**
 * Created by guanhang on 2016/5/31.
 */
public class CursorUtils {
    public static void printCursor(Cursor cursor) {
        LogUtils.i(cursor,"一共有" + cursor.getColumnCount() + "条数据");
        while (cursor.moveToNext()) {
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                String name = cursor.getColumnName(i);
                String content = cursor.getString(i);
                LogUtils.i(cursor, "名字 " + name + " " + "内容 " + content);
            }
            LogUtils.i(cursor, "=======================");
        }
    }
}
