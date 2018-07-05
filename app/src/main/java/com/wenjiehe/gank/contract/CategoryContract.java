package com.wenjiehe.gank.contract;

import com.wenjiehe.gank.base.BaseFragment;
import com.wenjiehe.gank.base.BasePresenter;
import com.wenjiehe.gank.fragment.CategoryFragment;
import com.wenjiehe.gank.model.GankModel;
import com.wenjiehe.gank.model.LoadCategoryCallBack;

public interface CategoryContract {
    abstract class Prestener extends BasePresenter<CategoryFragment, GankModel> {
        public abstract void loadCategory(LoadCategoryCallBack loadCategoryCallBack);
    }

    abstract class View extends BaseFragment<Prestener> {

    }
}
