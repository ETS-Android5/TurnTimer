package com.turntimer.layouts.settings;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.widget.TextView;
import com.turntimer.layouts.BaseLayout;

public class Setting extends BaseLayout
{
    private TextView settingText;
    private View[] controlElements;
    private Rect tempChildRect = new Rect();
    
    public Setting(Context context)
    {
        super(context);
        init("Setting");
    }
    
    public Setting(Context context, String textToDisplay, View... controlElements)
    {
        super(context);
        init(textToDisplay, controlElements);
    }
    
    private void init(String textToDisplay, View... controlElements)
    {
        this.settingText = new TextView(context);
        this.settingText.setText(textToDisplay);
        this.settingText.setTextSize(20);
        this.addView(settingText);
        
        this.controlElements = controlElements;
        for (View v : this.controlElements)
        {
            this.addView(v);
        }
    }
    
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        top = 0;
        int currentLeft = left;
        int childrenMaxHeight = 0;
        for (int i = 0; i < getChildCount(); i++)
        {
            if (getChildAt(i).getMeasuredHeight() > childrenMaxHeight)
            {
                childrenMaxHeight = getChildAt(i).getMeasuredHeight();
            }
        }
        
        for (int i = 0; i < getChildCount(); i++)
        {
            int childHeight = getChildAt(i).getMeasuredHeight();
            int childWidth = getChildAt(i).getMeasuredWidth();
            
            tempChildRect.left = currentLeft;
            tempChildRect.top = top + childrenMaxHeight / 2 - childHeight / 2;
            tempChildRect.right = tempChildRect.left + childWidth;
            tempChildRect.bottom = tempChildRect.top + childHeight;
            currentLeft = tempChildRect.right;
            
            getChildAt(i).layout(tempChildRect.left, tempChildRect.top, tempChildRect.right, tempChildRect.bottom);
        }
    }
}