
package com.andforce.keyboardlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.andforce.keyboardlayout.watcher.KeyboardStateWatcher;
import com.andforce.keyboardlayout.watcher.OnKeyboardStateChangeListener;


public class KeyboardFrameLayout extends FrameLayout {


    private KeyboardStateWatcher mKeyboardStateWatcher;

    public KeyboardFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mKeyboardStateWatcher = new KeyboardStateWatcher(this);
    }

    public KeyboardFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mKeyboardStateWatcher = new KeyboardStateWatcher(this);
    }

    public KeyboardFrameLayout(Context context) {
        super(context);
        mKeyboardStateWatcher = new KeyboardStateWatcher(this);
    }

    public void setOnKeyboardStateListener(OnKeyboardStateChangeListener listener) {
        mKeyboardStateWatcher.setOnKeyboardStateListener(listener);
    }


    public KeyboardStateWatcher getKeyboardStateWatcher() {
        return mKeyboardStateWatcher;
    }

}
