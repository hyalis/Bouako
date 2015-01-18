package com.m2dl.helloandroid.apnview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Switch;

/**
 * Created by Hyalis on 16/01/2015.
 */
public class CustomSwitch extends Switch {


    public CustomSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void requestLayout() {
        try {
            java.lang.reflect.Field mOnLayout = Switch.class.getDeclaredField ( "mOnLayout");
            mOnLayout.setAccessible (true);
            mOnLayout.set (this, null);
            java.lang.reflect.Field mOffLayout = Switch.class.getDeclaredField ( "mOffLayout");
            mOffLayout.setAccessible (true) ;
            mOffLayout.set (this, null);
        } catch (Exception ex) {
            Log.e("OK", ex.getMessage(), ex);
        }
        super.requestLayout();
    }
}
