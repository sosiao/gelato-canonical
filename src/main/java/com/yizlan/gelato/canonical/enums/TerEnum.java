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
import java.util.function.Supplier;

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
            S extends Comparable<S> & Serializable> implements TerDictionary<T, U, S> {

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
    }

    /**
     * Converts the values of this enum to a list of dictionary objects.
     *
     * @param enumValues all values of this enum
     * @param <T>        the type of the value field, should implement {@link Comparable} and {@link Serializable}
     * @param <U>        the type of the label field, should implement {@link Comparable} and {@link Serializable}
     * @param <S>        the type of the desc field, should implement {@link Comparable} and {@link Serializable}
     * @return a list of dictionary objects
     */
    static <T extends Comparable<T> & Serializable, U extends Comparable<U> & Serializable,
            S extends Comparable<S> & Serializable> List<TerDictionary<T, U, S>> toList(
            TerEnum<T, U, S>[] enumValues) {
        if (enumValues == null) {
            throw new NullPointerException("enumValues cannot be null");
        }
        if (enumValues.length == 0) {
            return Collections.emptyList();
        }
        List<TerDictionary<T, U, S>> dictionaries = new ArrayList<>(enumValues.length);

        for (TerEnum<T, U, S> item : enumValues) {
            TerDictionary<T, U, S> dictionary = new TerDictionaryAdapter<>(item);
            dictionaries.add(dictionary);
        }

        return dictionaries;
    }

    /**
     * Converts enums to a list of dictionary objects with a special data type.
     * This method allows customization of the dictionary object type through a supplier.
     *
     * @param enumValues all values of this enum
     * @param supplier   the supplier for creating dictionary objects, typically a lambda
     * @param <T>        the type of the value field, should implement {@link Comparable} and {@link Serializable}
     * @param <U>        the type of the label field, should implement {@link Comparable} and {@link Serializable}
     * @param <S>        the type of the desc field, should implement {@link Comparable} and {@link Serializable}
     * @param <R>        the type of the resulting dictionary objects
     * @return a list of dictionary objects of type R
     */
    static <T extends Comparable<T> & Serializable, U extends Comparable<U> & Serializable,
            S extends Comparable<S> & Serializable, R extends TerDictionary<T, U, S>> List<R> toList(
            TerEnum<T, U, S>[] enumValues, Supplier<R> supplier) {
        if (enumValues == null) {
            throw new NullPointerException("enumValues cannot be null");
        }
        if (enumValues.length == 0) {
            return Collections.emptyList();
        }
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

        return objects;
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
     */
    static <T extends Comparable<T> & Serializable, U extends Comparable<U> & Serializable,
            S extends Comparable<S> & Serializable> Map<T, S> toDescMap(
            TerEnum<T, U, S>[] enumValues) {
        if (enumValues == null) {
            throw new NullPointerException("enumValues cannot be null");
        }
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
