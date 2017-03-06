package cn.lcu.lfz.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.lcu.lfz.Discovery.R;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import dialog.AreaSelectDialogFragment;
import dialog.ColorPickerDialogFragment;
import dialog.SaveKeyBoardDialogFragment;
import diywidget.BottomToast;
import fragment.LoadKeyBoardDialogFragment;
import view.KeyBoardView;

/**
 * Created by Administrator on 2017/2/26.
 */
public class KeyBoard2Activity extends Activity implements View.OnClickListener, KeyBoardView.OnKeyBoardSelectedListener, ColorPickerDialogFragment.GetColorEventListener, AreaSelectDialogFragment.OnAreaSelectedListener, SaveKeyBoardDialogFragment.SaveKeyBoardCallback, LoadKeyBoardDialogFragment.LoadKeyBoardEventListener {

    private LinearLayout btn_model,btn_area,iv_palette,btn_save,iv_share,btn_load,btn_spray,layout_control,btn_hide;
    private ImageView btn_back;
    private ImageView btn_menu;
    private TextView tv_title,tv_color,tv_area;


    private KeyBoardView keyBoardView;
    private int color = Color.WHITE;
    private int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = getLayoutInflater();
        FrameLayout keyboard_layout = (FrameLayout) inflater.inflate(R.layout.activity_keyboard2,null);
        LinearLayout keyboard_container = (LinearLayout) keyboard_layout.findViewById(R.id.keyboard_container);
        keyBoardView = new KeyBoardView(this);
        keyboard_container.addView(keyBoardView);
        setContentView(keyboard_layout);
        init();
     }

    private void init() {
        btn_model = (LinearLayout) findViewById(R.id.keyboard_model);
        btn_area = (LinearLayout) findViewById(R.id.keyboard_area);
        btn_save = (LinearLayout) findViewById(R.id.keyboard_save);
        btn_load = (LinearLayout) findViewById(R.id.keyboard_load);
        btn_hide = (LinearLayout) findViewById(R.id.keyboard_hide_control);
        iv_share = (LinearLayout) findViewById(R.id.keyboard_share);
        iv_palette = (LinearLayout) findViewById(R.id.keyboard_palette);
        btn_spray = (LinearLayout) findViewById(R.id.keyboard_spray);
        btn_back = (ImageView) findViewById(R.id.keyboard_back);
        btn_menu = (ImageView) findViewById(R.id.keyboard_menu);
        tv_title = (TextView) findViewById(R.id.keyboard_title);
        tv_color = (TextView) findViewById(R.id.keyboard_info_color);
        tv_area = (TextView) findViewById(R.id.keyboard_info_area);
        layout_control = (LinearLayout) findViewById(R.id.keyboard_control_layout);

        btn_model.setOnClickListener(this);
        btn_area.setOnClickListener(this);
        btn_spray.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        btn_load.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        btn_hide.setOnClickListener(this);
        btn_menu.setOnClickListener(this);
        iv_share.setOnClickListener(this);
        iv_palette.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.keyboard_model:
                //更改模型
                BottomToast.showToast(this,"加载中，清稍等");
                keyBoardView.changeKeyboard(1,this);
                hideControlLayout();
                break;
            case R.id.keyboard_area:
                //更改区域
                if(!keyBoardView.isLoad()){
                    BottomToast.showToast(this,"还没选择键盘呢！");
                    return;
                }
                AreaSelectDialogFragment areaSelectDialogFragment = new AreaSelectDialogFragment(this);
                areaSelectDialogFragment.show(getFragmentManager(),"");
                hideControlLayout();
                break;
            case R.id.keyboard_spray:
                //涂色
                if(!keyBoardView.isLoad()){
                    BottomToast.showToast(this,"还没选择键盘呢！");
                    return;
                }
                keyBoardView.changeColor(pos,color);
                hideControlLayout();
                break;
            case R.id.keyboard_save:
                //保存
                if(!keyBoardView.isLoad()){
                    BottomToast.showToast(this,"还没选择键盘呢！");
                    return;
                }
                SaveKeyBoardDialogFragment saveKeyBoardDialogFragment = new SaveKeyBoardDialogFragment(this);
                saveKeyBoardDialogFragment.show(getFragmentManager(),"saveKeyBoard");
                hideControlLayout();
                break;
            case R.id.keyboard_load:
                if(!keyBoardView.isLoad()){
                    BottomToast.showToast(this,"还没选择键盘呢！");
                    return;
                }
                LoadKeyBoardDialogFragment loadKeyBoardDialogFragment = new LoadKeyBoardDialogFragment(this,false);
                loadKeyBoardDialogFragment.show(getFragmentManager(),"keyboardList");
                hideControlLayout();
                break;
            case R.id.keyboard_back:
                //返回
                finish();
                break;
            case R.id.keyboard_share:
                //分享
                BottomToast.showToast(this,"这个功能还木有开发");
                hideControlLayout();
                break;
            case R.id.keyboard_palette:
                //调色盘
                ColorPickerDialogFragment colorPickerDialogFragment = new ColorPickerDialogFragment(this);
                colorPickerDialogFragment.show(getFragmentManager(),"colorPicker");
                hideControlLayout();
                break;
            case R.id.keyboard_menu:
                showControlLayout();
                break;
            case R.id.keyboard_hide_control:
                hideControlLayout();
        }
    }

    private void showControlLayout() {
        layout_control.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.control_in);
        layout_control.startAnimation(animation);
    }

    private void hideControlLayout(){
        if(layout_control.getVisibility() == View.VISIBLE) {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.control_out);
            layout_control.startAnimation(animation);
            layout_control.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSelectedKeyBoard(String name) {
        tv_title.setText(name);
    }

    @Override
    public void onSelectedArea(String name) {

    }

    @Override
    public void getColorEvent(int newColor) {
        tv_color.setBackgroundColor(newColor);
        color = newColor;
    }

    @Override
    public void onSelectedArea(int pos, String name) {
        this.pos = pos;
        tv_area.setText(name);
    }

    @Override
    public void saveFinish(String str) {
        keyBoardView.keyboardModel.saveKeyBoard(str,new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e == null){
                    BottomToast.showToast(KeyBoard2Activity.this,"已经保存好了！");
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
}
