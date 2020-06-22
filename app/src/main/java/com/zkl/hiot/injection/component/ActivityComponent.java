package com.zkl.hiot.injection.component;

import android.app.Activity;

import com.zkl.hiot.injection.PerActivity;
import com.zkl.hiot.injection.module.ActivityModule;
import com.zkl.hiot.ui.login.LoginActivity;
import com.zkl.hiot.ui.main.MainActivity;
import com.zkl.hiot.ui.main.equipment.EquipmentFragment;
import com.zkl.hiot.ui.main.message.MessageFragment;
import com.zkl.hiot.ui.main.mine.MineFragment;
import com.zkl.hiot.ui.main.scene.SceneFragment;
import com.zkl.hiot.ui.register.RegisterActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity activity);
    void inject(MineFragment mineFragment);
    void inject(EquipmentFragment equipmentFragment);
    void inject(MessageFragment messageFragment);
    void inject(SceneFragment sceneFragment);
    void inject(LoginActivity loginActivity);
    void inject(RegisterActivity registerActivity);
}
