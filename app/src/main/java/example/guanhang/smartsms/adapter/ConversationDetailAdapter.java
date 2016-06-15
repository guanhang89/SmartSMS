package example.guanhang.smartsms.adapter;

import android.content.Context;
import android.database.Cursor;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import example.guanhang.smartsms.R;
import example.guanhang.smartsms.bean.Sms;
import example.guanhang.smartsms.global.Constant;

/**
 * Created by guanhang on 2016/6/11.
 */
public class ConversationDetailAdapter extends CursorAdapter {

    private static final int DURATION = 5*60*1000;
    private ListView lv;
    public ConversationDetailAdapter(Context context, Cursor c, ListView listView) {
        super(context, c);
        this.lv = listView;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return View.inflate(context, R.layout.item_converdetail,null);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = getHolder(view);
        Sms sms = Sms.createFromCursor(cursor);
        if (cursor.getPosition() == 0) {
            showDate(context,holder,sms);
        } else {
            long preDate = getPrevoiusSmsDate(cursor.getPosition());
            if (sms.getDate() - preDate > DURATION) {
                holder.tv_date.setVisibility(View.VISIBLE);
                showDate(context, holder, sms);
            } else {
                holder.tv_date.setVisibility(View.GONE);
            }
        }
        holder.tv_left.setVisibility(sms.getType() == Constant.SMS.TYPE_RECEIVED ? View.VISIBLE : View.GONE);
        holder.tv_right.setVisibility(sms.getType() == Constant.SMS.TYPE_SEND ? View.VISIBLE : View.GONE);
        if (sms.getType() == Constant.SMS.TYPE_RECEIVED) {
            holder.tv_left.setText(sms.getBody());
        } else {
            holder.tv_right.setText(sms.getBody());
        }
    }

    private ViewHolder getHolder(View view) {
        ViewHolder holder =  (ViewHolder) view.getTag();
        if (holder == null) {
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        return holder;
    }

    class ViewHolder {
        private final TextView tv_date;
        private final TextView tv_left;
        private final TextView tv_right;

        public ViewHolder(View v) {
            tv_date =  (TextView) v.findViewById(R.id.tv_item_detail_date);
            tv_left = (TextView) v.findViewById(R.id.tv_item_detail_left);
            tv_right = (TextView) v.findViewById(R.id.tv_item_detail_right);
        }
    }

    private void showDate(Context context, ViewHolder holder, Sms sms) {
        if (DateUtils.isToday(sms.getDate())) {
            holder.tv_date.setText(DateFormat.getTimeFormat(context).format(sms.getDate()));
        } else {
            holder.tv_date.setText(DateFormat.getDateFormat(context).format(sms.getDate()));
        }
    }

    private long getPrevoiusSmsDate(int position) {
        Cursor cursor = (Cursor) getItem(position - 1);
        Sms sms = Sms.createFromCursor(cursor);
        return sms.getDate();
    }

    @Override
    public void changeCursor(Cursor cursor) {
        super.changeCursor(cursor);
        lv.setSelection(cursor.getCount());
    }
}
