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
import com.yizlan.gelato.canonical.copier.NamedProvider;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 * Provide fields which named code and name with the different type for dictionary.
 * This is the two-arity specialization of {@link CodeProvider}.
 *
 * @param <T> the type of the code field, should implement {@link Comparable} and {@link Serializable}
 * @param <U> the type of the name field, should implement {@link Comparable} and {@link Serializable}
 * @author Zen Gershon
 * @see CodeProvider
 * @see NamedProvider
 * @since 1.0
 */
public interface BiDictionary<T extends Comparable<T> & Serializable, U extends Comparable<U> & Serializable>
        extends CodeProvider<T>, NamedProvider<U> {

    void setCode(T code);

    void setName(U name);

    /**
     * Convert dictionary to map
     *
     * @param biDictionaries A collection of classes or subclasses that implements {@link BiDictionary}.
     *                       Note that null elements within the list are ignored.
     * @param <T>            the type of the code field, should implement {@link Comparable} and {@link Serializable}
     * @param <U>            the type of the name field, should implement {@link Comparable} and {@link Serializable}
     * @return A Collector which collects elements into a Map whose keys are the code field, and whose
     * values are the name field. If {@code biDictionaries} is null or empty, returns an empty Map.
     * @throws IllegalArgumentException if duplicate keys based on the result of {@link BiDictionary#getCode()}
     *                                  are encountered.
     */
    static <T extends Comparable<T> & Serializable, U extends Comparable<U> & Serializable> Map<T, U> toMap(
            List<? extends BiDictionary<T, U>> biDictionaries) {
        if (biDictionaries == null || biDictionaries.isEmpty()) {
            return Collections.emptyMap();
        }

        return biDictionaries.stream()
                .filter(Objects::nonNull)
                .collect(
                        Collectors.toMap(
                                BiDictionary::getCode,
                                BiDictionary::getName,
                                (k1, k2) -> {
                                    throw new IllegalArgumentException("Duplicate key found: " + k1);
                                },
                                () -> new HashMap<>(biDictionaries.size(), 0.5f)
                        )
                );
    }

    /**
     * Convert dictionary to map
     *
     * @param biDictionaries A collection of classes or subclasses that implements {@link BiDictionary}.
     *                       Note that null elements within the list are ignored.
     * @param mergeFunction  a merge function, used to resolve collisions between values associated with the same
     *                       key, as supplied to {@link Map#merge(Object, Object, BiFunction)}
     * @param <T>            the type of the code field, should implement {@link Comparable} and {@link Serializable}
     * @param <U>            the type of the name field, should implement {@link Comparable} and {@link Serializable}
     * @return A Collector which collects elements into a Map whose keys are the code field, and whose
     * values are the name field. If {@code biDictionaries} is null or empty, returns an empty Map.
     */
    static <T extends Comparable<T> & Serializable, U extends Comparable<U> & Serializable> Map<T, U> toMap(
            List<? extends BiDictionary<T, U>> biDictionaries, BinaryOperator<U> mergeFunction) {
        if (biDictionaries == null || biDictionaries.isEmpty()) {
            return Collections.emptyMap();
        }

        return biDictionaries.stream()
                .filter(Objects::nonNull)
                .collect(
                        Collectors.toMap(
                                BiDictionary::getCode,
                                BiDictionary::getName,
                                mergeFunction,
                                () -> new HashMap<>(biDictionaries.size(), 0.5f)
                        )
                );
    }

}
