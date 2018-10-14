package com.kypi.demoproject.mvp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.kypi.demoproject.R;
import com.kypi.demoproject.base.BaseActivity;
import com.kypi.demoproject.di.component.ActivityComponent;

import butterknife.BindView;
import butterknife.OnClick;

public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.btn_start_game)
    Button btnStartGame;


    public static void showMe(BaseActivity activity) {
        Intent intent = new Intent(activity, WelcomeActivity.class);
        activity.startActivity(intent);
    }

    /**
     * get layoutEyeProtection to inflate
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    /**
     * Setup activity component/inject
     *
     * @param mActivityComponent
     */
    @Override
    protected void setupActivityComponent(ActivityComponent mActivityComponent) {

    }

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {

    }


    @OnClick(R.id.btn_start_game)
    public void startGame(){
        SelectLevelActivity.showMe(this);
    }

}
