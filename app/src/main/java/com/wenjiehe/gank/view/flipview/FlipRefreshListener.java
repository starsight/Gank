package com.wenjiehe.gank.view.flipview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class FlipRefreshListener extends RecyclerView.OnScrollListener {
    private Listener mListener;

    public FlipRefreshListener(@NonNull Listener listener) {
        mListener = listener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager != null && layoutManager instanceof FlipLayoutManager) {
            FlipLayoutManager flipLayoutManager = (FlipLayoutManager) layoutManager;
            if (flipLayoutManager.onRefreshPage()) {
                float percent = flipLayoutManager.getRefreshPercent();
                // 450*0.7 =315
                boolean shouldRefresh = percent > 0.7f;
                mListener.onDrag(percent, shouldRefresh);
            } else if (flipLayoutManager.shouldLoadMore()){
                mListener.onLoadMore();
            }
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        Log.d("hewenjie", "onScrollStateChanged:2 ");
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager != null && layoutManager instanceof FlipLayoutManager) {
            FlipLayoutManager flipLayoutManager = (FlipLayoutManager) layoutManager;
            if (!flipLayoutManager.onRefreshPage()) {
                return;
            }

            float percent = flipLayoutManager.getRefreshPercent();
            boolean shouldRefresh = percent > 0.7f;
            if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
                //惯性滑动
                if (shouldRefresh) {
                    mListener.onRefresh();
                }
            }
        }
    }

    public interface Listener {
        void onRefresh();
        void onDrag(float percent, boolean shouldRefresh);
        void onLoadMore();
    }
}
