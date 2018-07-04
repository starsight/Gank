package com.wenjiehe.gank.presenter;


import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.wenjiehe.gank.base.BasePresenter;
import com.wenjiehe.gank.contract.GankContract;
import com.wenjiehe.gank.fragment.GankFragment;
import com.wenjiehe.gank.model.GankItem;
import com.wenjiehe.gank.model.GankModel;
import com.wenjiehe.gank.model.LoadGankItemCallBack;
import com.wenjiehe.gank.mvp.IView;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class GankPresenter extends GankContract.Prestener {

    GankModel mDataManager;
    CompositeSubscription mSubscription;

    public GankPresenter(GankFragment view){
        iVews = view;
        mDataManager = getiModel();
        mSubscription = new CompositeSubscription();
    }

    @Override
    public GankModel initModel() {
        iModel = GankModel.getInstance(iVews.getContext());
        return iModel;
    }

    @Override
    public void loadNew(final LoadGankItemCallBack loadGankItemCallBack){
        //延时一下，效果看起来更明显
         Subscription subscription = mDataManager.loadNew()
                        .delay(1500, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<List<GankItem>>() {
                            @Override
                            public void onCompleted() {
                                loadGankItemCallBack.loadOnCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                loadGankItemCallBack.loadOnError(e);
                            }

                            @Override
                            public void onNext(List<GankItem> gankItems) {
                                loadGankItemCallBack.loadOnNext(gankItems);
                            }
                        });
         mSubscription.add(subscription);
    }


    @Override
    public void loadMore(String date, final LoadGankItemCallBack loadGankItemCallBack){
        Subscription subscription = mDataManager.loadMore(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<GankItem>>() {
                    @Override
                    public void onCompleted() {
                        loadGankItemCallBack.loadOnCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadGankItemCallBack.loadOnError(e);
                    }

                    @Override
                    public void onNext(List<GankItem> gankItems) {
                        loadGankItemCallBack.loadOnNext(gankItems);
                    }
                });
        mSubscription.add(subscription);
    }

    public void unsbscribe(){
        if(mSubscription!=null&&mSubscription.hasSubscriptions()){
            mSubscription.unsubscribe();
        }
    }


}
