package example.guanhang.smartsms.global;

import android.net.Uri;

import example.guanhang.smartsms.provider.GroupProvider;

/**
 * Created by guanhang on 2016/5/30.
 */
public class Constant {
    public interface URI {
        Uri URI_SMS_CONVERSATION = Uri.parse("content://sms/conversations");
        Uri URI_SMS = Uri.parse("content://sms");

        Uri URI_GROUP_INSERT = Uri.parse(GroupProvider.BASE_URI + "/groups/insert");
        Uri URI_GROUP_QUERY = Uri.parse(GroupProvider.BASE_URI + "/groups/query");
        Uri URI_GROUP_UPDATE = Uri.parse(GroupProvider.BASE_URI + "/groups/update");
        Uri URI_GROUP_DELETE = Uri.parse(GroupProvider.BASE_URI + "/groups/delete");
        Uri URI_THREAD_GROUP_INSERT = Uri.parse(GroupProvider.BASE_URI + "/thread_group/insert");
        Uri URI_THREAD_GROUP_QUERY = Uri.parse(GroupProvider.BASE_URI + "/thread_group/query");
        Uri URI_THREAD_GROUP_UPDATE = Uri.parse(GroupProvider.BASE_URI + "/thread_group/update");
        Uri URI_THREAD_GROUP_DELETE = Uri.parse(GroupProvider.BASE_URI + "/thread_group/delete");
    }

    public interface SMS {
        int TYPE_RECEIVED = 1;
        int TYPE_SEND = 2;
    }
}
