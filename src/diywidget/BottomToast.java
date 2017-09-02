package diywidget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import cn.lcu.lfz.Discovery.R;

/**
 * Created by Administrator on 2017/2/25.
 */
public class BottomToast {

    private static TextView mTextView;

    public static void showToast(Context context, String message) {
        View view = LayoutInflater.from(context).inflate(R.layout.toast_bottom, null);

        mTextView = (TextView) view.findViewById(R.id.toast_bottom_message);
        mTextView.setText(message);

        Toast toastStart = new Toast(context);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        toastStart.setGravity(Gravity.TOP, 0, (int) (height * 0.8));
        toastStart.setDuration(Toast.LENGTH_SHORT);
        toastStart.setView(view);
        toastStart.show();
    }
}
