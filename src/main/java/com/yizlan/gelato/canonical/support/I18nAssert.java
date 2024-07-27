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

package com.yizlan.gelato.canonical.support;

import com.yizlan.gelato.canonical.exception.UnaryException;
import com.yizlan.gelato.canonical.fluent.CodeAssert;
import com.yizlan.gelato.canonical.fluent.EnumAssert;
import com.yizlan.gelato.canonical.panic.I18nException;

/**
 * i18n assert
 *
 * @author Zen Gershon
 * @since 2.0
 */
public abstract class I18nAssert {

    /**
     * code assert
     *
     * @param condition boolean
     * @return {@link CodeAssert}
     */
    protected static CodeAssert codeAssert(final boolean condition) {
        return (code, args) -> {
            if (condition) {
                throwException(code, args);
            }
        };
    }

    /**
     * enum assert
     *
     * @param condition boolean
     * @return {@link EnumAssert}
     */
    protected static EnumAssert enumAssert(final boolean condition) {
        return (exception, args) -> {
            if (condition) {
                throwException(exception, args);
            }
        };
    }

    /**
     * throw i18n exception
     *
     * @param code error code
     * @param args placeholder parameters
     */
    public static void throwException(final String code, final Object... args) {
        throw new I18nException(code, args);
    }

    /**
     * throw i18n exception that supports i18n
     *
     * @param exception error enum
     * @param args      placeholder parameters
     */
    public static void throwException(final UnaryException<String> exception, final Object... args) {
        throw new I18nException(exception, args);
    }
}
