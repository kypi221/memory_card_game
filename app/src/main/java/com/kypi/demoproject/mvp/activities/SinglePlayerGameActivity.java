package com.kypi.demoproject.mvp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.kypi.demoproject.R;
import com.kypi.demoproject.base.BaseActivity;
import com.kypi.demoproject.di.component.ActivityComponent;
import com.kypi.demoproject.domain.entities.MemoryCard;
import com.kypi.demoproject.mvp.contracts.SinglePlayerContract;
import com.kypi.demoproject.mvp.presenter.SinglePlayerPresenter;

import java.util.List;

import javax.inject.Inject;

public class SinglePlayerGameActivity extends BaseActivity implements SinglePlayerContract.View {

    @Inject
    SinglePlayerPresenter presenter;


    public static void showMe(BaseActivity activity) {
        Intent intent = new Intent(activity, SinglePlayerGameActivity.class);
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
        presenter.loadGame();
    }

    @Override
    public void showGame(List<MemoryCard> listCard, int colum) {
        Log.d("KhoaHM", "List card = " + listCard.size() + " | col = " + colum );
        Toast.makeText(this,"List card = " + listCard.size() + " | col = " + colum , Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateGameTime(int timeLeft, int total) {

    }

    @Override
    public void updateSelectedCardStatus(int selectedFirst, int selectedSecond, int status) {

    }
}
