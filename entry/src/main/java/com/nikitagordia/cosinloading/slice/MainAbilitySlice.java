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

package com.nikitagordia.cosinloading.slice;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Checkbox;
import ohos.agp.components.RadioContainer;
import ohos.agp.components.Slider;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;
import com.nikitagordia.cosin.Cosin;
import com.nikitagordia.cosin.coloradapters.ColorAdapterBg;
import com.nikitagordia.cosin.coloradapters.ColorAdapterBr;
import com.nikitagordia.cosin.coloradapters.ColorAdapterGr;
import com.nikitagordia.cosin.coloradapters.ColorAdapterRb;
import com.nikitagordia.cosin.coloradapters.ColorAdapterRg;
import com.nikitagordia.cosin.coloradapters.DefaultColorAdapterGb;
import com.nikitagordia.cosin.textadapters.DefaultBinaryTextAdapter;
import com.nikitagordia.cosin.textadapters.WordTextAdapter;
import com.nikitagordia.cosinloading.ResourceTable;

/**
 * Renders the UI-ability_main and initialises the views.
 */
public class MainAbilitySlice extends AbilitySlice {
    private static final int BINARY = 0;
    private static final int WORD = 1;
    private static final int PI_BY_2 = 0;
    private static final int PI = 1;
    private static final int PI_2 = 2;
    private static final int PI_5 = 3;
    private static final int PI_10 = 4;
    private static final int COLOR_ADAPTER_RB = 5;
    private static final int COLOR_ADAPTER_BR = 4;
    private static final int COLOR_ADAPTER_RG = 2;
    private static final int COLOR_ADAPTER_GR = 3;
    private static final int COLOR_ADAPTER_BG = 1;
    private static final int COLOR_ADAPTER_GB = 0;
    private Checkbox isLoading;
    private Cosin cosin;
    private Text tvRectWidth;
    private Slider rectWidth;
    private Text tvViewWidth;
    private Slider viewWidth;
    private Text tvViewHeight;
    private Slider viewHeight;
    private RadioContainer period;
    private Text tvSpeed;
    private Slider speed;
    private RadioContainer colorAdapter;
    private Text tvOffset;
    private Slider offset;
    private RadioContainer rectText;
    private TextField etText;
    private Checkbox dir;
    private Button stop;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        isLoading = (Checkbox) findComponentById(ResourceTable.Id_is_loading);
        cosin = (Cosin) findComponentById(ResourceTable.Id_cosin);
        tvRectWidth = (Text) findComponentById(ResourceTable.Id_tv_rect_width);
        rectWidth = (Slider) findComponentById(ResourceTable.Id_rect_width);
        tvViewWidth = (Text) findComponentById(ResourceTable.Id_tv_view_width);
        viewWidth = (Slider) findComponentById(ResourceTable.Id_view_width);
        tvViewHeight = (Text) findComponentById(ResourceTable.Id_tv_view_height);
        viewHeight = (Slider) findComponentById(ResourceTable.Id_view_height);
        period = (RadioContainer) findComponentById(ResourceTable.Id_period);
        tvSpeed = (Text) findComponentById(ResourceTable.Id_tv_speed);
        speed = (Slider) findComponentById(ResourceTable.Id_speed);
        colorAdapter = (RadioContainer) findComponentById(ResourceTable.Id_color_adapter);
        tvOffset = (Text) findComponentById(ResourceTable.Id_tv_offset);
        offset = (Slider) findComponentById(ResourceTable.Id_offset);
        rectText = (RadioContainer) findComponentById(ResourceTable.Id_rect_text);
        etText = (TextField) findComponentById(ResourceTable.Id_et_text);
        dir = (Checkbox) findComponentById(ResourceTable.Id_dir);
        stop = (Button) findComponentById(ResourceTable.Id_stop);
        loadView();
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    private void loadView() {
        isLoading.setChecked(cosin.isLoadingData());
        isLoading.setCheckedStateChangedListener((absButton, b) -> cosin.setLoadingData(b));
        tvRectWidth.setText(partRev(cosin.getRectWidth(), cosin.limRectWidth) + " ");
        rectWidth.setProgressValue(partRev(cosin.getRectWidth(), cosin.limRectWidth));
        rectWidth.setValueChangedListener(new Slider.ValueChangedListener() {
            @Override
            public void onProgressUpdated(Slider slider, int i, boolean b) {
                cosin.setRectWidth(part(i, cosin.limRectWidth));
                tvRectWidth.setText(part(i, cosin.limRectWidth) + "");
            }

            @Override
            public void onTouchStart(Slider slider) {
                //not used
            }

            @Override
            public void onTouchEnd(Slider slider) {
                //not used
            }
        });
        tvViewWidth.setText(partRev(cosin.getViewWidth(), cosin.limLayoutWidth));
        viewWidth.setProgressValue(partRev(cosin.getViewWidth(), cosin.limLayoutWidth));
        viewWidth.setValueChangedListener(new Slider.ValueChangedListener() {
            @Override
            public void onProgressUpdated(Slider slider, int i, boolean b) {
                cosin.setLayoutWidth(part(i, cosin.limLayoutWidth));
                tvViewWidth.setText(part(i, cosin.limLayoutWidth) + "");
            }

            @Override
            public void onTouchStart(Slider slider) {
                //not used
            }

            @Override
            public void onTouchEnd(Slider slider) {
                //not used
            }
        });
        tvViewHeight.setText(partRev(cosin.getViewHeight(), cosin.limLayoutHeight) + "");
        viewHeight.setProgressValue(partRev(cosin.getViewHeight(), cosin.limLayoutHeight));
        viewHeight.setValueChangedListener(new Slider.ValueChangedListener() {
            @Override
            public void onProgressUpdated(Slider slider, int i, boolean b) {
                cosin.setLayoutHeight(part(i, cosin.limLayoutHeight));
                tvViewHeight.setText(part(i, cosin.limLayoutHeight) + "");
            }

            @Override
            public void onTouchStart(Slider slider) {
                //not used
            }

            @Override
            public void onTouchEnd(Slider slider) {
                //not used
            }
        });
        period.mark(1);
        period.setMarkChangedListener((radioContainer, i) -> {
            switch (i) {
                case PI_BY_2 :
                    cosin.setPeriod(Math.PI / 2d);
                    break;
                case PI :
                    cosin.setPeriod(Math.PI);
                    break;
                case PI_2 :
                    cosin.setPeriod(Math.PI * 2d);
                    break;
                case PI_5 :
                    cosin.setPeriod(Math.PI * 5d);
                    break;
                case PI_10 :
                    cosin.setPeriod(Math.PI * 10d);
                    break;
                default :
                    break;
            }
        });
        tvSpeed.setText(Math.round(partRev(cosin.getSpeed(), cosin.limSpeed)) + "");
        speed.setProgressValue((int) partRev(cosin.getSpeed(), cosin.limSpeed));
        speed.setValueChangedListener(new Slider.ValueChangedListener() {
            @Override
            public void onProgressUpdated(Slider slider, int i, boolean b) {
                cosin.setSpeed(part(i, cosin.limSpeed));
                tvSpeed.setText(Math.round(partRev(cosin.getSpeed(), cosin.limSpeed)) + "");
            }

            @Override
            public void onTouchStart(Slider slider) {
                //not used
            }

            @Override
            public void onTouchEnd(Slider slider) {
                //not used
            }
        });
        colorAdapter.mark(0);
        colorAdapter.setMarkChangedListener((radioContainer, i) -> {
            switch (i) {
                case COLOR_ADAPTER_RB :
                    cosin.setColorAdapter(new ColorAdapterRb());
                    break;
                case COLOR_ADAPTER_BR :
                    cosin.setColorAdapter(new ColorAdapterBr());
                    break;
                case COLOR_ADAPTER_RG :
                    cosin.setColorAdapter(new ColorAdapterRg());
                    break;
                case COLOR_ADAPTER_GR :
                    cosin.setColorAdapter(new ColorAdapterGr());
                    break;
                case COLOR_ADAPTER_BG :
                    cosin.setColorAdapter(new ColorAdapterBg());
                    break;
                case COLOR_ADAPTER_GB :
                    cosin.setColorAdapter(new DefaultColorAdapterGb());
                    break;
                default :
                    break;
            }
        });
        tvOffset.setText(partRev(cosin.getOffset(), cosin.limOffset) + "");
        offset.setProgressValue((int) partRev(cosin.getOffset(), cosin.limOffset));
        offset.setValueChangedListener(new Slider.ValueChangedListener() {
            @Override
            public void onProgressUpdated(Slider slider, int i, boolean b) {
                cosin.setOffset(part(i, cosin.limOffset));
                tvOffset.setText(part(i, cosin.limOffset) + "");
            }

            @Override
            public void onTouchStart(Slider slider) {
                //not used
            }

            @Override
            public void onTouchEnd(Slider slider) {
                //not used
            }
        });
        rectText.mark(ResourceTable.Id_binary);
        rectText.setMarkChangedListener((radioContainer, i) -> {
            switch (i) {
                case BINARY :
                    if (etText != null) {
                        etText.setHint("");
                    }
                    cosin.setTextAdapter(new DefaultBinaryTextAdapter());
                    break;
                case WORD :
                    if (etText != null) {
                        etText.setHint(getString(ResourceTable.String_hint_text));
                    }
                    cosin.setTextAdapter(new WordTextAdapter(etText.getText()));
                    break;
                default :
                    break;
            }
        });
        etText.addTextObserver((s, i, i1, i2) -> cosin.setTextAdapter(new WordTextAdapter(s)));
        dir.setCheckedStateChangedListener((absButton, b) -> cosin.setDirectionRight(b));
        stop.setClickedListener(component -> cosin.setEnd());
    }

    private int part(int x, Cosin.Limit<Integer> lim) {
        return (x * (lim.max - lim.min)) / 100 + lim.min;
    }

    private double part(double x, Cosin.Limit<Double> lim) {
        return ((x * (lim.max - lim.min)) / 100d + lim.min);
    }

    private int partRev(int x, Cosin.Limit<Integer> lim) {
        return ((x - lim.min) * 100) / (lim.max - lim.min);
    }

    private double partRev(double x, Cosin.Limit<Double> lim) {
        return ((x - lim.min) * 100d) / (lim.max - lim.min);
    }
}
