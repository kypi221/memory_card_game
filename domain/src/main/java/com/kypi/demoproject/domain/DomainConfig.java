package com.kypi.demoproject.domain;

public class DomainConfig {

    public interface Time {
        int TIME_30 = 30;
        int TIME_45 = 45;
        int TIME_60 = 60;
        int TIME_75 = 75;
        int TIME_90 = 90;
    }

    public interface Level {
        int LEVEL_BEGINNER = 1;
        int LEVEL_EASY = 2;
        int LEVEL_MEDIUM = 3;
        int LEVEL_HARD = 4;
        int LEVEL_EX_HARD = 5;
    }
}
