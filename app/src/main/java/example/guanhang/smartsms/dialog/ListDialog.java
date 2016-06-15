package example.guanhang.smartsms.dialog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import example.guanhang.smartsms.R;

/**
 * Created by guanhang on 2016/6/15.
 */
public class ListDialog extends BaseDialog {

    private TextView tv_listdialog_title;
    private ListView lv_listdialog;

    private Context context;
    private String title;
    private String[] items;
    private OnlistDialogListener onlistDialogListener;
    private TextView tv_item_listdialog;

    protected ListDialog(Context context, String title, String[] items, OnlistDialogListener onlistDialogListener) {
        super(context);
        this.context = context;
        this.title = title;
        this.items = items;
        this.onlistDialogListener = onlistDialogListener;
    }

    @Override
    public void initView() {
        setContentView(R.layout.dialog_list);
        tv_listdialog_title = (TextView) findViewById(R.id.tv_listdialog_title);
        lv_listdialog = (ListView) findViewById(R.id.lv_listdialog);
    }

    public static void showDialog(Context context, String title, String[] items, OnlistDialogListener onlistDialogListener) {
        ListDialog dialog = new ListDialog(context, title, items, onlistDialogListener);
        dialog.show();
    }
    @Override
    public void initListener() {
        lv_listdialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onlistDialogListener != null) {
                    onlistDialogListener.onItemClick(parent, view,position,id);
                }
                dismiss();
            }
        });
    }

    @Override
    public void initDate() {
//        System.out.println("listdialog_title" + tv_listdialog_title);
//        System.out.println("listdialog_list" + lv_listdialog);
        tv_listdialog_title.setText(title);
        lv_listdialog.setAdapter(new MyAdapter());
    }

    @Override
    public void processListener(View view) {

    }

    public interface OnlistDialogListener {
        void onItemClick(AdapterView<?> parent, View view, int position, long id);
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int position) {
            return items[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(context, R.layout.item_listdialog, null);
            tv_item_listdialog = (TextView) view.findViewById(R.id.tv_item_listdialog);
            tv_item_listdialog.setText(items[position]);
            return null;
        }
    }
}
