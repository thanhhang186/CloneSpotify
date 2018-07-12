package com.khtn.clonespotify.sign.presenter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.khtn.clonespotify.database.FirebaseManager;
import com.khtn.clonespotify.model.User;
import com.khtn.clonespotify.sign.view.SignUpView;

public class SignUpPresenterImpl implements SignUpPresenter {
    private FirebaseAuth auth;
    private SignUpView signUpView;
    private Activity context;
    private FirebaseUser user;

    public SignUpPresenterImpl(Activity context, FirebaseAuth firebaseAuth) {
        this.auth = firebaseAuth;
        this.context = context;
    }

    @Override
    public void signUp(String id, String email, String password) {
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
                                try {
                                    user = auth.getCurrentUser();
                                    FirebaseManager.getInstance().saveUser(id, new User(id, user.getUid(), email));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

        }
    }

    @Override
    public void attachView(SignUpView signUpView) {
        this.signUpView = signUpView;
    }

    @Override
    public void detachView() {
        signUpView = null;
    }

}
