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

import com.yizlan.gelato.canonical.exception.UnaryException;
import com.yizlan.gelato.canonical.panic.MetaException;

import java.io.Serializable;

/**
 * The functional interface {@code FuncAssert} is used to define an assertion operation
 * that can throw a custom exception when a condition is not met.
 * <p>
 * Note: Since version {@code 2.4}, this class has been moved to this package.
 *
 * @author Zen Gershon
 * @since 2.0
 */
@FunctionalInterface
public interface FuncAssert {

    /**
     * Throw a custom exception that extends {@link MetaException} when a condition is not met.
     *
     * @param exceptionClazz The class of the custom exception that extends {@link MetaException} to be thrown
     * @param exception      The unary operation exception object, used to define how to generate the exception
     *                       message based on parameters.
     * @param args           Optional parameters provided to the exception constructor for customizing the exception
     *                       message.
     * @param <T>            A comparable and serializable type used for the unary operation exception object to be
     *                       thrown.
     */
    <T extends Comparable<T> & Serializable> void throwException(Class<? extends MetaException> exceptionClazz,
                                                                 UnaryException<T> exception,
                                                                 Object... args);

}
