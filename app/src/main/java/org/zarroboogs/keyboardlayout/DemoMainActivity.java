package org.zarroboogs.keyboardlayout;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import org.zarroboogs.keyboardlayout.smilepicker.SmileyPicker;


public class DemoMainActivity extends Activity {

    private KeyboardRelativeLayout mRelativeLsyout;

    private EditText mEditText;

    private SmileyPicker smilePickerLayout;

    private ScrollView scrollView;

    private boolean mSwitchClicked = false;

    private int mKeyboardHeight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_main);
        mEditText = (EditText) findViewById(R.id.editText2);

        smilePickerLayout = (SmileyPicker) findViewById(R.id.smilePickerLayout);


        scrollView = (ScrollView) findViewById(R.id.scrollview);

        mRelativeLsyout = (KeyboardRelativeLayout) findViewById(R.id.root);
        smilePickerLayout.setEditText(mEditText);


        mRelativeLsyout.setOnKeyboardStateListener(new OnKeyboardStateChangeListener() {
            @Override
            public void onKeyBoardShow(int height) {
                mKeyboardHeight = height;
                if (mSwitchClicked) {
                    smilePickerLayout.setVisibility(View.GONE);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    scrollView.setLayoutParams(params);
                }

            }

            @Override
            public void onKeyBoardHide() {

                if (mSwitchClicked) {
//                    mImageView.setVisibility(View.VISIBLE);
                    showViewWithAnim(smilePickerLayout);
                }
            }
        });

        mEditText = (EditText) findViewById(R.id.editText2);

        final Button btn = (Button) findViewById(R.id.editText);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mSwitchClicked = true;

                if (mRelativeLsyout.getKeyBoardHelper().isKeyboardShow()) {

                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, scrollView.getHeight());
                    scrollView.setLayoutParams(params);

                    mRelativeLsyout.getKeyBoardHelper().hideKeyboard();

                } else {
                    mRelativeLsyout.getKeyBoardHelper().showKeyboard(mEditText);
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (smilePickerLayout.getVisibility() == View.VISIBLE) {
            removeViewWithAnim(smilePickerLayout);
        } else {
            super.onBackPressed();
        }
    }

    private void showViewWithAnim(View view) {
        smilePickerLayout.setVisibility(View.VISIBLE);

        Animation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0);
        animation.setDuration(150);
//        animation.setFillAfter(true);

        view.startAnimation(animation);

    }

    private void changeLayout() {

        final RelativeLayout.LayoutParams oldParams = (RelativeLayout.LayoutParams) scrollView.getLayoutParams();

        ValueAnimator valueAnimator = ValueAnimator.ofInt(oldParams.height, mKeyboardHeight + oldParams.height);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                int value = (int) animation.getAnimatedValue();
                if (value == oldParams.height) {
                    return;
                } else {
                    oldParams.height = value;
                }
//                Log.d("changeLayout", "onAnimationUpdate " + oldParams.height);
                scrollView.requestLayout();
            }
        });
        valueAnimator.setDuration(200);
        valueAnimator.start();

    }

    private void removeViewWithAnim(final View view) {
        final RelativeLayout.LayoutParams oldParams = (RelativeLayout.LayoutParams) scrollView.getLayoutParams();

        final int oldHeight = oldParams.height;

        AnimationSet animationSet = new AnimationSet(true);

        // create alpha anim
        Animation fadeOut = AnimationUtils.loadAnimation(view.getContext(), android.R.anim.fade_out);
        animationSet.addAnimation(fadeOut);

        // create translate anim
        TranslateAnimation transAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1) {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                super.applyTransformation(interpolatedTime, t);
                Log.d("applyTransformation", "time:  " + interpolatedTime);
                oldParams.height = (int) (oldHeight + mKeyboardHeight * interpolatedTime);
                scrollView.requestLayout();

            }
        };
        animationSet.addAnimation(transAnim);

        // set duration
        animationSet.setDuration(200);

        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
//                changeLayout();
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                view.setVisibility(View.GONE);
                smilePickerLayout.setVisibility(View.GONE);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                scrollView.setLayoutParams(params);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animationSet);

    }
}
