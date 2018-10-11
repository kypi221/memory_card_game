package com.kypi.demoproject.data.repository;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.kypi.demoproject.domain.DomainConfig;
import com.kypi.demoproject.domain.repository.GameConfigRepository;

import javax.inject.Inject;

public class GameConfigRepositoryImpl implements GameConfigRepository {

    private static final String PREF_GAME_TIME = "PREF_GAME_TIME";
    private static final String PREF_GAME_LEVEL = "PREF_GAME_LEVEL";



    protected final SharedPreferences sharedPreferences;
    protected final Gson gson;

    @Inject
    public GameConfigRepositoryImpl(SharedPreferences sharedPreferences, Gson gson) {
        this.sharedPreferences = sharedPreferences;
        this.gson = gson;
    }

    @Override
    public void setSettingTime(long time) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(PREF_GAME_TIME, time);
        editor.commit();
    }

    @Override
    public long getSettingTime() {
        return sharedPreferences.getLong(PREF_GAME_TIME, DomainConfig.Time.TIME_30);
    }

    @Override
    public void setSettingLevel(int level) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(PREF_GAME_LEVEL, level);
        editor.commit();
    }

    @Override
    public int getSettingLevel() {
        return sharedPreferences.getInt(PREF_GAME_LEVEL,DomainConfig.Level.LEVEL_EASY);
    }
}
