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

package com.yizlan.gelato.core.panic;

import com.yizlan.gelato.core.exception.BinaryException;
import com.yizlan.gelato.core.universal.ICode;
import com.yizlan.gelato.core.util.CSharpStrUtils;

import java.text.MessageFormat;

/**
 * service exception
 *
 * @author Zen Gershon
 * @since 1.0
 */
public class ServiceException extends RuntimeException implements ICode<String> {
    private static final long serialVersionUID = 1L;

    /**
     * error code
     */
    private final String code;

    /**
     * reserved parameters
     */
    private final Object[] args;

    @Override
    public String getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }

    /**
     * Constructs a new service exception with the specified code and detail message.
     *
     * @param code error code
     * @param args reserved parameters
     */
    public ServiceException(final String code, final Object... args) {
        super();
        this.code = code;
        this.args = args;
    }

    /**
     * Constructs a new service exception with binary generic enum interface as parameter and detail message.
     *
     * @param exception binary generic enum interface
     * @param args      reserved parameters
     */
    public ServiceException(final BinaryException<String> exception, final Object... args) {
        super();
        this.code = exception.getCode();
        String msg = exception.getMsg();
        if (CSharpStrUtils.isBlank(msg)) {
            this.args = args;
        } else {
            String message = MessageFormat.format(msg, args);
            this.args = new Object[]{message};
        }
    }
}
