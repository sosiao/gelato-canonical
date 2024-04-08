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
import com.yizlan.gelato.core.dictionary.Gender;
import com.yizlan.gelato.core.dictionary.TerDictionary;
import com.yizlan.gelato.core.dictionary.WarningSign;
import com.yizlan.gelato.core.enums.BiEnum;
import com.yizlan.gelato.core.enums.GenderEnum;
import com.yizlan.gelato.core.enums.TerEnum;
import com.yizlan.gelato.core.enums.WarningSignEnum;
import org.junit.jupiter.api.Test;

import java.util.List;

public class EnumTest {

    @Test
    void testEnum() {
        GenderEnum genderEnum = GenderEnum.getEnumByValue(1);
        assert genderEnum == GenderEnum.MAN;

        WarningSignEnum green = WarningSignEnum.getEnumByValue("GREEN");
        assert green == WarningSignEnum.GREEN;
    }

    @Test
    @SuppressWarnings("unchecked")
    void testToList() {
        BiEnum.toList(GenderEnum.values())
                .forEach(item -> System.out.println("convert BiEnum to BiDictionary, class name:" +
                        item.getClass().getTypeName() + ", \n value：" + item.getCode() + "|____|" + item.getText())
                );

        List<? extends BiDictionary<Integer, String>> biDictionaries = BiEnum.toList(GenderEnum.values(), Gender::new);
        biDictionaries.forEach(item -> System.out.println("convert BiEnum to Gender through BiDictionary, " +
                "class name:" + item.getClass().getTypeName() +
                ", \n value：" + item.getCode() + "|++++|" + item.getText())
        );

        List<TerDictionary<String, String, String>> terDictionaries = TerEnum.toList(WarningSignEnum.values());
        terDictionaries.forEach(item -> System.out.println("convert TernaryEnum to TerDictionary, class name:" +
                item.getClass().getTypeName() + ", \n value：" +
                item.getCode() + "|----|" + item.getText() + "|----|" + item.getDesc())
        );

        List<WarningSign> warningSignList = (List<WarningSign>) TerEnum.toList(WarningSignEnum.values(),
                WarningSign::new);
        warningSignList.stream()
                .map(warningSign -> "convert TernaryEnum to WarningSign through TerDictionary, class name:" +
                        warningSign.getClass().getTypeName() + ", \n value："
                        + warningSign.getCode() + "|____|" + warningSign.getText() + "|____|" + warningSign.getDesc())
                .forEach(System.out::println);
    }

    @Test
    void testToMap() {
        BiEnum.toMap(WarningSignEnum.values())
                .forEach((val, text) -> System.out.println("convert TernaryEnum to Map：" + val + "____" + text));
        TerEnum.toDescMap(WarningSignEnum.values())
                .forEach((val, desc) -> System.out.println("convert Desc of TernaryEnum to Map：" + val + "____" + desc));
    }
}