/*
 * Copyright (C) 2025 the original author or authors.
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
import java.util.concurrent.TimeUnit;

/**
 * Method-level annotation used for the distributed lock.
 *
 * @author Zen Gershon
 * @since 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DistributedLock {

    /**
     * Specifies the unique key for the distributed lock.
     * This key must be unique across the system to prevent lock conflicts.
     *
     * @return the unique key string for the lock
     */
    String key();

    /**
     * Maximum time to wait for acquiring the lock before timing out.
     *
     * @return wait timeout duration, defaults to 5
     */
    int waitTime() default 5;

    /**
     * Maximum time the lock will be held before automatic expiration.
     * Must be longer than the method execution time to prevent premature release.
     *
     * @return lock expiration duration, defaults to 30
     */
    int expireTime() default 30;

    /**
     * Time unit for both waitTime and expireTime durations.
     *
     * @return the time unit, defaults to {@code SECONDS}
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * Specifies the type of lock to be used.
     *
     * @return The enum class extending {@link UnaryEnum} that represents the lock type
     */
    Class<? extends UnaryEnum<?>> lockType();

    /**
     * Custom error message to return when lock acquisition fails.
     *
     * @return failure message, defaults to "系统繁忙，请稍后再试"
     */
    String msg() default "系统繁忙，请稍后再试";
}
