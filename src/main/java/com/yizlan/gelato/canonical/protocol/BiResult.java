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

package com.yizlan.gelato.canonical.protocol;

import com.yizlan.gelato.canonical.copier.CodeProvider;
import com.yizlan.gelato.canonical.copier.MessageProvider;

import java.io.Serializable;

/**
 * Provide fields which named code and message with different types for result.
 * This is the two-arity specialization of {@link CodeProvider}.
 *
 * @param <T> the type of the code field, should implement {@link Comparable} and {@link Serializable}
 * @param <U> the type of the message field, should implement {@link Comparable} and {@link Serializable}
 * @author Zen Gershon
 * @see CodeProvider
 * @see MessageProvider
 * @since 1.0
 */
public interface BiResult<T extends Comparable<T> & Serializable, U extends Comparable<U> & Serializable>
        extends CodeProvider<T>, MessageProvider<U> {

    void setCode(T code);

    void setMessage(U message);

    default BiResult<T, U> code(T code) {
        this.setCode(code);
        return this;
    }

    default BiResult<T, U> message(U message) {
        this.setMessage(message);
        return this;
    }

    BiResult<T, U> success(Object... args);

    BiResult<T, U> failure(Object... args);

}
