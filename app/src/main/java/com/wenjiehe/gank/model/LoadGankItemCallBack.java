package com.wenjiehe.gank.model;

import java.util.List;

public interface LoadGankItemCallBack {
     void loadOnNext(List<GankItem> gankItemList);
     void loadOnError(Throwable throwable);
     void loadOnCompleted();
}
