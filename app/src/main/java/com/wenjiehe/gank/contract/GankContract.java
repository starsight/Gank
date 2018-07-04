package com.wenjiehe.gank.contract;

import com.wenjiehe.gank.base.BaseFragment;
import com.wenjiehe.gank.model.GankItem;

import java.util.List;

import rx.Observable;
import rx.Subscription;

public interface GankContract {
    interface Prestener{
        Observable<List<GankItem>> loadNew();
        Observable<List<GankItem>> loadMore(String date);
    }

    interface Fragment{

    }
}
