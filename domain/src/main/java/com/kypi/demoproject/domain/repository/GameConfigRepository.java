package com.kypi.demoproject.domain.repository;

public interface GameConfigRepository {

    public void setSettingTime(long time);
    public long getSettingTime();

    public void setSettingLevel(int level);
    public int getSettingLevel();

}
