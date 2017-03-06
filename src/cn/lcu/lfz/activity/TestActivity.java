package cn.lcu.lfz.activity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;
import cn.lcu.lfz.Discovery.R;
import fragment.DiyFragment;
import fragment.MeFragment;
import fragment.NewsFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/7.
 */
public class TestActivity extends Activity {

    private ViewPager viewPager;
    private ArrayList<Fragment> fragmentList;
    private TextView tv1,tv2,tv3;
    private int currentIndex;

    private FragmentPagerAdapter fragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        initFragment();
        fragmentPagerAdapter = new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragmentList.get(i);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        };
        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.setCurrentItem(0);
    }

    private void initFragment() {
        fragmentList = new ArrayList<>();
        Fragment fragment1 = new DiyFragment();
        Fragment fragment2 = new NewsFragment();
        Fragment fragment3 = new MeFragment();
        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        fragmentList.add(fragment3);
    }
}
