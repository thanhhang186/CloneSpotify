package com.khtn.clonespotify.sign.presenter;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.khtn.clonespotify.sign.view.SignUpView;

public class SignUpPresenterImpl implements SignUpPresenter {
    private FirebaseAuth auth;
    private SignUpView signUpView;

    public SignUpPresenterImpl(FirebaseAuth firebaseAuth) {
        this.auth = firebaseAuth;
    }

    @Override
    public void signUp(String email, String password) {
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            signUpView.showValidationError();
        } else {
            signUpView.setProgressVisibility(true);
            auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            signUpView.setProgressVisibility(false);

                            if(!task.isSuccessful()){
                                signUpView.signUpError();
                            } else {
                                signUpView.signUpSuccess();
                            }
                        }
                    });
        }
    }

    @Override
    public void attachView(SignUpView signUpView) {
        signUpView = signUpView;
    }

    @Override
    public void detachView() {
        signUpView = null;
    }

}
