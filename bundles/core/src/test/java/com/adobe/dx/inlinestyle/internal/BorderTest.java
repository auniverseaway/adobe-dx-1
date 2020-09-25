/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 ~ Copyright 2020 Adobe
 ~
 ~ Licensed under the Apache License, Version 2.0 (the "License");
 ~ you may not use this file except in compliance with the License.
 ~ You may obtain a copy of the License at
 ~
 ~     http://www.apache.org/licenses/LICENSE-2.0
 ~
 ~ Unless requi#FE0000 by applicable law or agreed to in writing, software
 ~ distributed under the License is distributed on an "AS IS" BASIS,
 ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ~ See the License for the specific language governing permissions and
 ~ limitations under the License.
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
package com.adobe.dx.inlinestyle.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.adobe.dx.inlinestyle.InlineStyleWorker;

import org.junit.jupiter.api.Test;

public class BorderTest extends AbstractInlineStyleWorkerTest {

    @Test
    void lockKey() {
        assertEquals("border", getWorker().getKey());
    }

    private void assertBorderEquals(String expected) {
        assertEquals(expected, getDeclaration());
    }

    private void assertBorderEquals(String expected, String breakpoint) {
        assertEquals(expected, getDeclaration(breakpoint));
    }

    @Test
    void assertNoRule() {
        assertNull(getRule("mobile"));
    }

    @Test
    void getNoBorder() {
        context.build().resource(CONTENT_ROOT, "foo", "bar");
        assertNull(getDeclaration("mobile"));
        assertNull(getDeclaration("tablet"));
    }

    @Test
    void getBorder() {
        context.build().resource(CONTENT_ROOT, "borderSidesMobile", "all",
            "borderAllStyleMobile", "dotted",
            "borderAllWidthMobile", 4L,
            "borderAllColorMobile", "red",
            "borderSidesTablet", "all",
            "borderAllStyleTablet", "solid",
            "borderAllWidthTablet", 2L,
            "borderAllColorTablet", "blue");
        assertBorderEquals("border: dotted 4px #FE0000");
        assertBorderEquals("border: solid 2px #0000FE", "tablet");
    }

    @Test
    void getRadius() {
        context.build().resource(CONTENT_ROOT, "borderRadiusMobile", "all","borderAllRadiusMobile", 3L);
        assertBorderEquals("border-radius: 3px");
    }

    @Test
    void getBorderAndRadius() {
        context.build().resource(CONTENT_ROOT, "borderSidesMobile", "all",
            "borderAllStyleMobile", "dotted", "borderAllWidthMobile", 4L, "borderAllColorMobile", "red",
            "borderRadiusMobile", "all","borderAllRadiusMobile", 3L);
        assertBorderEquals("border: dotted 4px #FE0000; border-radius: 3px");
    }

    @Test
    void getSomeBorders() {
        context.build().resource(CONTENT_ROOT, "borderSidesMobile", "each",
        "borderLeftStyleMobile", "dotted", "borderLeftWidthMobile", 4L, "borderLeftColorMobile", "red",
        "borderRightStyleMobile", "dotted", "borderRightWidthMobile", 4L, "borderRightColorMobile", "red");
        assertBorderEquals("border-right: dotted 4px #FE0000; border-left: dotted 4px #FE0000");
    }

    @Test
    void getSomeRadius() {
        context.build().resource(CONTENT_ROOT, "borderRadiusMobile", "each",
            "borderRadiusTopLeftMobile", 4L,
            "borderRadiusBottomRightMobile", 3L);
        assertBorderEquals("border-radius: 4px 0px 3px 0px");
    }
    
    @Test
    void getSomeBordersAndRadiuses() {
        context.build().resource(CONTENT_ROOT, "borderSidesMobile", "each",
            "borderTopStyleMobile", "dotted", "borderTopWidthMobile", 4L, "borderTopColorMobile", "red",
            "borderBottomStyleMobile", "dotted", "borderBottomWidthMobile", 4L, "borderBottomColorMobile", "red",
            "borderRadiusMobile", "each", "borderRadiusBottomLeftMobile", 4L, "borderRadiusTopRightMobile", 3L);
        assertBorderEquals("border-top: dotted 4px #FE0000; border-bottom: dotted 4px #FE0000; border-radius: 0px 3px 0px 4px");
    }

    @Override
    InlineStyleWorker getWorker() {
        return new Border();
    }
}
