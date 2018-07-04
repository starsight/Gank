package com.wenjiehe.gank.mvp;

public  interface IPresenter<V extends IView> {
    void attachView(V view);
    void detachView();
    IView getIView();
}
