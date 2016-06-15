package example.guanhang.smartsms.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    public static final String ACTION_SEND_SMS = "com.guanhang.sendsms";

    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        int code = getResultCode();
        if(code == Activity.RESULT_OK ){
            Toast.makeText(context, "发送成功", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "发送失败", Toast.LENGTH_SHORT).show();
        }
    }
}
