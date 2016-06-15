package example.guanhang.smartsms.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import example.guanhang.smartsms.R;

/**
 * Created by guanhang on 2016/5/31.
 */
public class ConfirmDialog extends BaseDialog {

    private TextView tv_dialog_title;
    private TextView tv_dialog_message;
    private Button bt_dialog_cancel;
    private Button bt_dialog_confirm;

    public void setConfirmListener(ConfirmListener confirmListener) {
        this.confirmListener = confirmListener;
    }

    private ConfirmListener confirmListener;
    protected ConfirmDialog(Context context) {
        super(context);
    }


    private String title;
    private String message;
    public static void showDialog(Context context,String title, String message,ConfirmListener confirmListener) {
        ConfirmDialog dialog = new ConfirmDialog(context);
        dialog.title = title;
        dialog.message = message;
        dialog.confirmListener = confirmListener;
        dialog.show();
    }

    @Override
    public void initView() {
        setContentView(R.layout.dialog_confirm);
        tv_dialog_title = (TextView) findViewById(R.id.tv_dialog_title);
        tv_dialog_message = (TextView) findViewById(R.id.tv_dialog_message);
        bt_dialog_cancel = (Button) findViewById(R.id.bt_dialog_cancel);
        bt_dialog_confirm = (Button) findViewById(R.id.bt_dialog_confirm);
    }

    @Override
    public void initListener() {
        bt_dialog_cancel.setOnClickListener(this);
        bt_dialog_confirm.setOnClickListener(this);
    }

    @Override
    public void initDate() {
        tv_dialog_message.setText(message);
        tv_dialog_title.setText(title);
    }

    @Override
    public void processListener(View view) {
        switch (view.getId()) {
            case R.id.bt_dialog_cancel:
                if (confirmListener != null) {
                    confirmListener.onCancel();
                    dismiss();
                }
                break;
            case R.id.bt_dialog_confirm:
                if (confirmListener != null) {
                    confirmListener.onConfirm();
                    dismiss();
                }
                break;

        }
    }

    public interface ConfirmListener {
        void onCancel();

        void onConfirm();
    }

}
