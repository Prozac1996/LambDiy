package dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import cn.lcu.lfz.Discovery.R;

/**
 * Created by Administrator on 2017/2/3.
 */
public class SaveKeyBoardDialogFragment extends DialogFragment implements View.OnClickListener {

    private EditText et_proname;
    private Button btn_cancel,btn_ensure;
    private SaveKeyBoardCallback saveKeyBoardCallback;

    public interface SaveKeyBoardCallback{
        void saveFinish(String str);
    }

    public SaveKeyBoardDialogFragment(SaveKeyBoardCallback saveKeyBoardCallback){
        this.saveKeyBoardCallback = saveKeyBoardCallback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog_save,container,false);
        et_proname = (EditText) v.findViewById(R.id.et_proname);
        btn_cancel = (Button) v.findViewById(R.id.btn_cancel);
        btn_ensure = (Button) v.findViewById(R.id.btn_ensure);
        btn_cancel.setOnClickListener(this);
        btn_ensure.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cancel:
                getFragmentManager().popBackStack();
                dismiss();
                break;
            case R.id.btn_ensure:
                saveKeyBoardCallback.saveFinish(et_proname.getText().toString());
                dismiss();
                break;
        }
    }
}
