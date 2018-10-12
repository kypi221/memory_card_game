package com.kypi.demoproject.domain.usecase;

import com.kypi.demoproject.domain.DomainConfig;
import com.kypi.demoproject.domain.entities.MemoryCard;
import com.kypi.demoproject.domain.repository.GameConfigRepository;
import com.kypi.demoproject.domain.scheduler.SchedulerProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class GamePlayUseCase extends BaseUseCase {

    private final GameConfigRepository gameConfigRepository;

    @Inject
    public GamePlayUseCase(SchedulerProvider schedulerProvider, GameConfigRepository gameConfigRepository) {
        super(schedulerProvider);
        this.gameConfigRepository = gameConfigRepository;
    }


    public List<MemoryCard> createListCard(List<Integer> resourceIdList){

        List<MemoryCard> memoryCardList = new ArrayList<>();

        int level = gameConfigRepository.getSettingLevel();

        int totalSingleCard = 0;
        switch (level){
            case DomainConfig.Level.LEVEL_BEGINNER:
                totalSingleCard = 10;
                break;
            case DomainConfig.Level.LEVEL_EASY:
                totalSingleCard = 15;
                break;
            case DomainConfig.Level.LEVEL_MEDIUM:
                totalSingleCard = 20;
                break;
            case DomainConfig.Level.LEVEL_HARD:
                totalSingleCard = 25;
                break;
            case DomainConfig.Level.LEVEL_EX_HARD:
                totalSingleCard = 30;
                break;
        }


        // Tạo ra 1 danh sách tạm chứa random các hình ảnh;
        Collections.shuffle(resourceIdList);
        MemoryCard memoryCard1 = null;
        MemoryCard memoryCard2 = null;


        // Tạo ra danh sách card
        for (int i = 0; i < totalSingleCard; i++) {
            memoryCard1 = new MemoryCard();
            memoryCard2 = new MemoryCard();

            // Nếu i lớn hơn size của danh sách resource, thì lấy lại những resource đầu
            if(i >= resourceIdList.size()){
                memoryCard1.resourceId = resourceIdList.get(i - resourceIdList.size());
                memoryCard2.resourceId = resourceIdList.get(i - resourceIdList.size());
            }
            else {
                memoryCard1.resourceId = resourceIdList.get(i);
                memoryCard2.resourceId = resourceIdList.get(i);
            }

            // mỗi lần add sẽ add 1 cặp;
            memoryCardList.add(memoryCard1);
            memoryCardList.add(memoryCard2);
        }

        Collections.shuffle(memoryCardList);

        return memoryCardList;

    }
}
