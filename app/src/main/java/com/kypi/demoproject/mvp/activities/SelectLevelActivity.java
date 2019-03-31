package com.kypi.demoproject.mvp.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kypi.demoproject.R;
import com.kypi.demoproject.base.BaseActivity;
import com.kypi.demoproject.di.component.ActivityComponent;
import com.kypi.demoproject.domain.usecase.GameConfigUseCase;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectLevelActivity extends BaseActivity {

    @BindView(R.id.btn_start_game)
    Button btnStartGame;

    private int selectedLevel = -1;

    @Inject
    GameConfigUseCase gameConfigUseCase;

    public static void showMe(BaseActivity activity) {
        Intent intent = new Intent(activity, SelectLevelActivity.class);
        activity.startActivity(intent);
    }

    /**
     * get layoutEyeProtection to inflate
     */
    @Override
    protected int getLayoutId() {
        return R.layout.select_level_activity;
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
        btnStartGame.setVisibility(View.GONE);
    }


    @OnClick(R.id.btn_level_1)
    public void selectLevel1() {
        showDialogLevel(1, 20);
    }

    @OnClick(R.id.btn_level_2)
    public void selectLevel2() {
        showDialogLevel(2, 30);
    }

    @OnClick(R.id.btn_level_3)
    public void selectLevel3() {
        showDialogLevel(3, 40);
    }

    @OnClick(R.id.btn_level_4)
    public void selectLevel4() {
        showDialogLevel(4, 50);
    }

    @OnClick(R.id.btn_level_5)
    public void selectLevel5() {
        showDialogLevel(5, 60);
    }


    @OnClick(R.id.btn_start_game)
    public void btnStartGame() {
        gameConfigUseCase.setSettingLevel(selectedLevel);
        SelectTimeActivity.showMe(this);
        finish();
    }

    private void showDialogLevel(int level, int totalCard) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn chọn cấp độ " + level + " ! Số ô là : " + totalCard);


        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // continue with delete
                dialog.dismiss();
            }
        });

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        selectedLevel = level;
        btnStartGame.setVisibility(View.VISIBLE);
    }
}
