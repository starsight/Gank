package com.wenjiehe.gank.base;

import com.wenjiehe.gank.mvp.IModel;
import com.wenjiehe.gank.mvp.IPresenter;
import com.wenjiehe.gank.mvp.IView;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<V extends IView,M extends IModel> implements IPresenter {
    private WeakReference actReference;
    protected V iVews;
    protected M iModel;

    public M getiModel() {
        //使用前先进行初始化
        iModel = loadModel();
        return iModel;
    }

   @Override
    public void attachView(IView iView) {
        actReference = new WeakReference(iView);
    }

    @Override
    public void detachView() {
        if (actReference != null) {
            actReference.clear();
            actReference = null;
        }
    }

    @Override
    public V getIView() {
        return (V) actReference.get();
    }

    public abstract M loadModel();
}
