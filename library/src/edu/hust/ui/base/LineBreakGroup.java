package edu.hust.ui.base;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;

public class LineBreakGroup extends ViewGroup{
    public int VIEW_MARGIN = 2;//每个view之间的间距
    
    
    public LineBreakGroup(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }
    
    public LineBreakGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LineBreakGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        for (int index = 0; index < getChildCount(); index++) {
            final View child = getChildAt(index);
            // measure
            child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        }
        
        int l = 0;
        int t = 0;
        int r = MeasureSpec.getSize(widthMeasureSpec);
        int b = MeasureSpec.getSize(heightMeasureSpec);;
        
        final int count = getChildCount();
        int row = 0;// which row lay you view relative to parent
        int lengthX = l; // right position of child relative to parent
        int lengthY = t; // bottom position of child relative to parent
        for (int i = 0; i < count; i++) {
            final View child = this.getChildAt(i);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            lengthX += width + VIEW_MARGIN;
            lengthY = row * (height + VIEW_MARGIN) + VIEW_MARGIN + height + t;
            //if it can't drawing on a same line , skip to next line
            if (lengthX > r) {
                lengthX = width + VIEW_MARGIN + l;
                row++;
                lengthY = row * (height + VIEW_MARGIN) + VIEW_MARGIN + height + t;

            }
            child.setVisibility(View.VISIBLE);
            child.layout(lengthX - width, lengthY - height, lengthX, lengthY);
        }
        
        
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(lengthY, MeasureSpec.AT_MOST));
        
    }
    //l:left t:top r:right b:bottom
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
    
    
}
