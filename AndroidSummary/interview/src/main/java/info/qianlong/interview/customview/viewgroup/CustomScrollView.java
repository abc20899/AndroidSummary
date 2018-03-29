
package info.qianlong.interview.customview.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

/**
 * Created by android on 17/4/19.
 */

public class CustomScrollView extends ViewGroup {

    int mScreenHeight;

    private int mStartY;

    private int mEnd;

    private Scroller mScroller;

    private int mLastY;

    private int childCount;

    private int realChildCount;

    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    // 初始化一些必要变量并创建一些必要的对象
    private void init() {
        WindowManager wm = (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
        mScreenHeight = wm.getDefaultDisplay().getHeight();
        mScroller = new Scroller(getContext());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        realChildCount = 0;
        childCount = getChildCount();
        // set the ViewGroup's height
        MarginLayoutParams lp = (MarginLayoutParams)getLayoutParams();
        lp.height = mScreenHeight * childCount;
        setLayoutParams(lp);
        // 绘制子view的位置
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() != View.GONE) {
                realChildCount++;
                childView.layout(l, i * mScreenHeight, r, (i + 1) * mScreenHeight);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 在这个触摸事件中，需要判断两个距离，一个是手指移动的距离一个是view滚动的距离
        // 这就是随着手指的移动会发送改变的量
        int y = (int)event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                // 这是view滚动的距离
                mStartY = getScrollY();
                break;
            case MotionEvent.ACTION_MOVE:
                // 当我们再次触碰屏幕时，如果之前的滚动动画还没有停止，我们也让他立即停止
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                int dY = mLastY - y;
                // 当滚动时触及到上边缘时，我们增加一个回弹的效果
                if (getScrollY() < 0) {
                    dY /= 3;
                }
                // 如果是下边缘，我们不让它继续向下滚动
                if (getScrollY() > mScreenHeight * realChildCount - mScreenHeight) {
                    dY = 0;
                }
                // 让我们的view滚动相应的dy距离
                scrollBy(0, dY);
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                mEnd = getScrollY();
                int dScrollY = mEnd - mStartY;
                if (dScrollY > 0) {// 向上滚动的情况
                    if (dScrollY < mScreenHeight / 3 || getScrollY() < 0) {
                        mScroller.startScroll(0, getScrollY(), 0, -dScrollY);
                    } else {
                        mScroller.startScroll(0, getScrollY(), 0, mScreenHeight - dScrollY);
                    }
                } else {// 向下滚动的情况
                    if (-dScrollY < mScreenHeight / 3) {
                        mScroller.startScroll(0, getScrollY(), 0, -dScrollY);
                    } else {
                        mScroller.startScroll(0, getScrollY(), 0, -mScreenHeight - dScrollY);
                    }
                }
                break;
        }
        // 必须加该方法，否则上面的代码都会无效果
        postInvalidate();
        return true;
    }
}
