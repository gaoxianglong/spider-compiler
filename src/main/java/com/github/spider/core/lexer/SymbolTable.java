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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 符号表,通过词素获取
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2021/4/15 11:19 下午
 */
public class SymbolTable {
    /**
     * 记录所有预定义类型的字符(关键字、保留字、符号)长度
     */
    private int length;

    /**
     * 每一个词素对应的Token属性值
     */
    private Map<Chars, TokenAttribute> attributeMap;

    protected SymbolTable() {
        attributeMap = new ConcurrentHashMap<>(1 << 5);
    }

    /**
     * 根据词素从符号表中获取出对应Token的属性值，如果不存在就先添加
     *
     * @param morpheme
     * @return
     */
    protected TokenAttribute getAttribute(char[] morpheme) {
        var chars = new Chars(morpheme);
        return attributeMap.computeIfAbsent(chars, key -> {
            /**
             *  每个TokenAttribute.flag标记位的值等于递增的词素长度，用于构建反向索引，从缓存中获取出对应的TokenKin类型
             *  比如词素'int a = 10'，int的flag=3，a的flag=4，10的flag=6
             *  后续词法分析时，可根据flag来判断是否是预定义类型
             */
            length += morpheme.length;
            return new TokenAttribute(morpheme, length);
        });
    }
}