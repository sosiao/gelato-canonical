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

import com.yizlan.gelato.canonical.copier.DescriptionProvider;
import com.yizlan.gelato.canonical.copier.ValueProvider;
import com.yizlan.gelato.canonical.dictionary.TerDictionary;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Provide fields which named value、label and desc with the different type for enum.
 * This is the three-arity specialization of {@link ValueProvider}.
 *
 * @param <T> the type of the value field, should implement {@link Comparable} and {@link Serializable}
 * @param <U> the type of the label field, should implement {@link Comparable} and {@link Serializable}
 * @param <S> the type of the description field, should implement {@link Comparable} and {@link Serializable}
 * @author Zen Gershon
 * @see BiEnum
 * @see DescriptionProvider
 * @since 1.0
 */
public interface TerEnum<T extends Comparable<T> & Serializable, U extends Comparable<U> & Serializable,
        S extends Comparable<S> & Serializable> extends BiEnum<T, U>, DescriptionProvider<S> {

    /**
     * A class that implements the {@link TerDictionary} interface, providing a dictionary-like
     * structure for enum values. It wraps around a value of the enum and provides access to its
     * code, name, and description.
     */
    class TerDictionaryAdapter<T extends Comparable<T> & Serializable, U extends Comparable<U> & Serializable,
            S extends Comparable<S> & Serializable> implements TerDictionary<T, U, S>, Serializable {
        private static final long serialVersionUID = 1L;

        private final TerEnum<T, U, S> enumValue;

        private TerDictionaryAdapter(TerEnum<T, U, S> enumValue) {
            this.enumValue = Objects.requireNonNull(enumValue, "enumValue cannot be null");
        }

        @Override
        public T getCode() {
            return enumValue.getValue();
        }

        @Override
        public void setCode(T code) {
            throw new UnsupportedOperationException("This method is not supported.");
        }

        @Override
        public U getName() {
            return enumValue.getLabel();
        }

        @Override
        public void setName(U name) {
            throw new UnsupportedOperationException("This method is not supported.");
        }

        @Override
        public S getDesc() {
            return enumValue.getDesc();
        }

        @Override
        public void setDesc(S desc) {
            throw new UnsupportedOperationException("This method is not supported.");
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            TerDictionaryAdapter<?, ?, ?> that = (TerDictionaryAdapter<?, ?, ?>) obj;
            return Objects.equals(enumValue.getValue(), that.getCode()) &&
                    Objects.equals(enumValue.getLabel(), that.getName()) &&
                    Objects.equals(enumValue.getDesc(), that.getDesc());
        }

        @Override
        public int hashCode() {
            return Objects.hash(enumValue.getValue(), enumValue.getLabel(), enumValue.getDesc());
        }

    }

    /**
     * Converts the values of this enum to a list of dictionary objects.
     * Note that the null elements will be ignored.
     *
     * @param enumValues all values of this enum, not nullable
     * @param <T>        the type of the value field, should implement {@link Comparable} and {@link Serializable}
     * @param <U>        the type of the label field, should implement {@link Comparable} and {@link Serializable}
     * @param <S>        the type of the desc field, should implement {@link Comparable} and {@link Serializable}
     * @return a list of dictionary objects
     * @throws NullPointerException if {@code enumValues} is null
     */
    static <T extends Comparable<T> & Serializable, U extends Comparable<U> & Serializable,
            S extends Comparable<S> & Serializable> List<TerDictionary<T, U, S>> toList(
            TerEnum<T, U, S>[] enumValues) {
        Objects.requireNonNull(enumValues);
        if (enumValues.length == 0) {
            return Collections.emptyList();
        }

        return Stream.of(enumValues)
                .filter(Objects::nonNull)
                .map(TerDictionaryAdapter::new)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Converts enums to a list of dictionary objects with a special data type.
     * This method allows customization of the dictionary object type through a supplier.
     * Note that the null elements will be ignored.
     *
     * @param enumValues all values of this enum
     * @param supplier   the supplier for creating dictionary objects, typically a lambda
     * @param <T>        the type of the value field, should implement {@link Comparable} and {@link Serializable}
     * @param <U>        the type of the label field, should implement {@link Comparable} and {@link Serializable}
     * @param <S>        the type of the desc field, should implement {@link Comparable} and {@link Serializable}
     * @param <R>        the type of the resulting dictionary objects
     * @return a list of dictionary objects of type R
     * @throws NullPointerException if {@code enumValues} or {@code supplier}
     *                              or {@link Supplier#get()} is null
     */
    static <T extends Comparable<T> & Serializable, U extends Comparable<U> & Serializable,
            S extends Comparable<S> & Serializable, R extends TerDictionary<T, U, S>> List<R> toList(
            TerEnum<T, U, S>[] enumValues, Supplier<R> supplier) {
        Objects.requireNonNull(enumValues);
        Objects.requireNonNull(supplier);
        if (enumValues.length == 0) {
            return Collections.emptyList();
        }

        Objects.requireNonNull(supplier.get());
        List<R> objects = new ArrayList<>(enumValues.length);
        for (TerEnum<T, U, S> item : enumValues) {
            if (item != null) {
                R object = supplier.get();
                object.setCode(item.getValue());
                object.setName(item.getLabel());
                object.setDesc(item.getDesc());

                objects.add(object);
            }
        }

        return objects.stream().distinct().collect(Collectors.toList());
    }

    /**
     * Converts enums to a list of dictionary objects with a special data type,
     * and also using a mapping function to produce unique keys to deduplicate the list.
     * Note that the null elements will be ignored.
     *
     * @param enumValues   all values of this enum, not nullable
     * @param supplier     a supplier for instances of type R
     * @param keyExtractor a function to extract unique keys from instances of type R
     * @param <T>          the generic type T, which must implement {@link Comparable} and {@link Serializable}
     * @param <U>          the generic type U, which must implement {@link Comparable} and {@link Serializable}
     * @param <S>          the generic type S, which must implement {@link Comparable} and {@link Serializable}
     * @param <R>          the generic type R, which must implement the {@link TerDictionary} interface
     * @return a list of the specified type R
     * @throws NullPointerException if {@code enumValues} or {@code supplier}
     *                              or {@link Supplier#get()} or {@link Function#apply(Object)} is null
     */
    static <T extends Comparable<T> & Serializable, U extends Comparable<U> & Serializable,
            S extends Comparable<S> & Serializable, R extends TerDictionary<T, U, S>> List<R> toList(
            TerEnum<T, U, S>[] enumValues, Supplier<R> supplier, Function<? super R, Object> keyExtractor) {
        Objects.requireNonNull(enumValues);
        Objects.requireNonNull(supplier);
        if (enumValues.length == 0) {
            return Collections.emptyList();
        }

        Objects.requireNonNull(supplier.get());
        return Stream.of(enumValues)
                .filter(Objects::nonNull)
                .map(item -> {
                    R object = supplier.get();
                    object.setCode(item.getValue());
                    object.setName(item.getLabel());
                    object.setDesc(item.getDesc());

                    return object;
                })
                .filter(BiEnum.distinctByKey(keyExtractor))
                .collect(Collectors.toList());
    }

    /**
     * Converts enums to a map where the keys are the value fields of the enums,
     * and the values are the description fields.
     *
     * @param enumValues all values of this enum
     * @param <T>        the type of the value field, should implement {@link Comparable} and {@link Serializable}
     * @param <U>        the type of the label field, should implement {@link Comparable} and {@link Serializable}
     * @param <S>        the type of the desc field, should implement {@link Comparable} and {@link Serializable}
     * @return a map collecting enum values to their descriptions
     * @throws NullPointerException if {@code enumValues} is null
     */
    static <T extends Comparable<T> & Serializable, U extends Comparable<U> & Serializable,
            S extends Comparable<S> & Serializable> Map<T, S> toDescMap(
            TerEnum<T, U, S>[] enumValues) {
        Objects.requireNonNull(enumValues);
        if (enumValues.length == 0) {
            return Collections.emptyMap();
        }
        Map<T, S> map = new HashMap<>(enumValues.length);

        for (TerEnum<T, U, S> item : enumValues) {
            map.put(item.getValue(), item.getDesc());
        }

        return map;
    }

}
