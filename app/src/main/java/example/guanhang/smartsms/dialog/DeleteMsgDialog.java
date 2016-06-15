package example.guanhang.smartsms.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import example.guanhang.smartsms.R;

/**
 * Created by guanhang on 2016/6/5.
 */
public class DeleteMsgDialog extends BaseDialog {

    private TextView tv_delete;
    private ProgressBar pb_delete;
    private Button bt_delete_cancel;

    private int maxProgress;
    protected DeleteMsgDialog(Context context, int maxProgress,OnDeleteCancelListener onDeleteCancelListener) {
        super(context);
        this.maxProgress = maxProgress;
        this.onDeleteCancelListener = onDeleteCancelListener;
    }
    public static DeleteMsgDialog showDeleteDialog(Context context, int maxProgress,OnDeleteCancelListener onDeleteCancelListener){
        DeleteMsgDialog deleteMsgDialog = new DeleteMsgDialog(context,maxProgress,onDeleteCancelListener);
        deleteMsgDialog.show();
        return  deleteMsgDialog;
    }

    private OnDeleteCancelListener onDeleteCancelListener;

    @Override
    public void initView() {
        setContentView(R.layout.dialog_delete);

        tv_delete = (TextView) findViewById(R.id.tv_delete_title);
        pb_delete = (ProgressBar) findViewById(R.id.delete_pb);
        bt_delete_cancel = (Button) findViewById(R.id.bt_delete_cancel);
    }

    @Override
    public void initListener() {
        bt_delete_cancel.setOnClickListener(this);

    }

    @Override
    public void initDate() {
        tv_delete.setText("正在删除(0/".concat(maxProgress + "").concat(")"));
        pb_delete.setMax(maxProgress);

    }

    @Override
    public void processListener(View view) {
        switch (view.getId()) {
            case R.id.bt_delete_cancel:
                if (onDeleteCancelListener != null) {
                    onDeleteCancelListener.onCancel();
                }
                dismiss();
                break;
        }
    }

    public interface OnDeleteCancelListener {
        void onCancel();
    }

    public void updateprogress(int progress) {
        pb_delete.setProgress(progress);
        tv_delete.setText("正在删除(".concat(progress+"/" + maxProgress + ")"));
    }
}
