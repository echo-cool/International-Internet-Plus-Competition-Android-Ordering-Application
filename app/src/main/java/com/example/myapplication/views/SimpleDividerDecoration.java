package com.example.myapplication.views;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;


public class SimpleDividerDecoration extends RecyclerView.ItemDecoration {

	private int dividerHeight = 1;
	private Paint dividerPaint;

	public SimpleDividerDecoration(Context context) {
		dividerPaint = new Paint();
		dividerPaint.setColor(ContextCompat.getColor(context, R.color.dividerColor));
	}


	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		super.getItemOffsets(outRect, view, parent, state);
		outRect.bottom = dividerHeight;
	}

	@Override
	public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
		int childCount = parent.getChildCount();
		int left = parent.getPaddingLeft();
		int right = parent.getWidth() - parent.getPaddingRight();

		for (int i = 0; i < childCount - 1; i++) {
			View view = parent.getChildAt(i);
			float top = view.getBottom();
			float bottom = view.getBottom() + dividerHeight;
			c.drawRect(left, top, right, bottom, dividerPaint);
		}
	}
}