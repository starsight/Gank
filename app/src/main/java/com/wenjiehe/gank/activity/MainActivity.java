package com.wenjiehe.gank.activity;

import android.animation.Animator;
import android.provider.ContactsContract;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wenjiehe.gank.R;
import com.wenjiehe.gank.fragment.AboutFragment;
import com.wenjiehe.gank.fragment.CategoryFragment;
import com.wenjiehe.gank.fragment.GankFragment;
import com.wenjiehe.gank.presenter.AboutPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    @BindView(R.id.scrim)
    View scrim;
    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.text)
    TextView text;

    private GankFragment gankFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(mPagerAdapter);
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        //tabLayout.getTabAt(0).setIcon(R.drawable.icon_main);
        tabLayout.getTabAt(1).setIcon(R.drawable.icon_category);
        tabLayout.getTabAt(2).setIcon(R.drawable.icon_about);
        TabLayout.Tab tab = tabLayout.getTabAt(0);
        if (tab != null) {
            tabLayout.getTabAt(0).setCustomView(getTabView(0));
            if (tab.getCustomView() != null) {
                View tabView = (View) tab.getCustomView().getParent();
                tabView.setTag(0);
                tabView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = (int) v.getTag();
                        if (position == 0 && tabLayout.getTabAt(position).isSelected()) {
                            gankFragment.clickIcon();
                            //Toast.makeText(MainActivity.this, "点击了第一个tab", Toast.LENGTH_SHORT).show();
                        }
//                        else if (position == 1 && tabLayout.getTabAt(position).isSelected() == true) {
//                            Toast.makeText(MainActivity.this, "点击了第二个tab", Toast.LENGTH_SHORT).show();
//                        } else {
//                            TabLayout.Tab tab = tabLayout.getTabAt(position);
//                            if (tab != null) {
//                                tab.select();
//                            }
//                        }
                    }
                });

            }

        }

    }

    public View getTabView(int position){
        ImageView view = new ImageView(MainActivity.this);
        if (position == 0) {
            view.setImageResource(R.drawable.icon_main);
        }
        return view;
    }

    private PagerAdapter mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

        @Override
        public Fragment getItem(int position) {
            if (position == 1) {
                return CategoryFragment.newInstance();
            } else if (position == 2) {
                return AboutFragment.newInstance();
            }
            gankFragment = GankFragment.newInstance(null);
            return gankFragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    };

    @Override
    public void setLoading(boolean isLoading) {
        Log.d(TAG, "setLoading: " + isLoading);

        scrim.removeCallbacks(hideInfo);
        scrim.animate().cancel();
        scrim.animate().setListener(null);

        if (isLoading) {
            scrim.setVisibility(View.VISIBLE);
            loading.setVisibility(View.VISIBLE);
            text.setText("加载中...");
            scrim.setAlpha(0f);
            scrim.animate().alpha(1f).start();
        } else {
            scrim.animate().alpha(0f).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    scrim.setVisibility(View.INVISIBLE);
                }

                @Override public void onAnimationStart(Animator animation) { }
                @Override public void onAnimationCancel(Animator animation) { }
                @Override public void onAnimationRepeat(Animator animation) { }
            }).start();
        }
    }

    @Override
    public void showInfo(String info) {
        Log.d(TAG, "showInfo: " + info);

        scrim.animate().cancel();
        scrim.removeCallbacks(hideInfo);
        scrim.animate().setListener(null);

        scrim.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);
        text.setText(info);
        scrim.setAlpha(1f);
        scrim.postDelayed(hideInfo, 800);
    }

    private Runnable hideInfo = new Runnable() {
        @Override
        public void run() {
            Log.d(TAG, "run: hideInfo");

            scrim.animate().cancel();
            scrim.animate().alpha(0f).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    scrim.setVisibility(View.INVISIBLE);
                }

                @Override public void onAnimationStart(Animator animation) { }
                @Override public void onAnimationCancel(Animator animation) { }
                @Override public void onAnimationRepeat(Animator animation) { }
            }).start();
        }
    };
}
