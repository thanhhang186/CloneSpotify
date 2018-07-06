package com.khtn.clonespotify;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class LaunchFragment extends Fragment {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.layout_dots)
    LinearLayout dotsLayout;
    @BindView(R.id.sign_in_page)
    Button btnSignInPage;
    @BindView(R.id.sign_up_page)
    Button btnSignUpPage;
    @BindView(R.id.tv_intro)
    TextView tvIntro;
    private String[] text;
    private TextView[] dots;
    private Callback callback;

    public LaunchFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_launch, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onClickBtn();
    }

    private void onClickBtn() {
        btnSignInPage.setOnClickListener(v -> {
            callback.onSignInPage();
        });
        btnSignUpPage.setOnClickListener(v -> {
            callback.onSignUpPage();
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (Callback) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    public interface Callback {
        void onSignInPage();

        void onSignUpPage();
    }

}
