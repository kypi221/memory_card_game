package com.kypi.demoproject.app.sound;

import android.content.Context;
import android.media.MediaPlayer;

import com.kypi.demoproject.R;

/**
 * Created by Khoa on 5/6/2017.
 */

public class SoundHelper {

    public static final int SOUND_GAME_CORRECT = 0;

    private MediaPlayer[] listMediaPlayers = new MediaPlayer[1];

    private MediaPlayer bgSound;

    private int playingSoundIndex = -1;

    public SoundHelper(Context context) {
        listMediaPlayers[SOUND_GAME_CORRECT] = MediaPlayer.create(context, R.raw.correct_answer);
        bgSound = MediaPlayer.create(context, R.raw.bg_music);
    }

    public void playBgSound(){
        bgSound.seekTo(0);
        bgSound.start();
    }

    public void stopBgSound(){
        bgSound.pause();
    }

    public void playSound(int soundID) {
        if (playingSoundIndex != -1 && listMediaPlayers[playingSoundIndex].isPlaying()) {
            listMediaPlayers[playingSoundIndex].pause();
        }

        playingSoundIndex = soundID;

        listMediaPlayers[playingSoundIndex].seekTo(0);
        listMediaPlayers[playingSoundIndex].start();
    }


    public void pauseCurrentSound() {
        if (playingSoundIndex != -1 && listMediaPlayers[playingSoundIndex].isPlaying()) {
            listMediaPlayers[playingSoundIndex].pause();
        }
    }


}
