package com.silvandrade.instagramremakejava.common.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.silvandrade.instagramremakejava.R;
import com.silvandrade.instagramremakejava.common.util.Colors;
import com.silvandrade.instagramremakejava.common.util.Drawables;

public abstract class AbstractActivity extends AppCompatActivity implements com.silvandrade.instagramremakejava.common.view.View {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getView());

        onInject();
    }

    public Drawable findDrawable(@DrawableRes int drawableId) {
        return Drawables.getDrawable(this, drawableId);
    }

    public int findColor(@ColorRes int colorId) {
        return Colors.getColor(this, colorId);
    }

    protected abstract View getView();

    protected abstract void onInject();

    @Override
    public Context getContext() {
        return getBaseContext();
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void setStatusBarDark() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(findColor(R.color.black));
        }
    }
}
