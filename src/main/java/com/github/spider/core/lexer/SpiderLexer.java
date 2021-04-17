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

import com.github.spider.exception.LexerParseException;

/**
 * Spider词法分析器接口
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2021/4/15 10:46 下午
 */
public interface SpiderLexer {
    /**
     * 读取下一个Token序列
     *
     * @return
     * @throws LexerParseException
     */
    Token nextToken() throws LexerParseException;

    /**
     * 回溯到上一个Token
     *
     * @param pos
     */
    void prevToken(int pos);

    /**
     * 回溯到上一个字符,这里是为了解决预读场景
     */
    void prevChar();

    /**
     * 读取下一个字符
     */
    void nextChar();
}
