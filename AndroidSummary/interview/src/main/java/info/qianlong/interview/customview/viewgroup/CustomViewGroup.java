
package info.qianlong.interview.customview.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by android on 17/4/20.
 */

public class CustomViewGroup extends ViewGroup {

    public CustomViewGroup(Context context) {
        super(context);
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int mViewGroupWidth = getMeasuredWidth(); // 获取viewgroup的总宽度
        int mViewGroupHeight = getMeasuredHeight(); // 获取viewgroup的总高度

        int positionX = left; // 当前x坐标
        int positionY = top; // 当前y坐标

        int childCount = getChildCount(); // 子view总数

        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            int width = childView.getMeasuredWidth(); // 获取子view的宽度
            int height = childView.getMeasuredHeight(); // 获取子view的高度

            LayoutParams margins = (LayoutParams)childView
                    .getLayoutParams();
            // ChildView占用的width = width+leftMargin+rightMargin
            // ChildView占用的height = height+topMargin+bottomMargin
            // 如果剩余的空间不够，则移到下一行开始位置
            if (positionX + width + margins.leftMargin + margins.rightMargin > mViewGroupWidth) {
                positionX = left;
                positionY += height + margins.topMargin + margins.bottomMargin;
            }
            // 绘制子view
            childView.layout(positionX + margins.leftMargin, positionY + margins.topMargin,
                    positionX + margins.leftMargin + width, positionY + height + margins.topMargin);
            positionX += width + margins.leftMargin + margins.rightMargin;
        }

    }

    public static class LayoutParams extends MarginLayoutParams {
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }
}
