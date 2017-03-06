package dialog;

/*
           调色盘模块，调色图可以自己更改

 */

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;
import cn.lcu.lfz.Discovery.R;
import view.ColorPickView;

/**
 * Created by Administrator on 2017/2/1.
 */
public class ColorPickerDialogFragment extends DialogFragment implements View.OnClickListener {
    private TextView txtColor;
    private ColorPickView myView;
    private Button btn_getColor;
    private int nowColor;

    GetColorEventListener listener;

    public interface GetColorEventListener{
        void getColorEvent(int newColor);
    }

    //回调方法，点击确定传值出去
    public ColorPickerDialogFragment(GetColorEventListener listener){
        super();
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View v = LayoutInflater.from(getActivity()).inflate(
                R.layout.fragment_colorpick, null);
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialogAnim;
        dialog.setContentView(v);
        myView = (ColorPickView)v.findViewById(R.id.color_picker_view);
        txtColor = (TextView) v.findViewById(R.id.txt_color);
        btn_getColor = (Button) v.findViewById(R.id.btn_getColor);
        btn_getColor.setOnClickListener(this);
        myView.setOnColorChangedListener(new ColorPickView.OnColorChangedListener() {

            @Override
            public void onColorChange(int color) {
                txtColor.setBackgroundColor(color);
                nowColor = color;
            }

        });

        return dialog;
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
//        View v = inflater.inflate(R.layout.fragment_colorpick,container,false);
//        myView = (ColorPickView)v.findViewById(R.id.color_picker_view);
//        txtColor = (TextView) v.findViewById(R.id.txt_color);
//        btn_getColor = (Button) v.findViewById(R.id.btn_getColor);
//        btn_getColor.setOnClickListener(this);
//        myView.setOnColorChangedListener(new ColorPickView.OnColorChangedListener() {
//
//            @Override
//            public void onColorChange(int color) {
//                txtColor.setBackgroundColor(color);
//                nowColor = color;
//            }
//
//        });
//        return v;
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_getColor:
                listener.getColorEvent(nowColor);
                dismiss();
                break;
        }
    }
}
