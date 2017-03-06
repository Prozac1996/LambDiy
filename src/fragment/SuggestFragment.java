package fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.lcu.lfz.Discovery.R;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;

/**
 * Created by Administrator on 2017/2/2.
 */
public class SuggestFragment extends Fragment implements View.OnClickListener {

    private EditText et_suggest;
    private Button btn_submit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog_suggest,container,false);
        et_suggest = (EditText) v.findViewById(R.id.et_suggest);
        btn_submit = (Button) v.findViewById(R.id.btn_submitSuggest);
        btn_submit.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submitSuggest:
                String suggest = et_suggest.getText().toString();
                AVObject object = new AVObject("Suggest");
                object.put("user", AVUser.getCurrentUser());
                object.put("suggest",suggest);
                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if(e == null){
                            Toast.makeText(getActivity(), "提交完成", Toast.LENGTH_SHORT).show();
                            getFragmentManager().popBackStack();
                        }else{
                            e.printStackTrace();
                        }
                    }
                });
                break;
        }
    }
}
