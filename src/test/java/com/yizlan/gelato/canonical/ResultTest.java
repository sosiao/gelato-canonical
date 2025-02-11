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

package com.yizlan.gelato.canonical;

import com.yizlan.gelato.canonical.dictionary.Gender;
import com.yizlan.gelato.canonical.protocol.ApiResult;
import org.junit.jupiter.api.Test;

public class ResultTest {

    @Test
    public void testResult() {
        ApiResult<Gender> apiResult = new ApiResult<>();

        Gender gender = new Gender();
        gender.setCode(1);
        gender.setName("男");
        apiResult.setData(gender);


        System.out.println(apiResult.success().code(200).message("success").data(gender));
        System.out.println(apiResult.failure().varargs(1));
    }

}
