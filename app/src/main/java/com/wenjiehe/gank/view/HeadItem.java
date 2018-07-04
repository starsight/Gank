package com.wenjiehe.gank.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.wenjiehe.gank.model.GankItem;
import com.wenjiehe.gank.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HeadItem extends FrameLayout implements GankItemView {

    private static final String TAG = "HeadItem";

    @BindView(R.id.image)
    ImageView image;

    public HeadItem(Context context) {
        super(context);
    }

    public HeadItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeadItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void bind(GankItem gankItem, Listener listener) {
        if (gankItem != null) {
            if (!TextUtils.isEmpty(gankItem.getImage())) {
                Glide.with(getContext())
                        .load(gankItem.getImage())
                        .centerCrop()
                        .into(image);
            } else {
                Log.e(TAG, "no image " + new Gson().toJson(gankItem));
            }
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }
}
