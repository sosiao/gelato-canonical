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

package com.yizlan.gelato.canonical.dictionary;

import com.yizlan.gelato.canonical.copier.CodeProvider;
import com.yizlan.gelato.canonical.copier.DescriptionProvider;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Provide fields which named code、name and desc with the different type for dictionary.
 * This is the three-arity specialization of {@link CodeProvider}.
 *
 * @param <T> the type of the code field, should implement {@link Comparable} and {@link Serializable}
 * @param <U> the type of the name field, should implement {@link Comparable} and {@link Serializable}
 * @param <S> the type of the desc filed, should implement {@link Comparable} and {@link Serializable}
 * @author Zen Gershon
 * @see BiDictionary
 * @see DescriptionProvider
 * @since 1.0
 */
public interface TerDictionary<T extends Comparable<T> & Serializable, U extends Comparable<U> & Serializable,
        S extends Comparable<S> & Serializable> extends BiDictionary<T, U>, DescriptionProvider<S> {

    void setDesc(S desc);

    /**
     * Convert dictionary to map
     *
     * @param terDictionaries A collection of classes or subclasses that implements {@link TerDictionary}.
     *                        Note that null elements within the list are ignored.
     * @param <T>             the type of the code field, should implement {@link Comparable} and {@link Serializable}
     * @param <U>             the type of the name field, should implement {@link Comparable} and {@link Serializable}
     * @param <S>             the type of the desc field, should implement {@link Comparable} and {@link Serializable}
     * @return A Collector which collects elements into a Map whose keys are the code field, and whose
     *         values are the desc field. If {@code terDictionaries} is null or empty, returns an empty Map.
     * @throws IllegalArgumentException if duplicate keys are encountered.
     */
    static <T extends Comparable<T> & Serializable, U extends Comparable<U> & Serializable,
            S extends Comparable<S> & Serializable> Map<T, S> toDescMap(
            List<? extends TerDictionary<T, U, S>> terDictionaries) {
        if (terDictionaries == null || terDictionaries.isEmpty()) {
            return Collections.emptyMap();
        }

        return terDictionaries.stream()
                .filter(Objects::nonNull)
                .collect(
                        Collectors.toMap(
                                TerDictionary::getCode,
                                TerDictionary::getDesc,
                                (k1, k2) -> {
                                    throw new IllegalArgumentException("Duplicate key found: " + k1);
                                },
                                () -> new HashMap<>(Math.max(terDictionaries.size(), 16))
                        )
                );
    }

}
