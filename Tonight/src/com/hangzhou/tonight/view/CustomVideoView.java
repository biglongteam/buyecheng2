package com.hangzhou.tonight.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

public class CustomVideoView extends VideoView {

	public CustomVideoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public CustomVideoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public CustomVideoView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            // TODO Auto-generated method stub
            // Log.i("@@@@", "onMeasure");

			
            int width = getDefaultSize(500, widthMeasureSpec);
            int height = getDefaultSize(500, heightMeasureSpec);
            setMeasuredDimension(width, height);
    }
}
