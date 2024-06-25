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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Provide fields which named value、label and desc with the different type for enum.
 * This is the three-arity specialization of {@link ValueProvider}.
 *
 * @param <T> the type of the value field
 * @param <U> the type of the label field
 * @param <S> the type of the desc field
 * @author Zen Gershon
 * @see BiEnum
 * @see DescriptionProvider
 * @since 1.0
 */
public interface TerEnum<T extends Comparable<T> & Serializable, U extends Comparable<U> & Serializable,
        S extends Comparable<S> & Serializable> extends BiEnum<T, U>, DescriptionProvider<S> {

    /**
     * convert enums to dictionary list
     *
     * @param enumValues the values of enum
     * @param <T>        the type of the value field
     * @param <U>        the type of the label field
     * @param <S>        the type of the desc field
     * @return dictionary list
     */
    static <T extends Comparable<T> & Serializable, U extends Comparable<U> & Serializable,
            S extends Comparable<S> & Serializable> List<TerDictionary<T, U, S>> toList(
            TerEnum<T, U, S>[] enumValues) {
        List<TerDictionary<T, U, S>> terDictionaries = new ArrayList<>(enumValues.length);

        for (TerEnum<T, U, S> item : enumValues) {
            TerDictionary<T, U, S> terDictionary = new TerDictionary<T, U, S>() {
                @Override
                public T getCode() {
                    return item.getValue();
                }

                @Override
                public void setCode(T code) {
                    // to do nothing
                }

                @Override
                public U getName() {
                    return item.getLabel();
                }

                @Override
                public void setName(U name) {
                    // to do nothing
                }

                @Override
                public S getDesc() {
                    return item.getDesc();
                }

                @Override
                public void setDesc(S desc) {
                    // to do nothing
                }
            };
            terDictionaries.add(terDictionary);
        }

        return terDictionaries;
    }

    /**
     * convert enums to dictionary list with special data type
     *
     * @param enumValues the values of enum
     * @param supplier   the supplier (typically bound to a lambda expression)
     * @param <T>        the type of the value field
     * @param <U>        the type of the label field
     * @param <S>        the type of the desc field
     * @return dictionary list
     */
    static <T extends Comparable<T> & Serializable, U extends Comparable<U> & Serializable,
            S extends Comparable<S> & Serializable> List<? extends TerDictionary<T, U, S>> toList(
            TerEnum<T, U, S>[] enumValues, Supplier<? extends TerDictionary<T, U, S>> supplier) {
        List<TerDictionary<T, U, S>> terDictionaries = new ArrayList<>(enumValues.length);

        for (TerEnum<T, U, S> item : enumValues) {
            TerDictionary<T, U, S> terDictionary = supplier.get();
            terDictionary.setCode(item.getValue());
            terDictionary.setName(item.getLabel());
            terDictionary.setDesc(item.getDesc());

            terDictionaries.add(terDictionary);
        }

        return terDictionaries;
    }

    /**
     * convert enums to map
     *
     * @param enumValues the values of enum
     * @param <T>        the type of the value field
     * @param <U>        the type of the label field
     * @param <S>        the type of the desc field
     * @return an Enum which collects elements into a Map whose keys are the code field, and whose
     * values are the desc field.
     */
    static <T extends Comparable<T> & Serializable, U extends Comparable<U> & Serializable,
            S extends Comparable<S> & Serializable> Map<T, S> toDescMap(
            TerEnum<T, U, S>[] enumValues) {
        Map<T, S> map = new HashMap<>(enumValues.length);

        for (TerEnum<T, U, S> item : enumValues) {
            map.put(item.getValue(), item.getDesc());
        }

        return map;
    }
}
