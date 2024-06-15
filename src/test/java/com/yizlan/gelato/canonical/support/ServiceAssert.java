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

/**
 * service assert
 *
 * @author Zen Gershon
 * @since 1.0
 */
public class ServiceAssert extends DigitalAssert {

    /**
     * Assert a boolean expression, throwing an {@exception DigitalException}
     * if {@code expression} evaluates to {@code false}.
     * <pre class="code">Assert.isTrue(i &gt; 0, 123, "placeholder parameters");</pre>
     *
     * @param condition a boolean expression
     * @param code      error code
     * @param args      placeholder parameters
     */
    public static void isTrue(boolean condition, Integer code, Object... args) {
        numericAssert(!condition).throwException(code, args);
    }
}
