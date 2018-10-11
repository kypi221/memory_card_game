package com.kypi.demoproject.mvp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kypi.demoproject.R;
import com.kypi.demoproject.base.BaseActivity;
import com.kypi.demoproject.di.component.ActivityComponent;
import com.kypi.demoproject.domain.DomainConfig;
import com.kypi.demoproject.mvp.contracts.MainMenuContract;
import com.kypi.demoproject.mvp.presenter.MainMenuPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class MainMenuActivity extends BaseActivity implements MainMenuContract.View {

    @BindView(R.id.tv_current_time)
    TextView tvCurrentTime;
    @BindView(R.id.tv_current_level)
    TextView tvCurrentLevel;

    @BindView(R.id.btn_update_level)
    Button btnUpdateLevel;
    @BindView(R.id.btn_update_time)
    Button btnUpdateTime;

    @BindView(R.id.layout_selector)
    ViewGroup layoutSelector;
    @BindView(R.id.layout_select_level)
    ViewGroup layoutSelectLevel;
    @BindView(R.id.layout_select_time)
    ViewGroup layoutSelectTime;


    @Inject
    MainMenuPresenter presenter;


    public static void showMe(BaseActivity activity) {
        Intent intent = new Intent(activity, MainMenuActivity.class);
        activity.startActivity(intent);
    }

    /**
     * get layoutEyeProtection to inflate
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_menu;
    }

    /**
     * Setup activity component/inject
     *
     * @param mActivityComponent
     */
    @Override
    protected void setupActivityComponent(ActivityComponent mActivityComponent) {
        mActivityComponent.inject(this);
    }

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        presenter.attachView(this);
        presenter.loadCurrentConfigLevel();
        presenter.loadCurrentConfigTime();
    }

    @Override
    public void showCurrentConfigTime(long time) {
        tvCurrentTime.setText(time + " Seconds");
    }

    @Override
    public void showCurrentConfigLevel(int level) {

        switch (level){
            case DomainConfig.Level.LEVEL_BEGINNER:
                tvCurrentLevel.setText(R.string.level_1);
                break;
            case DomainConfig.Level.LEVEL_EASY:
                tvCurrentLevel.setText(R.string.level_2);
                break;
            case DomainConfig.Level.LEVEL_MEDIUM:
                tvCurrentLevel.setText(R.string.level_3);
                break;
            case DomainConfig.Level.LEVEL_HARD:
                tvCurrentLevel.setText(R.string.level_4);
                break;
            case DomainConfig.Level.LEVEL_EX_HARD:
                tvCurrentLevel.setText(R.string.level_5);
                break;

        }

    }


    @OnClick(R.id.btn_update_time)
    public void showUpdateTime() {
        layoutSelector.setVisibility(View.VISIBLE);
        layoutSelectTime.setVisibility(View.VISIBLE);
        layoutSelectLevel.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_update_level)
    public void showUpdateLevel() {
        layoutSelector.setVisibility(View.VISIBLE);
        layoutSelectTime.setVisibility(View.GONE);
        layoutSelectLevel.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.layout_selector)
    public void hideLayoutSelector() {
        layoutSelector.setVisibility(View.GONE);
    }

    @OnClick({
            R.id.btn_level_1,
            R.id.btn_level_2,
            R.id.btn_level_3,
            R.id.btn_level_4,
            R.id.btn_level_5,
    })
    public void selectLevel(View view) {
        int level = DomainConfig.Level.LEVEL_BEGINNER;

        int viewId = view.getId();
        switch (viewId) {
            case R.id.btn_level_2:
                level = DomainConfig.Level.LEVEL_EASY;
                break;
            case R.id.btn_level_3:
                level = DomainConfig.Level.LEVEL_MEDIUM;
                break;
            case R.id.btn_level_4:
                level = DomainConfig.Level.LEVEL_HARD;
                break;
            case R.id.btn_level_5:
                level = DomainConfig.Level.LEVEL_EX_HARD;
                break;
        }

        hideLayoutSelector();
        presenter.setConfigLevel(level);

    }


    @OnClick({
            R.id.btn_time_1,
            R.id.btn_time_2,
            R.id.btn_time_3,
            R.id.btn_time_4,
            R.id.btn_time_5,
    })
    public void selectTime(View view) {
        int time = DomainConfig.Time.TIME_30;

        int viewId = view.getId();
        switch (viewId) {
            case R.id.btn_time_2:
                time = DomainConfig.Time.TIME_45;
                break;
            case R.id.btn_time_3:
                time = DomainConfig.Time.TIME_60;
                break;
            case R.id.btn_time_4:
                time = DomainConfig.Time.TIME_75;
                break;
            case R.id.btn_time_5:
                time = DomainConfig.Time.TIME_90;
                break;
        }

        hideLayoutSelector();
        presenter.setConfigTime(time);

    }
}
