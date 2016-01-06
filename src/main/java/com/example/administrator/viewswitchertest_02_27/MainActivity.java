package com.example.administrator.viewswitchertest_02_27;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;

public class MainActivity extends Activity {

    public static final int NUMBER_PER_SCREEN = 12;

    public static class DataItem {
        public String dataName;
        public Drawable drawable;
    }

    private ArrayList<DataItem> items = new ArrayList<>();
    private int screenNum = -1;//当前显示的第几屏
    private int screenCount;//总的数量s
    ViewSwitcher switcher;
    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inflater = LayoutInflater.from(MainActivity.this);
        for (int i = 0; i < 40; i++) {
            String label = "应用" + (i + 1);
            Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
            DataItem dataItem = new DataItem();
            dataItem.dataName = label;
            dataItem.drawable = drawable;
            items.add(dataItem);
        }
        screenCount = items.size() % NUMBER_PER_SCREEN == 0 ? items.size() / NUMBER_PER_SCREEN : items.size() / NUMBER_PER_SCREEN + 1;
        switcher = (ViewSwitcher) findViewById(R.id.viewSwitcher);
        switcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                return inflater.inflate(R.layout.slidelistview, null);
            }
        });
        next(null);

    }

    private BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            if (screenNum == screenCount - 1 && items.size() % NUMBER_PER_SCREEN != 0) {
                return items.size() % NUMBER_PER_SCREEN;
            }
            return NUMBER_PER_SCREEN;
        }

        @Override
        public DataItem getItem(int position) {
            return items.get(screenNum * NUMBER_PER_SCREEN + position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (convertView == null) {
                view = inflater.inflate(R.layout.labelicon, null);
            }
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            imageView.setImageDrawable(getItem(position).drawable);
            TextView textView = (TextView) view.findViewById(R.id.textView);
            textView.setText(getItem(position).dataName);

            return view;
        }
    };

    public void next(View v) {
        if (screenNum < screenCount - 1) {
            screenNum++;
            switcher.setInAnimation(this, R.anim.slide_in_right);
            switcher.setOutAnimation(this, R.anim.slide_out_left);
            ((GridView) switcher.getNextView()).setAdapter(adapter);
            switcher.showNext();
        }

    }

    public void prev(View v) {
        if (screenNum > 0) {
            screenNum--;
            switcher.setInAnimation(this, R.anim.slide_in_left);
            switcher.setOutAnimation(this, R.anim.slide_out_right);
            ((GridView) switcher.getNextView()).setAdapter(adapter);
            switcher.showPrevious();
        }
    }

}
