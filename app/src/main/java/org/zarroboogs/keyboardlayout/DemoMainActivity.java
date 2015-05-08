package org.zarroboogs.keyboardlayout;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import org.zarroboogs.keyboardlayout.smilepicker.SmileyPicker;


public class DemoMainActivity extends ActionBarActivity {

    private KeyboardRelativeLayout mRelativeLsyout;

    private EditText mEditText;

//    private ImageView mImageView;

    private SmileyPicker smilePickerLayout;

    private ScrollView scrollView;

    private boolean mSwitchCkick = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_main);
//        mImageView = (ImageView) findViewById(R.id.hide_imagview);
        mEditText = (EditText) findViewById(R.id.editText2);

        smilePickerLayout = (SmileyPicker) findViewById(R.id.smilePickerLayout);


        scrollView = (ScrollView) findViewById(R.id.scrollview);

        mRelativeLsyout = (KeyboardRelativeLayout) findViewById(R.id.root);
        smilePickerLayout.setEditText( mEditText);


        mRelativeLsyout.setOnKeyboardStateListener(new OnKeyboardStateChangeListener() {
            @Override
            public void onKeyBoardShow(int height) {

                if (mSwitchCkick){
                    smilePickerLayout.setVisibility(View.GONE);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    scrollView.setLayoutParams(params);
                }

            }

            @Override
            public void onKeyBoardHide() {

                if (mSwitchCkick){
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

                mSwitchCkick = true;

                if (mRelativeLsyout.getKeyBoardHelper().isKeyboardShow()){

                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, scrollView.getHeight());
                    scrollView.setLayoutParams(params);

                    mRelativeLsyout.getKeyBoardHelper().hideKeyboard();

                }else {
                    mRelativeLsyout.getKeyBoardHelper().showKeyboard(mEditText);
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (smilePickerLayout.getVisibility() == View.VISIBLE){
            removeViewWithAnim(smilePickerLayout);
        }else{
            super.onBackPressed();
        }
    }

    private void showViewWithAnim(View view){
        smilePickerLayout.setVisibility(View.VISIBLE);

        Animation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF,1, Animation.RELATIVE_TO_SELF, 0);
        animation.setDuration(200);
        animation.setFillAfter(true);

        view.startAnimation(animation);

    }

    private void removeViewWithAnim(View view){
        Animation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF,0, Animation.RELATIVE_TO_SELF, 1);
        animation.setDuration(200);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                scrollView.setLayoutParams(params);
                smilePickerLayout.setVisibility(View.GONE);
                smilePickerLayout.requestLayout();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animation);

    }
}
