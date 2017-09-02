package cn.lcu.lfz.activity;

import fragment.LoginFragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.Toast;
import model.SimpleFragmentActivity;
import tools.LeancloudTools;
import cn.lcu.lfz.Discovery.R;


public class LoginActivity extends SimpleFragmentActivity {
    /**
     * Called when the activity is first created.
     */

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Bundle arg = msg.getData();
                    String str = arg.getString("str");
                    Toast.makeText(LoginActivity.this, str, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    fm.popBackStack();
            }
        }
    };

    public void showMsg(String str) {
        Message msg = new Message();
        msg.what = 1;
        Bundle args = new Bundle();
        args.putString("str", str);
        msg.setData(args);
        handler.sendMessage(msg);
    }

    public void popFragment() {
        Message msg = new Message();
        msg.what = 2;
        handler.sendMessage(msg);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        LeancloudTools.init(this);
        replaceFragment(R.id.container, new LoginFragment());
    }

}
