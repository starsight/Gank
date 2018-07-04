package com.wenjiehe.gank.contract;

import com.wenjiehe.gank.base.BaseFragment;
import com.wenjiehe.gank.base.BasePresenter;
import com.wenjiehe.gank.fragment.GankFragment;
import com.wenjiehe.gank.model.GankModel;
import com.wenjiehe.gank.model.LoadGankItemCallBack;

public interface GankContract {
    abstract class Prestener extends BasePresenter<GankFragment,GankModel>{
        public abstract void loadNew(LoadGankItemCallBack loadGankItemCallBack);
        public abstract void loadMore(String date,LoadGankItemCallBack loadGankItemCallBack);
    }

    abstract class View extends BaseFragment<Prestener> {

    }
}
