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

package com.yizlan.gelato.core.exception;

import com.yizlan.gelato.core.universal.ICode;

import java.io.Serializable;

/**
 * Provide fields which named code and msg with the different type for exception.
 * This is the two-arity specialization of {@link ICode}.
 *
 * @param <T> the type of the filed which named code
 * @param <U> the type of the filed which named msg
 * @author Zen Gershon
 * @see ICode
 * @since 1.0
 */
public interface BiException<T extends Serializable, U extends Serializable> extends ICode<T> {

    /**
     * Get exception message
     *
     * @return exception message
     */
    U getMsg();
}
