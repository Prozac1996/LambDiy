package fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import cn.lcu.lfz.activity.MainActivity;
import cn.lcu.lfz.Discovery.R;
import com.avos.avoscloud.AVUser;
import diywidget.BottomToast;
import model.SimpleFragmentActivity;
import tools.LeancloudTools;

/**
 * Created by Administrator on 2016/11/13.
 */
public class LoginFragment extends Fragment implements View.OnClickListener{

    private EditText et_username,et_password;
    private Button btn_submit,btn_register;
//    private TextView tv_login_phone;
    private CheckBox cb_remember;
    private ImageView password_hide;
    private boolean isHide = true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AVUser user = AVUser.getCurrentUser();
        if(user != null){
            Intent i = new Intent(getActivity(), MainActivity.class);
            getActivity().startActivity(i);
            getActivity().finish();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login,container,false);
        et_username = (EditText) v.findViewById(R.id.username);
        et_password = (EditText) v.findViewById(R.id.password);
        btn_submit = (Button) v.findViewById(R.id.submit);
        btn_submit.setOnClickListener(this);
        btn_register = (Button) v.findViewById(R.id.register);
        btn_register.setOnClickListener(this);
        cb_remember = (CheckBox) v.findViewById(R.id.cb_remember);
//        tv_login_phone = (TextView) v.findViewById(R.id.tv_login_phone);
//        tv_login_phone.setOnClickListener(this);
        password_hide = (ImageView) v.findViewById(R.id.password_hide);
        password_hide.setOnClickListener(this);
        if(savedInstanceState != null){
            et_username.setText(savedInstanceState.getString("username"));
            et_password.setText(savedInstanceState.getString("password"));
        }
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                if(username.equals("") || password.equals(""))
//                    Toast.makeText(getActivity(),"填完再登陆呗！", Toast.LENGTH_SHORT).show();
                    BottomToast.showToast(getActivity(),"填完再登陆呗！");
                else
                    LeancloudTools.login(getActivity(),username,password);
                break;
            case R.id.register:
                SimpleFragmentActivity sfa = (SimpleFragmentActivity) getActivity();
                sfa.addFragment(R.id.container,new RegisterFragment());

                break;
            case R.id.password_hide:
                if(isHide){
                    isHide = false;
                    et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    isHide = true;
                    et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                break;
//            case R.id.tv_login_phone:
//                getFragmentManager().beginTransaction().addToBackStack("").add(R.id.container,new LoginPhoneFragment()).commit();
//                break;
        }
    }

    private void saveData(){
        SharedPreferences sp = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if(cb_remember.isChecked()){
            editor.putString("username",et_username.getText().toString());
            editor.putString("password",et_password.getText().toString());
            editor.putBoolean("isSave",true);
            editor.commit();
        }else{
            editor.putBoolean("isSave",false);
            editor.putString("username","");
            editor.putString("password","");
            editor.commit();
        }
    }

    private void loadData(){
            SharedPreferences sp = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
            boolean isSave = sp.getBoolean("isSave",false);
            if(isSave) {
                et_username.setText(sp.getString("username", ""));
                et_password.setText(sp.getString("password", ""));
                cb_remember.setChecked(true);
            }
    }

    @Override
    public void onPause() {
        super.onPause();
        saveData();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }
}
