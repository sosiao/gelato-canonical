/*
 * Copyright (C) 2024 the original author or authors.
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

package com.yizlan.gelato.canonical.fluent;

/**
 * code assert
 *
 * @author Zen Gershon
 * @since 1.0
 */
@FunctionalInterface
public interface CodeAssert {

    /**
     * throw exception
     *
     * @param code error code
     * @param args placeholder parameters
     */
    void throwException(final String code, final Object... args);
}
