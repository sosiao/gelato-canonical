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

package com.yizlan.gelato.core.util;

/**
 * C# string IsNullOrEmpty and IsNullOrWhiteSpace
 *
 * @author Zen Gershon
 * @since 1.0
 */
public class CSharpStrUtils {

    /**
     * <p>Checks if a CharSequence is empty (""), null or whitespace only.</p>
     *
     * <p>Whitespace is defined by {@link Character#isWhitespace(char)}.</p>
     *
     * <pre>
     * CSharpStrUtils.isBlank(null)      = true
     * CSharpStrUtils.isBlank("")        = true
     * CSharpStrUtils.isBlank(" ")       = true
     * CSharpStrUtils.isBlank("bob")     = false
     * CSharpStrUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param value the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is null, empty or whitespace only
     */
    public static boolean isBlank(final CharSequence value) {
        if (value == null || value.length() == 0) {
            return true;
        }
        for (int i = 0, len = value.length(); i < len; i++) {
            if (!Character.isWhitespace(value.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
