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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Method-level annotation used for rate limiting.
 *
 * @author Zen Gershon
 * @since 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimit {

    /**
     * Unique identifier for the rate limiter.
     *
     * @return The key that distinguishes different rate limiting rules
     */
    String key();

    /**
     * Number of allowed requests per second.
     *
     * @return Maximum permitted requests per second, default to 100
     */
    int permits() default 100;

    /**
     * Timeout duration for rate-limited requests.
     *
     * @return Timeout duration in seconds, default to 1
     */
    int timeout() default 1;
}

