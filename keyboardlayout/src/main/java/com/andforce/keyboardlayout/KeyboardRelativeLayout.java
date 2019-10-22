
package com.andforce.keyboardlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.andforce.keyboardlayout.watcher.KeyboardStateWatcher;
import com.andforce.keyboardlayout.watcher.OnKeyboardStateChangeListener;

public class KeyboardRelativeLayout extends RelativeLayout {


    private KeyboardStateWatcher mKeyboardStateWatcher;

    public KeyboardRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mKeyboardStateWatcher = new KeyboardStateWatcher(this);
    }

    public KeyboardRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mKeyboardStateWatcher = new KeyboardStateWatcher(this);
    }

    public KeyboardRelativeLayout(Context context) {
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
