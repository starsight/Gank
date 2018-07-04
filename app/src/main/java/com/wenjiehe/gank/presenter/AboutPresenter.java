package com.wenjiehe.gank.presenter;

import android.content.Intent;
import android.net.Uri;

import com.wenjiehe.gank.contract.AboutContract;
import com.wenjiehe.gank.fragment.AboutFragment;
import com.wenjiehe.gank.model.AboutModel;

public class AboutPresenter extends AboutContract.Presenter {
    AboutContract.View view;

    public AboutPresenter(AboutContract.View v) {
        view = v;
    }

    @Override
    public void email() {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "zhangfene@gmail.com", null));
        ((AboutFragment) view).startActivity(Intent.createChooser(intent, "Send Email to zhangfene@gmail.com"));
    }

    @Override
    public void github() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/starsight"));
        ((AboutFragment) view).startActivity(intent);
    }

    @Override
    public AboutModel initModel() {
        return null;
    }
}
