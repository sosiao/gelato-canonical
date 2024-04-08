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

package com.yizlan.gelato.core.dictionary;

import com.yizlan.gelato.core.universal.IDescription;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Provide fields which named code、text and desc with the different type for dictionary.
 *
 * @param <T> the type of the filed which named code
 * @param <U> the type of the filed which named text
 * @param <S> the type of the filed which named desc
 * @author Zen Gershon
 * @see BiDictionary
 * @see IDescription
 * @since 1.0
 */
public interface TerDictionary<T extends Serializable, U extends Serializable, S extends Serializable>
        extends BiDictionary<T, U>, IDescription<S> {

    void setDesc(S desc);

    /**
     * convert dictionary to map
     *
     * @param terDictionaries dictionary
     * @param <T>             the type of the filed which named code
     * @param <U>             the type of the filed which named text
     * @param <S>             the type of the filed which named desc
     * @return a Collector which collects elements into a Map whose keys are the filed which named code, and whose
     * values are the filed which named desc.
     */
    static <T extends Serializable, U extends Serializable, S extends Serializable> Map<T, S> toDescMap(
            List<? extends TerDictionary<T, U, S>> terDictionaries) {
        if (terDictionaries == null || terDictionaries.isEmpty()) {
            return Collections.emptyMap();
        }

        return terDictionaries.stream().collect(
                Collectors.toMap(TerDictionary::getCode, TerDictionary::getDesc, (k1, k2) -> k1)
        );
    }
}
