/*
 * Copyright (C) 2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yizlan.gelato.canonical;

import com.yizlan.gelato.canonical.exception.I18nException;
import com.yizlan.gelato.canonical.exception.UnaryException;
import com.yizlan.gelato.canonical.support.I18nAssert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AssertTest {

    @Test
    void testCode() {
        try {
            I18nAssert.isTrue(false, "str");
        } catch (I18nException e) {
            String code = e.getCode();
            assertEquals("str", code);
        }
    }

    @Test
    void testUnaryException() {
        try {
            UnaryException<String> exception =null;
            I18nAssert.isTrue(false, exception, 1, 2, 2);
        } catch (I18nException e) {
            String code = e.getCode();
            assertNull(code);
        }
    }
}
