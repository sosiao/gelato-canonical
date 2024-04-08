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

import com.yizlan.gelato.core.universal.ICode;
import com.yizlan.gelato.core.dictionary.TerDictionary;
import com.yizlan.gelato.core.universal.IDescription;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Provide fields which named value、text and desc with the different type for enum.
 * This is the three-arity specialization of {@link ICode}.
 *
 * @param <T> the type of the filed which named value
 * @param <U> the type of the filed which named text
 * @param <S> the type of the filed which named desc
 * @author Zen Gershon
 * @see BiEnum
 * @see IDescription
 * @since 1.0
 */
public interface TerEnum<T extends Serializable, U extends Serializable, S extends Serializable>
        extends BiEnum<T, U>, IDescription<S> {

    /**
     * @param enumValues the values of enum
     * @param <T>        the type of the filed which named value
     * @param <U>        the type of the filed which named text
     * @param <S>        the type of the filed which named desc
     * @return dictionary list
     */
    static <T extends Serializable, U extends Serializable, S extends Serializable> List<TerDictionary<T, U, S>> toList(
            TerEnum<T, U, S>[] enumValues) {
        List<TerDictionary<T, U, S>> terDictionaries = new ArrayList<>(enumValues.length);

        for (TerEnum<T, U, S> item : enumValues) {
            TerDictionary<T, U, S> terDictionary = new TerDictionary<T, U, S>() {
                @Override
                public U getText() {
                    return item.getText();
                }

                @Override
                public S getDesc() {
                    return item.getDesc();
                }

                @Override
                public T getCode() {
                    return item.getValue();
                }

                @Override
                public void setDesc(S desc) {

                }

                @Override
                public void setCode(T code) {

                }

                @Override
                public void setText(U text) {

                }
            };
            terDictionaries.add(terDictionary);
        }

        return terDictionaries;
    }

    /**
     * @param enumValues the values of enum
     * @param supplier   the supplier (typically bound to a lambda expression)
     * @param <T>        the type of the filed which named value
     * @param <U>        the type of the filed which named text
     * @param <S>        the type of the filed which named desc
     * @return dictionary list
     */
    static <T extends Serializable, U extends Serializable, S extends Serializable> List<? extends TerDictionary<T, U, S>> toList(
            TerEnum<T, U, S>[] enumValues, Supplier<? extends TerDictionary<T, U, S>> supplier) {
        List<TerDictionary<T, U, S>> terDictionaries = new ArrayList<>(enumValues.length);

        for (TerEnum<T, U, S> item : enumValues) {
            TerDictionary<T, U, S> terDictionary = supplier.get();
            terDictionary.setCode(item.getValue());
            terDictionary.setText(item.getText());
            terDictionary.setDesc(item.getDesc());

            terDictionaries.add(terDictionary);
        }

        return terDictionaries;
    }

    /**
     * @param enumValues the values of enum
     * @param <T>        the type of the filed which named value
     * @param <U>        the type of the filed which named text
     * @param <S>        the type of the filed which named desc
     * @return an Enum which collects elements into a Map whose keys are the filed which named code, and whose
     * values are the filed which named text.
     */
    static <T extends Serializable, U extends Serializable, S extends Serializable> Map<T, S> toDescMap(TerEnum<T, U, S>[] enumValues) {
        Map<T, S> map = new HashMap<>(enumValues.length);

        for (TerEnum<T, U, S> item : enumValues) {
            map.put(item.getValue(), item.getDesc());
        }

        return map;
    }
}
