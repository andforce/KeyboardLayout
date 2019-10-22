
package org.zarroboogs.keyboardlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import org.zarroboogs.keyboardlayout.helper.KeyboardStateWatcher;

public class KeyboardLinearLayout extends LinearLayout {


    private KeyboardStateWatcher mKeyboardStateWatcher;

    public KeyboardLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mKeyboardStateWatcher = new KeyboardStateWatcher(this);
    }

    public KeyboardLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mKeyboardStateWatcher = new KeyboardStateWatcher(this);
    }

    public KeyboardLinearLayout(Context context) {
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
