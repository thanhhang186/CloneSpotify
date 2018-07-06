package com.khtn.clonespotify.sign.presenter;

import com.khtn.clonespotify.sign.view.SignInView;

public interface SignInPresenter {
    void signIn(String email, String password);
    void checkSignIn();
    void attachView(SignInView view);
    void detachView();
}

