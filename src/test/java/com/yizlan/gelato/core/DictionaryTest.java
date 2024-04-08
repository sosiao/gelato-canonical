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

package com.yizlan.gelato.core;

import com.yizlan.gelato.core.dictionary.BiDictionary;
import com.yizlan.gelato.core.dictionary.TerDictionary;
import com.yizlan.gelato.core.dictionary.WarningSign;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DictionaryTest {
    private static final List<WarningSign> warningSigns = new ArrayList<>();

    @BeforeEach
    void init() {
        WarningSign red = new WarningSign();
        red.setCode("Red");
        red.setText("红色");
        red.setDesc("#667788");
        WarningSign green = new WarningSign();
        green.setCode("Green");
        green.setText("绿色 ");
        green.setDesc("#997766");

        warningSigns.add(red);
        warningSigns.add(green);
    }


    @Test
    void testToMap() {
        Map<String, String> map = BiDictionary.toMap(warningSigns);
        map.forEach((key, val) -> System.out.println("BiDictionary-toMap：" + key + "____" + val));

        TerDictionary.toDescMap(warningSigns)
                .forEach((key, desc) -> System.out.println("TerDictionary-toDescMap：" + key + "____" + desc));
    }
}
