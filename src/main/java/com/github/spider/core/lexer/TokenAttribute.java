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
 * 每个Token对应的属性值,属性值中的内容主要包括词素和关键字/保留字标记
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2021/4/15 10:19 下午
 */
public class TokenAttribute {
    /**
     * 词素
     */
    private char[] morpheme;

    /**
     * 标记位，用于预定义类型判断和从TokenKin中获取出对应值
     */
    private int flag;

    protected TokenAttribute(char[] morpheme, int flag) {
        this.morpheme = morpheme;
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "TokenAttribute{" +
                "morpheme=" + new String(morpheme) +
                ", flag=" + flag +
                '}';
    }

    protected char[] getMorpheme() {
        return morpheme;
    }

    protected int getFlag() {
        return flag;
    }
}
