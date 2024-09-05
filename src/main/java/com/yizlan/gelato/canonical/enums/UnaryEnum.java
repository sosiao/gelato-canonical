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

package com.yizlan.gelato.canonical.enums;

import com.yizlan.gelato.canonical.copier.ValueProvider;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Provides fields which named value for enum.
 *
 * @param <T> the type of the value field, should implement {@link Comparable} and {@link Serializable}
 * @author Zen Gershon
 * @see ValueProvider
 * @since 2.0
 */
public interface UnaryEnum<T extends Comparable<T> & Serializable> extends ValueProvider<T> {

    /**
     * Compares this enum's value with the given value.
     *
     * @param enumValue the value to compare with
     * @return {@code true} if the values are considered equal, otherwise {@code false}
     */
    default boolean valueEquals(T enumValue) {
        AtomicBoolean flag = new AtomicBoolean(false);
        Optional.ofNullable(enumValue)
                .ifPresent(enumVal -> {
                    T value = this.getValue();
                    if (enumVal instanceof String && value instanceof String) {
                        flag.set(((String) enumVal).equalsIgnoreCase((String) value));
                    } else {
                        flag.set(Objects.equals(enumVal, value));
                    }
                });
        return flag.get();
    }

}
