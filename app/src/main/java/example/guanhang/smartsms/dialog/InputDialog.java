package example.guanhang.smartsms.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import example.guanhang.smartsms.R;

/**
 * Created by guanhang on 2016/6/13.
 */
public class InputDialog extends BaseDialog {

    private OnInputDialogListener onInputDialogListener;
    private String title;
    private TextView tv_inputdialog_title;
    private EditText et_inputdialog_message;
    private Button bt_inputdialog_cancel;
    private Button bt_inputdialog_confirm;

    protected InputDialog(Context context, String title, OnInputDialogListener onInputDialogListener) {
        super(context);
        this.title = title;
        this.onInputDialogListener = onInputDialogListener;
    }

    public static void showDialog(Context context, String title, OnInputDialogListener onInputDialogListener) {
        InputDialog dialog = new InputDialog(context, title, onInputDialogListener);
        dialog.setView(new EditText(context));
        dialog.show();
    }

    @Override
    public void initView() {
        setContentView(R.layout.dialog_input);
        tv_inputdialog_title = (TextView) findViewById(R.id.tv_inputdialog_title);
        et_inputdialog_message = (EditText) findViewById(R.id.et_inputdialog_message);
        bt_inputdialog_cancel = (Button) findViewById(R.id.bt_inputdialog_cancel);
        bt_inputdialog_confirm = (Button) findViewById(R.id.bt_inputdialog_confirm);
    }

    @Override
    public void initListener() {
        bt_inputdialog_cancel.setOnClickListener(this);
        bt_inputdialog_confirm.setOnClickListener(this);
    }

    @Override
    public void initDate() {
        tv_inputdialog_title.setText(title);
    }

    @Override
    public void processListener(View view) {
        switch (view.getId()) {
            case R.id.bt_inputdialog_cancel:
                if (onInputDialogListener != null) {
                    onInputDialogListener.onCancel();
                }
                dismiss();
                break;
            case R.id.bt_inputdialog_confirm:
                if (onInputDialogListener != null) {
//                    System.out.println("inputdialog_数据改变");
                    onInputDialogListener.onConfirm(et_inputdialog_message.getText().toString());
                }
                dismiss();
                break;
        }

    }

    public interface OnInputDialogListener {
        void onCancel();

        void onConfirm(String text);
    }
}
