package com.silvandrade.instagramremakejava.common.view;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.silvandrade.instagramremakejava.common.util.Colors;
import com.silvandrade.instagramremakejava.common.util.Drawables;

public abstract class AbstractFragment<T> extends Fragment implements com.silvandrade.instagramremakejava.common.view.View {

    protected T presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        configComponents(view);
        return view;
    }

    @Nullable
    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void setStatusBarDark() {

    }

    public void setPresenter(T presenter) {
        this.presenter = presenter;
    }

    public Drawable findDrawable(@DrawableRes int drawableId) {
        return Drawables.getDrawable(getContext(), drawableId);
    }

    public int findColor(@ColorRes int colorId) {
        return Colors.getColor(getContext(), colorId);
    }

    protected abstract @LayoutRes int getLayout();

    protected abstract void bindLayoutComponents();

    protected abstract void setListenerComponents();

    protected abstract void configComponents(View view);
}
