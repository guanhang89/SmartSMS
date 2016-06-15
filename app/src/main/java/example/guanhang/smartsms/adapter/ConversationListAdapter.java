package example.guanhang.smartsms.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import example.guanhang.smartsms.R;
import example.guanhang.smartsms.bean.Conversation;
import example.guanhang.smartsms.dao.ContactDao;

/**
 * 会话界面
 * Created by guanhang on 2016/5/31.
 */
public class ConversationListAdapter extends CursorAdapter {

    public boolean isSelectMode() {
        return isSelectMode;
    }

    public void setSelectMode(boolean selectMode) {
        isSelectMode = selectMode;
    }

    public List<Integer> getSelectedConversationIds() {
        return selectedConversationIds;
    }

    List<Integer> selectedConversationIds = new ArrayList<>();

    private boolean isSelectMode = false;

    public ConversationListAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return View.inflate(context, R.layout.item_conversation_list, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = getHolder(view);
        Conversation conversation = Conversation.createFromCursor(cursor);
        holder.tv_body.setText(conversation.getSnippet());

        if (isSelectMode) {
            holder.iv_check.setVisibility(View.VISIBLE);
            if (selectedConversationIds.contains(conversation.getThread_id())) {
                holder.iv_check.setBackgroundResource(R.drawable.common_checkbox_checked);
            } else {
                holder.iv_check.setBackgroundResource(R.drawable.common_checkbox_normal);
            }
        } else {
            holder.iv_check.setVisibility(View.GONE);
        }

        String name = ContactDao.getNameByAddress(context.getContentResolver(), conversation.getAddress());
        if (TextUtils.isEmpty(name)) {
            holder.tv_address.setText(conversation.getAddress().concat("(").concat(conversation.getMsg_count().concat(")")));
        } else {
            holder.tv_address.setText(name.concat("(").concat(conversation.getMsg_count().concat(")")));
        }
        if (DateUtils.isToday(conversation.getDate())) {
            holder.tv_date.setText(DateFormat.getTimeFormat(context).format(conversation.getDate()));
        } else {
            holder.tv_date.setText(DateFormat.getDateFormat(context).format(conversation.getDate()));
        }
        Bitmap avatar = ContactDao.getAvatarByAddress(context.getContentResolver(), conversation.getAddress());
        if (avatar == null) {
            holder.iv_avator.setBackgroundResource(R.drawable.img_default_avatar);
        } else {
            holder.iv_avator.setBackgroundDrawable(new BitmapDrawable(context.getResources(), avatar));
        }

    }

    private ViewHolder getHolder(View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        return holder;
    }

    public void selectAll() {
        Cursor cuusor = getCursor();
        cuusor.moveToPosition(-1);
        selectedConversationIds.clear();
        while (cuusor.moveToNext()) {
            Conversation conversation = Conversation.createFromCursor(cuusor);
            selectedConversationIds.add(conversation.getThread_id());
        }
//        cuusor.close();
        notifyDataSetChanged();
    }

    public void cancelSelect() {
        selectedConversationIds.clear();
    }

    class ViewHolder {

        private final ImageView iv_avator;
        private final TextView tv_address;
        private final TextView tv_body;
        private final TextView tv_date;
        private final ImageView iv_check;

        public ViewHolder(View view) {
            iv_avator = (ImageView) view.findViewById(R.id.iv_avator);
            iv_check = (ImageView) view.findViewById(R.id.iv_check);
            tv_address = (TextView) view.findViewById(R.id.tv_address);
            tv_body = (TextView) view.findViewById(R.id.tv_body);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
        }
    }

    public void selectSingle(int position) {
        Cursor cursor = (Cursor) getItem(position);
        Conversation conversation = Conversation.createFromCursor(cursor);
        if (selectedConversationIds.contains(conversation.getThread_id())) {
            selectedConversationIds.remove((Integer) conversation.getThread_id());
        } else {
            selectedConversationIds.add(conversation.getThread_id());
        }
        notifyDataSetChanged();
    }
}
