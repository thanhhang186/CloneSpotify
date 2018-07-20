package com.khtn.clonespotify.detail.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.khtn.clonespotify.R;
import com.khtn.clonespotify.database.FirebaseManager;
import com.khtn.clonespotify.detail.adapter.DeviceAdapter;
import com.khtn.clonespotify.detail.presenter.DevicePresenter;
import com.khtn.clonespotify.detail.presenter.DevicePresenterImpl;
import com.khtn.clonespotify.model.Device;
import com.khtn.clonespotify.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeviceFragment extends Fragment implements OnClickDeviceListener {
    @BindView(R.id.btn_close)
    ImageView btnClose;

    @OnClick(R.id.btn_close)
    void onClickBtnClose() {
        getActivity().getFragmentManager().popBackStack();
    }

    @BindView(R.id.list_device)
    RecyclerView listDevice;
    private List<Device> devices;
    private DeviceAdapter deviceAdapter;
    private DevicePresenter devicePresenter;
    private FirebaseAuth auth;
    public DeviceFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_device, container, false);
        ButterKnife.bind(this, view);
        initLayoutReferences();
        return view;
    }

    private void initLayoutReferences() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        listDevice.setLayoutManager(layoutManager);
        listDevice.setHasFixedSize(true);
        listDevice.setItemAnimator(new DefaultItemAnimator());

        devices = new ArrayList<>();
        deviceAdapter = new DeviceAdapter(this, devices);
        listDevice.setAdapter(deviceAdapter);
        FirebaseManager.getInstance().getDeviceListByID(PrefUtils.getUserId(getActivity()), deviceAdapter);

    }

    @Override
    public void onVideoClicked(Device device) {

    }
}
