package cn.lcu.lfz.activity;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.lcu.lfz.Discovery.R;
import diywidget.LambTitleBar;
import fragment.DiyFragment;
import fragment.MeFragment;
import fragment.NewsFragment;
import fragment.SuggestFragment;
import model.SimpleFragmentActivity;
import tools.LeancloudTools;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/22.
 * 主Activity，完成其他项目的衔接与引导。
 */
public class MainActivity extends SimpleFragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private LinearLayout nav_diy, nav_news, nav_me;
    private TextView nav_diy_text, nav_news_text, nav_me_text;
    private ViewPager main_viewPager;
    private FragmentStatePagerAdapter adapter;
    private ArrayList<Fragment> fragmentList;
    private LambTitleBar lambTitleBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        LeancloudTools.init(this);
        fm = getFragmentManager();
        nav_diy = (LinearLayout) findViewById(R.id.nav_diy);
        nav_news = (LinearLayout) findViewById(R.id.nav_news);
        nav_me = (LinearLayout) findViewById(R.id.nav_me);
        nav_diy_text = (TextView) findViewById(R.id.nav_diy_text);
        nav_news_text = (TextView) findViewById(R.id.nav_news_text);
        nav_me_text = (TextView) findViewById(R.id.nav_me_text);
        main_viewPager = (ViewPager) findViewById(R.id.main_viewPager);
        lambTitleBar = (LambTitleBar) findViewById(R.id.main_title_bar);
        nav_diy.setOnClickListener(this);
        nav_news.setOnClickListener(this);
        nav_me.setOnClickListener(this);
        lambTitleBar.setButtonClickListener(new LambTitleBar.OnButtonClickListener() {
            @Override
            public void onLeftClick() {
                onBackPressed();
            }

            @Override
            public void onRightClick() {
                addFragment(R.id.main_container, new SuggestFragment());
            }
        });
        fragmentList = new ArrayList<>();
        fragmentList.add(new NewsFragment());
        fragmentList.add(new DiyFragment());
        fragmentList.add(new MeFragment());
        adapter = new FragmentStatePagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragmentList.get(i);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        };
        main_viewPager.setAdapter(adapter);
        main_viewPager.setOnPageChangeListener(this);
        main_viewPager.setCurrentItem(2);
        main_viewPager.setOffscreenPageLimit(2);
        setSelectColor(2);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_diy:
//                replaceFragment(R.id.tab_container,new DiyFragment());
                main_viewPager.setCurrentItem(1);
                setSelectColor(1);
                break;
            case R.id.nav_news:
//                replaceFragment(R.id.tab_container,new NewsFragment());
                main_viewPager.setCurrentItem(0);
                setSelectColor(0);
                break;
            case R.id.nav_me:
                main_viewPager.setCurrentItem(2);
//                replaceFragment(R.id.tab_container,new MeFragment());
                setSelectColor(2);
                break;
        }
    }

    private void setSelectColor(int which) {

        nav_diy_text.setTextColor(Color.WHITE);
        nav_news_text.setTextColor(Color.WHITE);
        nav_me_text.setTextColor(Color.WHITE);
        switch (which) {
            case 1:
                nav_diy_text.setTextColor(Color.GREEN);
                break;
            case 0:
                nav_news_text.setTextColor(Color.GREEN);
                break;
            case 2:
                nav_me_text.setTextColor(Color.GREEN);
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.submit_suggest:
                addFragment(R.id.main_container, new SuggestFragment());
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        setSelectColor(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
