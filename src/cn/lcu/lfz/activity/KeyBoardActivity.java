package cn.lcu.lfz.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.*;
import cn.lcu.lfz.Discovery.R;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import dialog.ColorPickerDialogFragment;
import dialog.SaveKeyBoardDialogFragment;
import fragment.LoadKeyBoardDialogFragment;
import tools.MyTools;
import view.KeyBoardView;

/**
 * Created by Administrator on 2017/1/31.
 */
public class KeyBoardActivity extends Activity implements AdapterView.OnItemSelectedListener, View.OnClickListener, ColorPickerDialogFragment.GetColorEventListener, SaveKeyBoardDialogFragment.SaveKeyBoardCallback, LoadKeyBoardDialogFragment.LoadKeyBoardEventListener, KeyBoardView.OnKeyBoardSelectedListener {

    private KeyBoardView keyBoardView;
    private Spinner spn_kb,spn_area;
    private Button btn_enter,btn_save,btn_load;
    private ImageButton btn_back;
    private int color = Color.RED;
    private TextView tv_color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        LeancloudTools.init(this);
        LayoutInflater inflater = getLayoutInflater();
        LinearLayout v = (LinearLayout) inflater.inflate(R.layout.activity_keyboard,null);
        keyBoardView = new KeyBoardView(this);
        v.addView(keyBoardView);
        setContentView(v);
        tv_color = (TextView) findViewById(R.id.tv_color);
        spn_kb = (Spinner) v.findViewById(R.id.spn_kb);
        spn_area = (Spinner) v.findViewById(R.id.spn_area);
        btn_enter = (Button) v.findViewById(R.id.btn_enter);
        btn_save = (Button) v.findViewById(R.id.btn_save);
        btn_load = (Button) v.findViewById(R.id.btn_load);
        btn_back = (ImageButton) v.findViewById(R.id.btn_back);
        spn_kb.setOnItemSelectedListener(this);
//        spn_area.setOnItemSelectedListener(this);
        btn_enter.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        btn_load.setOnClickListener(this);
        tv_color.setOnClickListener(this);
        btn_back.setOnClickListener(this);

        btn_back.setImageDrawable(MyTools.loadAssets2Drawable(getAssets(),"back.png"));
    }


    //当具体的键盘被点击之后
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        keyBoardView.changeKeyboard(position,this);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_enter:
                if(spn_kb.getSelectedItemPosition() == 0){
                    Toast.makeText(KeyBoardActivity.this, "您还未选择键盘", Toast.LENGTH_SHORT).show();
                    return;
                }
//                确定点击，改变一层颜色
                int pos = spn_area.getSelectedItemPosition();
                keyBoardView.changeColor(pos,color);
                break;
            case R.id.tv_color:
                if(spn_kb.getSelectedItemPosition() == 0){
                    Toast.makeText(KeyBoardActivity.this, "您还未选择键盘", Toast.LENGTH_SHORT).show();
                    return;
                }
//                废弃代码：已用下面DialogFragment代替
//                Intent colorIntent = new Intent(this,ColorPickerActivity.class);
//                startActivity(colorIntent);

                ColorPickerDialogFragment colorPickerDialogFragment = new ColorPickerDialogFragment(this);
                colorPickerDialogFragment.show(getFragmentManager(),"colorPicker");
                break;
            case R.id.btn_save:
                if(spn_kb.getSelectedItemPosition() == 0){
                    Toast.makeText(KeyBoardActivity.this, "您还未选择键盘", Toast.LENGTH_SHORT).show();
                    return;
                }
                //保存当前自定义数据至服务器 保存类型为 AVObject("KeyBoard");
                //保存完毕后回调函数SaveCallback执行
                SaveKeyBoardDialogFragment saveKeyBoardDialogFragment = new SaveKeyBoardDialogFragment(this);
                saveKeyBoardDialogFragment.show(getFragmentManager(),"saveKeyBoard");

                break;
            case R.id.btn_load:
                if(spn_kb.getSelectedItemPosition() == 0){
                    Toast.makeText(KeyBoardActivity.this, "您还未选择键盘", Toast.LENGTH_SHORT).show();
                    return;
                }
                searchOtherKeyBoard();
                break;
            case R.id.btn_back:
                finish();
                break;
        }

    }

    //读取已保存键盘信息
    private void searchOtherKeyBoard() {
        LoadKeyBoardDialogFragment loadKeyBoardDialogFragment = new LoadKeyBoardDialogFragment(this,false);
        loadKeyBoardDialogFragment.show(getFragmentManager(),"keyboardList");
    }

    //选择颜色的回调函数
    @Override
    public void getColorEvent(int newColor) {
        tv_color.setBackgroundColor(newColor);
        color = newColor;
    }

    //保存设计项目至服务器
    @Override
    public void saveFinish(String str) {

        keyBoardView.keyboardModel.saveKeyBoard(str,new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e == null){
                    Toast.makeText(KeyBoardActivity.this, "保存数据完成！", Toast.LENGTH_SHORT).show();
                }else{
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onLoad(String destId) {
        keyBoardView.keyboardModel.recoverKeyBoard();
        keyBoardView.keyboardModel.loadKeyBoard(destId);
    }

    @Override
    public void onSelectedKeyBoard(String name) {

    }

    @Override
    public void onSelectedArea(String name) {

    }
}
