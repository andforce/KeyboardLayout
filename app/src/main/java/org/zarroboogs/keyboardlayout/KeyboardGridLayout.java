
package org.zarroboogs.keyboardlayout;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

public class KeyboardGridLayout extends RelativeLayout {

    private OnKeyboardStateChangeListener mListener;
    private static final int UNKNOW = -1;
    private Rect mRect = new Rect();
    private Rect mOriRect = new Rect();
    private int mKeyboardHeight = UNKNOW;

    public KeyboardGridLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public KeyboardGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KeyboardGridLayout(Context context) {
        super(context);
    }

    public void setOnKeyboardStateListener(OnKeyboardStateChangeListener listener) {
        mListener = listener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (mListener == null){
                    return;
                }

                KeyboardGridLayout.this.getWindowVisibleDisplayFrame(mRect);

                int height = mOriRect.height() - mRect.height();
                if (height == mKeyboardHeight){
                    return;
                }else{
                    if (height > 0){
                        mListener.onKeyBoardShow(height);
                    }else{
                        mListener.onKeyBoardHide();
                    }
                    mKeyboardHeight = height;
                }
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (mOriRect.height() == 0){
            KeyboardGridLayout.this.getWindowVisibleDisplayFrame(mOriRect);
        }
    }

}
