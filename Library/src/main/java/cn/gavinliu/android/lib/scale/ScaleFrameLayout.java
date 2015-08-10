package cn.gavinliu.android.lib.scale;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import cn.gavinliu.android.lib.scale.helper.ScaleLayoutHelper;

/**
 * Created by GavinLiu on 2015-08-10
 */
public class ScaleFrameLayout extends FrameLayout {

    private ScaleLayoutHelper mHelper;

    public ScaleFrameLayout(Context context) {
        this(context, null);
    }

    public ScaleFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaleFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mHelper = ScaleLayoutHelper.create(this, attrs);
    }

    @Override
    public ScaleFrameLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new ScaleFrameLayout.LayoutParams(this.getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.mHelper.adjustChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.mHelper.handleMeasuredStateTooSmall()) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.mHelper.restoreOriginalParams();
    }

    public static class LayoutParams extends FrameLayout.LayoutParams implements ScaleLayoutHelper.ScaleLayoutParams {
        private ScaleLayoutHelper.ScaleLayoutInfo mPercentLayoutInfo;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            this.mPercentLayoutInfo = ScaleLayoutHelper.getScaleLayoutInfo(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        @Override
        public ScaleLayoutHelper.ScaleLayoutInfo getScaleLayoutInfo() {
            return this.mPercentLayoutInfo;
        }

        protected void setBaseAttributes(TypedArray a, int widthAttr, int heightAttr) {
            ScaleLayoutHelper.fetchWidthAndHeight(this, a, widthAttr, heightAttr);
        }

    }
}