package example.guanhang.smartsms.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import example.guanhang.smartsms.R;

/**
 * 输入数字时自动弹出的listview的adapter
 * Created by guanhang on 2016/6/12.
 */
public class AutoSearchAdapter extends CursorAdapter {
    public AutoSearchAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return View.inflate(context, R.layout.item_auto_search_tv,null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = getHolder(view);
//        System.out.println("cursor 是不是为空" + cursor);
//        System.out.println("名字：" + cursor.getString(cursor.getColumnIndex("display_name")) + " " + cursor.getString(cursor.getColumnIndex("data1")));
        holder.tv_autosearch_name.setText(cursor.getString(cursor.getColumnIndex("display_name")));
        holder.tv_autosearch_address.setText(cursor.getString(cursor.getColumnIndex("data1")));
    }

    private ViewHolder getHolder(View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        return holder;
    }

    class ViewHolder {

        private TextView tv_autosearch_name;
        private TextView tv_autosearch_address;
        public ViewHolder(View view) {
            tv_autosearch_name = (TextView) view.findViewById(R.id.tv_autosearch_address);
            tv_autosearch_address = (TextView) view.findViewById(R.id.tv_autosearch_name);
        }
    }

    @Override
    public CharSequence convertToString(Cursor cursor) {
        //调用这个方法，
        return cursor.getString(cursor.getColumnIndex("data1"));
    }
}
