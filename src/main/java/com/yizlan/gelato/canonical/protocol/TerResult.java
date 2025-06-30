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

import java.io.Serializable;

/**
 * Provide fields which named code、message and data with different types for result.
 * This is the three-arity specialization of {@link CodeProvider}.
 *
 * @param <P> the type of data protocol itself that implements {@link TerResult}
 * @param <T> the type of the code field, should implement {@link Comparable} and {@link Serializable}
 * @param <U> the type of the message field, should implement {@link Comparable} and {@link Serializable}
 * @param <S> the type of the data filed
 * @author Zen Gershon
 * @see BiResult
 * @since 1.0
 */
public interface TerResult<P extends TerResult<P, T, U, S>, T extends Comparable<T> & Serializable,
        U extends Comparable<U> & Serializable, S> extends BiResult<P, T, U> {

    /**
     * Get data
     *
     * @return data
     */
    S getData();

    void setData(S data);

    default P data(S data) {
        this.setData(data);
        return this.self();
    }

    @Override
    default P empty() {
        this.setCode(null);
        this.setMessage(null);
        this.setData(null);
        return this.self();
    }

    default P success() {
        return this.empty();
    }

    default P failure() {
        return this.empty();
    }

    default TerResult<T, U, S> failure(Object... args) {
        this.setCode(null);
        this.setMessage(null);
        this.setData(null);
        return this;
    }

}
