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
 * Token类型
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2021/4/15 10:23 下午
 */
public enum TokenKind {
    // 使用@formatter拒绝格式化
    // @formatter:off
    IDENTIFIER, INT("int"), INTLITERAL(), STRINGLITERAL(),
    TRUE("true"), FALSE("false"), BOOL("bool"), IF("if"),
    ELSE("else"), FOR("for"), LPAREN("("), RPAREN(")"),
    RBRACE("}"), LBRACKET("["), RBRACKET("]"), LBRACE("{"),
    COMMA(","), DOT("."), EQ("="), NULL("null"),
    GT(">"), LT("<"), BANG("!"), TILDE("~"),
    QUES("?"), COLON(":"), EQEQ("=="), LTEQ("<="),
    GTEQ(">="), BANGEQ("!="), AMPAMP("&&"), BARBAR("||"),
    PLUSPLUS("++"), SUBSUB("--"), PLUS("+"), SUB("-"),
    STAR("*"), SLASH("/"), AMP("&"), BAR("|"),
    CARET("^"), PERCENT("%"), LTLT("<<"), GTGT(">>"),
    GTGTGT(">>>"), PLUSEQ("+="), SUBEQ("-="), STAREQ("*="),
    SLASHEQ("/="), AMPEQ("&="), BAREQ("|="), CARETEQ("^="),
    PERCENTEQ("%="), LTLTEQ("<<="), GTGTEQ(">>="), GTGTGTEQ(">>>="),
    MONKEYS_AT("@"), RETURN("return"), SEMI(";"), VOID("void"),
    STRING("string"), ANNOTATION("//"), FLOAT("float"), FLOATLITERAL(),
    UNKNOWN();
    // @formatter:on
    public String name;

    TokenKind() {
    }

    TokenKind(String name) {
        this.name = name;
    }
}
