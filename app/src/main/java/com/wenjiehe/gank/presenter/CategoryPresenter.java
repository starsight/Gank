package com.wenjiehe.gank.presenter;



import com.wenjiehe.gank.contract.CategoryContract;
import com.wenjiehe.gank.fragment.CategoryFragment;
import com.wenjiehe.gank.model.GankItem;
import com.wenjiehe.gank.model.GankModel;
import com.wenjiehe.gank.model.LoadCategoryCallBack;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class CategoryPresenter extends CategoryContract.Prestener {
    GankModel mDataManager;
    CompositeSubscription mSubscription;

    public CategoryPresenter(CategoryFragment view) {
        iVews = view;
        mDataManager = getiModel();
        mSubscription = new CompositeSubscription();
    }

    @Override
    public GankModel initModel() {
        iModel = GankModel.getInstance(iVews.getContext());
        return iModel;
    }


    public void unsbscribe() {
        if (mSubscription != null && mSubscription.hasSubscriptions()) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void loadCategory(final LoadCategoryCallBack loadCategoryCallBack) {
        Subscription subscription = mDataManager.loadCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<String>>() {
                               @Override
                               public void onCompleted() {
                                   loadCategoryCallBack.loadOnCompleted();
                               }

                               @Override
                               public void onError(Throwable e) {
                                   loadCategoryCallBack.loadOnError(e);
                               }

                               @Override
                               public void onNext(List<String> str) {
                                   loadCategoryCallBack.loadOnNext(str);
                               }
                           }
                );
        mSubscription.add(subscription);
    }
}
