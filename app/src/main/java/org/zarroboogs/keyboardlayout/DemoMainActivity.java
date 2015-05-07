package org.zarroboogs.keyboardlayout;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class DemoMainActivity extends ActionBarActivity {

    private KeyboardLinearLayout mRelativeLsyout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_main);
        mRelativeLsyout = (KeyboardLinearLayout) findViewById(R.id.demo_layout);
        mRelativeLsyout.setOnKeyboardStateListener(new OnKeyboardStateChangeListener() {
            @Override
            public void onKeyBoardShow(int height) {
                Toast.makeText(DemoMainActivity.this,"show " + height,Toast.LENGTH_SHORT).show();
                Log.d("KeyBoardStateChange: ", " show "+ height);
            }

            @Override
            public void onKeyBoardHide() {
                Toast.makeText(DemoMainActivity.this,"hide",Toast.LENGTH_SHORT).show();
                Log.d("KeyBoardStateChange: ", " hide");
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.demo_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
