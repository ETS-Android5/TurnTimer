package com.turntimer;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Objects;
import com.turntimer.layouts.MainLayout;
import com.turntimer.layouts.timers.TimerParentLayout;

public class MainActivity extends AppCompatActivity
{
    private MainLayout mainLayout;
    private static MainActivity m;
    public static DisplayMetricsController displayMetricsController;
    
    public MainActivity()
    {
        m = this;
    }
    
    public static MainActivity getInstance()
    {
        return m;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(this.getSupportActionBar()).hide();
        
        Resources resources = getResources();
        WindowManager windowManager = getWindowManager();
        displayMetricsController = new DisplayMetricsController(windowManager.getDefaultDisplay(), resources.getDisplayMetrics().density);
        
        mainLayout = new MainLayout(this);
        setContentView(mainLayout);
    }
    
    @Override
    protected void onPause()
    {
        super.onPause();
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        
        TimerParentLayout timerParentLayout = getLayout().getTimerParentLayout();
        if (timerParentLayout.getTimerMode() == TimerParentLayout.TimerMode.Countdown)
        {
            editor.putBoolean("countDownMode", true);
        }
        else
        {
            editor.putBoolean("countDownMode", false);
        }
        
        editor.apply();
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        
        TimerParentLayout timerParentLayout = getLayout().getTimerParentLayout();
        if (preferences.getBoolean("countDownMode", false))
        {
            timerParentLayout.changeTimerMode(TimerParentLayout.TimerMode.Countdown);
        }
        else
        {
            timerParentLayout.changeTimerMode(TimerParentLayout.TimerMode.Stopwatch);
        }
    }
    
    public MainLayout getLayout()
    {
        return mainLayout;
    }
}
