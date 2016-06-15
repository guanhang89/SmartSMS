package example.guanhang.smartsms.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import example.guanhang.smartsms.R;
import example.guanhang.smartsms.adapter.AutoSearchAdapter;
import example.guanhang.smartsms.base.BaseActivity;
import example.guanhang.smartsms.dao.SmsDao;

/**dfs
 * Created by guanhang on 2016/6/12.
 */
public class NewMsgActivity extends BaseActivity {

    private AutoCompleteTextView et_newmsg_address;
    private EditText et_newmsg_body;
    private Button bt_newmsg_send;
    private ImageView iv_newmsg_select_contact;
    private AutoSearchAdapter adapter;

    @Override
    public void initView() {
        setContentView(R.layout.activity_newmsg);
        et_newmsg_address = (AutoCompleteTextView) findViewById(R.id.tv_newmsg_address);
        et_newmsg_body = (EditText) findViewById(R.id.et_newmsg_body);
        bt_newmsg_send = (Button) findViewById(R.id.bt_newmsg_send);
        iv_newmsg_select_contact = (ImageView) findViewById(R.id.iv_newmsg_select);
    }

    @Override
    public void initListener() {
        iv_newmsg_select_contact.setOnClickListener(this);
        bt_newmsg_send.setOnClickListener(this);
    }

    @Override
    public void initData() {
        adapter = new AutoSearchAdapter(this, null);
        et_newmsg_address.setAdapter(adapter);

        adapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence constraint) {
                String[] projection = {
                        "data1",
                        "display_name",
                        "_id"
                };
                String selection = "data1 like '%" + constraint + "%'";
                Cursor cursor = getContentResolver().query(Phone.CONTENT_URI,
                        projection, selection, null, null);
                return cursor;
            }
        });
        initTitleBar();

    }

    private void initTitleBar() {
        findViewById(R.id.iv_titlebar_back).setOnClickListener(this);
        ((TextView) findViewById(R.id.tv_tittlebar)).setText("发送短信");
    }


    @Override
    public void processOnlick(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.iv_newmsg_select:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("vnd.android.cursor.dir/contact");
                startActivityForResult(intent, 0);
                break;
            default:
                String address = et_newmsg_address.getText().toString();
                String body = et_newmsg_body.getText().toString();
                if (!TextUtils.isEmpty(address) && !TextUtils.isEmpty(body)) {
                    SmsDao.sendSms(this, address, body);
                }
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Uri uri = data.getData();
            System.out.println(uri);
            if (uri != null) {
                String[] projection = {
                        "_id",
                        "has_phone_number"
                };
                Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();
                String _id = cursor.getString(0);
                int has_phone_number = cursor.getInt(1);

                if (has_phone_number == 0) {
                    Toast.makeText(this,"该联系人没有号码", Toast.LENGTH_SHORT).show();
                } else {
                    String selection = "contact_id =" + _id;
                    Cursor cursor2 = getContentResolver().query(Phone.CONTENT_URI,
                            new String[]{"data1"},selection, null, null);
                    cursor2.moveToFirst();
                    String data1 = cursor2.getString(0);
                    data1 = data1.replace(" ", "").replace("-", "");
                    et_newmsg_address.setText(data1);
                    et_newmsg_body.requestFocus();
                }
            }
        }

    }
}
