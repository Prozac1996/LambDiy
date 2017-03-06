package fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.lcu.lfz.Discovery.R;

/**
 * Created by Administrator on 2017/1/26.
 *                                   被废弃的类
 */
public class KeyBoardFragment extends Fragment {

    public int mScreenHeight = 0;
    public int mScreenWidth = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        mScreenHeight = dm.heightPixels;
        mScreenWidth = dm.widthPixels;
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View v = new KeyBoardView(getActivity());

        View v = inflater.inflate(R.layout.activity_keyboard,container,false);
        return v;
    }


}
