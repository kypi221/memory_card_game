package com.kypi.demoproject.mvp.activities;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kypi.demoproject.R;
import com.kypi.demoproject.base.BaseActivity;
import com.kypi.demoproject.di.component.ActivityComponent;
import com.kypi.demoproject.domain.entities.MemoryCard;
import com.kypi.demoproject.mvp.contracts.SinglePlayerContract;
import com.kypi.demoproject.mvp.presenter.SinglePlayerPresenter;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class SinglePlayerGameActivity extends BaseActivity implements SinglePlayerContract.View {

    @BindView(R.id.layout_grid)
    LinearLayout layoutGrid;

    @Inject
    SinglePlayerPresenter presenter;

    private List<View> listViewManager;


    public static void showMe(BaseActivity activity) {
        Intent intent = new Intent(activity, SinglePlayerGameActivity.class);
        activity.startActivity(intent);
    }


    /**
     * get layoutEyeProtection to inflate
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_game_single_player;
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
        listViewManager = new ArrayList<>();
        layoutGrid.removeAllViews();
        LinearLayout layoutRow = null;
        for (int i = 0; i < listCard.size(); i++) {

            // Bắt đầu 1 dòng mới
            if(i % colum == 0){
                layoutRow = createNewRow();
            }

            // Add vào danh sách quản lý
            listViewManager.add(createViewHolder(layoutRow, listCard.get(i), i));
        }
    }

    @Override
    public void updateGameTime(int timeLeft, int total) {

    }

    @Override
    public void updateSelectedCardStatus(int selectedFirst, int selectedSecond, int status) {

    }


    /**
     * Tạo 1 dòng mới
     * @return
     */
    private LinearLayout createNewRow(){
        LinearLayout layoutRow = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        layoutParams.weight = 1;

        layoutGrid.addView(layoutRow, layoutParams);
        return layoutRow;
    }


    private EasyFlipView createViewHolder(LinearLayout layoutRow, MemoryCard item, int index){

        // Tạo ra view item
        View itemView = LayoutInflater.from(this).inflate(R.layout.item_list_card, null, false);
        EasyFlipView layoutFlipView = itemView.findViewById(R.id.flipView);
        ImageView imageView = itemView.findViewById(R.id.img_icon_card);

        // Set hình ảnh vào mặt dưới
        imageView.setImageResource(item.resourceId);
        layoutFlipView.setTag(R.id.tag_object, item);

        layoutFlipView.setFlipDuration(0);
        layoutFlipView.flipTheView();


        // Add View Item vào Row
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1;
        layoutRow.addView(itemView, layoutParams);

        layoutFlipView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MemoryCard item = (MemoryCard) v.getTag(R.id.tag_object);
                if (item.status == 0) {
                    item.status = 1;
                }
                else if (item.status == 1){
                    item.status = 0;
                }
                else {
                    return;
                }
                layoutFlipView.setFlipDuration(400);
                layoutFlipView.flipTheView();
            }
        });

        return layoutFlipView;
    }


    private class ViewHolder{
        public EasyFlipView layoutFlipView;
        public ViewGroup lootLayout;
        public ImageView imgItem;

    }
}
