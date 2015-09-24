package edu.hust.ui.base;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class PopLayout extends RelativeLayout {
    private ImageView mImageView = null;
    private ArrowPopWindow mArrowPopWindow = null;
    private boolean mNeedArrowCenter = false;
    private static final int LINE_EAGE = 12;

    public PopLayout(View root, ArrowPopWindow arrowPopWindow, boolean needArrowCenter, Drawable arrowDrawable) {
        super(root.getContext());
        this.removeAllViews();
        this.addView(root);
        mArrowPopWindow = arrowPopWindow;
        mNeedArrowCenter = needArrowCenter;
        mImageView = new ImageView(root.getContext());
        mImageView.setBackgroundDrawable(arrowDrawable);
        this.addView(mImageView);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        countPosition();
    }

    private void countPosition() {
        int marginLeft = 0;
        int[] anchorLocation = new int[2];
        int[] popLocation = new int[2];
        mArrowPopWindow.mAnchor.getLocationOnScreen(anchorLocation);
        this.getLocationOnScreen(popLocation);
        if (mArrowPopWindow.mAnchor.getMeasuredWidth() / 3 < this.getMeasuredWidth()) {
            marginLeft = anchorLocation[0] - popLocation[0] + 12;
        } else {
            marginLeft = (this.getMeasuredWidth() - mImageView.getMeasuredWidth()) / 3;
        }
        if (mNeedArrowCenter) {
            if (mArrowPopWindow.mAnchor.getMeasuredWidth() / 2 < this.getMeasuredWidth() - 2 * LINE_EAGE) {
                marginLeft = anchorLocation[0] - popLocation[0] + mArrowPopWindow.mAnchor.getMeasuredWidth() / 2
                        - mImageView.getMeasuredWidth() / 2;
            } else {
                marginLeft = (this.getMeasuredWidth() - mImageView.getMeasuredWidth()) / 2;
            }
        } else {
            marginLeft = anchorLocation[0] - popLocation[0] + LINE_EAGE;
        }
        mImageView.layout(marginLeft, 0, marginLeft + mImageView.getMeasuredWidth(), mImageView.getMeasuredHeight());
    }
}
