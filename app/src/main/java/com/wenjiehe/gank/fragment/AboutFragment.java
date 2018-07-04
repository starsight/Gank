package com.wenjiehe.gank.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wenjiehe.gank.R;
import com.wenjiehe.gank.contract.AboutContract;
import com.wenjiehe.gank.presenter.AboutPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AboutFragment extends  AboutContract.View{
    private static final String TAG = "AboutFragment";

    @BindView(R.id.image_github)
    ImageView github;
    @BindView(R.id.image_write)
    ImageView write;
    private Unbinder unbinder;
    private AboutContract.Presenter mPresenter;

    public AboutFragment() {
        mPresenter = new AboutPresenter(this);
    }

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        Glide.with(this)
                .load(R.drawable.image_github)
                .centerCrop()
                .into(github);

        Glide.with(this)
                .load(R.drawable.image_write)
                .centerCrop()
                .into(write);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.click_email)
    protected void email() {
        mPresenter.email();
    }

    @OnClick(R.id.click_github)
    protected void github() {
        mPresenter.github();
    }

    @Override
    public void onClick(View v) {

    }
}
