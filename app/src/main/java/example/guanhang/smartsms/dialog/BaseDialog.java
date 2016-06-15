package example.guanhang.smartsms.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import example.guanhang.smartsms.R;

/**基类对话框
 * Created by guanhang on 2016/5/31.
 */
public abstract class BaseDialog extends AlertDialog implements View.OnClickListener {
    protected BaseDialog(Context context) {
        super(context, R.style.BaseDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        initDate();
    }

    public abstract void initView();

    public abstract void initListener();

    public abstract void initDate();

    public abstract void processListener(View view);

    @Override
    public void onClick(View v) {
        processListener(v);
    }
}
