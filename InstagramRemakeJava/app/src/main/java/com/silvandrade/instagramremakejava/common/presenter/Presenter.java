package com.silvandrade.instagramremakejava.common.presenter;

import com.silvandrade.instagramremakejava.common.model.UserAuth;

public interface Presenter<T> {
    void onSuccess(T response);
    void onError(String message);
    void onComplete();
}
