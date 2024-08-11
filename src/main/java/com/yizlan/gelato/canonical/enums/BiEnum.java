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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

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
            implements BiDictionary<T, U> {

        private final T code;

        private final U name;

        private ImmutableBiDictionary(T code, U name) {
            this.code = code;
            this.name = name;
        }

        @Override
        public T getCode() {
            return code;
        }

        @Override
        public void setCode(T code) {
            throw new UnsupportedOperationException("This method is not supported.");
        }

        @Override
        public U getName() {
            return name;
        }

        @Override
        public void setName(U name) {
            throw new UnsupportedOperationException("This method is not supported.");
        }
    }

    /**
     * Converts the values of this enum to a list of {@link BiDictionary} objects.
     * Each element in the list corresponds to a dictionary entry for an enumeration value and its label.
     *
     * @param enumValues all values of this enum, not nullable
     * @param <T>        the type of the value field, should implement {@link Comparable} and {@link Serializable}
     * @param <U>        the type of the label field, should implement {@link Comparable} and {@link Serializable}
     * @return a list of {@link BiDictionary} objects, not nullable
     * @throws NullPointerException     if the input array of enumeration values is null
     * @throws IllegalArgumentException if any of the elements in the array is null
     */
    static <T extends Comparable<T> & Serializable, U extends Comparable<U> & Serializable> List<BiDictionary<T, U>>
    toList(BiEnum<T, U>[] enumValues) {
        if (enumValues == null) {
            throw new NullPointerException("The values of enum cannot be null");
        }
        if (enumValues.length == 0) {
            return Collections.emptyList();
        }
        List<BiDictionary<T, U>> dictionaries = new ArrayList<>(enumValues.length);

        for (BiEnum<T, U> item : enumValues) {
            if (item == null) {
                throw new IllegalArgumentException("The values of enum cannot contain null elements.");
            }
            BiDictionary<T, U> dictionary = new ImmutableBiDictionary<>(item.getValue(), item.getLabel());
            dictionaries.add(dictionary);
        }
        return dictionaries;
    }

    /**
     * Converts the values of this enum to a list of specified {@link BiDictionary} implementations.
     * This method allows for the creation of a list of any {@link BiDictionary} implementation by providing a supplier.
     *
     * @param enumValues all values of this enum, not nullable
     * @param supplier   a supplier for creating instances of a specific {@link BiDictionary} implementation, not
     *                   nullable
     * @param <T>        the type of the value field, should implement {@link Comparable} and {@link Serializable}
     * @param <U>        the type of the label field, should implement {@link Comparable} and {@link Serializable}
     * @param <R>        the type of the specific {@link BiDictionary} implementation
     * @return a list of specified {@link BiDictionary} implementations
     * @throws NullPointerException     if the input array of enumeration values or supplier is null
     * @throws IllegalArgumentException if any of the elements in the array is null
     */
    static <T extends Comparable<T> & Serializable, U extends Comparable<U> & Serializable,
            R extends BiDictionary<T, U>> List<R> toList(BiEnum<T, U>[] enumValues, Supplier<R> supplier) {
        if (enumValues == null) {
            throw new NullPointerException("enumValues cannot be null");
        }
        if (enumValues.length == 0) {
            return Collections.emptyList();
        }
        List<R> objects = new ArrayList<>(enumValues.length);

        for (BiEnum<T, U> item : enumValues) {
            if (item == null) {
                throw new IllegalArgumentException("Enum values cannot contain null elements");
            }
            R object = supplier.get();
            if (object == null) {
                throw new NullPointerException("The instance of BiDictionary cannot be null");
            }
            object.setCode(item.getValue());
            object.setName(item.getLabel());

            objects.add(object);
        }
        return objects;
    }

    /**
     * Converts enums to a map where the keys are the value fields of the enums,
     * and the values are the label fields.
     *
     * @param enumValues all values of this enum
     * @param <T>        the type of the value field, should implement {@link Comparable} and {@link Serializable}
     * @param <U>        the type of the label field, should implement {@link Comparable} and {@link Serializable}
     * @return a map collecting enum values to their labels
     * @throws NullPointerException if enumValues is null
     */
    static <T extends Comparable<T> & Serializable, U extends Comparable<U> & Serializable> Map<T, U> toMap(
            BiEnum<T, U>[] enumValues) {
        if (enumValues == null) {
            throw new NullPointerException("enumValues cannot be null");
        }
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
