package com.wenjiehe.gank.model;

import java.util.List;

public interface LoadCategoryCallBack {
    void loadOnNext(List<String> str);
    void loadOnError(Throwable throwable);
    void loadOnCompleted();
}
