package net.simonvt.numberpicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.simonvt.numberpicker.R;

public class CommonLoadingAnim extends LinearLayout {
    private static final int MSG_WHAT_LOADING_SYMBOL = 1;
    private static final int LOADING_SYMBOL_DELAY = 300;

    private Context mContext;
    private ImageView mLoadingIcon;
    private TextView mLoadingText;
    private TextView mLoadingSymbol;
    private String mLoadingTextValue = null;
    private String mSize = null;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MSG_WHAT_LOADING_SYMBOL:
                int count = msg.arg1 % 3;
                StringBuilder loadingText = new StringBuilder();
                loadingText.append(".");
                for (int i = 0; i < count; i++) {
                    loadingText.append(".");
                }
                mLoadingSymbol.setText(loadingText);
                mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_WHAT_LOADING_SYMBOL, msg.arg1 + 1, 0),
                        LOADING_SYMBOL_DELAY);
                break;
            default:
                break;
            }
            super.handleMessage(msg);
        }
    };

    public CommonLoadingAnim(Context context, AttributeSet attrs) {
        super(context, attrs);

        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);

        mContext = context.getApplicationContext();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CommonLoadingAnim);
        this.mLoadingTextValue = a.getString(R.styleable.CommonLoadingAnim_text1);
        mSize = a.getString(R.styleable.CommonLoadingAnim_size);

        a.recycle();
        initView();
    }

    private void initView() {
        inflate(mContext, R.layout.commnon_loading_anim, this);
        mLoadingIcon = (ImageView) findViewById(R.id.loading_icon);
        mLoadingText = (TextView) findViewById(R.id.loading_text);
        mLoadingSymbol = (TextView) findViewById(R.id.loading_symbol);
        if (mLoadingTextValue == null) {
            mLoadingText.setText(R.string.common_loading_text);
        } else {
            mLoadingText.setText(mLoadingTextValue);
        }
        if ("small".equals(mSize)) {

            ViewGroup.LayoutParams lpIcon = mLoadingIcon.getLayoutParams();
            lpIcon.width = (int)mContext.getResources().getDimension(R.dimen.common_loading_anim_size_samll);
            lpIcon.height = lpIcon.width;
            mLoadingIcon.setLayoutParams(lpIcon);
            
            ImageView mLoadingBg = (ImageView) findViewById(R.id.loading_bg);
            ViewGroup.LayoutParams lpBg = mLoadingBg.getLayoutParams();
            lpBg.width = lpIcon.width;
            lpBg.height = lpIcon.width;
            mLoadingBg.setLayoutParams(lpBg);
        }
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == View.VISIBLE) {
            mLoadingIcon.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.common_data_loading_rotate));
            mHandler.sendMessage(mHandler.obtainMessage(MSG_WHAT_LOADING_SYMBOL, 0, 0));
        } else {
            mLoadingIcon.clearAnimation();
            mHandler.removeMessages(MSG_WHAT_LOADING_SYMBOL);
        }
    }
    
    public TextView getLoadingTextView() {
        return mLoadingText;
    }
    
    public TextView getLoadingSymbolTextView() {
        return mLoadingSymbol;
    }

    public void setText(int id) {
        mLoadingText.setText(id);
    }

    public void setText(String str) {
        mLoadingText.setText(str);
    }
}