package com.wenjiehe.gank.contract;

import com.wenjiehe.gank.base.BaseFragment;
import com.wenjiehe.gank.base.BasePresenter;
import com.wenjiehe.gank.fragment.AboutFragment;
import com.wenjiehe.gank.model.AboutModel;

public interface AboutContract {
    abstract class Presenter extends BasePresenter<AboutFragment,AboutModel> {
        public abstract void email();
        public abstract void github();
    }
    abstract class View extends BaseFragment<Presenter> {

    }
}
