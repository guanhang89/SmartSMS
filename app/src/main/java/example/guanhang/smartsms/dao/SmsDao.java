package example.guanhang.smartsms.dao;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

import java.util.List;

import example.guanhang.smartsms.global.Constant;
import example.guanhang.smartsms.receiver.MyReceiver;

/**
 * Created by guanhang on 2016/6/12.
 */
public class SmsDao {

    public static void sendSms(Context context, String address, String body) {
        SmsManager manager = SmsManager.getDefault();
        List<String> smss = manager.divideMessage(body);
        Intent intent = new Intent(MyReceiver.ACTION_SEND_SMS);
        PendingIntent sentIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        for (String text : smss) {
            manager.sendTextMessage(address, null, text, sentIntent, null);
            insertSms(context, address, text);
        }
    }

    private static void insertSms(Context context, String address, String text) {
        ContentValues values = new ContentValues();
        values.put("address", address);
        values.put("body", text);
        values.put("type", Constant.SMS.TYPE_SEND);
        context.getContentResolver().insert(Constant.URI.URI_SMS, values);
    }
}
