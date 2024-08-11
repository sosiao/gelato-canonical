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

package com.yizlan.gelato.canonical.fluent.asserts;

import com.yizlan.gelato.canonical.panic.MetaException;

import java.io.Serializable;

/**
 * This functional interface {@code CodeAssert} defines an assertion operation
 * that can be used to throw a custom exception when a condition is not met.
 * <p>
 * Note: Since version {@code 2.4}, this class has been moved to this package.
 *
 * @author Zen Gershon
 * @since 1.0
 */
@FunctionalInterface
public interface CodeAssert {

    /**
     * Throws an exception with the specified type.
     *
     * @param exceptionClazz The exception class, specifying the type of exception that extends {@code MetaException}.
     * @param code           The exception code, which must be a value of type T.
     * @param args           Optional parameters provided to the exception constructor for customizing the exception
     *                       message
     * @param <T>            A comparable and serializable type used for the exception code.
     */
    <T extends Comparable<T> & Serializable> void throwException(Class<? extends MetaException> exceptionClazz,
                                                                 T code,
                                                                 Object... args);

}
