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

package com.yizlan.gelato.canonical.enums;

import com.yizlan.gelato.canonical.copier.LabelProvider;
import com.yizlan.gelato.canonical.copier.ValueProvider;
import com.yizlan.gelato.canonical.dictionary.BiDictionary;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Provide fields which named value and label with the different type for enum.
 * This is the two-arity specialization of {@link ValueProvider}.
 *
 * @param <T> the type of the value field, should implement {@link Comparable} and {@link Serializable}
 * @param <U> the type of the label field, should implement {@link Comparable} and {@link Serializable}
 * @author Zen Gershon
 * @see UnaryEnum
 * @see LabelProvider
 * @since 1.0
 */
public interface BiEnum<T extends Comparable<T> & Serializable, U extends Comparable<U> & Serializable>
        extends UnaryEnum<T>, LabelProvider<U> {

    /**
     * An immutable binary dictionary class, implementing the {@link BiDictionary} interface.
     * It is used for storing pairs of code and name.
     *
     * @param <T> the type of the code, which must implement {@link Comparable} and {@link Serializable}
     * @param <U> the type of the name, which must implement {@link Comparable} and {@link Serializable}
     */
    class ImmutableBiDictionary<T extends Comparable<T> & Serializable, U extends Comparable<U> & Serializable>
            implements BiDictionary<T, U>, Serializable {
        private static final long serialVersionUID = 1L;

        private T code;

        private U name;

        private ImmutableBiDictionary() {
            // to do nothing
        }

        @Override
        public T getCode() {
            return code;
        }

        @Override
        public void setCode(T code) {
            this.code = code;
        }

        @Override
        public U getName() {
            return name;
        }

        @Override
        public void setName(U name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "ImmutableBiDictionary{" +
                    "code=" + code +
                    ", name=" + name +
                    '}';
        }
    }

    /**
     * Converts the values of this enum to a list of {@link BiDictionary} objects.
     * Each element in the list corresponds to a dictionary entry for an enumeration value and its label.
     * Note that the null elements will be ignored.
     *
     * @param enumValues all values of this enum, not nullable
     * @param <T>        the type of the value field, should implement {@link Comparable} and {@link Serializable}
     * @param <U>        the type of the label field, should implement {@link Comparable} and {@link Serializable}
     * @return a list of {@link BiDictionary} objects, not nullable
     * @throws NullPointerException if {@code enumValues} is null
     */
    static <T extends Comparable<T> & Serializable, U extends Comparable<U> & Serializable> List<BiDictionary<T, U>>
    toList(BiEnum<T, U>[] enumValues) {
        return toList(enumValues, ImmutableBiDictionary::new);
    }

    /**
     * Converts the values of this enum to a list of specified {@link BiDictionary} implementations.
     * This method allows for the creation of a list of any {@link BiDictionary} implementation by providing a supplier.
     * Note that the null elements will be ignored.
     *
     * @param enumValues all values of this enum, not nullable
     * @param supplier   a supplier for creating instances of a specific {@link BiDictionary} implementation,
     *                   not nullable
     * @param <T>        the type of the value field, should implement {@link Comparable} and {@link Serializable}
     * @param <U>        the type of the label field, should implement {@link Comparable} and {@link Serializable}
     * @param <R>        the type of the specific {@link BiDictionary} implementation
     * @return a list of specified {@link BiDictionary} implementations
     * @throws NullPointerException if {@code enumValues} or {@code supplier}
     *                              or {@link Supplier#get()} is null
     */
    static <T extends Comparable<T> & Serializable, U extends Comparable<U> & Serializable,
            R extends BiDictionary<T, U>> List<R> toList(BiEnum<T, U>[] enumValues, Supplier<R> supplier) {
        return toList(enumValues, supplier, Object::toString);
    }


    /**
     * Converts the values of this enum into a list of a specific type,
     * and also using a mapping function to produce unique keys to deduplicate the list.
     * Note that the null elements will be ignored.
     *
     * @param <T>          the generic type T, which must implement {@link Comparable} and {@link Serializable}
     * @param <U>          the generic type U, which must implement {@link Comparable} and {@link Serializable}
     * @param <R>          the generic type R, which must implement the {@link BiDictionary} interface
     * @param enumValues   all values of this enum, not nullable
     * @param supplier     a supplier for instances of type R
     * @param keyExtractor a function to extract unique keys from instances of type R
     * @return a list of the specified type R
     * @throws NullPointerException if {@code enumValues} or {@code supplier}
     *                              or {@link Supplier#get()} or {@link Function#apply(Object)} is null
     */
    static <T extends Comparable<T> & Serializable, U extends Comparable<U> & Serializable,
            R extends BiDictionary<T, U>> List<R> toList(BiEnum<T, U>[] enumValues,
                                                         Supplier<R> supplier,
                                                         Function<? super R, Object> keyExtractor) {
        Objects.requireNonNull(enumValues);
        Objects.requireNonNull(supplier);
        if (enumValues.length == 0) {
            return Collections.emptyList();
        }

        R r = supplier.get();
        Objects.requireNonNull(r);
        return Stream.of(enumValues)
                .filter(Objects::nonNull)
                .map(item -> {
                    R object = supplier.get();
                    object.setCode(item.getValue());
                    object.setName(item.getLabel());
                    return object;
                })
                .filter(distinctByKey(keyExtractor))
                .collect(Collectors.toList());
    }

    /**
     * If a key could not be put into ConcurrentHashMap, that means the key is duplicated
     *
     * @param keyExtractor a mapping function to produce keys
     * @param <T>          the type of the input elements
     * @return {@code true} if key is duplicated, otherwise {@code false}
     * @throws NullPointerException If the unique key extracted from the generic type T is null
     */
    static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> {
            Object key = Objects.requireNonNull(keyExtractor.apply(t));
            return map.putIfAbsent(key, Boolean.TRUE) == null;
        };
    }

    /**
     * Converts enums to a map where the keys are the value fields of the enums,
     * and the values are the label fields.
     *
     * @param enumValues all values of this enum
     * @param <T>        the type of the value field, should implement {@link Comparable} and {@link Serializable}
     * @param <U>        the type of the label field, should implement {@link Comparable} and {@link Serializable}
     * @return a map collecting enum values to their labels
     * @throws NullPointerException if {@code enumValues} is null
     */
    static <T extends Comparable<T> & Serializable, U extends Comparable<U> & Serializable> Map<T, U> toMap(
            BiEnum<T, U>[] enumValues) {
        Objects.requireNonNull(enumValues);
        if (enumValues.length == 0) {
            return Collections.emptyMap();
        }
        Map<T, U> map = new HashMap<>(enumValues.length);

        for (BiEnum<T, U> item : enumValues) {
            map.put(item.getValue(), item.getLabel());
        }

        return map;
    }

}
