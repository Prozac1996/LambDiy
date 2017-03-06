package fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.lcu.lfz.activity.LoginActivity;
import cn.lcu.lfz.Discovery.R;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import tools.LeancloudTools;
import tools.MobTools;
import tools.MyTools;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/11/15.
 * 注册界面Fragment
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {

    private EditText et_username,et_password,et_telephone,et_checkCode,et_pwdAgain;
    private Button btn_submit,btn_check;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobTools.init(getActivity());
        SMSSDK.registerEventHandler(h);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register,container,false);
        et_username = (EditText) v.findViewById(R.id.et_username);
        et_password = (EditText) v.findViewById(R.id.et_password);
        et_pwdAgain = (EditText) v.findViewById(R.id.et_pwdAgain);
        et_telephone = (EditText) v.findViewById(R.id.et_telephone);
        et_checkCode = (EditText) v.findViewById(R.id.et_checkCode);
        btn_submit = (Button) v.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        btn_check = (Button) v.findViewById(R.id.btn_check);
        btn_check.setOnClickListener(this);
        return v;
    }

    EventHandler h = new EventHandler(){
        @Override
        public void afterEvent(int event, int result, Object data) {
            if(result == SMSSDK.RESULT_COMPLETE) {
                if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //发送验证码
                    ((LoginActivity)getActivity()).showMsg("已发送成功");

                }else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码之后
                    HashMap<String,String> map = (HashMap<String, String>) data;
                    if(map.size() != 0){
                        //验证成功
                        String username = et_username.getText().toString();
                        String password = et_password.getText().toString();
                        String telephone = et_telephone.getText().toString();
                        LeancloudTools.register(getActivity(),username,password,telephone);
                    }else{
                        //验证失败
                        Toast.makeText(getActivity(), "验证码错误", Toast.LENGTH_SHORT).show();
                    }
                }
            }else{
                ((Throwable)data).printStackTrace();
            }
        }
    };


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_submit:

                if(checkEnable() == true);
                else{
                    Toast.makeText(getActivity(), "搞啥子搞，信息还没输完呢！", Toast.LENGTH_SHORT).show();
                    break;
                }

                if(!et_password.getText().toString().equals(et_pwdAgain.getText().toString())){
                    Toast.makeText(getActivity(), "两次输入密码不正确，请重新输入！", Toast.LENGTH_SHORT).show();
                    return;
                }
                String telephone = et_telephone.getText().toString();
                String code = et_checkCode.getText().toString();
                MobTools.submitCode(telephone,code);
                break;
            case R.id.btn_check:
                String phone = et_telephone.getText().toString();
                if(MyTools.isChinaPhoneLegal(phone))
                    MobTools.getCode(phone);
                else
                    Toast.makeText(getActivity(), "请输入正确的手机号！", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private boolean checkEnable() {
        boolean isOk = true;
        if(et_username.getText().toString().equals(""))
            isOk = false;
        if(et_password.getText().toString().equals(""))
            isOk = false;
        if(et_pwdAgain.getText().toString().equals(""))
            isOk = false;
        if(et_checkCode.getText().toString().equals(""))
            isOk = false;
        if(et_telephone.getText().toString().equals(""))
            isOk = false;

        return isOk;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(h);
    }

}
