package com.wenjiehe.gank.base;

import android.support.v4.app.Fragment;
import android.view.View;

import com.wenjiehe.gank.mvp.IView;

public abstract class BaseFragment <P extends BasePresenter> extends Fragment implements IView,View.OnClickListener {
    protected View view;
    protected P mPresenter;
    public abstract void showInfo(String info);
    public abstract void setLoading(boolean isLoading);
}
