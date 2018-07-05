package com.wenjiehe.gank.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wenjiehe.gank.mvp.IView;

public abstract class BaseActivity extends AppCompatActivity {
    public abstract void showInfo(String info);
    public abstract void setLoading(boolean isLoading);
}
