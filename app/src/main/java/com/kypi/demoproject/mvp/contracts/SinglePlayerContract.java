package com.kypi.demoproject.mvp.contracts;

import com.kypi.demoproject.domain.entities.DemoObject;
import com.kypi.demoproject.domain.entities.IReadBookInfo;
import com.kypi.demoproject.domain.entities.MemoryCard;

import java.util.List;

public interface SinglePlayerContract {

    public interface View extends BaseContract.View {
        void showGame(List<MemoryCard> listCard, int colum);

        void updateGameTime(int timeLeft, int total);

        void updateSelectedCardStatus(int selectedFirst, int selectedSecond, int status);

        void showVictory();

    }

    public interface Presenter extends BaseContract.BasePresenter<SinglePlayerContract.View> {
        void loadGame();

        void selectedCard(int selectedIndex);


    }
}
