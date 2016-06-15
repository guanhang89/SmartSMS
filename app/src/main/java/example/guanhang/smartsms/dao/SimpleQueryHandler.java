package example.guanhang.smartsms.dao;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.widget.CursorAdapter;

/**
 * Created by guanhang on 2016/5/31.
 */
public class SimpleQueryHandler extends AsyncQueryHandler {

    public SimpleQueryHandler(ContentResolver cr) {
        super(cr);
    }

    /**
     * 异步查询成功，就使cursor传给adapter
     */
    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        super.onQueryComplete(token, cookie, cursor);
        if (cookie != null && cookie instanceof CursorAdapter) {
            ((CursorAdapter)cookie).changeCursor(cursor);
        }
    }
}
