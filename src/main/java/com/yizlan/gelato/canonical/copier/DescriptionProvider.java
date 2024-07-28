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

package com.yizlan.gelato.canonical.copier;

import java.io.Serializable;

/**
 * Provide description.
 *
 * <p>This interface defines a generic mechanism for retrieving a description of a specific type.
 * The type of the description must implement both {@link Comparable} and {@link Serializable}.
 * When implementing this interface, ensure that the returned description adheres to the contract
 * of {@link Comparable}, and that it is properly serializable.
 *
 * @param <T> the type of the description, which must implement {@link Comparable} and {@link Serializable}
 * @author Zen Gershon
 * @since 1.0
 */
public interface DescriptionProvider<T extends Comparable<T> & Serializable> {

    /**
     * Get description
     *
     * @return description
     */
    T getDesc();

}
