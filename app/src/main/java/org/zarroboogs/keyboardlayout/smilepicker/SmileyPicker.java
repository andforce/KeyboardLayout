
package org.zarroboogs.keyboardlayout.smilepicker;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import org.zarroboogs.keyboardlayout.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

/**
 * User: qii Date: 13-1-18
 */
public class SmileyPicker extends LinearLayout {

    private int mPickerHeight;

    private EditText mEditText;

    private LayoutInflater mInflater;

    private Context mContext;

    private final LayoutTransition transitioner = new LayoutTransition();

    private ViewPager viewPager;

    private ImageView centerPoint;

    private ImageView leftPoint;

    private ImageView rightPoint;
    public LinkedHashMap<Integer, LinkedHashMap<String, Bitmap>> emotionsPic = null;

    public SmileyPicker(Context paramContext) {
        super(paramContext);

    }

    public SmileyPicker(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        mContext = paramContext;

        emotionsPic = new LinkedHashMap<Integer, LinkedHashMap<String, Bitmap>>();

        this.mInflater = LayoutInflater.from(paramContext);
        View view = this.mInflater.inflate(R.layout.writeweiboactivity_smileypicker, null);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new SmileyPagerAdapter());
        leftPoint = (ImageView) view.findViewById(R.id.left_point);
        centerPoint = (ImageView) view.findViewById(R.id.center_point);
        rightPoint = (ImageView) view.findViewById(R.id.right_point);
        if (true) {
            rightPoint.setVisibility(View.VISIBLE);
        } else {
            rightPoint.setVisibility(View.GONE);
        }
        leftPoint.getDrawable().setLevel(1);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        leftPoint.getDrawable().setLevel(1);
                        centerPoint.getDrawable().setLevel(0);
                        rightPoint.getDrawable().setLevel(0);
                        break;
                    case 1:
                        leftPoint.getDrawable().setLevel(0);
                        centerPoint.getDrawable().setLevel(1);
                        rightPoint.getDrawable().setLevel(0);
                        break;
                    case 2:
                        leftPoint.getDrawable().setLevel(0);
                        centerPoint.getDrawable().setLevel(0);
                        rightPoint.getDrawable().setLevel(1);
                        break;

                }
            }
        });
        addView(view);
    }

    public void setEditText(ViewGroup rootLayout, EditText paramEditText) {
        this.mEditText = paramEditText;
        rootLayout.setLayoutTransition(transitioner);

    }


    private class SmileyPagerAdapter extends PagerAdapter {

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.smileypicker_gridview, container, false);

            GridView gridView = (GridView) view.findViewById(R.id.smiley_grid);

            gridView.setAdapter(new SmileyAdapter(mContext, position));
            container.addView(view, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            return view;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);

        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }
    }

    private final class SmileyAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        private List<String> keys;

        private Map<String, Bitmap> bitmapMap;

        private int emotionPosition;

        private int count;


        public SmileyAdapter(Context context, int emotionPosition) {
            this.emotionPosition = emotionPosition;
            this.mInflater = LayoutInflater.from(context);
            this.keys = new ArrayList<String>();
            Set<String> keySet;
            switch (emotionPosition) {
                case SmileyMap.GENERAL_EMOTION_POSITION:
                    keySet = getEmotionsPics().keySet();
                    keys.addAll(keySet);
                    bitmapMap = getEmotionsPics();
                    count = bitmapMap.size();
                    break;
                case SmileyMap.EMOJI_EMOTION_POSITION:
                    keySet = EmojiMap.getInstance().getMap().keySet();
                    keys.addAll(keySet);
                    bitmapMap = null;
                    count = keys.size();
                    break;
                case SmileyMap.HUAHUA_EMOTION_POSITION:
                    keySet = getHuahuaPics().keySet();
                    keys.addAll(keySet);
                    bitmapMap = getHuahuaPics();
                    count = bitmapMap.size();
                    break;
                default:
                    throw new IllegalArgumentException("emotion position is invalid");
            }

        }

        private void bindView(final int position, View contentView) {
            ImageView imageView = ((ImageView) contentView.findViewById(R.id.smiley_item));
            TextView textView = (TextView) contentView.findViewById(R.id.smiley_text_item);
            if (emotionPosition != SmileyMap.EMOJI_EMOTION_POSITION) {
                imageView.setVisibility(View.VISIBLE);
                textView.setVisibility(View.INVISIBLE);
                imageView.setImageBitmap(bitmapMap.get(keys.get(position)));

            } else {
                imageView.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.VISIBLE);
                textView.setText(keys.get(position));
            }

            contentView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = mEditText.getSelectionStart();
                    String text = keys.get(position);
                    Editable edit = mEditText.getEditableText();// 获取EditText的文字
                    if (index < 0 || index >= edit.length()) {
                        edit.append(text);
                    } else {
                        edit.insert(index, text);// 光标所在位置插入文字
                    }
                    String content = mEditText.getText().toString();
                    addEmotions(mEditText, content);
                    mEditText.setSelection(index + text.length());
                }
            });
        }



        public int getCount() {
            return count;
        }

        public Object getItem(int paramInt) {
            return null;
        }

        public long getItemId(int paramInt) {
            return 0L;
        }

        public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
            if (paramView == null) {
                paramView = this.mInflater.inflate(R.layout.writeweiboactivity_smileypicker_item, null);
            }
            bindView(paramInt, paramView);
            return paramView;
        }
    }

    public void addEmotions(EditText et, String txt) {
        String hackTxt;
        if (txt.startsWith("[") && txt.endsWith("]")) {
            hackTxt = txt + " ";
        } else {
            hackTxt = txt;
        }
        SpannableString value = SpannableString.valueOf(hackTxt);
        addEmotions(value);
        et.setText(value);
    }

    public synchronized Map<String, Bitmap> getEmotionsPics() {
        if (emotionsPic != null && emotionsPic.size() > 0) {
            return emotionsPic.get(SmileyMap.GENERAL_EMOTION_POSITION);
        } else {
            getEmotionsTask();
            return emotionsPic.get(SmileyMap.GENERAL_EMOTION_POSITION);
        }
    }

    public synchronized Map<String, Bitmap> getHuahuaPics() {
        if (emotionsPic != null && emotionsPic.size() > 0) {
            return emotionsPic.get(SmileyMap.HUAHUA_EMOTION_POSITION);
        } else {
            getEmotionsTask();
            return emotionsPic.get(SmileyMap.HUAHUA_EMOTION_POSITION);
        }
    }

    private void getEmotionsTask() {
        Map<String, String> general = SmileyMap.getInstance().getGeneral();
        emotionsPic.put(SmileyMap.GENERAL_EMOTION_POSITION, getEmotionsTask(general));
        Map<String, String> huahua = SmileyMap.getInstance().getHuahua();
        emotionsPic.put(SmileyMap.HUAHUA_EMOTION_POSITION, getEmotionsTask(huahua));
    }

    private LinkedHashMap<String, Bitmap> getEmotionsTask(Map<String, String> emotionMap) {
        List<String> index = new ArrayList<String>();
        index.addAll(emotionMap.keySet());
        LinkedHashMap<String, Bitmap> bitmapMap = new LinkedHashMap<String, Bitmap>();
        for (String str : index) {
            String name = emotionMap.get(str);
            AssetManager assetManager = getContext().getAssets();
            InputStream inputStream;
            try {
                inputStream = assetManager.open(name);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                if (bitmap != null) {
                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap,
                            50,50, true);
                    if (bitmap != scaledBitmap) {
                        bitmap.recycle();
                        bitmap = scaledBitmap;
                    }
                    bitmapMap.put(str, bitmap);
                }
            } catch (IOException ignored) {

            }
        }

        return bitmapMap;
    }

    private void addEmotions(SpannableString value) {
        Matcher localMatcher = WeiboPatterns.EMOTION_URL.matcher(value);
        while (localMatcher.find()) {
            String str2 = localMatcher.group(0);
            int k = localMatcher.start();
            int m = localMatcher.end();
            if (m - k < 8) {
                Bitmap bitmap = getEmotionsPics().get(str2);
                if (bitmap == null) {
                    bitmap = getHuahuaPics().get(str2);
                }
                if (bitmap != null) {
                    ImageSpan localImageSpan = new ImageSpan(this.getContext(), bitmap,
                            ImageSpan.ALIGN_BASELINE);
                    value.setSpan(localImageSpan, k, m, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }

            }
        }
    }
}
