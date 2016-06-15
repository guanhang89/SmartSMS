package example.guanhang.smartsms.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.List;

import example.guanhang.smartsms.R;
import example.guanhang.smartsms.adapter.ConversationListAdapter;
import example.guanhang.smartsms.base.BaseFragment;
import example.guanhang.smartsms.bean.Conversation;
import example.guanhang.smartsms.dao.SimpleQueryHandler;
import example.guanhang.smartsms.dialog.ConfirmDialog;
import example.guanhang.smartsms.dialog.DeleteMsgDialog;
import example.guanhang.smartsms.global.Constant;
import example.guanhang.smartsms.ui.activity.ConversationDetail;
import example.guanhang.smartsms.ui.activity.NewMsgActivity;

/**
 * 会话fragment写法
 * Created by guanhang on 2016/5/28.
 */
public class ConversationFragment extends BaseFragment {

    private View bt_conversation_edit;
    private View bt_newsms;
    private View bt_conversation_select;
    private View bt_conversation_cancel;
    private View bt_conversation_delete;
    private LinearLayout ll_select_menu;
    private LinearLayout ll_edit_menu;
    private ListView lv_conversation;
    private ConversationListAdapter adapter;
    private List<Integer> selectedConversationIds;

    private static final int DELETE_COMPLETE = 0;
    private static final int UPDATE_DELETE_PROGRESS = 1;
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DELETE_COMPLETE:
                    adapter.setSelectMode(false);
                    adapter.notifyDataSetChanged();
                    showEditMenu();
                    deleteMsgDialog.dismiss();
                    break;
                case UPDATE_DELETE_PROGRESS:
                    deleteMsgDialog.updateprogress(msg.arg1);
                    break;
                default:
                    break;
            }
        }
    };
    private DeleteMsgDialog deleteMsgDialog;
    private Button bt_conversation_newsms;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversation, null);
        bt_conversation_edit = view.findViewById(R.id.bt_edit);
        bt_newsms = view.findViewById(R.id.bt_conversation_newsms);
        bt_conversation_select = view.findViewById(R.id.bt_conversation_select);
        bt_conversation_cancel = view.findViewById(R.id.bt_conversation_cancel);
        bt_conversation_delete = view.findViewById(R.id.bt_conversation_delete);
        ll_select_menu = (LinearLayout) view.findViewById(R.id.ll_select_menu);
        ll_edit_menu = (LinearLayout) view.findViewById(R.id.ll_edit_menu);

        lv_conversation = (ListView) view.findViewById(R.id.lv_conversation);
        bt_conversation_newsms = (Button) view.findViewById(R.id.bt_conversation_newsms);
        return view;
    }

    @Override
    public void initListener() {
        bt_conversation_edit.setOnClickListener(this);
        bt_newsms.setOnClickListener(this);
        bt_conversation_select.setOnClickListener(this);
        bt_conversation_cancel.setOnClickListener(this);
        bt_conversation_delete.setOnClickListener(this);
        lv_conversation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (adapter.isSelectMode()) {
                    adapter.selectSingle(position);
                } else {
                    Intent intent = new Intent(getActivity(), ConversationDetail.class);
                    Cursor cursor = (Cursor) adapter.getItem(position);
                    Conversation con = Conversation.createFromCursor(cursor);
                    intent.putExtra("address", con.getAddress());
//                    System.out.println("id号 " + con.getThread_id());
                    intent.putExtra("thread_id", con.getThread_id());
                    startActivity(intent);
                }
            }
        });
        bt_conversation_newsms.setOnClickListener(this);
    }

    @Override
    public void initData() {
        String[] projection = {
                "sms.body AS snippet",
                "sms.thread_id AS _id",
                "groups.msg_count AS msg_count",
                "address AS address",
                "date AS date"
        };
        adapter = new ConversationListAdapter(getActivity(), null);
        lv_conversation.setAdapter(adapter);
/*        Cursor cursor = getActivity().getContentResolver().query(Constant.URI.URI_SMS_CONVERSATION, null, null, null, null);
        CursorUtils.printCursor(cursor);*/
        SimpleQueryHandler queryHandler = new SimpleQueryHandler(getActivity().getContentResolver());

        queryHandler.startQuery(0, adapter, Constant.URI.URI_SMS_CONVERSATION, projection, null, null, "date desc");
    }

    @Override
    public void processListener(View view) {
        switch (view.getId()) {
            case R.id.bt_edit:
                showselectMenu();
//                System.out.println("监听到事件");
                adapter.setSelectMode(true);
                adapter.notifyDataSetChanged();
                break;
            case R.id.bt_conversation_cancel:
                showEditMenu();
                adapter.setSelectMode(false);
                adapter.cancelSelect();
                adapter.notifyDataSetChanged();
                break;
            case R.id.bt_conversation_select:
                adapter.selectAll();
                break;
            case R.id.bt_conversation_delete:
                selectedConversationIds = adapter.getSelectedConversationIds();
                if (selectedConversationIds.size() == 0)
                    return;
                showDeleteDialog();
                break;
            case R.id.bt_conversation_newsms:
                Intent intent = new Intent(getActivity(), NewMsgActivity.class);
                startActivity(intent);
                break;

        }
    }

    private void showDeleteDialog() {
        ConfirmDialog.showDialog(getActivity(), "提示", "确定要删除会话吗？", new ConfirmDialog.ConfirmListener() {
            @Override
            public void onCancel() {
            }

            @Override
            public void onConfirm() {
                deleteSms();
            }
        });

    }

    private void showselectMenu() {
        ViewPropertyAnimator.animate(ll_edit_menu).translationY(ll_edit_menu.getHeight()).setDuration(200);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ViewPropertyAnimator.animate(ll_select_menu).translationY(0).setDuration(200);
            }
        },200);

    }

    private void showEditMenu() {
        ViewPropertyAnimator.animate(ll_select_menu).translationY(ll_select_menu.getHeight()).setDuration(200);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ViewPropertyAnimator.animate(ll_edit_menu).translationY(0).setDuration(200);
            }
        }, 200);
    }

    private boolean isStop = false;
    private void deleteSms() {
//        System.out.println(selectedConversationIds.size() + "进来的时候");
        deleteMsgDialog = DeleteMsgDialog.showDeleteDialog(getActivity(), selectedConversationIds.size(), new DeleteMsgDialog.OnDeleteCancelListener() {
            @Override
            public void onCancel() {
                isStop = true;
            }
        });
        Thread t = new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < selectedConversationIds.size(); i++) {
                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (isStop) {
                        isStop = false;
                        break;
                    }
                    String where = "thread_id = " + selectedConversationIds.get(i);
//                    System.out.println(where);
                    getActivity().getContentResolver().delete(Constant.URI.URI_SMS, where, null);
                    Message message = handler.obtainMessage();
                    message.what = UPDATE_DELETE_PROGRESS;
                    message.arg1 = i+1;
                    handler.sendMessage(message);
                }
                selectedConversationIds.clear();
                handler.sendEmptyMessage(DELETE_COMPLETE);
            }
        };
        t.start();
    }

}
