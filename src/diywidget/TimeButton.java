package diywidget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/1/20.
 */
public class TimeButton extends Button implements View.OnClickListener {

    private long time = 60 * 1000;
    private Timer t;
    private TimerTask tt;
    private OnClickListener mOnClickLlistener;

    private String oldText = "发送验证码";
    private String afterText = "s后重发";
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setText(time/1000+afterText);
            time-=1000;
            if(time >= 0){
                setText(time/1000+afterText);
                setEnabled(false);
            }else{
                setText(oldText);
                setEnabled(true);
                clearTimer();
            }
        }
    };

    public TimeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }

    public TimeButton(Context context) {
        super(context);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        if(l instanceof TimeButton)
            super.setOnClickListener(l);
        else
            mOnClickLlistener = l;
    }

    private void initTimer(){
        time = 60 * 1000;
        t = new Timer();
        tt = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0x01);
            }
        };
        t.schedule(tt,0,1000);
    }

    private void clearTimer(){
        if(tt != null){
            tt.cancel();
            tt = null;
        }
        if(t != null)
            t = null;
        time = 60 * 1000;
    }

    @Override
    public void onClick(View v) {
        if(mOnClickLlistener != null)
            mOnClickLlistener.onClick(v);
        initTimer();
    }
}
