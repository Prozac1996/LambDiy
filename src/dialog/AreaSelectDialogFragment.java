package dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.GridLayout;
import android.widget.TextView;
import cn.lcu.lfz.Discovery.R;

/**
 * Created by Administrator on 2017/2/26.
 */
public class AreaSelectDialogFragment extends DialogFragment {

    private OnAreaSelectedListener listener;

    public AreaSelectDialogFragment(OnAreaSelectedListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        GridLayout areaLayout = new GridLayout(getActivity());
        areaLayout.setOrientation(GridLayout.HORIZONTAL);
        areaLayout.setColumnCount(5);
//        LinearLayout linearLayout = new LinearLayout(getActivity());
//        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        String[] areas = getResources().getStringArray(R.array.kb_area);
        for (int i = 0; i < areas.length; i++) {
            String areaName = areas[i];
            TextView textView = new TextView(getActivity());
            textView.setText(areaName);
            textView.setPadding(20, 20, 20, 20);
            final int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onSelectedArea(finalI, areaName);
                    dismiss();
                }
            });
            areaLayout.addView(textView);
        }
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialogAnim;
        dialog.setContentView(areaLayout);
        return dialog;
    }

    public interface OnAreaSelectedListener {
        public void onSelectedArea(int pos, String name);
    }
}
