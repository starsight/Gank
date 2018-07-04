package com.wenjiehe.gank.base;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wenjiehe.gank.mvp.IView;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements IView,View.OnClickListener {
    public abstract void showInfo(String info);
    public abstract void setLoading(boolean isLoading);
}
