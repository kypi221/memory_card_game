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
import com.kypi.demoproject.utils.helper.CardHelper;
import com.kypi.demoproject.widget.CustomToast;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

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

    private int firstIndex = -1;

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
        firstIndex = -1;

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
            ViewGroup viewSelected = (ViewGroup) firstItem.getTag(R.id.tag_view);
            viewSelected.setVisibility(View.GONE);
        }
        if (selectedSecond != -1) {
            secondItem = listViewManager.get(selectedSecond);
            ViewGroup viewSelected = (ViewGroup) secondItem.getTag(R.id.tag_view);
            viewSelected.setVisibility(View.GONE);
        }

        // Trạng thái none
        if (status == 0) {
            MemoryCard memoryCard1 = (MemoryCard) firstItem.getTag(R.id.tag_object);
            if(memoryCard1.status != MemoryCard.Status.ToolFaceUp){
                CardHelper.faceDownCard(firstItem);
            }

            MemoryCard memoryCard2 = (MemoryCard) secondItem.getTag(R.id.tag_object);
            if(memoryCard2.status != MemoryCard.Status.ToolFaceUp){
                CardHelper.faceDownCard(secondItem);
            }
        } else if (status == 1) {
            CardHelper.faceUpCard(firstItem);
            CardHelper.faceUpCard(secondItem);
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

    @Override
    public void toolFaceUp(int firstItem, int secondItem) {
        EasyFlipView firstView = listViewManager.get(firstItem);
        EasyFlipView secondView = listViewManager.get(secondItem);

        MemoryCard firstCard = (MemoryCard) firstView.getTag(R.id.tag_object);
        MemoryCard secondCard = (MemoryCard) secondView.getTag(R.id.tag_object);

        firstCard.status = MemoryCard.Status.ToolFaceUp;
        secondCard.status = MemoryCard.Status.ToolFaceUp;

        firstView.setFlipDuration(400);
        firstView.flipTheView();

        secondView.setFlipDuration(400);
        secondView.flipTheView();
    }

    @Override
    public void toolRemoveCouple(int firstIndex, int secondIndex) {
        EasyFlipView firstView = listViewManager.get(firstIndex);
        EasyFlipView secondView = listViewManager.get(secondIndex);

        if (firstView != null) {
            firstView.setVisibility(View.INVISIBLE);
            firstView.setOnClickListener(null);
        }

        if (secondView != null) {
            secondView.setVisibility(View.INVISIBLE);
            secondView.setOnClickListener(null);
        }

    }


    @OnClick(R.id.tv_help_1)
    public void help1Clicked(View view){
        presenter.openRandom();
        view.setVisibility(View.INVISIBLE);
    }


    @OnClick(R.id.tv_help_2)
    public void help2Clicked(View view){
        presenter.removeRandomCouple();
        view.setVisibility(View.INVISIBLE);
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
        ViewGroup layoutSelected = itemView.findViewById(R.id.layout_selected);

        // Set hình ảnh vào mặt dưới
        imageView.setImageResource(item.resourceId);
        layoutFlipView.setTag(R.id.tag_object, item);
        layoutFlipView.setTag(R.id.tag_position, index);
        layoutFlipView.setTag(R.id.tag_view, layoutSelected);

        layoutFlipView.setFlipDuration(0);
        layoutFlipView.flipTheView();

        // Add View Item vào Row
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1;
        layoutRow.addView(itemView, layoutParams);

        layoutFlipView.setOnClickListener(v -> {

            // Lấy vị trí ra
            int position = (int) v.getTag(R.id.tag_position);
            ViewGroup viewSelected = (ViewGroup) v.getTag(R.id.tag_view);

            // Nếu position bằng với vị trí click đầu thì ko làm gì
            if (position == firstIndex) {
                return;
            }

            CardHelper.faceUpCard(layoutFlipView);

            viewSelected.setVisibility(View.VISIBLE);

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
