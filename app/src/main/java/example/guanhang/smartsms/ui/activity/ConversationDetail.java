package example.guanhang.smartsms.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import example.guanhang.smartsms.R;
import example.guanhang.smartsms.adapter.ConversationDetailAdapter;
import example.guanhang.smartsms.base.BaseActivity;
import example.guanhang.smartsms.dao.ContactDao;
import example.guanhang.smartsms.dao.SimpleQueryHandler;
import example.guanhang.smartsms.dao.SmsDao;
import example.guanhang.smartsms.global.Constant;

/**
 * Created by guanhang on 2016/6/5.
 */
public class ConversationDetail extends BaseActivity {

    private String address;
    private int thread_id;
    private ConversationDetailAdapter adapter;
    private ListView lv_detail;
    private EditText et_detail;
    private Button bt_detail;
    private ImageView iv_titlebar_back;

    @Override
    public void initView() {
        setContentView(R.layout.activity_conversation_detail);

        lv_detail = (ListView) findViewById(R.id.lv_conversation_detail);
        et_detail = (EditText) findViewById(R.id.et_conversation_detail);
        bt_detail = (Button) findViewById(R.id.bt_conversation_detail);

        iv_titlebar_back = (ImageView) findViewById(R.id.iv_titlebar_back);
        lv_detail.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
    }

    @Override
    public void initListener() {
        iv_titlebar_back.setOnClickListener(this);
        bt_detail.setOnClickListener(this);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            address = intent.getStringExtra("address");
            thread_id = intent.getIntExtra("thread_id",-1);
//            System.out.println(address + " 数据" + thread_id);
            initTitleBar();
        }
        adapter = new ConversationDetailAdapter(this, null,lv_detail);
        lv_detail.setAdapter(adapter);
        String[] projections = {
                "_id",
                "body",
                "type",
                "date"
        };
        String selection = "thread_id = " + thread_id;
        SimpleQueryHandler queryHandler = new SimpleQueryHandler(getContentResolver());
//        Cursor cursor = getContentResolver().query(Constant.URI.URI_SMS, projections, selection, null, null);
//        CursorUtils.printCursor(cursor);
        queryHandler.startQuery(0, adapter, Constant.URI.URI_SMS, projections, selection, null, "date");

    }

    @Override
    public void processOnlick(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.bt_conversation_detail:
                String body = et_detail.getText().toString();
                if (!TextUtils.isEmpty(body)) {
                    SmsDao.sendSms(this, address, body);
                    et_detail.setText("");
                }
        }

    }

    private void initTitleBar() {
        findViewById(R.id.bt_conversation_detail).setOnClickListener(this);
        String name = ContactDao.getNameByAddress(getContentResolver(),address);
//        System.out.println(name);
        ((TextView)findViewById(R.id.tv_tittlebar)).setText(TextUtils.isEmpty(name)?address:name);
    }
}
