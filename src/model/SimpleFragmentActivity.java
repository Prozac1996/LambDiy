package model;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import cn.lcu.lfz.Discovery.R;
import diywidget.BottomToast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/11/13.
 */
public abstract class SimpleFragmentActivity extends FragmentActivity {
    public FragmentManager fm;
    private int endTime;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    public void addFragment(int containerId, Fragment fragment) {
        fm = getFragmentManager();
        fm.beginTransaction().setCustomAnimations(R.animator.slide_in_up, R.animator.slide_out_up, R.animator.slide_in_up, R.animator.slide_out_up)
                .addToBackStack("").add(containerId, fragment).commit();
    }

    public void replaceFragment(int containerId, Fragment fragment) {
        fm = getFragmentManager();
        fm.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(containerId, fragment).commit();
    }

    @Override
    public void onBackPressed() {

        if (getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
            return;
        }

        if (endTime > 0) {
            finish();
        }
        BottomToast.showToast(this, "再按一次退出程序");
        endTime = 2000;
        Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                endTime--;
                if (endTime < 0) {
                    endTime = 0;
                    cancel();
                }
            }
        };
        t.schedule(tt, 0, 1);
    }
}
