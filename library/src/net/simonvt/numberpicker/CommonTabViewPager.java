package net.simonvt.numberpicker;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import net.simonvt.numberpicker.R;


/**
 * 
 */
public class CommonTabViewPager extends LinearLayout implements OnPageChangeListener {

    private LinearLayout mSliderBar;
    private View mSliderLine;
    private View mSliderBottomLine;
    private ViewPager mPager; 

    private List<View> mTitleViews;
    private OnPageChangeListener mOnPageChangedListener;

    public OnPageChangeListener getOnPageChangedListener() {
        return mOnPageChangedListener;
    }

    public void setOnPageChangedListener(OnPageChangeListener onPageChangedListener) {
        mOnPageChangedListener = onPageChangedListener;
    }

    public CommonTabViewPager(Context context) {
        super(context);
        init();
    }

    public int getCount() {
        if (mPager != null && mPager.getAdapter() != null) {
            return mPager.getAdapter().getCount();
        }
        return 0;
    }

    public CommonTabViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setViewPager(ViewPager pager) {
        mPager = pager;
        if (mPager != null) {
            mPager.setOnPageChangeListener(this);
            refreshSlider(0, 0);
        }
    }

    private void init() {
        inflate(getContext(), R.layout.widget_tab_viewpagger, this);
        mSliderBar = (LinearLayout) findViewById(R.id.common_slider_bar);
        mSliderLine = findViewById(R.id.common_slider_line);
        mSliderBottomLine = findViewById(R.id.common_slider_bottom_line);
        mTitleViews = new ArrayList<View>();
        ViewPager vp = (ViewPager) findViewById(R.id.common_viewpager);
        setViewPager(vp);
    }

    public void setSelectedPage(int idx) {
        if (mTitleViews != null && idx < mTitleViews.size() && idx >= 0) {
            mPager.setCurrentItem(idx);
        }
    }

    /** 细的绿线 */
    public View getSliderBottomLine() {
        return mSliderBottomLine;
    }

    /** 粗的绿线 */
    public View getSliderLine() {
        return mSliderLine;
    }

    public void hideSliderBar() {
        mSliderBottomLine.setVisibility(View.GONE);
        mSliderLine.setVisibility(View.GONE);
        mSliderBar.setVisibility(View.GONE);
    }

    public void showSliderBar() {
        mSliderBottomLine.setVisibility(View.VISIBLE);
        mSliderLine.setVisibility(View.VISIBLE);
        mSliderBar.setVisibility(View.VISIBLE);
    }

    public void setPageViews(List<View> views) {
        if (views == null) {
            return;
        }
        mPager.setAdapter(new DefaultViewPagerAdapter(views));
    }

    public void setPageViewsByResId(List<Integer> layoutResIds) {
        if (layoutResIds == null) {
            return;
        }
        List<View> views = new ArrayList<View>();
        for (int i = 0; i < layoutResIds.size(); i++) {
            views.add(inflate(getContext(), layoutResIds.get(i), null));
        }
        setPageViews(views);
    }

    public void setTitleViewsByResId(List<Integer> layoutResIds) {
        if (layoutResIds == null) {
            return;
        }
        List<View> views = new ArrayList<View>();
        for (int i = 0; i < layoutResIds.size(); i++) {
            views.add(inflate(getContext(), layoutResIds.get(i), null));
        }
        setTitleViews(views);
    }

    private void initTitleEvent() {
        if (mTitleViews == null) {
            return;
        }
        for (int i = 0; i < mTitleViews.size(); i++) {
            final int idx = i;
            mTitleViews.get(i).setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (mPager != null) {
                        mPager.setCurrentItem(idx);
                    }
                }
            });
        }
    }

    public void setTitleViews(List<View> views) {
        if (views == null) {
            return;
        }
        mTitleViews.clear();
        mSliderBar.removeAllViews();
        LayoutParams lp = new LayoutParams(0, LayoutParams.FILL_PARENT);
        //        lp.gravity = Gravity.CENTER;
        for (int i = 0; i < views.size(); i++) {
            View v = views.get(i);
            lp.weight = 1;
            v.setLayoutParams(lp);
            mSliderBar.addView(v);
            mTitleViews.add(v);
            v.setSelected(i == 0);
        }
        initTitleEvent();
        refreshSlider(0, 0);
    }

    public void setTitles(List<String> titles) {
        if (titles == null) {
            return;
        }
        mTitleViews.clear();
        mSliderBar.removeAllViews();
        LayoutParams lp = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < titles.size(); i++) {
            TextView tv = new TextView(getContext());
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(getResources().getColorStateList(R.drawable.widget_selector_tab_text_color));
            tv.setText(titles.get(i));
            lp.weight = 1;
            tv.setLayoutParams(lp);
            mSliderBar.addView(tv);
            mTitleViews.add(tv);
            tv.setSelected(i == 0);
        }
        initTitleEvent();
        refreshSlider(0, 0);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            int pos = 0;
            if (mPager != null) {
                pos = mPager.getCurrentItem();
            }
            refreshSlider(pos, 0);
        }
    }

    private void refreshSlider(int pos, float offset) {
        if (getCount() > 0 && mTitleViews != null && mTitleViews.size() == getCount()) {
            LayoutParams lpSlider = (LayoutParams) mSliderLine.getLayoutParams();
            lpSlider.width = mSliderBar.getWidth() / getCount();
            lpSlider.leftMargin = (int) (pos * lpSlider.width + lpSlider.width * offset);
            mSliderLine.setLayoutParams(lpSlider);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mOnPageChangedListener != null) {
            mOnPageChangedListener.onPageScrollStateChanged(state);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        refreshSlider(position, positionOffset);
        if (mOnPageChangedListener != null) {
            mOnPageChangedListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < mTitleViews.size(); i++) {
            mTitleViews.get(i).setSelected(i == position);
        }
        if (mOnPageChangedListener != null) {
            mOnPageChangedListener.onPageSelected(position);
        }
    }

    class DefaultViewPagerAdapter extends PagerAdapter {
        private List<View> mListViews;

        public DefaultViewPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mListViews.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mListViews.get(position), 0);
            return mListViews.get(position);
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }
}
