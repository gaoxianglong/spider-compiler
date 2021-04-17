/*
 * Copyright 2019-2119 gao_xianglong@sina.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.spider.core.lexer;

import java.util.Arrays;

/**
 * 词素封装类
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2021/4/15 10:38 下午
 */
public class Chars {
    /**
     * 词素
     */
    private char[] morpheme;

    protected Chars(char[] morpheme) {
        this.morpheme = morpheme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chars)) return false;
        Chars chars = (Chars) o;
        return Arrays.equals(getMorpheme(), chars.getMorpheme());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getMorpheme());
    }

    public char[] getMorpheme() {
        return morpheme;
    }

    @Override
    public String toString() {
        return "Chars{" +
                "morpheme=" + Arrays.toString(morpheme) +
                '}';
    }
}
