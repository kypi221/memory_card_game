package com.kypi.demoproject.mvp.presenter;

import com.kypi.demoproject.R;
import com.kypi.demoproject.app.rx.SimpleEmptyObserver;
import com.kypi.demoproject.app.rx.SimpleObserver;
import com.kypi.demoproject.base.BasePresenter;
import com.kypi.demoproject.domain.DomainConfig;
import com.kypi.demoproject.domain.entities.MemoryCard;
import com.kypi.demoproject.domain.usecase.GameConfigUseCase;
import com.kypi.demoproject.domain.usecase.GamePlayUseCase;
import com.kypi.demoproject.mvp.contracts.SinglePlayerContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SinglePlayerPresenter extends BasePresenter<SinglePlayerContract.View> implements SinglePlayerContract.Presenter {

    public static final int STATUS_NONE = 0;            // Card đang úp xuống, chưa đụng chạm gì cả
    public static final int STATUS_FACE_UP = 1;         // Card đang lật lên, có thể là đã được chọn, hoặc được auto up
    public static final int STATUS_COMPLETE = 2;        // Card đã được chọn thành công

    private CompositeDisposable updateTime;

    private static final int[] resourceList = {
            R.drawable.image_1,
            R.drawable.image_2,
            R.drawable.image_3,
            R.drawable.image_4,
            R.drawable.image_5,
            R.drawable.image_6,
            R.drawable.image_7,
            R.drawable.image_8,
            R.drawable.image_9,
            R.drawable.image_10,
            R.drawable.image_11,
            R.drawable.image_12,
            R.drawable.image_13,
            R.drawable.image_14,
            R.drawable.image_15,
            R.drawable.image_16,
            R.drawable.image_17,
            R.drawable.image_18,
            R.drawable.image_19,
            R.drawable.image_20,
            R.drawable.image_21,
            R.drawable.image_22,
            R.drawable.image_23,
            R.drawable.image_24,
            R.drawable.image_25
    };



    private final GameConfigUseCase gameConfigUseCase;
    private final GamePlayUseCase gamePlayUseCase;

    // Biến để đánh dấu đã hoàn thành bao nhiêu cặp, để biết khi nào win game
    private int completedCount;

    // Danh sách các cặp game
    private List<MemoryCard> memoryCards;
    private long settingTime;
    private long startTime;
    private int currentPoint;

    @Inject
    public SinglePlayerPresenter(GameConfigUseCase gameConfigUseCase, GamePlayUseCase gamePlayUseCase){
        this.gameConfigUseCase = gameConfigUseCase;
        this.gamePlayUseCase = gamePlayUseCase;
    }

    @Override
    public void detachView() {
        super.detachView();
        updateTime.dispose();
    }

    @Override
    public void loadGame() {

        // Hiển thị các config của game
        currentPoint = 0;
        int level = gameConfigUseCase.getSettingLevel();
        getMvpView().showPoint(currentPoint);
        getMvpView().showCurrentGameLevel(level);


        updateTime = new CompositeDisposable();

        List<Integer> myArray = new ArrayList<>();
        for (int i = 0; i < resourceList.length; i++)
            myArray.add(resourceList[i]);

        // Tạo ra danh sách card
        memoryCards = gamePlayUseCase.createListCard(myArray);
        completedCount = 0;

        // Nhỏ nhất
        if(memoryCards.size() == 20){
            getMvpView().showGame(memoryCards, 5);
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

        startGameTimer();

    }

    @Override
    public void checkCard(int firstIndex, int secondIndex) {
        if(memoryCards.get(firstIndex).resourceId == memoryCards.get(secondIndex).resourceId){
            if(completedCount == memoryCards.size() -2){
                updateTime.dispose();
                getMvpView().showVictory();
                return;
            }

            Observable.defer(() -> Observable.just("")
                    .delay(600, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                    .doOnNext(ignore -> getMvpView().updateSelectedCardStatus(firstIndex, secondIndex, STATUS_COMPLETE))
                    .doOnNext(ignore -> addPoint(100))
                    .doOnNext(ignore -> completedCount += 2)
                    .doOnNext(ignore -> {
                        memoryCards.get(firstIndex).status = MemoryCard.Status.Complete;
                        memoryCards.get(secondIndex).status = MemoryCard.Status.Complete;
                    }))
                    .subscribeWith(new SimpleEmptyObserver<>());
            return;
        }


        Observable.defer(() -> Observable.just("")
                .delay(600, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .doOnNext(ignore -> getMvpView().updateSelectedCardStatus(firstIndex, secondIndex, STATUS_NONE)))
                .subscribeWith(new SimpleEmptyObserver<>());
    }

    @Override
    public void openRandom() {
        List<Integer> listTemp = new ArrayList<>();

        for (int i = 0; i < memoryCards.size(); i++) {
            if(memoryCards.get(i).status != MemoryCard.Status.Complete){
                listTemp.add(i);
            }
        }

        if(listTemp.size() < 2){
            return;
        }

        Collections.shuffle(listTemp);

        int firstItem = listTemp.get(0);
        int secondItem = listTemp.get(1);

        getMvpView().toolFaceUp(firstItem, secondItem);

    }

    @Override
    public void removeRandomCouple() {

        // Tạo ra 1 danh sách tạm chứa tất cả các index còn lại của thẻ
        List<Integer> listIndexOfCard = new ArrayList<>();
        for (int i = 0; i < memoryCards.size(); i++) {
            if(memoryCards.get(i).status != MemoryCard.Status.Complete){
                listIndexOfCard.add(i);
            }
        }

        // Nếu số lượng ít hơn 2, có nghĩa là game đã kết thúc
        if(listIndexOfCard.size() < 2){
            return;
        }

        // Random danh sách index
        Collections.shuffle(listIndexOfCard);

        // Lấy ra index đầu tiên và đi tìm index của card có cùng resourceId
        int firstIndex = listIndexOfCard.get(0);
        MemoryCard current = memoryCards.get(firstIndex);

        int secondIndex = -1;

        for (int i = 1; i < listIndexOfCard.size(); i++) {
            secondIndex = listIndexOfCard.get(i);

            MemoryCard tempCard = memoryCards.get(secondIndex);
            if(current.resourceId == tempCard.resourceId){

                // Trả về index của 1 cặp vừa tìm đc
                getMvpView().toolRemoveCouple(firstIndex, secondIndex) ;
                addPoint(100);
                completedCount += 2;
                memoryCards.get(firstIndex).status = MemoryCard.Status.Complete;
                memoryCards.get(secondIndex).status = MemoryCard.Status.Complete;
                return;
            }
        }
    }

    @Override
    public void addTime() {
        settingTime += 5;
    }

    private void addPoint(int point) {
        currentPoint += point;
        getMvpView().showPoint(currentPoint);
    }

    private void startGameTimer(){
        settingTime = gameConfigUseCase.getSettingTime();
        startTime = System.currentTimeMillis();

        updateTime.add(Observable.interval(
                50,
                500,
                TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new SimpleObserver<Long>() {
                    @Override
                    public void onResponse(@NonNull Long aLong) throws Exception {
                        long currentTime = (System.currentTimeMillis() - startTime)/1000;
                        long timeLeft = settingTime-currentTime;

                        if(timeLeft <= 0){
                            getMvpView().showLose();
                            return;
                        }

                        getMvpView().updateGameTime(timeLeft, settingTime);
                    }

                    @Override
                    public void onResponseError(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                }));


    }
}
