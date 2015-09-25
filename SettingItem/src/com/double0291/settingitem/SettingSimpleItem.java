package com.double0291.settingitem;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingSimpleItem extends RelativeLayout implements SettingItemConstants {
    private CharSequence mLeftText;
    private Drawable mLeftIcon;
    private int mLeftIconWidth;
    private int mLeftIconHeight;

    private CharSequence mRightText;
    private Drawable mRightIcon;
    private int mRightIconWidth;
    private int mRightIconHeight;

    private boolean mShowRedPoint;
    private boolean mShowArrow;
    private Drawable mArrow;

    private TextView mLeftTextView;
    private TextView mRightTextView;

    private int mItemHeight;
    private int mPadding;
    private int mRedPointMargin;
    private int mRedPointWidth;
    private int mBgType;

    /**
     * ��Ҫ�����ұ�textview�Ƿ���ʾicon����������̬����MaxWidth
     */
    private int mRightTextViewMaxWidth;
    private int mRightIconMaxWidth;
    private int mArrowMaxWidth;

    private int mLeftTextViewMaxWidth;
    private int mRightTextLeftPadding;

    public SettingSimpleItem(Context context) {
        this(context, null);
    }

    public SettingSimpleItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPadding = this.getResources().getDimensionPixelSize(R.dimen.setting_item_horizontal_padding);
        int defaultItemHeight = this.getResources().getDimensionPixelSize(R.dimen.setting_item_default_height);

        mRightIconMaxWidth = (int) (getResources().getDisplayMetrics().density * 35 + 0.5);
        mArrowMaxWidth = (int) (getResources().getDisplayMetrics().density * 15 + 0.5);
        mArrow = getResources().getDrawable(R.drawable.arrow_right_normal);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SettingItem);

        // ���û����ʾָ������Item�߶ȣ���ȡĬ�ϸ߶�
        mItemHeight = a.getDimensionPixelSize(R.styleable.SettingItem_customHeight, defaultItemHeight);

        mLeftText = a.getString(R.styleable.SettingItem_leftText);
        mLeftIcon = a.getDrawable(R.styleable.SettingItem_leftIcon);
        mLeftIconWidth = a.getDimensionPixelSize(R.styleable.SettingItem_leftIconWidth, 0);
        mLeftIconHeight = a.getDimensionPixelSize(R.styleable.SettingItem_leftIconHeight, 0);

        mLeftIconHeight = Math.min(mItemHeight, mLeftIconHeight);

        mRightText = a.getString(R.styleable.SettingItem_rightText);
        mRightTextLeftPadding = a.getDimensionPixelSize(R.styleable.SettingItem_rightTextLeftPadding, 0);
        mRightIcon = a.getDrawable(R.styleable.SettingItem_rightIcon);
        mRightIconWidth = a.getDimensionPixelSize(R.styleable.SettingItem_rightIconWidth, 0);
        mRightIconHeight = a.getDimensionPixelSize(R.styleable.SettingItem_rightIconHeight, 0);

        mRightIconHeight = Math.min(mItemHeight, mRightIconHeight);

        mShowArrow = a.getBoolean(R.styleable.SettingItem_showArrow, true);

        mBgType = a.getInt(R.styleable.SettingItem_bgType, BG_TYPE_NONE);

        a.recycle();

        initViews();
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(mItemHeight, MeasureSpec.EXACTLY));
        setMeasuredDimension(getMeasuredWidth(), mItemHeight);
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
     * @param bgType
     * @return
     */
    public Drawable getSelectorByType(int bgType) {
        switch (bgType) {
        case BG_TYPE_SINGLE:
            return getResources().getDrawable(R.drawable.common_strip_setting_bg);
        case BG_TYPE_TOP:
            return getResources().getDrawable(R.drawable.common_strip_setting_top);
        case BG_TYPE_MIDDLE:
            return getResources().getDrawable(R.drawable.common_strip_setting_middle);
        case BG_TYPE_BOTTOM:
            return getResources().getDrawable(R.drawable.common_strip_setting_bottom);
        default:
            return getResources().getDrawable(R.drawable.common_strip_setting_bg);
        }
    }

    public int getBackgroundResId(int bgType) {
        switch (bgType) {
        case BG_TYPE_SINGLE:
            return R.drawable.common_strip_setting_bg;
        case BG_TYPE_TOP:
            return R.drawable.common_strip_setting_top;
        case BG_TYPE_MIDDLE:
            return R.drawable.common_strip_setting_middle;
        case BG_TYPE_BOTTOM:
            return R.drawable.common_strip_setting_bottom;
        default:
            return R.drawable.common_strip_setting_bg;
        }
    }

    /**
     * ���ñ�����������
     * 
     * @param text
     */
    public void setLeftText(CharSequence text) {
        if (!TextUtils.isEmpty(text) && mLeftTextView != null) {
            mLeftText = text;
            mLeftTextView.setText(mLeftText);
            mLeftTextView.setTextColor(getResources().getColor(R.color.black));
            mLeftTextView.setContentDescription(mLeftText);
        }
    }

    /**
     * ���ñ�������icon
     * 
     * @param drawable
     *            drawable ���Ϊnull,�����ԭ����icon
     */
    public void setLeftIcon(Drawable drawable) {
        if (mLeftTextView != null) {
            mLeftIcon = drawable;
            if (drawable == null) {
                mLeftTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            } else {
                final int height = drawable.getIntrinsicHeight();

                if (height > mItemHeight) {
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), mItemHeight);
                    mLeftTextView.setCompoundDrawables(drawable, null, null, null);
                } else {
                    mLeftTextView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                }

                mLeftTextView.setCompoundDrawablePadding(mPadding);
            }
        }
    }

    /**
     * ���ñ�������icon���ֶ�ָ��icon�Ŀ��ߣ����iconWidth����iconHeightΪ0������ʾͼƬ��ԭ����
     * 
     * @param drawable
     * @param iconWidth
     * @param iconHeight
     */
    public void setLeftIcon(Drawable drawable, int iconWidth, int iconHeight) {
        if (drawable == null || mLeftTextView == null || iconWidth < 0 || iconHeight < 0) {
            return;
        }

        if (iconWidth > 0 && iconHeight > 0) {
            mLeftIcon = drawable;
            mLeftIconWidth = iconWidth;
            mLeftIconHeight = Math.min(mItemHeight, iconHeight);

            drawable.setBounds(0, 0, iconWidth, mLeftIconHeight);
            mLeftTextView.setCompoundDrawables(drawable, null, null, null);
            mLeftTextView.setCompoundDrawablePadding(mPadding);
        } else if (iconWidth == 0 || iconHeight == 0) {
            setLeftIcon(drawable);
        }
    }

    /**
     * ���ñ����Ҳ������
     * 
     * @param text
     */
    public void setRightText(CharSequence text) {
        mRightText = text;
        updateRightViews();
    }

    /**
     * ���ñ�������icon
     * 
     * @param drawable
     */
    public void setRightIcon(Drawable drawable) {
        setRightIcon(drawable, 0, 0);
    }

    /**
     * ���ñ����Ҳ��icon���ֶ�ָ��icon�Ŀ��� ���iconWidth��iconHeightΪ0������drawable
     * 
     * @param drawable
     * @param iconWidth
     * @param iconHeight
     */
    public void setRightIcon(Drawable drawable, int iconWidth, int iconHeight) {
        if (iconWidth < 0 || iconHeight < 0) {
            return;
        }
        mRightIconWidth = iconWidth;
        mRightIconHeight = Math.min(mItemHeight, iconHeight);

        mRightIcon = drawable;
        updateRightViews();
    }

    /**
     * ���ú������
     *
     * @param type
     *            ȡֵΪ {@link #RED_POINT_TYPE_NONE}, {@link #RED_POINT_TYPE_DOT}, {@link #RED_POINT_TYPE_NEW}
     */
    public void setRedPointType(int type) {
        switch (type) {
        case RED_POINT_TYPE_DOT: {
            // ���
            ImageView redPointImage = new ImageView(getContext());
            redPointImage.setId(R.id.setting_item_red_point);
            redPointImage.setBackgroundResource(R.drawable.tips_dot);
            mRedPointMargin = this.getResources().getDimensionPixelSize(R.dimen.setting_item_red_point_dot_margin);
            mRedPointWidth = this.getResources().getDimensionPixelSize(R.dimen.setting_item_red_point_dot_width);

            LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            lp.rightMargin = mRedPointMargin;
            lp.addRule(RelativeLayout.LEFT_OF, R.id.setting_item_right_textview);
            lp.addRule(RelativeLayout.CENTER_VERTICAL);
            addView(redPointImage, lp);

            // ��Ҫ���¼�������TextView����󳤶�
            mShowRedPoint = true;
            calucateTextMaxWidth();
            mLeftTextView.setMaxWidth(mLeftTextViewMaxWidth);
            mRightTextView.setMaxWidth(mRightTextViewMaxWidth);
            break;
        }
        case RED_POINT_TYPE_NEW: {
            // NEWͼ��
            ImageView redPointImage = new ImageView(getContext());
            redPointImage.setId(R.id.setting_item_red_point);
            redPointImage.setBackgroundResource(R.drawable.tips_new);
            mRedPointMargin = this.getResources().getDimensionPixelSize(R.dimen.setting_item_red_point_new_margin);
            mRedPointWidth = this.getResources().getDimensionPixelSize(R.dimen.setting_item_red_point_new_width);

            LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            lp.rightMargin = mRedPointMargin;
            lp.addRule(RelativeLayout.LEFT_OF, R.id.setting_item_right_textview);
            lp.addRule(RelativeLayout.CENTER_VERTICAL);
            addView(redPointImage, lp);

            // ��Ҫ���¼�������TextView����󳤶�
            mShowRedPoint = true;
            calucateTextMaxWidth();
            mLeftTextView.setMaxWidth(mLeftTextViewMaxWidth);
            mRightTextView.setMaxWidth(mRightTextViewMaxWidth);
            break;
        }
        case RED_POINT_TYPE_NONE: {
            if (!mShowRedPoint) {
                return;
            }

            ImageView redPointImage = (ImageView) findViewById(R.id.setting_item_red_point);
            if (redPointImage != null) {
                redPointImage.setVisibility(View.GONE);
            }

            // ��Ҫ���¼�������TextView����󳤶�
            mShowRedPoint = false;
            calucateTextMaxWidth();
            mLeftTextView.setMaxWidth(mLeftTextViewMaxWidth);
            mRightTextView.setMaxWidth(mRightTextViewMaxWidth);
        }
        default:
            break;
        }
    }

    public void clearRedPoint() {
        setRedPointType(RED_POINT_TYPE_NONE);
    }

    /**
     * �Ƿ���ʾ���ҵļ�ͷ
     * 
     * @param show
     *            true��ʾ��false����ʾ
     */
    public void showArrow(boolean show) {
        mShowArrow = show;
        updateRightViews();
    }

    /**
     * �������ҵļ�ͷicon.
     * 
     * @param drawable
     */
    public void setArrowIcon(Drawable drawable) {
        mArrow = drawable;
    }

    /**
     * ���ñ�����selector����
     * 
     * @param bgType
     *            ȡֵΪ {@link #BG_TYPE_SINGLE}, {@link #BG_TYPE_TOP}, {@link #BG_TYPE_MIDDLE}, or {@link #BG_TYPE_BOTTOM}
     */
    public void setBgType(int bgType) {
        if (bgType != BG_TYPE_SINGLE && bgType != BG_TYPE_TOP && bgType != BG_TYPE_MIDDLE && bgType != BG_TYPE_BOTTOM) {
            throw new RuntimeException("Parameter bgType is illegal!");
        }

        mBgType = bgType;
        int selectorResId = getBackgroundResId(mBgType);
        setBackgroundResource(selectorResId);
    }

    protected void initViews() {
        // �����õĻ������ϰ����ʶ�˳������
        setFocusable(true);

        // �����õĻ���selector��������
        setClickable(true);

        // ������ߵ�Textview
        mLeftTextView = new TextView(getContext());

        if (!TextUtils.isEmpty(mLeftText)) {
            mLeftTextView.setText(mLeftText);
            mLeftTextView.setContentDescription(mLeftText);
        }

        // ����id��˫����ʾ��Ҫ��������λ��
        mLeftTextView.setId(R.id.setting_item_left_textview);
        mLeftTextView.setSingleLine(true);
        mLeftTextView.setTextColor(getResources().getColor(R.color.black));
        mLeftTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        mLeftTextView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        mLeftTextView.setEllipsize(TextUtils.TruncateAt.END);
        mLeftTextView.setDuplicateParentStateEnabled(true);

        setLeftIcon(mLeftIcon, mLeftIconWidth, mLeftIconHeight);

        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.leftMargin = mPadding;
        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        lp.addRule(RelativeLayout.CENTER_VERTICAL);
        mLeftTextView.setMaxWidth(mLeftTextViewMaxWidth);
        addView(mLeftTextView, lp);

        // �����ұߵ�Textview
        mRightTextView = new TextView(getContext());
        mRightTextView.setId(R.id.setting_item_right_textview);
        mRightTextView.setSingleLine(true);
        mRightTextView.setTextColor(getResources().getColor(R.color.setting_gray_color));
        mRightTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        mRightTextView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        mRightTextView.setEllipsize(TextUtils.TruncateAt.END);
        mRightTextView.setDuplicateParentStateEnabled(true);

        LayoutParams rp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        rp.rightMargin = mPadding;
        rp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rp.addRule(RelativeLayout.CENTER_VERTICAL);
        addView(mRightTextView, rp);

        updateRightViews();

        // ����
        if (mBgType != BG_TYPE_NONE) {
            int selectorResId = getBackgroundResId(mBgType);
            setBackgroundResource(selectorResId);
        }
    }

    private void calucateTextMaxWidth() {
        int contentWidth = getResources().getDisplayMetrics().widthPixels - mPadding * 2;

        if (mRightText == null) {

            int rightMaxWidth = 0;

            if (mRightIcon != null && mShowArrow) {
                rightMaxWidth = mRightIconMaxWidth + mPadding + mArrowMaxWidth;
            } else if (mRightIcon != null && !mShowArrow) {
                rightMaxWidth = mRightIconMaxWidth;
            } else if (mRightIcon == null && mShowArrow) {
                rightMaxWidth = mArrowMaxWidth;
            } else {
                rightMaxWidth = 0;
            }

            if (mShowRedPoint) {
                rightMaxWidth += mRedPointMargin + mRedPointWidth;
            }

            mLeftTextViewMaxWidth = contentWidth - rightMaxWidth;
            mRightTextViewMaxWidth = 0;
        } else {
            // 1����Ҫ�ȿ۳�����textview���padding
            contentWidth -= mPadding;
            // �����ʾ�˺�㣬��Ҫ��ȥ����padding
            if (mShowRedPoint) {
                contentWidth -= mRedPointMargin + mRedPointWidth;
            }

            // 2���ȼ������textview����ȫ����ʾ�Ŀ���
            int leftTextViewWidth = 0;
            int leftIconWidth = 0;
            int leftTextWidth = 0;

            if (mLeftIcon != null) {
                leftIconWidth = mLeftIconWidth == 0 ? mLeftIcon.getIntrinsicWidth() : mLeftIconWidth;
                leftIconWidth += mPadding;// ��Ҫ����drawable padding
            }

            Paint paint = mLeftTextView.getPaint();
            leftTextWidth = (int) paint.measureText(mLeftText.toString());
            leftTextViewWidth = leftIconWidth + leftTextWidth;

            // 3���ټ����ұ�textview����ȫ����ʾ�Ŀ���
            int rightTextViewWidth = 0;
            int rightIconWidth = 0;// ����icon�ͼ�ͷ
            int rightTextWidth = 0;

            if (mRightIcon != null) {
                rightIconWidth += mRightIconWidth == 0 ? mRightIcon.getIntrinsicWidth() : mRightIconWidth;
                rightIconWidth += mPadding;// ��Ҫ����drawable padding
            }

            if (mShowArrow) {
                rightIconWidth += mArrow.getIntrinsicWidth();
                rightIconWidth += mPadding;// ��Ҫ����drawable padding
            }

            paint = mRightTextView.getPaint();
            rightTextWidth = (int) paint.measureText(mRightText.toString());
            rightTextViewWidth = rightIconWidth + rightTextWidth + mRightTextView.getPaddingLeft()
                    + mRightTextView.getPaddingRight();

            int halfContentWidth = contentWidth / 2;
            // 4���������textview��ʾ���ݶ�����һ�룬����֣�����������ʾ����С��textview�����ȳ���textviewռ��ʣ��Ŀռ�
            if (leftTextViewWidth >= halfContentWidth && rightTextViewWidth >= halfContentWidth) {
                leftTextViewWidth = rightTextViewWidth = halfContentWidth;
            } else if (leftTextViewWidth > halfContentWidth && rightTextViewWidth < halfContentWidth) {
                leftTextViewWidth = contentWidth - rightTextViewWidth;
            } else if (leftTextViewWidth < halfContentWidth && rightTextViewWidth > halfContentWidth) {
                rightTextViewWidth = contentWidth - leftTextViewWidth;
            }

            mLeftTextViewMaxWidth = leftTextViewWidth;
            mRightTextViewMaxWidth = rightTextViewWidth;
        }
    }

    private void updateRightViews() {
        if (mRightTextView == null) {
            return;
        }

        calucateTextMaxWidth();
        if (mLeftTextView != null) {
            mLeftTextView.setMaxWidth(mLeftTextViewMaxWidth);
        }

        if (!TextUtils.isEmpty(mRightText)) {
            mRightTextView.setVisibility(View.VISIBLE);
            mRightTextView.setText(mRightText);
            mRightTextView.setTextColor(getResources().getColor(R.color.setting_gray_color));
            mRightTextView.setContentDescription(mRightText);
            mRightTextView.setMaxWidth(mRightTextViewMaxWidth);

            if (mRightIcon != null && mShowArrow) {
                if (mRightIconWidth > 0 && mRightIconHeight > 0) {
                    mArrow.setBounds(0, 0, mArrow.getIntrinsicWidth(), mArrow.getIntrinsicHeight());
                    mRightIcon.setBounds(0, 0, mRightIconWidth, mRightIconHeight);
                    mRightTextView.setCompoundDrawables(mRightIcon, null, mArrow, null);
                } else {
                    mRightTextView.setCompoundDrawablesWithIntrinsicBounds(mRightIcon, null, mArrow, null);
                }

                mRightTextView.setCompoundDrawablePadding(mRightTextLeftPadding);
            } else if (mRightIcon != null && !mShowArrow) {
                if (mRightIconWidth > 0 && mRightIconHeight > 0) {
                    mRightIcon.setBounds(0, 0, mRightIconWidth, mRightIconHeight);
                    mRightTextView.setCompoundDrawables(mRightIcon, null, null, null);
                } else {
                    mRightTextView.setCompoundDrawablesWithIntrinsicBounds(mRightIcon, null, null, null);
                }

                mRightTextView.setCompoundDrawablePadding(mRightTextLeftPadding);
            } else if (mRightIcon == null && mShowArrow) {
                mRightTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, mArrow, null);
                mRightTextView.setCompoundDrawablePadding(mPadding);
            } else if (mRightIcon == null && !mShowArrow) {
                mRightTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                mRightTextView.setCompoundDrawablePadding(0);
            }

        } else {
            // �������
            mRightTextView.setText("");

            if (mRightIcon != null && mShowArrow) {
                mRightTextView.setVisibility(View.VISIBLE);

                if (mRightIconWidth > 0 && mRightIconHeight > 0) {
                    mRightIcon.setBounds(0, 0, mRightIconWidth, mRightIconHeight);
                    mArrow.setBounds(0, 0, mArrow.getIntrinsicWidth(), mArrow.getIntrinsicHeight());

                    mRightTextView.setCompoundDrawables(mRightIcon, null, mArrow, null);
                } else {
                    mRightTextView.setCompoundDrawablesWithIntrinsicBounds(mRightIcon, null, mArrow, null);
                }
                mRightTextView.setCompoundDrawablePadding(mPadding / 2);
            } else if (mRightIcon != null && !mShowArrow) {
                mRightTextView.setVisibility(View.VISIBLE);

                if (mRightIconWidth > 0 && mRightIconHeight > 0) {
                    mRightIcon.setBounds(0, 0, mRightIconWidth, mRightIconHeight);
                    mRightTextView.setCompoundDrawables(mRightIcon, null, null, null);
                } else {
                    mRightTextView.setCompoundDrawablesWithIntrinsicBounds(mRightIcon, null, null, null);
                }
                mRightTextView.setCompoundDrawablePadding(0);
            } else if (mRightIcon == null && mShowArrow) {
                mRightTextView.setVisibility(View.VISIBLE);
                mRightTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, mArrow, null);
                mRightTextView.setCompoundDrawablePadding(0);
            } else if (mRightIcon == null && !mShowArrow) {
                mRightTextView.setVisibility(View.GONE);
            }
        }
    }
}