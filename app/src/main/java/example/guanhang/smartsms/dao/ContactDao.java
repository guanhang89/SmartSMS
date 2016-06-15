package example.guanhang.smartsms.dao;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;

import java.io.InputStream;

/**查询
 * Created by guanhang on 2016/5/31.
 */
public class ContactDao {

    public static String getNameByAddress(ContentResolver contentResolver, String address) {
        String name = null;
        Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, address);
        Cursor cursor = contentResolver.query(uri, new String[]{PhoneLookup.DISPLAY_NAME}, null, null, null);
        assert cursor != null;
        if (cursor.moveToFirst()) {
            name = cursor.getString(0);
            cursor.close();
        }
        return name;
    }

    public static Bitmap getAvatarByAddress(ContentResolver contentResolver, String address) {
        Bitmap avatar = null;
        Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, address);
        Cursor cursor = contentResolver.query(uri, new String[]{PhoneLookup._ID}, null, null, null);
        assert cursor != null;
        if (cursor.moveToFirst()) {
            String _id = cursor.getString(0);
            InputStream is = ContactsContract.Contacts.openContactPhotoInputStream(contentResolver, Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, _id));
            avatar = BitmapFactory.decodeStream(is);
            cursor.close();
        }
        return avatar;

    }
}
