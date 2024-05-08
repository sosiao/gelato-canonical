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

package com.yizlan.gelato.core.util;

import com.yizlan.gelato.core.enums.BiEnum;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.Objects;

/**
 * enum utils for enums package
 *
 * @author Zen Gershon
 * @since 1.0
 */
public class EnumUtils {

    /**
     * Get an element from Enum according to their source value.
     *
     * @param enumClazz the class of enum
     * @param value     the value of enum
     * @param <E>       enum
     * @param <T>       the type of the value field
     * @param <U>       the type of the label field
     * @param <M>       subclass of BiEnum
     * @return an element from Enum.
     */
    @SuppressWarnings("unchecked")
    public static <E extends Enum<E>, T extends Serializable, U extends Serializable, M extends BiEnum<T, U>> M getEnumByValue(
            Class<E> enumClazz, T value) {
        if (Objects.isNull(enumClazz)) {
            return null;
        }
        EnumSet<E> es = EnumSet.allOf(enumClazz);
        for (E item : es) {
            if (item instanceof BiEnum && (((M) item).valueEquals(value))) {
                return (M) item;
            }
        }
        return null;
    }
}
