package com.wenjiehe.gank.presenter;


import android.view.View;

import com.wenjiehe.gank.base.BasePresenter;
import com.wenjiehe.gank.fragment.GankFragment;
import com.wenjiehe.gank.model.GankItem;
import com.wenjiehe.gank.model.GankModel;
import com.wenjiehe.gank.mvp.IView;

import java.util.List;

import rx.Observable;

public class GankPresenter extends BasePresenter<GankFragment,GankModel>  {

    GankModel mDataManager;

    public GankPresenter(GankFragment view){
        iVews = view;
        mDataManager = getiModel();
    }

    @Override
    public GankModel initModel() {
        iModel = GankModel.getInstance(iVews.getContext());
        return iModel;
    }

    public Observable<List<GankItem>> loadNew(){
        return mDataManager.loadNew();
    }

    public Observable<List<GankItem>> loadMore(String date){
        return mDataManager.loadMore(date);
    }


}
