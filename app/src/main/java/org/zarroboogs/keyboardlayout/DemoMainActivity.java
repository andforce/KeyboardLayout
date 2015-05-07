package org.zarroboogs.keyboardlayout;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class DemoMainActivity extends ActionBarActivity {

    private KeyboardRelativeLayout mRelativeLsyout;

    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_main);
        mRelativeLsyout = (KeyboardRelativeLayout) findViewById(R.id.root);

        mRelativeLsyout.setOnKeyboardStateListener(new OnKeyboardStateChangeListener() {
            @Override
            public void onKeyBoardShow(int height) {
                Toast.makeText(DemoMainActivity.this, "show " + height, Toast.LENGTH_SHORT).show();
                Log.d("KeyBoardStateChange: ", " show " + height);
            }

            @Override
            public void onKeyBoardHide() {
                Toast.makeText(DemoMainActivity.this, "hide", Toast.LENGTH_SHORT).show();
                Log.d("KeyBoardStateChange: ", " hide");
            }
        });

        mEditText = (EditText) findViewById(R.id.editText2);

        final Button btn = (Button) findViewById(R.id.editText);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRelativeLsyout.getKeyBoardHelper().isKeyboardShow()){
                    mRelativeLsyout.getKeyBoardHelper().hideKeyboard();
                }else {
                    mRelativeLsyout.getKeyBoardHelper().showKeyboard(mEditText);
                }

            }
        });
    }
}
