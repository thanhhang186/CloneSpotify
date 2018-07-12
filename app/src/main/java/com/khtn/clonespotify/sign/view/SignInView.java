package com.khtn.clonespotify.sign.view;

public interface SignInView {
    void showVadidationError();
    void loginSuccess();
    void loginError();
    void setProgressVisibility(boolean visibility);
    void isLogin(boolean isLogin);
    void loadHomeAct();
}
