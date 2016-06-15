package example.guanhang.smartsms.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import example.guanhang.smartsms.dao.GroupeOpenHelper;

/**内容提供者
 * Created by guanhang on 2016/6/13.
 */
public class GroupProvider extends ContentProvider{

    GroupeOpenHelper helper;
    private SQLiteDatabase db;
    private static final String TABLE_GROUPS = "groups";
    private static final String TABLE_THREAD_GROUP = "thread_group";

    private static final String authority = "com.guanhang.momo";
    public static final Uri BASE_URI = Uri.parse("content://" + authority);

    static final int CODE_GROUPS_INSERT = 0;
    static final int CODE_GROUPS_QUERY = 1;
    static final int CODE_GROUPS_UPDATE = 2;
    static final int CODE_GROUPS_DELETE = 3;
    static final int CODE_THREAD_GROUP_INSERT = 4;
    static final int CODE_THREAD_GROUP_QUERY = 5;
    static final int CODE_THREAD_GROUP_UPDATE = 6;
    static final int CODE_THREAD_GROUP_DELETE = 7;
    UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    {
        //添加匹配规则
        //authority是主机名，//path是要匹配的路径
        matcher.addURI(authority , "groups/insert", CODE_GROUPS_INSERT);
        matcher.addURI(authority , "groups/query", CODE_GROUPS_QUERY);
        matcher.addURI(authority , "groups/update", CODE_GROUPS_UPDATE);
        matcher.addURI(authority , "groups/delete", CODE_GROUPS_DELETE);
        matcher.addURI(authority , "thread_group/insert", CODE_THREAD_GROUP_INSERT);
        matcher.addURI(authority , "thread_group/query", CODE_THREAD_GROUP_QUERY);
        matcher.addURI(authority , "thread_group/update", CODE_THREAD_GROUP_UPDATE);
        matcher.addURI(authority , "thread_group/delete", CODE_THREAD_GROUP_DELETE);
    }
    @Override
    public boolean onCreate() {
        helper = GroupeOpenHelper.getInstance(getContext());
        db = helper.getWritableDatabase();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (matcher.match(uri)) {
            case CODE_GROUPS_QUERY:
                Cursor cursor = db.query(TABLE_GROUPS, projection, selection, selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), BASE_URI);
                return cursor;
            case CODE_THREAD_GROUP_QUERY:
                cursor = db.query(TABLE_THREAD_GROUP, projection, selection, selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), BASE_URI);
                return cursor;
            default:
                throw new IllegalArgumentException("未识别的uri: " + uri);
        }

    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (matcher.match(uri)) {
            case CODE_GROUPS_INSERT:
                long rawId = db.insert("groups", null, values);
                if (rawId == -1) {
                    return null;
                } else {
                    return ContentUris.withAppendedId(uri, rawId);
                }
            default:
                break;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (matcher.match(uri)) {
            case CODE_GROUPS_DELETE:
                int num = db.delete(TABLE_GROUPS, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(BASE_URI, null);
                return num;
            case CODE_THREAD_GROUP_DELETE:
                num = db.delete(TABLE_THREAD_GROUP, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(BASE_URI, null);
                return num;
            default:
                throw new IllegalArgumentException("未识别的uri:" + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        switch (matcher.match(uri)) {
            case CODE_GROUPS_UPDATE:
                int num = db.update(TABLE_GROUPS, values,selection, selectionArgs);
                getContext().getContentResolver().notifyChange(BASE_URI, null);
                return num;
            case CODE_THREAD_GROUP_UPDATE:
                num = db.update(TABLE_THREAD_GROUP, values, selection, selectionArgs);
                return num;
            default:
                throw new IllegalArgumentException("未识别的uri:" + uri);
        }
    }
}
