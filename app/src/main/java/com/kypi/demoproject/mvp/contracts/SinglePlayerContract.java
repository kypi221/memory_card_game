package com.kypi.demoproject.mvp.contracts;

import com.kypi.demoproject.domain.entities.DemoObject;
import com.kypi.demoproject.domain.entities.IReadBookInfo;
import com.kypi.demoproject.domain.entities.MemoryCard;

import java.util.List;

public interface SinglePlayerContract {

    public interface View extends BaseContract.View {
        void showGame(List<MemoryCard> listCard, int colum);

        void updateGameTime(long timeLeft, long total);

        void updateSelectedCardStatus(int selectedFirst, int selectedSecond, int status);

        void showVictory();

        void showLose();

        void showPoint(int currentPoint);

        void showCurrentGameLevel(int level);

        void toolFaceUp(int firstItem, int secondItem);

        void toolRemoveCouple(int firstIndex, int secondIndex);
    }

    public interface Presenter extends BaseContract.BasePresenter<SinglePlayerContract.View> {
        void loadGame();

        void checkCard(int firstIndex, int secondIndex);

        void openRandom();

        void removeRandomCouple();

        void addTime();

    }
}
