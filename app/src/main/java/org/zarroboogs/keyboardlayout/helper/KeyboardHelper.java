package org.zarroboogs.keyboardlayout.helper;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

import org.zarroboogs.keyboardlayout.OnKeyboardStateChangeListener;

/**
 * Created by wangdiyuan on 15-5-7.
 */
public class KeyboardHelper {

    private OnKeyboardStateChangeListener mListener;
    private static final int UNKNOW = -1;
    private Rect mRect = new Rect();
    private Rect mOriRect = new Rect();
    private int mKeyboardHeight = UNKNOW;

    private View mRootView;

    public void setOnKeyboardStateListener(OnKeyboardStateChangeListener listener) {
        mListener = listener;
    }

    public KeyboardHelper(View root){
        this.mRootView = root;
        if (this.mRootView == null){
            throw new RuntimeException(KeyboardHelper.class.getName() + " RootView Can NOT be null");
        }
        setGlobalLayoutListener();
    }

    /**
     * this method must call after onLayout()
     */
    public void init(){
        if (mOriRect.height() == 0){
            this.mRootView.getWindowVisibleDisplayFrame(mOriRect);
        }
    }
    private void setGlobalLayoutListener() {

        this.mRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (mOriRect.height() == 0){
                    throw new RuntimeException(KeyboardHelper.class.getName() + " You must call init() before setGlobalLayoutListener()");
                }
                if (mListener == null){
                    return;
                }

                mRootView.getWindowVisibleDisplayFrame(mRect);

                int height = mOriRect.height() - mRect.height();
                if (height == mKeyboardHeight){
                    return;
                }else{
                    if (mKeyboardHeight != UNKNOW){
                        if (height > 0){
                            mListener.onKeyBoardShow(height);
                        }else{
                            mListener.onKeyBoardHide();
                        }
                    }

                    mKeyboardHeight = height;
                }
            }
        });
    }
}
