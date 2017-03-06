package tools;

import android.content.Context;
import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2017/1/18.
 */
public class MobTools {

    private static Context context;

    public static void init(Context c) {
        context = c;
        SMSSDK.initSDK(context, "1303051201e78", "f05b9ea0f41d511d1193789045fa9a79");
    }

    public static void getCode(String phone) {
        SMSSDK.getVerificationCode("86", phone);
    }

    public static void submitCode(String phone,String code){
        SMSSDK.submitVerificationCode("86",phone,code);
    }

}
