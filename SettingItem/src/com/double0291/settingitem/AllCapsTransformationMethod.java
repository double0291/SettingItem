package com.double0291.settingitem;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import java.util.Locale;

/**
 * Transforms source text into an ALL CAPS string, locale-aware.
 *
 * @hide
 */
public class AllCapsTransformationMethod implements TransformationMethod2 {
    private boolean mEnabled;
    private Locale mLocale;

    public AllCapsTransformationMethod(Context context) {
        mLocale = context.getResources().getConfiguration().locale;
    }

    @Override
    public CharSequence getTransformation(CharSequence source, View view) {
        if (mEnabled) {
            return source != null ? source.toString().toUpperCase(mLocale) : null;
        }
        return source;
    }

    @Override
    public void onFocusChanged(View view, CharSequence sourceText, boolean focused, int direction,
            Rect previouslyFocusedRect) {
    }

    @Override
    public void setLengthChangesAllowed(boolean allowLengthChanges) {
        mEnabled = allowLengthChanges;
    }

}
