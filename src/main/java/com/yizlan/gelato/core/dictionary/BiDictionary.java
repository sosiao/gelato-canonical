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

import com.yizlan.gelato.core.universal.ICode;
import com.yizlan.gelato.core.universal.IText;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Provide fields which named code and text with the different type for dictionary.
 *
 * @param <T> the type of the filed which named code
 * @param <U> the type of the filed which named text
 * @author Zen Gershon
 * @see ICode
 * @see IText
 * @since 1.0
 */
public interface BiDictionary<T extends Serializable, U extends Serializable> extends ICode<T>, IText<U> {

    void setCode(T code);

    void setText(U text);

    /**
     * convert dictionary to map
     *
     * @param biDictionaries dictionary list
     * @param <T>            the type of the filed which named code
     * @param <U>            the type of the filed which named text
     * @return a Collector which collects elements into a Map whose keys are the filed which named code, and whose
     * values are the filed which named text.
     */
    static <T extends Serializable, U extends Serializable> Map<T, U> toMap(
            List<? extends BiDictionary<T, U>> biDictionaries) {
        if (biDictionaries == null || biDictionaries.isEmpty()) {
            return Collections.emptyMap();
        }

        return biDictionaries.stream().collect(
                Collectors.toMap(BiDictionary::getCode, BiDictionary::getText, (k1, k2) -> k1)
        );
    }
}
