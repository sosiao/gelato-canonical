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

package com.yizlan.gelato.canonical.annotations;

import com.yizlan.gelato.canonical.enums.UnaryEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Method-level annotation used for audit logging.
 *
 * @author Zen Gershon
 * @since 2.6
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuditLog {

    /**
     * It indicates the module to which the log belongs.
     *
     * @return the module identifier, defaults to empty string
     */
    String module() default "";

    /**
     * Defines the specific action being performed, represented by an enum that extends {@link UnaryEnum}.
     *
     * @return the action info
     */
    Class<? extends UnaryEnum<?>> action();

    /**
     * Provides a brief summary or description of the operation.
     *
     * @return the operation summary, defaults to empty string
     */
    String summary() default "";

    /**
     * Controls whether method parameters should be included in the audit log.
     *
     * @return {@code true} if parameters should be logged, defaults to true
     */
    boolean logParams() default true;

    /**
     * Determines if the method return value should be recorded in the audit log.
     *
     * @return {@code true} if return value should be logged, defaults to false
     */
    boolean logResult() default false;
}
