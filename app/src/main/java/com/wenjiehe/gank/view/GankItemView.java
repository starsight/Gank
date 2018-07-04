package com.wenjiehe.gank.view;

import com.wenjiehe.gank.model.GankItem;

public interface GankItemView {
    void bind(GankItem gankItem, Listener listener);

    interface Listener {
        void open(GankItem gankItem);
        void showBottomSheet(GankItem gankItem);
        void like(GankItem gankItem);
    }
}
