package fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.lcu.lfz.activity.MainActivity;
import cn.lcu.lfz.Discovery.R;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.RequestMobileCodeCallback;

/**
 * Created by Administrator on 2017/2/7.
 * 未用，原因是使用的Mob的免费短信验证码，没有使用LeanCloud的验证码。
 */
public class LoginPhoneFragment extends Fragment implements View.OnClickListener {

    private Button btn_sendCode, btn_submit;
    private EditText et_phone, et_checkCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login_phone, container, false);
        btn_sendCode = (Button) v.findViewById(R.id.btn_sendCode);
        btn_submit = (Button) v.findViewById(R.id.btn_submit);
        et_phone = (EditText) v.findViewById(R.id.et_phone);
        et_checkCode = (EditText) v.findViewById(R.id.et_checkCode);
        btn_submit.setOnClickListener(this);
        btn_sendCode.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sendCode:
                AVUser.requestLoginSmsCodeInBackground(et_phone.getText().toString(), new RequestMobileCodeCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            Toast.makeText(getActivity(), "验证码发送成功", Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(getActivity(), "验证码发送失败,原因是：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.btn_submit:
                AVUser.signUpOrLoginByMobilePhoneInBackground(et_phone.getText().toString(), et_checkCode.getText().toString(), new LogInCallback<AVUser>() {
                    @Override
                    public void done(AVUser avUser, AVException e) {
                        if (e == null) {
                            Toast.makeText(getActivity(), "欢迎你，" + avUser.getUsername(), Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getActivity(), MainActivity.class);
                            startActivity(i);
                            getActivity().finish();
                        }
                    }
                });
                break;
        }
    }
}
