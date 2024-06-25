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

package com.yizlan.gelato.canonical.panic;

import com.yizlan.gelato.canonical.exception.UnaryException;

import java.io.Serializable;
import java.util.Arrays;

/**
 * meta exception
 *
 * @author Zen Gershon
 * @since 2.0
 */
class MetaException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * error code
     */
    private final Serializable code;

    /**
     * placeholder parameters
     */
    private final Object[] args;

    public Serializable getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }

    /**
     * Constructs a new meta exception with the specified code and placeholder.
     *
     * @param code error code
     * @param args placeholder parameters
     */
    <T extends Comparable<T> & Serializable> MetaException(final T code, final Object... args) {
        super();
        this.code = code;
        this.args = args;
    }

    /**
     * Constructs a new meta exception with unary generic enum interface as parameter and placeholder.
     *
     * @param exception unary generic enum interface
     * @param args      placeholder parameters
     */
    <T extends Comparable<T> & Serializable> MetaException(final UnaryException<T> exception, final Object... args) {
        super();
        this.code = exception.getCode();
        this.args = args;
    }

    @Override
    public String toString() {
        return "MetaException{" +
                "code=" + code +
                ", args=" + Arrays.toString(args) +
                '}';
    }
}
