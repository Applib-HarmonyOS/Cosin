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

package com.nikitagordia.cosinloading;

import static org.junit.Assert.*;
import ohos.aafwk.ability.delegation.AbilityDelegatorRegistry;
import ohos.agp.components.Attr;
import ohos.agp.components.AttrSet;
import ohos.app.Context;
import com.nikitagordia.cosin.Cosin;
import org.junit.Before;
import org.junit.Test;
import java.util.Optional;

public class CosinTest {
    private Cosin.ColorAdapter colorAdapter;
    private Cosin.TextAdapter textAdapter;
    private Cosin cosin;

    @Before
    public void setUp() {
        Context context;
        AttrSet attrSet;
        context = AbilityDelegatorRegistry.getAbilityDelegator().getAppContext();
        attrSet = new AttrSet() {
            @Override
            public Optional<String> getStyle() {
                return Optional.empty();
            }

            @Override
            public int getLength() {
                return 0;
            }

            @Override
            public Optional<Attr> getAttr(int i) {
                return Optional.empty();
            }

            @Override
            public Optional<Attr> getAttr(String s) {
                return Optional.empty();
            }
        };
        cosin = new Cosin(context, attrSet);
    }

    @Test
    public void testGetSpeed() {
        double speed = cosin.getSpeed();
        assertTrue(0.005d == speed);
    }

    @Test
    public void testSetSpeed() {
        double speed = 0.01d;
        cosin.setSpeed(speed);
        assertTrue(speed == cosin.getSpeed());
    }

    @Test
    public void testIsLoadingData() {
        assertFalse(cosin.isLoadingData());
    }

    @Test
    public void testSetLoadingData() {
        cosin.setLoadingData(true);
        assertTrue(cosin.isLoadingData());
    }

    @Test
    public void testGetRectWidth() {
        int rectWidth = 60;
        assertEquals(rectWidth, cosin.getRectWidth());
    }

    @Test
    public void testSetRectWidth() {
        int rectWidth = 50;
        cosin.setRectWidth(rectWidth);
        assertEquals(cosin.getRectWidth(), rectWidth);
    }

    @Test
    public void testGetPeriod() {
        double period = Math.PI;
        assertTrue(period == cosin.getPeriod());
    }

    @Test
    public void testSetPeriod() {
        double period = Math.PI / 2d;
        cosin.setPeriod(period);
        assertTrue(period == cosin.getPeriod());
    }

    @Test
    public void testGetViewWidth() {
        int viewWidth = 0;
        assertEquals(viewWidth, cosin.getViewWidth());
    }

    @Test
    public void testSetLayoutWidth() {
        int width = 400;
        cosin.setLayoutWidth(width);
        assertEquals(width, cosin.getViewWidth());
    }

    @Test
    public void testGetViewHeight() {
        int viewHeight = 0;
        assertEquals(viewHeight, cosin.getViewHeight());
    }

    @Test
    public void testSetLayoutHeight() {
        int height = 400;
        cosin.setLayoutHeight(height);
        assertEquals(height, cosin.getViewHeight());
    }

    @Test
    public void testGetColorAdapter() {
        assertNotNull(cosin.getColorAdapter());
    }

    @Test
    public void testSetColorAdapter() {
        cosin.setColorAdapter(colorAdapter);
        assertEquals(colorAdapter, cosin.getColorAdapter());
    }

    @Test
    public void testGetOffset() {
        double offset = 1.5d;
        assertTrue(offset == cosin.getOffset());
    }

    @Test
    public void testSetOffset() {
        double offset = 1.0d;
        cosin.setOffset(offset);
        assertTrue(offset == cosin.getOffset());
    }

    @Test
    public void testGetTextAdapter() {
        assertNotNull(cosin.getTextAdapter());
    }

    @Test
    public void testSetTextAdapter() {
        cosin.setTextAdapter(textAdapter);
        assertEquals(textAdapter, cosin.getTextAdapter());
    }

    @Test
    public void testIsDirectionRight() {
        assertFalse(cosin.isDirectionRight());
    }

    @Test
    public void testSetDirectionRight() {
        cosin.setDirectionRight(true);
        assertEquals(true, cosin.isDirectionRight());
    }
}