package com.zkl.hiot.ui.main.equipment;

import com.zkl.hiot.base.BaseView;
import com.zkl.hiot.entity.HolderDeviceEntity;

import java.util.List;

public interface EquipmentView extends BaseView {
    void showToast(String msg);
    void refreshData(List<HolderDeviceEntity> data);
    void stopRefresh();
}
