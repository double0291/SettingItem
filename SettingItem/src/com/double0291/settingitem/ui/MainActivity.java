package com.double0291.settingitem.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.double0291.settingitem.R;
import com.double0291.settingitem.SettingItemConstants;
import com.double0291.settingitem.SettingSimpleItem;

public class MainActivity extends Activity implements OnClickListener {
    Button mClearBtn, mShowDotBtn, mShowNewBtn;
    SettingSimpleItem mItem1, mItem2, mItem3, mItem4, mItem5, mItem6, mItem7, mItem8, mItem9, mItem10, mItem11,
            mItem12;
    SettingSimpleItem[] mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mClearBtn = (Button) findViewById(R.id.clear_btn);
        mClearBtn.setOnClickListener(this);
        mShowDotBtn = (Button) findViewById(R.id.show_dot_btn);
        mShowDotBtn.setOnClickListener(this);
        mShowNewBtn = (Button) findViewById(R.id.show_new_btn);
        mShowNewBtn.setOnClickListener(this);

        mItem1 = (SettingSimpleItem) findViewById(R.id.item1);
        mItem2 = (SettingSimpleItem) findViewById(R.id.item2);
        mItem3 = (SettingSimpleItem) findViewById(R.id.item3);
        mItem4 = (SettingSimpleItem) findViewById(R.id.item4);
        mItem5 = (SettingSimpleItem) findViewById(R.id.item5);
        mItem6 = (SettingSimpleItem) findViewById(R.id.item6);
        mItem7 = (SettingSimpleItem) findViewById(R.id.item7);
        mItem8 = (SettingSimpleItem) findViewById(R.id.item8);
        mItem9 = (SettingSimpleItem) findViewById(R.id.item9);
        mItem10 = (SettingSimpleItem) findViewById(R.id.item10);
        mItem11 = (SettingSimpleItem) findViewById(R.id.item11);
        mItem12 = (SettingSimpleItem) findViewById(R.id.item12);

        mItems = new SettingSimpleItem[] { mItem1, mItem2, mItem3, mItem4, mItem5, mItem6, mItem7, mItem8, mItem9,
                mItem10, mItem11, mItem12 };
    }

    @Override
    public void onClick(View v) {
        int size = mItems.length;

        switch (v.getId()) {
        case R.id.clear_btn:
            for (int i = 0; i < size; i++) {
                mItems[i].clearRedPoint();
            }
            break;

        case R.id.show_dot_btn:
            for (int i = 0; i < size; i++) {
                mItems[i].setRedPointType(SettingItemConstants.RED_POINT_TYPE_DOT);
            }
            break;

        case R.id.show_new_btn:
            for (int i = 0; i < size; i++) {
                mItems[i].setRedPointType(SettingItemConstants.RED_POINT_TYPE_NEW);
            }
            break;

        default:
            break;
        }
    }
}
