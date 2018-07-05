package com.wenjiehe.gank.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wenjiehe.gank.R;
import com.wenjiehe.gank.adapter.GankAdapter;
import com.wenjiehe.gank.activity.BaseActivity;
import com.wenjiehe.gank.contract.GankContract;
import com.wenjiehe.gank.model.GankItem;
import com.wenjiehe.gank.model.LoadGankItemCallBack;
import com.wenjiehe.gank.presenter.GankPresenter;
import com.wenjiehe.gank.view.flipview.FlipLayoutManager;
import com.wenjiehe.gank.view.flipview.FlipRefreshListener;
import com.wenjiehe.gank.view.flipview.MySnap;

import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class GankFragment extends GankContract.View implements FlipRefreshListener.Listener, GankAdapter.Listener {
    private static final String TAG = "GankFragment";

    private static final String ARG_TYPE = "type";

    private String mType;
    private Unbinder unbinder;
    private GankAdapter mAdapter;
    private String mLastLoad;
    private String mLatest;
    //private DataManager mDataManager;
    //private Subscription mSubscription;
    private BaseActivity baseActivity;
    private boolean mIsLoading;
    private FlipRefreshListener mFlipListener;
    private boolean mHasMore = true;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_hint)
    TextView refreshHint;
    @BindView(R.id.refresh_icon)
    ImageView refreshIcon;

    public GankFragment() {
    }

    public static GankFragment newInstance(String type) {
        GankFragment fragment = new GankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getString(ARG_TYPE);
        }

        mPresenter = new GankPresenter(this);
        //mPresenter.attachView(this);
        //mDataManager = DataManager.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gank, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        if (mAdapter == null) {
            mAdapter = new GankAdapter(getContext(), this);
        }

        recyclerView.setAdapter(mAdapter);
        FlipLayoutManager layoutManager = new FlipLayoutManager(getContext());

        recyclerView.setItemAnimator(null);
        recyclerView.setLayoutManager(layoutManager);
        MySnap snap = new MySnap();
        snap.attachToRecyclerView(recyclerView);
        if (mFlipListener == null) {
            mFlipListener = new FlipRefreshListener(this);
        }
        recyclerView.addOnScrollListener(mFlipListener);
        recyclerView.addOnChildAttachStateChangeListener(mChildAttachStateChangeListener);
        if (mAdapter.getDataCount() == 0) {
            if (mType == null) {
                loadNew();
            } else {
                loadMore();
            }
        } else {
            mFlipListener.onScrolled(recyclerView, 0, 0);
        }
    }

    private RecyclerView.OnChildAttachStateChangeListener mChildAttachStateChangeListener = new RecyclerView.OnChildAttachStateChangeListener() {
        //第一次进入界面的监听，可以用来实现首次播放的逻辑
        @Override
        public void onChildViewAttachedToWindow(View view) {
            Log.d(TAG, "onChildViewAttachedToWindow: ");
        }

        // 可以释放资源的监听，也就是回收Item的时候
        @Override
        public void onChildViewDetachedFromWindow(View view) {

        }
    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
//        if (mSubscription != null) {
//            mSubscription.unsubscribe();
//        }
        ((GankPresenter)mPresenter).unsbscribe();

        mIsLoading = false;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            baseActivity = (BaseActivity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        baseActivity = null;
    }

    private void loadMore() {
//        if (mSubscription != null) {
//            mSubscription.unsubscribe();
//        }

        mIsLoading = true;

        mPresenter.loadMore(mLastLoad,loadMoreGankItemCallBack);
    }

    private void loadNew() {
        Log.d(TAG, "loadNew: ");

//        if (mSubscription != null) {
//            mSubscription.unsubscribe();
//        }
        mIsLoading = true;
        mPresenter.loadNew(loadNewGankItemCallBack);
    }

    private LoadGankItemCallBack loadNewGankItemCallBack  = new LoadGankItemCallBack() {
        @Override
        public void loadOnNext(List<GankItem> gankItemList) {
            mIsLoading = false;

            if (gankItemList.size() > 0) {
                mHasMore = true;
                final String latest = gankItemList.get(0).day;
                if (!latest.equals(mLatest)) {
                    mLatest = latest;
                    mLastLoad = latest;
                    mAdapter.clear();
                    if (mType != null) {
                        Iterator<GankItem> itemIterator = gankItemList.listIterator();
                        while (itemIterator.hasNext()) {
                            if (!itemIterator.next().type.equals(mType)) {
                                itemIterator.remove();
                            }
                        }
                    }
                    mAdapter.addData(gankItemList);
                    recyclerView.scrollToPosition(0);

                    if (baseActivity != null) {
                        baseActivity.setLoading(false);
                    }
                } else if (baseActivity != null) {
                    baseActivity.showInfo("已经是最新内容");
                }
            }
            mFlipListener.onScrolled(recyclerView, 0, 0);
        }

        @Override
        public void loadOnError(Throwable throwable) {
            mIsLoading = false;

            Log.e(TAG, "onError", throwable);
            if (baseActivity != null) {
                baseActivity.showInfo("加载失败");
            } else {
                Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void loadOnCompleted() {

        }
    };

    private LoadGankItemCallBack loadMoreGankItemCallBack  = new LoadGankItemCallBack() {
        @Override
        public void loadOnNext(List<GankItem> gankItemList) {
            mIsLoading = false;

            if (gankItemList.size() > 0) {
                mLastLoad = gankItemList.get(0).day;
                Log.d(TAG, "onMore: " + mLastLoad + new Gson().toJson(gankItemList.get(0)));
                if (mType != null && !mType.equals("like")) {
                    Iterator<GankItem> itemIterator = gankItemList.listIterator();
                    while (itemIterator.hasNext()) {
                        if (!itemIterator.next().type.equals(mType)) {
                            itemIterator.remove();
                        }
                    }
                }

                if (mType != null && mType.equals("like")) {
                    Iterator<GankItem> itemIterator = gankItemList.listIterator();
                    while (itemIterator.hasNext()) {
                        if (!itemIterator.next().like) {
                            itemIterator.remove();
                        }
                    }
                }

                mAdapter.addData(gankItemList);

                if (mLatest == null) {
                    mLatest = mLastLoad;
                }
            } else {
                mHasMore = false;
                mAdapter.setHasMore(mHasMore);
            }

            mAdapter.setHasMore(mHasMore);
            mFlipListener.onScrolled(recyclerView, 0, 0);
        }

        @Override
        public void loadOnError(Throwable throwable) {

        }

        @Override
        public void loadOnCompleted() {

        }
    };

    @Override
    public void onRefresh() {
        loadNew();
        if (baseActivity != null) {
            baseActivity.setLoading(true);
        }
    }

    @Override
    public void onDrag(float percent, boolean shouldRefresh) {
        if (refreshHint != null && getView() != null) {
            if (shouldRefresh) {
                refreshHint.setText("松开可以刷新");
            } else {
                refreshHint.setText("下拉刷新页面");
            }

            View view = getView();
            int from = 0x00;
            int to = 0x30;
            int now = (from + (int) ((to - from) * percent));
            int color = 0xFF << 24 | now << 16 | now << 8 | now;
            view.setBackgroundColor(color);
            refreshIcon.setRotation(percent * 360);
        }
    }

    @Override
    public void onLoadMore() {
        if (!mIsLoading && mHasMore) {
            loadMore();
        }
    }

    @Override
    public void showInfo(String info) {
        if (baseActivity != null) {
            baseActivity.showInfo(info);
        }
    }

    @Override
    public void onClick(View v) {

    }
}

