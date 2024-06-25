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

package com.yizlan.gelato.canonical.protocol;

import java.io.Serializable;

/**
 * Provide fields which named code、message with the same type and data with the different type for result.
 * This is a specialization of {@link TerResult} for the case.
 *
 * @param <T> the type of fields that contains code and message
 * @param <U> the type of the data filed
 * @author Zen Gershon
 * @see TerResult
 * @see BinaryResult
 * @since 1.0
 */
public interface TernaryResult<T extends Comparable<T> & Serializable, U> extends TerResult<T, T, U> {

}
