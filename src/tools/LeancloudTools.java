package tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import cn.lcu.lfz.activity.LoginActivity;
import cn.lcu.lfz.activity.MainActivity;
import com.avos.avoscloud.*;

/**
 * Created by Administrator on 2017/1/18.
 */
public class LeancloudTools {

    public static void init(Context context){
        AVOSCloud.initialize(context,"hJXrFJLHqFwx2BcV9FE4JCVh-gzGzoHsz", "hlgBDhF75y5MzfvsxcrQ2jpn");
    }


    public static void register(Context context,String username,String password,String number){
        AVUser user = new AVUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setMobilePhoneNumber(number);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if(e == null){
                    Toast.makeText(context,"用户注册成功！请登录！",Toast.LENGTH_SHORT).show();
                    ((LoginActivity)context).popFragment();
                }else{
                    Toast.makeText(context,"用户注册失败！原因是:"+e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void login(Context context,String username,String password){
        AVUser.logInInBackground(username, password, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if(e == null){
                    Toast.makeText(context, "登陆成功！", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(context, MainActivity.class);
                    ((Activity)context).startActivity(i);
                    ((Activity)context).finish();
                }else{
                    Toast.makeText(context, "登陆失败！原因是："+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
