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

package com.yizlan.gelato.core.support;

import com.yizlan.gelato.core.exception.UnaryException;
import com.yizlan.gelato.core.fluent.FuncAssert;
import com.yizlan.gelato.core.fluent.NumericAssert;
import com.yizlan.gelato.core.panic.DigitalException;

/**
 * digital assert
 *
 * @author Zen Gershon
 * @since 1.0
 */
public abstract class DigitalAssert {

    /**
     * numeric assert
     *
     * @param condition boolean
     * @return {@link NumericAssert}
     */
    protected static NumericAssert numericAssert(final boolean condition) {
        return (code, args) -> {
            if (condition) {
                throwException(code, args);
            }
        };
    }

    /**
     * func assert
     *
     * @param condition boolean
     * @return {@link FuncAssert}
     */
    protected static FuncAssert funcAssert(final boolean condition) {
        return (exception, args) -> {
            if (condition) {
                throwException(exception, args);
            }
        };
    }

    /**
     * throw digital exception
     *
     * @param code error code
     * @param args placeholder parameters
     */
    public static void throwException(final Integer code, final Object... args) {
        throw new DigitalException(code, args);
    }

    /**
     * throw digital exception
     *
     * @param exception error enum
     * @param args      placeholder parameters
     */
    public static void throwException(final UnaryException<Integer> exception, final Object... args) {
        throw new DigitalException(exception, args);
    }
}
