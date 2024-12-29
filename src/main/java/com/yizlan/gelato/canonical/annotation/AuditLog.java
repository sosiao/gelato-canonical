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

package com.yizlan.gelato.canonical.annotation;

import com.yizlan.gelato.canonical.enums.UnaryEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used for audit logging, to be applied on methods to record module, action, and summary information.
 * The purpose of this annotation is to collect audit information during method execution for subsequent log analysis
 * and system monitoring.
 *
 * @author Zen Gershon
 * @since 2.6
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuditLog {

    /**
     * Specifies the module information using a subclass of UnaryEnum. It indicates the module to which the log belongs.
     *
     * @return the module info
     */
    Class<? extends UnaryEnum<?>> module();

    /**
     * Specifies the action information using a subclass of UnaryEnum. It represents the specific action being
     * performed.
     *
     * @return the action info
     */
    Class<? extends UnaryEnum<?>> action();

    /**
     * An optional field providing a brief summary or description of the method execution. Defaults to an empty string.
     *
     * @return summary
     */
    String summary() default "";
}
