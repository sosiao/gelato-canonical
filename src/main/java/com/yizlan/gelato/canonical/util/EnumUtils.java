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

package com.yizlan.gelato.canonical.util;

import com.yizlan.gelato.canonical.enums.UnaryEnum;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * enum utils for {@link com.yizlan.gelato.canonical.enums} package
 *
 * @author Zen Gershon
 * @since 1.0
 */
public class EnumUtils {

    // Cache to store Enum instances, to avoid repeated queries
    private static final Map<Class<? extends Enum<?>>, Map<Object, ? extends Enum<?>>> ENUM_VALUE_CACHE =
            new ConcurrentHashMap<>();

    /**
     * Get an element from Enum according to their source value.
     *
     * @param enumClazz the class of enum
     * @param value     the value of enum
     * @param <E>       enum
     * @param <T>       the type of the value field
     * @return Optional containing the Enum element if found; otherwise, an empty Optional
     */
    @SuppressWarnings("unchecked")
    public static <E extends Enum<E> & UnaryEnum<T>, T extends Comparable<T> & Serializable> Optional<E> getEnumByValueOpt(
            Class<E> enumClazz, T value) {
        Objects.requireNonNull(enumClazz, "Enum class cannot be null");
        if (Objects.isNull(value)) {
            return Optional.empty();
        }

        Map<Object, E> typedCache = (Map<Object, E>) ENUM_VALUE_CACHE
                .computeIfAbsent(enumClazz, k -> new ConcurrentHashMap<>());
        E enumInstance = typedCache.get(value);
        if (enumInstance != null) {
            return Optional.of(enumInstance);
        }

        Optional<E> itemOptional = EnumSet.allOf(enumClazz).stream()
                .filter(item -> item.valueEquals(value))
                .findFirst();
        itemOptional.ifPresent(item -> typedCache.computeIfAbsent(value, k -> item));
        return itemOptional;
    }

    /**
     * Get an element from Enum according to their source value.
     *
     * @param enumClazz the class of enum
     * @param value     the value of enum
     * @param <E>       enum
     * @param <T>       the type of the value field
     * @return an element from Enum, if not found, return null
     */
    public static <E extends Enum<E> & UnaryEnum<T>, T extends Comparable<T> & Serializable> E getEnumByValue(
            Class<E> enumClazz, T value) {
        Optional<E> valueOpt = getEnumByValueOpt(enumClazz, value);
        return valueOpt.orElseGet(() -> null);
    }

}
