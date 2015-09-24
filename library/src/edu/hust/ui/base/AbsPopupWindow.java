package edu.hust.ui.base;

import net.simonvt.numberpicker.R;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;


public abstract class AbsPopupWindow {
    protected View mAnchor;
    private ArrowPopWindow mWindow;
    private View mRoot;
    private Drawable mBackground = null;
    private TimerHandler mTimerHandler = null;
    private int mDelayedTime = 2000;
    private boolean mAutoDismiss = false;
    private int mAnimationStyle = R.style.Animations_PopDownMenu;
    private boolean isEnableTouchClose;

    /**
     * mark by xulc
     * @param anchor
     * @param autoDismiss
     * @param needArrowCenter
     * @param arrowDrawable
     */
    public AbsPopupWindow(View anchor, boolean autoDismiss, boolean needArrowCenter, Drawable arrowDrawable,
            boolean enableTouchClose) {
        mTimerHandler = new TimerHandler();
        this.mAnchor = anchor;
        this.mWindow = new ArrowPopWindow(mAnchor, needArrowCenter, arrowDrawable);
        this.mAutoDismiss = autoDismiss;
        isEnableTouchClose = enableTouchClose;

        // when a touch even happens outside of the window
        // make the window go away
//        this.mWindow.setTouchInterceptor(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
//                    dismiss();
//                    return true;
//                }
//                return false;
//            }
//        });
        onCreate();
    }

    public boolean isShowing() {
        
        return mWindow.isShowing();
    }

    protected abstract void onCreate();

    protected void onShow() {
        if (mAutoDismiss) {
            mTimerHandler.removeMessages(TimerHandler.MESSAGE_DISMISS);
            mTimerHandler.sendEmptyMessageDelayed(TimerHandler.MESSAGE_DISMISS, mDelayedTime);
        }
    }

    public void setDismissDealyedTime(int delayedTime) {
        mDelayedTime = delayedTime;
    }
    
    public int mAbsPopupWidth = WindowManager.LayoutParams.WRAP_CONTENT;
    public int mAbsPopupHeight =WindowManager.LayoutParams.WRAP_CONTENT;
    
    public void setAbsPopParams(int width,int height){
    	mAbsPopupWidth = width;
    	mAbsPopupHeight = height;
    }
    
    private void preShow() {
        if (this.mRoot == null) {
            throw new IllegalStateException("setContentView was not called with a view to display.");
        }
        onShow();

        if (this.mBackground == null) {
            this.mWindow.setBackgroundDrawable(new BitmapDrawable());
        } else {
            this.mWindow.setBackgroundDrawable(this.mBackground);
        }
        this.mWindow.setWidth(mAbsPopupWidth);
        this.mWindow.setHeight(mAbsPopupHeight);
        if (isEnableTouchClose) {
            this.mWindow.setTouchable(true);
            this.mWindow.setFocusable(true);
            this.mWindow.setOutsideTouchable(true);
        }

        this.mWindow.setContentView(this.mRoot);
        
    }

    public void setBackgroundDrawable(Drawable background) {
        this.mBackground = background;
    }

    public void setContentView(View root) {
        this.mRoot = root;
        // this.mWindow.setContentView(root);
    }

    public void setContentView(int layoutResID) {
        LayoutInflater inflator = LayoutInflater.from(this.mAnchor.getContext()); // (LayoutInflater) Utils.getSystemService(this.mAnchor.getContext(), Context.LAYOUT_INFLATER_SERVICE);
        this.setContentView(inflator.inflate(layoutResID, null));
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener listener) {
        this.mWindow.setOnDismissListener(listener);
    }

    public void show() {
        this.show(0, 0);

//        if (isEnableTouchClose) {
//            this.mWindow.setFocusable(true);
//        }
    }

    public void show(int xOffset, int yOffset) {
        this.preShow();

        this.mWindow.setAnimationStyle(mAnimationStyle);

        this.mWindow.showAsDropDown(this.mAnchor, xOffset, yOffset);
    }

    public void setAnimationStyle(int animationStyle) {
        mAnimationStyle = animationStyle;
    }

    public void dismiss() {
        if (mAutoDismiss) {
            mTimerHandler.removeMessages(TimerHandler.MESSAGE_DISMISS);
        }
        try {
            AbsPopupWindow.this.mWindow.dismiss();
        } catch (Exception ex) {
            /* 
                 java.lang.NullPointerException
                    at android.view.WindowManagerImpl.removeViewLocked(WindowManagerImpl.java:239)
                    at android.view.WindowManagerImpl.removeView(WindowManagerImpl.java:201)
                    at android.widget.PopupWindow.dismiss(PopupWindow.java:1052)
             */
        }

        if (isEnableTouchClose) {
            mWindow.setFocusable(false);
        }
    }

    public void handleDismiss(long time) {
        if (mAutoDismiss) {
            mTimerHandler.removeMessages(TimerHandler.MESSAGE_DISMISS);
        }
        mTimerHandler.sendEmptyMessageDelayed(TimerHandler.MESSAGE_DISMISS, time);
    }

    class TimerHandler extends Handler {
        static final int MESSAGE_DISMISS = 0;

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MESSAGE_DISMISS:
                try {
                    dismiss();
                } catch (Exception e) {
                    // ignore
                }
                break;
            }
            super.handleMessage(msg);
        }
    }
}
