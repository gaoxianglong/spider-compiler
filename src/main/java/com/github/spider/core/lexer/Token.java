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

/**
 * 词法单元,也称之为Token，每个Token都对应着一个词素（词素即Token的一个实例）
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2021/4/15 10:17 下午
 */
public class Token {
    /**
     * 对个Token都对应一个属性值
     */
    private TokenAttribute attribute;

    /**
     * Token类型
     */
    private TokenKind tokenKind;

    /**
     * Token位置，回溯时使用
     */
    private int pos;

    /**
     * 词素长度
     */
    private int length;

    protected Token(TokenAttribute attribute, TokenKind tokenKind, int pos) {
        this.attribute = attribute;
        this.tokenKind = tokenKind;
        this.pos = pos;
        this.length = attribute.getMorpheme().length;
    }

    public TokenAttribute getAttribute() {
        return attribute;
    }

    public TokenKind getTokenKind() {
        return tokenKind;
    }

    public int getPos() {
        return pos;
    }

    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        return "Token{" +
                "attribute=" + attribute +
                ", tokenKind=" + tokenKind +
                ", pos=" + pos +
                ", length=" + length +
                '}';
    }
}
