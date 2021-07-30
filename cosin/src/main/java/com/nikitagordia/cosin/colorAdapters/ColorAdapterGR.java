package com.nikitagordia.cosin.colorAdapters;

import ohos.agp.utils.Color;
import com.nikitagordia.cosin.Cosin;

/**
 * Created by nikitagordia on 05.03.18.
 */
public class ColorAdapterGR implements Cosin.ColorAdapter {

    @Override
    public int getBackgroundColor() {
        return Color.TRANSPARENT.getValue();
    }

    @Override
    public int calcColor(int numOfRect, double percentOfHeight) {
        return Color.argb(150, (int) (255 * (1d - percentOfHeight)), (int) (255 * percentOfHeight), 0);
    }
}
