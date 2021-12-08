package com.silvandrade.instagramremakejava.common.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class UserAuth {

    private String email;
    private String password;

    public UserAuth() {

    }

    public UserAuth(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String getUserID() {
        return String.valueOf(hashCode());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAuth userAuth = (UserAuth) o;
        return Objects.equals(email, userAuth.email) &&
                Objects.equals(password, userAuth.password);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }
}
