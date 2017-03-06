package cn.lcu.lfz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import cn.lcu.lfz.Discovery.R;
/**
 * Created by Administrator on 2017/2/8.
 */
public class WelcomeActivity extends Activity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);
        imageView = (ImageView) findViewById(R .id.welcome_img);
        AlphaAnimation anima = new AlphaAnimation(0.3f,1.0f);
        anima.setDuration(3000);
        imageView.startAnimation(anima);

        anima.setAnimationListener(new AnimationImpl());
    }

    private class AnimationImpl implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {
            imageView.setBackgroundResource(R.drawable.welcome);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            skip();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    private void skip() {
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }
}
