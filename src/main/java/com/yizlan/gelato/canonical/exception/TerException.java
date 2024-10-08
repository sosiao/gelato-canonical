/*
 * Copyright 2024 the original author or authors.
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

package com.yizlan.gelato.canonical.exception;

import com.yizlan.gelato.canonical.copier.CodeProvider;

import java.io.Serializable;

/**
 * Provide fields which named code、message and business with different types for exception.
 * This is the three-arity specialization of {@link CodeProvider}.
 *
 * @param <T> the type of the code field, should implement {@link Comparable} and {@link Serializable}
 * @param <U> the type of the message field, should implement {@link Comparable} and {@link Serializable}
 * @param <S> the type of the business field, should implement {@link Comparable} and {@link Serializable}
 * @author Zen Gershon
 * @see BiException
 * @since 1.0
 */
public interface TerException<T extends Comparable<T> & Serializable, U extends Comparable<U> & Serializable,
        S extends Comparable<S> & Serializable> extends BiException<T, U> {

    /**
     * Get business
     *
     * @return business
     */
    S getBusiness();

}
