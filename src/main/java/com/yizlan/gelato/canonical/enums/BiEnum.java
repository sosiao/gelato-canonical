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
     * convert values of enum to dictionary list
     *
     * @param enumValues the values of enum
     * @param <T>        the type of the value field, should implement {@link Comparable} and {@link Serializable}
     * @param <U>        the type of the label field, should implement {@link Comparable} and {@link Serializable}
     * @return dictionary list
     */
    static <T extends Comparable<T> & Serializable, U extends Comparable<U> & Serializable>
    List<BiDictionary<T, U>> toList(BiEnum<T, U>[] enumValues) {
        if (enumValues == null) {
            throw new NullPointerException("The values of enum cannot be null");
        }
        if (enumValues.length == 0) {
            return Collections.emptyList();
        }
        List<BiDictionary<T, U>> biDictionaries = new ArrayList<>(enumValues.length);

        for (BiEnum<T, U> item : enumValues) {
            if (item == null) {
                throw new IllegalArgumentException("The values of enum cannot contain null elements.");
            }
            BiDictionary<T, U> biDictionary = new ImmutableBiDictionary<>(item.getValue(), item.getLabel());
            biDictionaries.add(biDictionary);
        }
        return Collections.unmodifiableList(biDictionaries);
    }

    /**
     * convert values of enum to dictionary list with special data type
     *
     * @param enumValues the values of enum
     * @param supplier   the supplier (typically bound to a lambda expression)
     * @param <T>        the type of the value field, should implement {@link Comparable} and {@link Serializable}
     * @param <U>        the type of the label field, should implement {@link Comparable} and {@link Serializable}
     * @return dictionary list
     */
    static <T extends Comparable<T> & Serializable, U extends Comparable<U> & Serializable>
    List<? extends BiDictionary<T, U>> toList(BiEnum<T, U>[] enumValues,
                                              Supplier<? extends BiDictionary<T, U>> supplier) {
        if (enumValues == null) {
            throw new NullPointerException("enumValues cannot be null");
        }
        if (enumValues.length == 0) {
            return Collections.emptyList();
        }
        List<BiDictionary<T, U>> biDictionaries = new ArrayList<>(enumValues.length);

        for (BiEnum<T, U> item : enumValues) {
            if (item == null) {
                throw new IllegalArgumentException("Enum values cannot contain null elements");
            }
            BiDictionary<T, U> biDictionary = supplier.get();
            if (biDictionary == null) {
                throw new IllegalArgumentException("The instance of BiDictionary cannot be null");
            }
            biDictionary.setCode(item.getValue());
            biDictionary.setName(item.getLabel());

            biDictionaries.add(biDictionary);
        }
        return Collections.unmodifiableList(biDictionaries);
    }

    /**
     * convert values of enum to map
     *
     * @param enumValues the values of enum
     * @param <T>        the type of the value field, should implement {@link Comparable} and {@link Serializable}
     * @param <U>        the type of the label field, should implement {@link Comparable} and {@link Serializable}
     * @return an Enum which collects elements into a Map whose keys are the code field, and whose
     * values are the label field.
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

        return Collections.unmodifiableMap(map);
    }

}
