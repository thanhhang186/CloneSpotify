package com.khtn.clonespotify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.khtn.clonespotify.sign.view.SignInFragment;
import com.khtn.clonespotify.sign.view.SignUpFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LaunchFragment.Callback {
    public static final String LAUNCH_FRAGMENT = "LauchFragment";
    public static final String SIGN_IN_FRAGMENT = "SignInFragment";
    public static final String SIGN_UP_FRAGMENT = "SignUpFragment";
    @BindView(R.id.container)
    FrameLayout container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LaunchFragment launchFragment = new LaunchFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, launchFragment, LAUNCH_FRAGMENT).commit();
    }

    @Override
    public void onSignInPage() {
        SignInFragment signInFragment = new SignInFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,signInFragment,SIGN_IN_FRAGMENT).commit();
    }

    @Override
    public void onSignUpPage() {
        SignUpFragment signUpFragment = new SignUpFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,signUpFragment,SIGN_UP_FRAGMENT).commit();
    }
}
