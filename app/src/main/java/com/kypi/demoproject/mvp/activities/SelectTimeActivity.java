package com.kypi.demoproject.mvp.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kypi.demoproject.R;
import com.kypi.demoproject.base.BaseActivity;
import com.kypi.demoproject.di.component.ActivityComponent;
import com.kypi.demoproject.domain.usecase.GameConfigUseCase;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectTimeActivity extends BaseActivity {

    @BindView(R.id.edit_time)
    EditText editTime;

    private long selectedTime = 0;

    @Inject
    GameConfigUseCase gameConfigUseCase;

    public static void showMe(BaseActivity activity) {
        Intent intent = new Intent(activity, SelectTimeActivity.class);
        activity.startActivity(intent);
    }

    /**
     * get layoutEyeProtection to inflate
     */
    @Override
    protected int getLayoutId() {
        return R.layout.select_time_activity;
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
    }


    @OnClick(R.id.btn_start_game)
    public void btnStartGame() {
        String timeStr= editTime.getText().toString();

        try {
            selectedTime = Long.parseLong(timeStr);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(selectedTime <= 0 || selectedTime >= 10000){
            showDialogLevel();
            return;
        }

        gameConfigUseCase.setSettingTime(selectedTime);
        SinglePlayerGameActivity.showMe(this);
        finish();
    }

    private void showDialogLevel() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setMessage("Thời gian chơi game phải lớn hơn 0 và nhỏ hơn 10000");


        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // continue with delete
                dialog.dismiss();
            }
        });

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
