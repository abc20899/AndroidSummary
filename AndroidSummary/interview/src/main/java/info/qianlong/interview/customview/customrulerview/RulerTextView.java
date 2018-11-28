
package info.qianlong.interview.customview.customrulerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;

//刻度view
public class RulerTextView extends AppCompatTextView {

    public RulerTextView(Context context) {
        super(context);
        init();
    }

    public RulerTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setGravity(Gravity.BOTTOM);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        float mmWidth = ((float) getWidth()) / 10;
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        float top = 2;
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                canvas.drawRect(i * mmWidth, top, i * mmWidth + mmWidth, top + mmWidth, p);
            }
        }
    }
}
