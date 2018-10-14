package com.kypi.demoproject.utils.helper;

import com.wajahatkarim3.easyflipview.EasyFlipView;

public class CardHelper {

    /**
     * Lật card lên
     * @param easyFlipView
     */
    public static final void faceUpCard(EasyFlipView easyFlipView){
       faceUpCard(easyFlipView, 400);

    }

    /**
     * Lật card lên
     * @param easyFlipView
     * @param duration
     */
    public static final void faceUpCard(EasyFlipView easyFlipView, int duration){
        if(easyFlipView == null){
            return;
        }

        if(easyFlipView.isBackSide()){
            easyFlipView.setFlipDuration(duration);
            easyFlipView.flipTheView();
            return;
        }

    }


    /**
     *  Úp card xuống
     *
     * @param easyFlipView
     */
    public static final void faceDownCard(EasyFlipView easyFlipView){
        faceDownCard(easyFlipView, 400);

    }


    /**
     * Úp card xuống
     * @param easyFlipView
     * @param duration
     */
    public static final void faceDownCard(EasyFlipView easyFlipView, int duration){
        if(easyFlipView == null){
            return;
        }

        if(!easyFlipView.isBackSide()){
            easyFlipView.setFlipDuration(duration);
            easyFlipView.flipTheView();
            return;
        }

    }
}
