package com.wenjiehe.gank.model;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface Api {
    String BASE_URL = "http://gank.io/";

    @GET("api/day/{year}/{month}/{day}")
    Observable<GankResponse> loadData(@Path("year") int year, @Path("month") int month, @Path("day") int day);
}
