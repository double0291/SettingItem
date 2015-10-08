package com.double0291.settingitem;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingSwitchItem extends RelativeLayout implements SettingItemConstants {
    private CharSequence mText;
    private int mBgType;
    private boolean mChecked;

    private int mItemHeight;
    private int mPadding;

    private TextView mTextView;
    private Drawable mLeftIcon;
    private int mLeftIconWidth;
    private int mLeftIconHeight;
    private Switch mSwitch;

    public SettingSwitchItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPadding = this.getResources().getDimensionPixelSize(R.dimen.setting_item_horizontal_padding);

        int defaultItemHeight = this.getResources().getDimensionPixelSize(R.dimen.setting_item_default_height);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SettingItem);

        // ���û����ʾָ������Item�߶ȣ���ȡĬ�ϸ߶�
        mItemHeight = a.getDimensionPixelSize(R.styleable.SettingItem_customHeight, defaultItemHeight);

        mText = a.getString(R.styleable.SettingItem_switchText);
        mLeftIcon = a.getDrawable(R.styleable.SettingItem_leftIcon);
        mLeftIconWidth = a.getDimensionPixelSize(R.styleable.SettingItem_leftIconWidth, 0);
        mLeftIconHeight = a.getDimensionPixelSize(R.styleable.SettingItem_leftIconHeight, 0);

        mLeftIconHeight = Math.min(mItemHeight, mLeftIconHeight);

        mChecked = a.getBoolean(R.styleable.SettingItem_switchChecked, false);
        mBgType = a.getInt(R.styleable.SettingItem_bgType, BG_TYPE_NONE);
        a.recycle();

        initViews();
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(mItemHeight, MeasureSpec.EXACTLY));
        setMeasuredDimension(getMeasuredWidth(), mItemHeight);
    }

    @Override
    public void setContentDescription(CharSequence contentDescription) {
        mSwitch.setContentDescription(contentDescription);
    }

    /**
     * �����Զ���߶�
     * 
     * @param height
     */
    public void setCustomHeight(int height) {
        if (height > 0) {
            mItemHeight = height;
            requestLayout();
        }
    }

    /**
     * ����bgType���ض�Ӧ��selector��Դ
     * 
     * @param res
     * @param bgType
     * @return
     */
    public Drawable getBgDrawable(int bgType) {
        switch (bgType) {
        case BG_TYPE_SINGLE:
            return getResources().getDrawable(R.drawable.skin_setting_strip_bg_unpressed);
        case BG_TYPE_TOP:
            return getResources().getDrawable(R.drawable.skin_setting_strip_top_unpressed);
        case BG_TYPE_MIDDLE:
            return getResources().getDrawable(R.drawable.skin_setting_strip_middle_unpressed);
        case BG_TYPE_BOTTOM:
            return getResources().getDrawable(R.drawable.skin_setting_strip_bottom_unpressed);
        default:
            return getResources().getDrawable(R.drawable.skin_setting_strip_bg_unpressed);
        }
    }

    /**
     * �������ֱ���
     * 
     * @param text
     */
    public void setText(CharSequence text) {
        if (mTextView != null) {
            if (!TextUtils.isEmpty(text)) {
                mText = text;
                mTextView.setText(mText);
                mTextView.setTextColor(getResources().getColor(R.color.black));
            } else {
                mTextView.setVisibility(View.GONE);
            }
        }
    }

    /**
     * ���ñ�������icon��icon��СΪDrawable��Դ��Ĭ�ϴ�С
     * 
     * @param drawable
     *            ���Ϊnull,�����ԭ����icon
     */
    public void setLeftIcon(Drawable drawable) {
        if (mTextView != null) {
            mLeftIcon = drawable;
            if (drawable == null) {
                mTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            } else {
                final int height = drawable.getIntrinsicHeight();

                if (height > mItemHeight) {
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), mItemHeight);
                    mTextView.setCompoundDrawables(drawable, null, null, null);
                } else {
                    mTextView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                }

                mTextView.setCompoundDrawablePadding(mPadding);
            }
        }
    }

    /**
     * ���ñ�������icon���ֶ�ָ��icon�Ŀ��ߣ����iconWidth����iconHeightΪ0������ʾͼƬ��ԭ����
     */
    public void setLeftIcon(Drawable drawable, int iconWidth, int iconHeight) {
        if (drawable == null || mTextView == null || iconWidth < 0 || iconHeight < 0) {
            return;
        }

        if (iconWidth > 0 && iconHeight > 0) {
            mLeftIcon = drawable;
            mLeftIconWidth = iconWidth;
            mLeftIconHeight = Math.min(mItemHeight, iconHeight);

            drawable.setBounds(0, 0, mLeftIconWidth, mLeftIconHeight);
            mTextView.setCompoundDrawables(drawable, null, null, null);
            mTextView.setCompoundDrawablePadding(mPadding);
        } else if (iconWidth == 0 || iconHeight == 0) {
            setLeftIcon(drawable);
        }
    }

    /**
     * �Ƿ���ÿ���
     */
    @Override
    public void setEnabled(boolean enabled) {
        if (mSwitch != null) {
            mSwitch.setEnabled(enabled);
        }
    }

    public void setChecked(boolean checked) {
        if (mSwitch != null) {
            mSwitch.setChecked(checked);
        }
    }

    /**
     * �жϿ��ؿؼ��Ƿ�ѡ��
     * 
     * @return trueѡ�У�falseδѡ��
     */
    public boolean isChecked() {
        if (mSwitch != null) {
            return mSwitch.isChecked();
        }
        return false;
    }

    /**
     * ����������ѡ���¼��Ļص�����
     * 
     * @param l
     */
    public void setOnCheckedChangeListener(OnCheckedChangeListener l) {
        if (mSwitch != null) {
            mSwitch.setOnCheckedChangeListener(l);
        }
    }

    /**
     * ���ñ����ؼ�����������
     * 
     * @param bgType
     */
    public void setBgType(int bgType) {
        if (bgType != BG_TYPE_SINGLE && bgType != BG_TYPE_TOP && bgType != BG_TYPE_MIDDLE && bgType != BG_TYPE_BOTTOM) {
            throw new RuntimeException("Parameter bgType is illegal!");
        }

        mBgType = bgType;
        setBackgroundDrawable(getBgDrawable(mBgType));
    }

    private void initViews() {
        mTextView = new TextView(getContext());
        mTextView.setId(R.id.setting_item_textview);
        if (!TextUtils.isEmpty(mText)) {
            mTextView.setText(mText);
            mTextView.setContentDescription(mText);
        }
        mTextView.setSingleLine(true);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        mTextView.setTextColor(getResources().getColor(R.color.black));
        mTextView.setGravity(Gravity.LEFT | Gravity.CENTER);
        mTextView.setEllipsize(TruncateAt.END);

        setLeftIcon(mLeftIcon, mLeftIconWidth, mLeftIconHeight);

        LayoutParams p1 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        p1.leftMargin = mPadding;
        p1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        p1.addRule(RelativeLayout.CENTER_VERTICAL);
        p1.addRule(LEFT_OF, R.id.setting_item_switch);
        addView(mTextView, p1);

        // ��ʼ������
        mSwitch = new Switch(getContext());
        mSwitch.setChecked(mChecked);
        mSwitch.setId(R.id.setting_item_switch);
        mSwitch.setTextOn("");
        mSwitch.setTextOff("");
        LayoutParams p2 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        p2.rightMargin = mPadding;
        p2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        p2.addRule(RelativeLayout.CENTER_VERTICAL);

        addView(mSwitch, p2);

        if (mBgType != BG_TYPE_NONE) {
            setBackgroundDrawable(getBgDrawable(mBgType));
        }
    }
}