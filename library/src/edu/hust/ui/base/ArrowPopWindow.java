package edu.hust.ui.base;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.PopupWindow;

public class ArrowPopWindow extends PopupWindow {
    private PopLayout mPopLayout = null;
    View mAnchor = null;
    private boolean mNeedArrowCenter = false;
    private Drawable mArrowDrawable = null;

    public ArrowPopWindow(View anchor, boolean needArrowCenter, Drawable arrowDrawable) {
        super(anchor.getContext());
        mAnchor = anchor;
        mNeedArrowCenter = needArrowCenter;
        mArrowDrawable = arrowDrawable;
    }

    @Override
    public void setContentView(View contentView) {
        super.setContentView(null);
        if (mPopLayout != null) {
            mPopLayout.removeAllViews();
        }
        mPopLayout = new PopLayout(contentView, ArrowPopWindow.this, mNeedArrowCenter, mArrowDrawable);
        super.setContentView(mPopLayout);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        super.showAsDropDown(anchor, xoff, yoff);
    }
}