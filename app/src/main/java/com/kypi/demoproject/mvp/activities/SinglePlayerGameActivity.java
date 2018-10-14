package com.kypi.demoproject.mvp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kypi.demoproject.R;
import com.kypi.demoproject.base.BaseActivity;
import com.kypi.demoproject.di.component.ActivityComponent;
import com.kypi.demoproject.domain.entities.MemoryCard;
import com.kypi.demoproject.mvp.contracts.SinglePlayerContract;
import com.kypi.demoproject.mvp.presenter.SinglePlayerPresenter;
import com.kypi.demoproject.widget.CustomToast;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class SinglePlayerGameActivity extends BaseActivity implements SinglePlayerContract.View {

    @BindView(R.id.layout_grid)
    LinearLayout layoutGrid;

    @BindView(R.id.layout_exp_bar)
    ViewGroup layoutExpBar;
    @BindView(R.id.layout_exp_bar_parent)
    ViewGroup layoutExpBarParent;

    @BindView(R.id.tv_time_left)
    TextView tvTimeLeft;
    @BindView(R.id.tv_current_point)
    TextView tvCurrentPoint;

    @BindView(R.id.tv_current_level)
    TextView tvCurrentLevel;


    @Inject
    SinglePlayerPresenter presenter;

    // Danh sách cái view đang đc dùng
    private List<EasyFlipView> listViewManager;

    private int firstIndex;

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
    protected void onDestroy() {
        super.onDestroy();

        presenter.detachView();
    }

    @Override
    public void showGame(List<MemoryCard> listCard, int colum) {
        listViewManager = new ArrayList<>();
        layoutGrid.removeAllViews();
        LinearLayout layoutRow = null;
        for (int i = 0; i < listCard.size(); i++) {

            // Bắt đầu 1 dòng mới
            if (i % colum == 0) {
                layoutRow = createNewRow();
            }

            // Add vào danh sách quản lý
            listViewManager.add(createViewItem(layoutRow, listCard.get(i), i));
        }
    }

    @Override
    public void updateGameTime(long timeLeft, long total) {

        if(timeLeft>= 0){
            tvTimeLeft.setText(String.valueOf(timeLeft) + " giây");
        }

        // Update progress bar
        long percentExp = timeLeft*100 / total;

        int maxHeight = layoutExpBarParent.getHeight();
        long levelHeight = (maxHeight * percentExp)/100 ;
        ViewGroup.LayoutParams layoutParams = layoutExpBar.getLayoutParams();
        layoutParams.height = (int) levelHeight;
        layoutExpBar.setLayoutParams(layoutParams);

    }

    @Override
    public void updateSelectedCardStatus(int selectedFirst, int selectedSecond, int status) {

        EasyFlipView firstItem = null;
        EasyFlipView secondItem = null;
        if (selectedFirst != -1) {
            firstItem = listViewManager.get(selectedFirst);
        }
        if (selectedSecond != -1) {
            secondItem = listViewManager.get(selectedSecond);
        }

        // Trạng thái none
        if (status == 0) {
            if (firstItem != null && firstItem.getCurrentFlipState() == EasyFlipView.FlipState.FRONT_SIDE) {
                firstItem.setFlipDuration(400);
                firstItem.flipTheView();
            }

            if (secondItem != null && secondItem.getCurrentFlipState() == EasyFlipView.FlipState.FRONT_SIDE) {
                secondItem.setFlipDuration(400);
                secondItem.flipTheView();
            }
        } else if (status == 1) {
            if (firstItem != null && firstItem.getCurrentFlipState() == EasyFlipView.FlipState.BACK_SIDE) {
                firstItem.setFlipDuration(400);
                firstItem.flipTheView();
            }

            if (secondItem != null && secondItem.getCurrentFlipState() == EasyFlipView.FlipState.BACK_SIDE) {
                secondItem.setFlipDuration(400);
                secondItem.flipTheView();
            }
        } else {
            if (firstItem != null) {
                firstItem.setVisibility(View.INVISIBLE);
                firstItem.setOnClickListener(null);
            }

            if (secondItem != null) {
                secondItem.setVisibility(View.INVISIBLE);
                secondItem.setOnClickListener(null);
            }



        }
    }

    @Override
    public void showVictory() {
        CustomToast.showSuccessMgs(this, "You Win !");
        finish();
    }

    @Override
    public void showLose() {
        CustomToast.showWarningMgs(this, "You Lose !");
        finish();
    }

    @Override
    public void showPoint(int point) {
        tvCurrentPoint.setText(String.valueOf(point));
    }

    @Override
    public void showCurrentGameLevel(int level) {
        tvCurrentLevel.setText(String.valueOf(level));
    }

    /**
     * Tạo 1 dòng mới
     *
     * @return
     */
    private LinearLayout createNewRow() {
        LinearLayout layoutRow = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        layoutParams.weight = 1;

        layoutGrid.addView(layoutRow, layoutParams);
        return layoutRow;
    }


    /**
     * Tạo ra các phần tử card trong ma trận
     *
     * @param layoutRow
     * @param item
     * @param index
     * @return
     */
    private EasyFlipView createViewItem(LinearLayout layoutRow, MemoryCard item, int index) {

        // Tạo ra view item
        View itemView = LayoutInflater.from(this).inflate(R.layout.item_list_card, null, false);
        EasyFlipView layoutFlipView = itemView.findViewById(R.id.flipView);
        ImageView imageView = itemView.findViewById(R.id.img_icon_card);

        // Set hình ảnh vào mặt dưới
        imageView.setImageResource(item.resourceId);
        layoutFlipView.setTag(R.id.tag_object, item);
        layoutFlipView.setTag(R.id.tag_position, index);

        layoutFlipView.setFlipDuration(0);
        layoutFlipView.flipTheView();


        // Add View Item vào Row
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1;
        layoutRow.addView(itemView, layoutParams);

        layoutFlipView.setOnClickListener(v -> {

            // Lấy vị trí ra
            int position = (int) v.getTag(R.id.tag_position);

            // Nếu position bằng với vị trí click đầu thì ko làm gì
            if (position == firstIndex) {
                return;
            }

            // Lật view nếu trạng thái đang là chưa lật
            layoutFlipView.setFlipDuration(400);
            layoutFlipView.flipTheView();


            // Lần đầu click thì chỉ thực hiện animation lật ( nếu đang úp )
            if (firstIndex == -1) {
                firstIndex = position;
                return;
            }

            // nếu có rồi thì gọi check và
            presenter.checkCard(firstIndex, position);
            firstIndex = -1;

        });

        return layoutFlipView;
    }

}
