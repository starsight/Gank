package com.wenjiehe.gank.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wenjiehe.gank.R;
import com.wenjiehe.gank.adapter.CategoryAdapter;
import com.wenjiehe.gank.contract.CategoryContract;
import com.wenjiehe.gank.model.LoadCategoryCallBack;
import com.wenjiehe.gank.presenter.CategoryPresenter;
import com.wenjiehe.gank.view.GridItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class CategoryFragment extends CategoryContract.View {
    private static final String TAG = "CategoryFragment";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private CategoryAdapter mCategoryAdapter;
    private Unbinder unbinder;

    public CategoryFragment() {

    }

    public static CategoryFragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new CategoryPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        if (mCategoryAdapter == null) {
            mCategoryAdapter = new CategoryAdapter();
        }
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(mCategoryAdapter);
        recyclerView.addItemDecoration(new GridItemDecoration(getContext()));

        mPresenter.loadCategory(new LoadCategoryCallBack() {
            @Override
            public void loadOnNext(List<String> strings) {
                mCategoryAdapter.addCategory(strings);
            }

            @Override
            public void loadOnError(Throwable throwable) {
                Log.e(TAG, "call: ", throwable);
            }

            @Override
            public void loadOnCompleted() {

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        ((CategoryPresenter)mPresenter).unsbscribe();
    }

    @Override
    public void onClick(View v) {

    }
}
