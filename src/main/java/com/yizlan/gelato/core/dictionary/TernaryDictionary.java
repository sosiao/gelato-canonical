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

import java.io.Serializable;

/**
 * Provide fields which named code、text and desc with the same type for dictionary.
 * This is a specialization of {@link TerDictionary} for the case.
 *
 * @param <T> the type of fields which named code、text and desc
 * @author Zen Gershon
 * @see TerDictionary
 * @see BinaryDictionary
 * @since 1.0
 */
public interface TernaryDictionary<T extends Serializable> extends TerDictionary<T, T, T> {

}
