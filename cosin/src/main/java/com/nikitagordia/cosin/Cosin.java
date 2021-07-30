/*
 * Copyright (C) 2020-21 Application Library Engineering Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nikitagordia.cosin;

import ohos.agp.components.AttrSet;
import ohos.agp.components.Component;
import ohos.agp.render.BlendMode;
import ohos.agp.render.Canvas;
import ohos.agp.render.Paint;
import ohos.agp.text.Font;
import ohos.agp.utils.Color;
import ohos.app.Context;
import com.nikitagordia.cosin.colorAdapters.DefaultColoradapterGB;
import com.nikitagordia.cosin.textAdapters.DefaultBinaryTextAdapter;

/**
 * Created by nikitagordia on 05.03.18.
 */
public class Cosin extends Component implements Component.DrawTask {
    private static final double DOUBLE_PI = Math.PI * 2d;
    private static final String NOT_IN_LIMIT = " not in limit ";
    public Limit<Integer> limRectWidth = new Limit<>(5, 100);
    public Limit<Double> limSpeed = new Limit<>(0.0001d, 0.05d);
    public Limit<Integer> limLayoutWidth = new Limit<>(0, 1000);
    public Limit<Integer> limLayoutHeight = new Limit<>(0, 1000);
    public Limit<Double> limOffset = new Limit<>(0d, 10d);
    private ColorAdapter colorAdapter;
    private TextAdapter textAdapter;
    private OnEnd callback;
    private int count;
    private double angle;
    private double heightMid;
    private double endingPart;
    private Paint paint;
    private Paint paintBack;
    private Paint paintText;
    private double[] cosBuff;
    private char[] textBuff;
    private float[] widthBuff;
    private float[] peekBuff;
    private long tm;
    private int rectWidth = 60;
    private double period = Math.PI;
    private double speed = 0.005d;
    private double offset = 1.5d;
    private double endingSpeed = 0.0008d;
    private boolean directionRight = false;
    private Limit<Double> limPeriod = new Limit<>(0d, Math.PI * 10);
    private boolean isLoadingData;
    private boolean isEnding;

    /**
     * Constructor defined to construct the default view.
     *
     * @param context receives the context from the MainAbilitySlice
     * @param attributesSet receives the Attribute Set from the MainAbilitySlice
     */
    public Cosin(Context context, AttrSet attributesSet) {
        super(context, attributesSet);
        angle = 0;
        endingPart = 1d;
        isEnding = false;
        paint = new Paint();
        paintBack = new Paint();
        paintText = new Paint();
        paintText.setTextSize(rectWidth / 2);
        paintText.setFont(Font.DEFAULT_BOLD);
        paintBack.setColor(new Color(Color.argb(255, 255, 255, 255)));
        paint.setColor(Color.GREEN);
        colorAdapter = new DefaultColoradapterGB();
        textAdapter = new DefaultBinaryTextAdapter();
        addDrawTask(this);
    }

    private void updateProp() {
        double intervalRad;
        paintText.setTextSize(rectWidth / 2);
        count = getWidth() / rectWidth;
        intervalRad = period / count;
        heightMid = getHeight() / 2d;
        cosBuff = new double[count];
        textBuff = new char[count];
        widthBuff = new float[count];
        peekBuff = new float[count + 1];
        for (int i = 0; i <= count; i++) {
            peekBuff[i] = rectWidth * (float) i;
        }
        for (int i = 0; i < count; i++) {
            cosBuff[i] = (intervalRad * i + intervalRad * (i + 1)) / 2d;
            updateText(i);
            widthBuff[i] = peekBuff[i] + rectWidth / 2f - paintText.measureText(textBuff[i] + "") / 2f;
        }
    }

    private void updateText(int pos) {
        textBuff[pos] = textAdapter.getString(pos);
    }

