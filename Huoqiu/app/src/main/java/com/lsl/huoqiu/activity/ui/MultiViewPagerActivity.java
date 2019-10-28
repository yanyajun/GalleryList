package com.lsl.huoqiu.activity.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.exoplayer2.ui.PlayerView;
import com.lsl.huoqiu.R;
import com.lsl.huoqiu.utils.DeviceUtils;
import com.lsl.huoqiu.widget.PullToRefreshListView;
import com.lsl.huoqiu.widget.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Forrest on 16/5/17.
 */
public class MultiViewPagerActivity extends AppCompatActivity implements  PullToRefreshListView.OnRefreshListener {
    private static int     TOTAL_COUNT = 10;
//    private PullToRefreshListView listView;
//    private RelativeLayout viewPagerContainer;
    private ViewPager viewPager;
    List<String> data = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private List<Fragment> mFragmentList;
    private SimpleExoPlayerWrapper playerWrapper;
    private TestFragment mCurFragment;
    private int mCurPos = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_viewpager);
//        viewPagerContainer= (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.layout_viewpager, null);
        viewPager= (ViewPager) findViewById(R.id.viewpager);
//        listView= (PullToRefreshListView) findViewById(R.id.ptfListView);

        playerWrapper = new SimpleExoPlayerWrapper(this);

        playerWrapper.setPlayerEventListener(new SimpleExoPlayerWrapper.PlayerEventListener() {
            @Override
            public void onRenderedFirstFrame() {
//                if (mCurFragment != null) {
//                    mCurFragment.showPlayView();
//                }
            }

            @Override
            public void onPlayCountChanged(int playCount) {

            }

            @Override
            public void onStateBuffering() {

            }

            @Override
            public void onStateReady() {
                if (mCurFragment != null) {
                    mCurFragment.showPlayView();
                }
            }
        });

//        viewPager=new ViewPager(MultiViewPagerActivity.this);
        //clipChild用来定义他的子控件是否要在他应有的边界内进行绘制。 默认情况下，clipChild被设置为true。 也就是不允许进行扩展绘制。
        viewPager.setClipChildren(false);

        mFragmentList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TestFragment fragment = new TestFragment();
            mFragmentList.add(fragment);
        }

        FragmentPagerAdapter fragment_adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                mCurPos = position;

                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return "";
            }
        };

//        viewPager= (ViewPager) findViewById(R.id.viewpager);
//        AbsListView.LayoutParams params1=new AbsListView.LayoutParams(
//                DeviceUtils.getWindowWidth(this)*10/10,
//                DeviceUtils.getWindowHeight(this)*8/10);
//        viewPagerContainer.setLayoutParams(params1);
//        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(
//                DeviceUtils.getWindowWidth(this)*5/10,
//                DeviceUtils.getWindowHeight(this)*6/10);
//        viewPager.setLayoutParams(params);
//        viewPager.setAdapter(new MyPagerAdapter());

        viewPager.setAdapter(fragment_adapter);
        viewPager.setPageTransformer(true,new ZoomOutPageTransformer());
        // to cache all page, or we will see the right item delayed
        viewPager.setOffscreenPageLimit(TOTAL_COUNT);
        viewPager.setPageMargin(130);
        MyOnPageChangeListener myOnPageChangeListener = new MyOnPageChangeListener();
        viewPager.setOnPageChangeListener(myOnPageChangeListener);
        viewPager.setCurrentItem(0);
        mCurFragment = (TestFragment) mFragmentList.get(0);
        mCurFragment.setFragmentCreateListener(new TestFragment.FragmentCreateListener() {
            @Override
            public void created() {
                PlayerView playerView = mCurFragment.getPlayView();
                if (playerView != null) {
                    playerView.setPlayer(playerWrapper.getPlayer());
                    playerWrapper.resetPlayer("file:///android_asset/cover.mp4");
                    playerWrapper.start();
                }
            }
        });

//        viewPager.setCurrentItem(1);
//        listView.addHeaderView(viewPagerContainer);
//        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1, getData());
//        listView.setAdapter(adapter);
//        listView.setOnRefreshListener(this);
//        viewPagerContainer.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                // dispatch the events to the ViewPager, to solve the problem that we can swipe only the middle view.
//                return viewPager.dispatchTouchEvent(event);
//            }
//        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        playerWrapper.destroyPlayer();
    }

    @Override
    public void onRefresh() {
        new CountDownTimer(2000,1){

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
//                listView.onRefreshComplete();

        }
        }.start();

        Toast.makeText(this,"shuaxian",Toast.LENGTH_SHORT).show();

    }

    /**
     * this is a example fragment, just a imageview, u can replace it with your needs
     *
     * @author Trinea 2013-04-03
     */
    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return TOTAL_COUNT;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(MultiViewPagerActivity.this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(R.mipmap.test);
//            ((ViewPager)container).addView(imageView, position);//如果有position会引起数组越界
            ((ViewPager)container).addView(imageView);
            return imageView;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager)container).removeView((ImageView)object);
        }
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            Log.e("===>", "onPageSelected111111111111: " + position);
            mCurPos = position;

            for (int i = 0; i < mFragmentList.size(); i++) {
                TestFragment testFragment = (TestFragment) mFragmentList.get(i);
                if (testFragment == null) {
                    continue;
                }
                if (i == position) {
                    mCurFragment = testFragment;
                    PlayerView playerView = testFragment.getPlayView();
                    if (playerView != null) {
                        playerView.setPlayer(playerWrapper.getPlayer());
                        playerWrapper.resetPlayer("file:///android_asset/cover.mp4");
                        playerWrapper.start();
                    }
                } else {
                    testFragment.stopVideo(true);
                }

            }


        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // to refresh frameLayout
//            if (viewPagerContainer != null) {
//                viewPagerContainer.invalidate();
//            }

            Log.e("===>", "onPageScrolled: " + position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

            if (state == 0) { // 什么都没做
//                if (mCurFragment != null) {
//                    PlayerView playerView = mCurFragment.getPlayView();
//                    if (playerView != null) {
//                        playerView.setPlayer(playerWrapper.getPlayer());
//                        playerWrapper.resetPlayer("file:///android_asset/cover.mp4");
//                        playerWrapper.start();
//                    }
//                }
//                if (mCurFragment != null) {
//                    mCurFragment.showPlayView();
//                }

            } else if(state == 1) { // 开始
                for (int i = 0; i < mFragmentList.size(); i++) {
                    TestFragment testFragment = (TestFragment) mFragmentList.get(i);
                    if (testFragment == null) {
                        continue;
                    }
                    testFragment.stopVideo(false);
                }
            } else if(state == 2) { // 结束

            }

            Log.e("===>", "onPageScrollStateChanged: " + state);
        }
    }
    private List<String> getData(){

        data = new ArrayList<String>();
//        data.add("HuoQiuProgress");
//        data.add("HuoQiuProgressActivity");
//        data.add("HuoQiuMainActivity");
//        data.add("FengActivity");
//        data.add("VerticalViewPagerActivity");
//        data.add("GussBitmap");
//        data.add("GussLayout");
//        data.add("PullZoomActivity");
//        data.add("MultiViewPagerActivity");

        return data;
    }
}
