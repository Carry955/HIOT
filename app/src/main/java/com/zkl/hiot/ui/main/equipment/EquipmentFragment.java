package com.zkl.hiot.ui.main.equipment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.zkl.hiot.R;
import com.zkl.hiot.base.BaseActivity;
import com.zkl.hiot.base.BaseFragment;
import com.zkl.hiot.entity.HolderDeviceEntity;


import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EquipmentFragment extends BaseFragment<EquipmentView, EquipmentPresenter> implements EquipmentView, DeviceAdapter.OnItemClickListener {

    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rv_list)
    RecyclerView recyclerView;
    @BindView(R.id.tv_nodata)
    TextView tv_nodata;

    @Inject
    EquipmentPresenter equipmentPresenter;
    @Override
    protected  EquipmentPresenter createPresenter(){
        return equipmentPresenter;
    }
    @OnClick(R.id.iv_add_device)
    void addDevice(){
        showToast("添加设备");
    }
    private DeviceAdapter adapter;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_equipment, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        equipmentPresenter.getDeviceList();
    }

    @Override
    public void onResume() {
        super.onResume();
        equipmentPresenter.getDeviceList();
    }

    private void initViews(){
        tv_nodata.setVisibility(View.VISIBLE);
        tv_nodata.setText("暂无设备哦～，马上去绑定吧");
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_green_dark,
                android.R.color.holo_blue_dark,
                android.R.color.holo_orange_dark
        );
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                equipmentPresenter.getDeviceList();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new DeviceAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }
    @Override
    protected  void injectDependencies(){
        super.injectDependencies();
        ((BaseActivity)getActivity()).getActivityComponent().inject(this);
    }
    @Override
    public void showToast(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void refreshData(List<HolderDeviceEntity> data){
        if (data == null || data.size() == 0){
            tv_nodata.setVisibility(View.VISIBLE);
        }else{
            tv_nodata.setVisibility(View.GONE);
        }
        adapter.resetDataAndNotifyDataSetChanged(data);
    }
    @Override
    public void stopRefresh(){
        swipeRefreshLayout.setRefreshing(false);
    }

    public void onItemClick(int position, HolderDeviceEntity item){
        showToast("onItemCheck:" + position);
    }
}
