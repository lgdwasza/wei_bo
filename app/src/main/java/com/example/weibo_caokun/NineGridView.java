package com.example.weibo_caokun;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

public class NineGridView extends ViewGroup {



        private int maxCols = 3;
        private int horizontalSpacing; // 水平
        private int verticalSpacing;   // 垂直

        public NineGridView(Context context, AttributeSet attrs) {
            super(context, attrs);
            horizontalSpacing = -40;
            verticalSpacing = 50;
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            final int numChildren = getChildCount();
            int cols = Math.min(numChildren, maxCols); // 计算列数，不超过最大列数
            int rows = (numChildren + maxCols - 1) / maxCols; // 计算行数

            int widthSize = MeasureSpec.getSize(widthMeasureSpec);
            int cellWidth = (widthSize - (cols + 1) * horizontalSpacing) / cols;

            int cellHeight = cellWidth;
            int totalHeight = cellHeight * rows + (rows + 1) * verticalSpacing;
            setMeasuredDimension(widthSize, totalHeight);

            int childWidthSpec = MeasureSpec.makeMeasureSpec(cellWidth, MeasureSpec.EXACTLY);
            int childHeightSpec = MeasureSpec.makeMeasureSpec(cellHeight, MeasureSpec.EXACTLY);
            for (int index = 0; index < numChildren; index++) {
                getChildAt(index).measure(childWidthSpec, childHeightSpec);
            }
        }

        @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b) {
            final int numChildren = getChildCount();
            int cols = Math.min(numChildren, maxCols);
            int rows = (numChildren + maxCols - 1) / maxCols;

            int cellWidth = (getWidth() - (cols + 1) * horizontalSpacing) / cols;
            int cellHeight = cellWidth;

            for (int i = 0; i < numChildren; i++) {
                final View child = getChildAt(i);

                int col = i % cols;
                int row = i / cols;

                int childLeft = col * cellWidth + (col + 1) * horizontalSpacing;
                int childTop = row * cellHeight + (row + 1) * verticalSpacing;

                child.layout(childLeft, childTop, childLeft + cellWidth, childTop + cellHeight);
            }
        }


    public void setImageUrls(List<String> imageUrls, OnClickListener listener) {
            removeAllViews();

        for (int i=0 ;i<imageUrls.size();i++) {
            final int index = i;
            ImageView imageView = new ImageView(getContext());
          Glide.with(getContext()).load(imageUrls.get(i)).into(imageView);
            imageView.setOnClickListener(listener);
            addView(imageView);




            Log.i("glide", "setImageUrls: "+imageUrls.get(i));
        }

    }
}
