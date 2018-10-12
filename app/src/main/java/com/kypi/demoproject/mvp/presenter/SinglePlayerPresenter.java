package com.kypi.demoproject.mvp.presenter;

import com.kypi.demoproject.R;
import com.kypi.demoproject.base.BasePresenter;
import com.kypi.demoproject.domain.entities.MemoryCard;
import com.kypi.demoproject.domain.usecase.GameConfigUseCase;
import com.kypi.demoproject.domain.usecase.GamePlayUseCase;
import com.kypi.demoproject.mvp.contracts.SinglePlayerContract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class SinglePlayerPresenter extends BasePresenter<SinglePlayerContract.View> implements SinglePlayerContract.Presenter {

    public static final int STATUS_NONE = 0;            // Card đang úp xuống, chưa đụng chạm gì cả
    public static final int STATUS_FACE_UP = 1;         // Card đang lật lên, có thể là đã được chọn, hoặc được auto up
    public static final int STATUS_COMPLETE = 2;        // Card đã được chọn thành công


    private static final int[] resourceList = {
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5,
            R.drawable.image6,
            R.drawable.image7,
            R.drawable.image8,
            R.drawable.image9,
            R.drawable.image10,
            R.drawable.image11,
            R.drawable.image12,
            R.drawable.image13,
            R.drawable.image14,
            R.drawable.image15,
    };

    private final GameConfigUseCase gameConfigUseCase;
    private final GamePlayUseCase gamePlayUseCase;

    private int firstSelected = -1;
    private int secondSelected = -1;

    private List<MemoryCard> memoryCards;

    @Inject
    public SinglePlayerPresenter(GameConfigUseCase gameConfigUseCase, GamePlayUseCase gamePlayUseCase){
        this.gameConfigUseCase = gameConfigUseCase;
        this.gamePlayUseCase = gamePlayUseCase;
    }

    @Override
    public void loadGame() {

        List<Integer> myArray = new ArrayList<>();
        for (int i = 0; i < resourceList.length; i++)
            myArray.add(resourceList[i]);

        // Tạo ra danh sách card
        memoryCards = gamePlayUseCase.createListCard(myArray);



        // Nhỏ nhất
        if(memoryCards.size() == 20){
            getMvpView().showGame(memoryCards, 5);
            return;
        }

        if(memoryCards.size() == 30){
            getMvpView().showGame(memoryCards, 6);
        }

        if(memoryCards.size() == 40){
            getMvpView().showGame(memoryCards, 8);
        }

        if(memoryCards.size() == 50){
            getMvpView().showGame(memoryCards, 10);
        }

        if(memoryCards.size() == 60){
            getMvpView().showGame(memoryCards, 10);
        }

    }

    @Override
    public void selectedCard(int selectedIndex) {
        // Nếu chọn lại cái card vừa chọn thì ko làm gì cả
        if(firstSelected == selectedIndex){
            return;
        }

        // Nếu chưa chọn card đầu thì chọn card đầu
        if(firstSelected == -1){
            firstSelected = selectedIndex;
            getMvpView().updateSelectedCardStatus(firstSelected, secondSelected, STATUS_FACE_UP );
            return;
        }


        if(memoryCards.get(firstSelected).resourceId == memoryCards.get(selectedIndex).resourceId){
            getMvpView().updateSelectedCardStatus(firstSelected, selectedIndex, STATUS_COMPLETE);
            return;
        }

        getMvpView().updateSelectedCardStatus(firstSelected, selectedIndex, STATUS_NONE);

    }
}
