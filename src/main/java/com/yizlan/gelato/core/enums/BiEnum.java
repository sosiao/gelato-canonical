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

package com.yizlan.gelato.core.enums;

import com.yizlan.gelato.core.dictionary.BiDictionary;
import com.yizlan.gelato.core.universal.IText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

/**
 * Provide fields which named value and text with the different type for enum.
 * This is the two-arity specialization of {@link IText}.
 *
 * @param <T> the type of the filed which named value
 * @param <U> the type of the filed which named text
 * @author Zen Gershon
 * @see IText
 * @since 1.0
 */
public interface BiEnum<T extends Serializable, U extends Serializable> extends IText<U> {

    /**
     * Get value
     *
     * @return value
     */
    T getValue();

    /**
     * Compares enum according to their source value.
     *
     * @param enumValue the type of the filed which named value
     * @return equals result from the value field of enum.
     */
    default boolean valueEquals(T enumValue) {
        AtomicBoolean flag = new AtomicBoolean(false);
        Optional.ofNullable(enumValue).ifPresent(val -> {
            if (val instanceof String) {
                flag.set(((String) val).equalsIgnoreCase((String) this.getValue()));
            } else {
                flag.set(Objects.equals(this.getValue(), val));
            }
        });
        return flag.get();
    }

    /**
     * Compares enum
     *
     * @param biEnum {@link BiEnum}
     * @return equals result of enum.
     */
    default boolean equals(BiEnum<T, U> biEnum) {
        return this == biEnum;
    }

    /**
     * convert enums to dictionary list
     *
     * @param enumValues the values of enum
     * @param <T>        the type of the filed which named code
     * @param <U>        the type of the filed which named value
     * @return dictionary list
     */
    static <T extends Serializable, U extends Serializable> List<BiDictionary<T, U>> toList(BiEnum<T, U>[] enumValues) {
        List<BiDictionary<T, U>> biDictionaries = new ArrayList<>(enumValues.length);

        for (BiEnum<T, U> item : enumValues) {
            BiDictionary<T, U> biDictionary = new BiDictionary<T, U>() {
                @Override
                public U getText() {
                    return item.getText();
                }

                @Override
                public T getCode() {
                    return item.getValue();
                }

                @Override
                public void setCode(T code) {

                }

                @Override
                public void setText(U text) {

                }
            };
            biDictionaries.add(biDictionary);
        }
        return biDictionaries;
    }

    /**
     * convert enums to dictionary list
     *
     * @param enumValues the values of enum
     * @param supplier   the supplier (typically bound to a lambda expression)
     * @param <T>        the type of the filed which named code
     * @param <U>        the type of the filed which named value
     * @return dictionary list
     */
    static <T extends Serializable, U extends Serializable> List<? extends BiDictionary<T, U>> toList(
            BiEnum<T, U>[] enumValues, Supplier<? extends BiDictionary<T, U>> supplier) {
        List<BiDictionary<T, U>> biDictionaries = new ArrayList<>(enumValues.length);

        for (BiEnum<T, U> item : enumValues) {
            BiDictionary<T, U> biDictionary = supplier.get();
            biDictionary.setCode(item.getValue());
            biDictionary.setText(item.getText());

            biDictionaries.add(biDictionary);
        }
        return biDictionaries;
    }

    /**
     * convert enums to map
     *
     * @param enumValues the values of enum
     * @param <T>        the type of the filed which named code
     * @param <U>        the type of the filed which named value
     * @return an Enum which collects elements into a Map whose keys are the filed which named code, and whose
     * values are the filed which named value.
     */
    static <T extends Serializable, U extends Serializable> Map<T, U> toMap(BiEnum<T, U>[] enumValues) {
        Map<T, U> map = new HashMap<>(enumValues.length);

        for (BiEnum<T, U> item : enumValues) {
            map.put(item.getValue(), item.getText());
        }

        return map;
    }
}
