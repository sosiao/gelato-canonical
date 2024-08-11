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

package com.yizlan.gelato.canonical.support;

import com.yizlan.gelato.canonical.exception.UnaryException;
import com.yizlan.gelato.canonical.fluent.asserts.CodeAssert;
import com.yizlan.gelato.canonical.fluent.asserts.FuncAssert;
import com.yizlan.gelato.canonical.fluent.factory.ExceptionFactory;
import com.yizlan.gelato.canonical.panic.MetaException;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Abstract class for generating assertions and handling exceptions.
 * Provides a set of assertion methods for throwing exceptions in a more flexible way.
 * It allows custom exception handling by registering exception factories
 * and dynamically creating exceptions based on conditions.
 *
 * @author Zen Gershon
 * @since 2.3
 */
public abstract class MetaAssert {

    private static final Map<Class<? extends MetaException>, ExceptionFactory<?, ? extends MetaException>>
            DEFAULT_FACTORY = new ConcurrentHashMap<>();

    protected MetaAssert() {
        // to do nothing
    }

    /**
     * Registers an exception factory for a specific exception class.
     *
     * @param exceptionClazz the class of the exception for which the factory is being registered.
     * @param factory        the ExceptionFactory instance used to create exceptions with the specified type.
     * @param <T>            the type of the exception which must be a subclass of {@code MetaException}.
     * @throws IllegalArgumentException if the exception class or factory is null.
     */
    protected static <T extends MetaException> void registerFactory(Class<T> exceptionClazz,
                                                                    ExceptionFactory<?, T> factory) {
        if (exceptionClazz == null) {
            throw new IllegalArgumentException("Exception class is not specified.");
        }
        if (factory == null) {
            throw new IllegalArgumentException("ExceptionFactory cannot be null.");
        }
        DEFAULT_FACTORY.put(exceptionClazz, factory);
    }

    /**
     * Retrieves the default exception factory for a given exception class.
     *
     * @param exceptionClazz the class of the exception for which to retrieve the factory.
     * @param <T>            a comparable and serializable type used for the exception code.
     * @param <R>            the type of the exception which must be a subclass of {@code MetaException}.
     * @return the ExceptionFactory instance for creating exceptions with the specified type.
     * @throws IllegalStateException if no factory is registered for the exception class.
     */
    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T> & Serializable, R extends MetaException> ExceptionFactory<T, R> getDefaultFactory(
            Class<R> exceptionClazz) {
        Object factory = DEFAULT_FACTORY.get(exceptionClazz);
        return Optional.ofNullable(factory)
                .map(f -> (ExceptionFactory<T, R>) f)
                .orElseThrow(() -> new IllegalStateException("Factory not set."));
    }

    /**
     * Creates an exception instance using the default factory, based on the provided class, code, and additional
     * arguments.
     *
     * @param exceptionClazz the exception class, specifying the type of exception that extends {@code MetaException}.
     * @param code           the error code
     * @param args           optional parameters for formatting the exception message.
     * @param <T>            a comparable and serializable type used for the exception code.
     * @param <R>            the type of the exception which must be a subclass of {@code MetaException}.
     * @return An instance of the specified exception class.
     */
    protected static <T extends Comparable<T> & Serializable, R extends MetaException> R createException(final Class<R> exceptionClazz,
                                                                                                         final T code,
                                                                                                         final Object... args) {
        ExceptionFactory<T, R> exceptionFactory = getDefaultFactory(exceptionClazz);
        validateCode(code);
        return exceptionFactory.create(code, args);
    }

    /**
     * Creates an exception instance using the default factory, based on a UnaryException instance and additional
     * arguments.
     *
     * @param exceptionClazz the exception class, specifying the type of exception that extends {@code MetaException}.
     * @param exception      a UnaryException instance containing the error code.
     * @param args           optional arguments for formatting the exception message.
     * @param <T>            a comparable and serializable type used for the exception code.
     * @param <R>            the type of the exception which must be a subclass of {@code MetaException}.
     * @return An instance of the specified exception class.
     */
    protected static <T extends Comparable<T> & Serializable, R extends MetaException> R createException(final Class<R> exceptionClazz,
                                                                                                         final UnaryException<T> exception,
                                                                                                         final Object... args) {
        ExceptionFactory<T, R> exceptionFactory = getDefaultFactory(exceptionClazz);
        Objects.requireNonNull(exception, "UnaryException cannot be null.");
        validateCode(exception.getCode());
        return exceptionFactory.create(exception.getCode(), args);
    }

    /**
     * Creates a CodeAssert instance for conditional exception throwing based on a boolean value.
     *
     * @param condition the boolean condition; if true, an exception is thrown.
     * @return a CodeAssert instance for asserting conditions and throwing exceptions.
     */
    protected static CodeAssert codeAssert(final boolean condition) {
        return new CodeAssert() {

            @Override
            public <T extends Comparable<T> & Serializable> void throwException(final Class<? extends MetaException> exceptionClazz,
                                                                                final T code,
                                                                                final Object... args) {
                if (condition) {
                    throw createException(exceptionClazz, code, args);
                }
            }

        };
    }

    /**
     * Creates a FuncAssert instance for conditional exception throwing based on a boolean value, using a
     * UnaryException.
     *
     * @param condition the boolean condition; if true, an exception is thrown.
     * @return a FuncAssert instance for asserting conditions and throwing exceptions based on functional interfaces.
     */
    protected static FuncAssert funcAssert(final boolean condition) {
        return new FuncAssert() {

            @Override
            public <T extends Comparable<T> & Serializable> void throwException(final Class<? extends MetaException> exceptionClazz,
                                                                                final UnaryException<T> exception,
                                                                                final Object... args) {
                if (condition) {
                    throw createException(exceptionClazz, exception, args);
                }
            }

        };
    }

    /**
     * Validates the provided error code before throwing an exception.
     *
     * @param code the error code
     * @param <T>  the type of the parameter that named code,
     *             should implement {@link Comparable} and {@link Serializable}
     * @throws IllegalArgumentException if the error code is null or empty.
     */
    protected static <T extends Comparable<T> & Serializable> void validateCode(final T code) {
        if (code instanceof String) {
            String string = (String) code;
            if (string.isEmpty()) {
                throw new IllegalArgumentException("Error code must not be empty.");
            }
        }

        if (Objects.isNull(code)) {
            throw new IllegalArgumentException("Error code must not be null.");
        }
    }

}
