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

package com.yizlan.gelato.core.panic;

import com.yizlan.gelato.core.copier.CodeProvider;

/**
 * digital exception
 *
 * @author Zen Gershon
 * @since 1.0
 */
public class DigitalException extends MetaException {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new digital exception with the specified code and placeholder.
     *
     * @param code error code
     * @param args placeholder parameters
     * @param <T>  the type which extends {@link Number}
     */
    public <T extends Number> DigitalException(final T code, final Object... args) {
        super(code, args);
    }

    /**
     * Constructs a new digital exception with unary generic enum interface as parameter and placeholder.
     *
     * @param exception unary generic enum interface
     * @param args      placeholder parameters
     * @param <T>  the type which extends {@link Number}
     */
    public <T extends Number> DigitalException(final CodeProvider<T> exception, final Object... args) {
        super(exception, args);
    }
}