    @Override
    public void onDraw(Component component, Canvas canvas) {
        int alphaText;
        double part;
        updateProp();
        canvas.drawColor(colorAdapter.getBackgroundColor(), BlendMode.COLOR);
        if (!directionRight) {
            angle += ((double) System.currentTimeMillis() - tm) * speed;
        } else {
            angle -= ((double) System.currentTimeMillis() - tm) * speed;
        }
        if (angle > DOUBLE_PI) {
            angle -= DOUBLE_PI;
        }
        if (angle < -DOUBLE_PI) {
            angle += DOUBLE_PI;
        }
        if (isEnding) {
            isLoadingData = false;
            endingPart -= ((double) System.currentTimeMillis() - tm) * endingSpeed;
            if (endingPart < 0 && callback != null) {
                callback.onEnd();
            }
        }
        tm = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            part = (Math.cos(cosBuff[i] + angle) + offset) / (offset + 1d);
            paint.setColor(new Color(colorAdapter.calcColor(i, part)));
            canvas.drawRect(peekBuff[i], (float) (getHeight() * (1d - part * endingPart)),
                    peekBuff[i + 1], getHeight(), paint);
            if (isLoadingData) {
                alphaText = (int) (Math.max(0, 1000d * part - 750d));
                paintText.setColor(new Color(Color.argb(alphaText, 255, 255, 255)));
                if (alphaText == 0) {
                    updateText(i);
                }
                canvas.drawText(paintText, textBuff[i] + "", widthBuff[i], (float) heightMid);
            }
        }
        getContext().getUITaskDispatcher().asyncDispatch(this::invalidate);
    }

    public void setEnd(OnEnd callback) {
        isEnding = true;
        this.callback = callback;
    }

    public void setEnd() {
        isEnding = true;
    }

    public double getSpeed() {
        return speed;
    }

    /**
     * Setter method for speed.
     *
     * @param speed receives the speed from the MainAbilitySlice
     */
    public void setSpeed(double speed) {
        if (!limSpeed.inLimit(speed)) {
            throw new IndexOutOfBoundsException(speed + NOT_IN_LIMIT + limSpeed);
        }
        this.speed = speed;
        updateProp();
    }

    public boolean isLoadingData() {
        return isLoadingData;
    }

    public void setLoadingData(boolean loadingData) {
        isLoadingData = loadingData;
        updateProp();
    }

    public int getRectWidth() {
        return rectWidth;
    }

    /**
     * Setter method for rectangle width.
     *
     * @param rectWidth receives the width of a rectangle from the MainAbilitySlice
     */
    public void setRectWidth(int rectWidth) {
        if (!limRectWidth.inLimit(rectWidth)) {
            throw new IndexOutOfBoundsException(rectWidth + NOT_IN_LIMIT + limRectWidth);
        }
        this.rectWidth = rectWidth;
        updateProp();
    }

    public double getPeriod() {
        return period;
    }

    /**
     * Setter method for period.
     *
     * @param period receives the period from the MainAbilitySlice
     */
    public void setPeriod(double period) {
        if (!limPeriod.inLimit(period)) {
            throw new IndexOutOfBoundsException(period + NOT_IN_LIMIT + limPeriod);
        }
        this.period = period;
        updateProp();
    }

    public int getViewWidth() {
        return getWidth();
    }

    /**
     * Setter method for layout width of the overall figure.
     *
     * @param width receives the layout width from the MainAbilitySlice
     */
    public void setLayoutWidth(int width) {
        if (!limLayoutWidth.inLimit(width)) {
            throw new IndexOutOfBoundsException(width + NOT_IN_LIMIT + limLayoutWidth);
        }
        setWidth(width);
        postLayout();
        updateProp();
    }

    public int getViewHeight() {
        return getHeight();
    }

    /**
     * Setter method for layout height of the overall figure.
     *
     * @param height receives the layout height from the MainAbilitySlice
     */
    public void setLayoutHeight(int height) {
        if (!limLayoutHeight.inLimit(height)) {
            throw new IndexOutOfBoundsException(height + NOT_IN_LIMIT + limLayoutHeight);
        }
        setHeight(height);
        postLayout();
        updateProp();
    }

    public ColorAdapter getColorAdapter() {
        return colorAdapter;
    }

    /**
     * Setter method for color adapter of the view.
     *
     * @param colorAdapter receives the color adapter from the MainAbilitySlice
     */
    public void setColorAdapter(ColorAdapter colorAdapter) {
        this.colorAdapter = colorAdapter;
        postLayout();
        updateProp();
    }

    public double getOffset() {
        return offset;
    }

    public TextAdapter getTextAdapter() {
        return textAdapter;
    }

    public void setTextAdapter(TextAdapter textAdapter) {
        this.textAdapter = textAdapter;
    }

    public void setOffset(double offset) {
        this.offset = offset;
        updateProp();
    }

    public boolean isDirectionRight() {
        return directionRight;
    }

    public void setDirectionRight(boolean directionRight) {
        this.directionRight = directionRight;
    }

    /**
     * Class defined to check whether the parameters are within the range.
     *
     * @param <T> takes any general comparable parameter
     */
    public class Limit<T extends Comparable<T>> {
        public T min;
        public T max;

        public Limit(T min, T max) {
            this.min = min;
            this.max = max;
        }

        public boolean inLimit(T t) {
            return min.compareTo(t) < 1 && max.compareTo(t) > -1;
        }

        @Override
        public String toString() {
            return "[" + min + ", " + max + "]";
        }
    }

    /**
     * Interface to define the onEnd function to end the view.
     */
    public interface OnEnd {
        void onEnd();
    }

    /**
     * Interface to define methods for color adapters.
     */
    public interface ColorAdapter {
        int getBackgroundColor();

        int calcColor(int numOfRect, double percentOfHeight);
    }

    /**
     * Interface to define getString method for text adapters.
     */
    public interface TextAdapter {
        char getString(int numOfRect);
    }
}

