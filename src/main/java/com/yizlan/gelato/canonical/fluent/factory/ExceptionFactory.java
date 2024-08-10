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

package com.yizlan.gelato.canonical.fluent.factory;

import com.yizlan.gelato.canonical.panic.MetaException;

import java.io.Serializable;

/**
 * The Exception Factory interface, designed for creating exception objects with metadata.
 *
 * @param <T> A comparable and serializable type used for the exception code.
 * @param <R> Generic type parameter representing the type of the exception object to be returned,
 *            which must be a subclass of {@code MetaException}.
 * @author Zen Gershon
 * @since 2.3
 */
@FunctionalInterface
public interface ExceptionFactory<T extends Comparable<T> & Serializable, R extends MetaException> {

    /**
     * Creates an exception object of the specified type.
     *
     * @param code The exception code, which must be a value of type T.
     * @param args Additional parameters for constructing the exception message, may be empty.
     * @return Returns a well-formed exception object, the specific type is determined by the caller.
     */
    R create(final T code, final Object... args);

}
