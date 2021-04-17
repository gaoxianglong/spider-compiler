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
import com.github.spider.util.Constants;
import com.github.spider.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * Spider词法分析器实现类
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2021/4/15 11:13 下午
 */
public class Scanner implements SpiderLexer {
    /**
     * 源代码内容
     */
    private char[] codes;

    /**
     * 当前符号
     */
    private char ch;

    /**
     * 符号表
     */
    private SymbolTable symbolTable;

    /**
     * 记录预定义类型的最大标记位,如果此值意味着Token类型为标识符
     */
    private int maxflag;

    /**
     * 反向索引，Attribute.flag对应TokenKin序数
     */
    private Map<Integer, Integer> ordinals;

    /**
     * 词素缓存
     */
    private char[] sBuf;

    /**
     * 字符索引
     */
    private int index;
    private Logger logger = LoggerFactory.getLogger(Scanner.class);

    protected Scanner(char[] cs) {
        Objects.requireNonNull(cs);
        codes = cs;
        // 扩展源码数组长度,最后一位存放结束符
        codes = Arrays.copyOf(codes, codes.length + 1);
        codes[codes.length - 1] = Constants.EOI;
        // 初始化符号表
        symbolTable = new SymbolTable();
        ordinals = new ConcurrentHashMap<>(1 << 5);
    }

    /**
     * 词法解析器的初始化操作，主要任务如下：
     * 1、将TokenKin中所有预定义类型(关键字、保留字、符号等)的TokenAttribute添加到符号表中
     * 2、记录所有预定义类型的TokenKin序数
     * 3、记录最大标记位，词法解析时用于判断Token类型
     *
     * @return
     */
    protected Scanner init() {
        Stream.of(TokenKind.values()).forEach(tokenKind -> {
            var name = tokenKind.name;
            if (Objects.nonNull(name) && !name.isBlank()) {
                // 根据词素从符号表中获取出对应的TokenAttribute,没有则添加
                var attribute = symbolTable.getAttribute(name.toCharArray());
                var flag = attribute.getFlag();
                // 记录预定义类型的TokenKin序数，与flag相对应
                ordinals.put(flag, tokenKind.ordinal());
                // 记录预定义类型的标记位，当词素的flag>maxKey时则意味着是标识符
                maxflag = flag;
            }
        });
        logger.debug("maxflag:{}", maxflag);
        return this;
    }

    @Override
    public Token nextToken() throws LexerParseException {
        // 记录每一个Token的起始位
        var pos = index;
        Token token = null;
        loop:
        do {
            // 读取下一个字符
            nextChar();
            switch (ch) {
                //@formatter:off
                case ' ': case '\t': case Constants.LF: case Constants.CR: case '\u0000':
                    break;
                // 如果是字母或者符号"$"、"_"开头则以标识符的方式读取
                case 'A': case 'B': case 'C': case 'D': case 'E':
                case 'F': case 'G': case 'H': case 'I': case 'J':
                case 'K': case 'L': case 'M': case 'N': case 'O':
                case 'P': case 'Q': case 'R': case 'S': case 'T':
                case 'U': case 'V': case 'W': case 'X': case 'Y':
                case 'Z':
                case 'a': case 'b': case 'c': case 'd': case 'e':
                case 'f': case 'g': case 'h': case 'i': case 'j':
                case 'k': case 'l': case 'm': case 'n': case 'o':
                case 'p': case 'q': case 'r': case 's': case 't':
                case 'u': case 'v': case 'w': case 'x': case 'y':
                case 'z':
                case '$': case '_':
                    // 读取标识符
                    token = scanIdent(pos);
                    break loop;
                // 读取数字字面值
                case '0': case '1': case '2': case '3': case '4':
                case '5': case '6': case '7': case '8': case '9':
                    token = scanNumber(pos);
                    break loop;
                // 读取字符串字面值
                case '\"':
                    token = scanString(pos);
                    break loop;
                case '[': case ']': case '(': case ')':case '.':
                case ',': case '{': case '}': case ';':
                   try{
                       addMorpheme();
                       token = getToken(pos);
                   }finally{
                       sBuf = null;
                   }
                    break loop;
                //@formatter:on
                default:
                    // 读取中文标识符或符号
                    token = Utils.isChinese(ch) ? scanIdent(pos) : scanOperator(pos);
                    break loop;
            }
        } while (ch != Constants.EOI);
        return token;
    }

    /**
     * 组装词素，将其缓存起来
     */
    private void addMorpheme() {
        if (Objects.isNull(sBuf)) {
            sBuf = new char[1];
        } else {
            // 当有新的字符被添加时，动态扩展数组长度
            sBuf = Arrays.copyOf(sBuf, sBuf.length + 1);
        }
        sBuf[sBuf.length - 1] = ch;
    }

    /**
     * 读取符号
     *
     * @param pos
     * @return
     */
    private Token scanOperator(int pos) {
        if (ch == Constants.EOI) {
            return null;
        }
        while (true) {
            switch (ch) {
                //@formatter:off
                case '!': case '%': case '&': case '*':
                case '?': case '+': case '-': case ':':
                case '<': case '=': case '>': case '^':
                case '|': case '~': case '@': case '/':
                    break;
                //@formatter:on
                default:
                    try {
                        return getToken(pos);
                    } finally {
                        prevChar();
                        sBuf = null;
                    }
            }
            addMorpheme();
            var tokenKin = getTokenKin(symbolTable.getAttribute(sBuf).getFlag());
            if (TokenKind.IDENTIFIER == tokenKin) {
                try {
                    sBuf = Arrays.copyOf(sBuf, sBuf.length - 1);
                    return getToken(pos);
                } finally {
                    prevChar();
                    sBuf = null;
                }
            }
            if (TokenKind.ANNOTATION == tokenKin) {
                while (true) {
                    nextChar();
                    addMorpheme();
                    if (ch == Constants.CR || ch == Constants.LF || ch == Constants.EOI) {
                        try {
                            return getToken(TokenKind.ANNOTATION, pos);
                        } finally {
                            prevChar();
                            sBuf = null;
                        }
                    }
                }
            }
            nextChar();
        }
    }

    /**
     * 读取字符串字面值
     *
     * @param pos
     * @return
     * @throws LexerParseException
     */
    private Token scanString(int pos) throws LexerParseException {
        while (true) {
            addMorpheme();
            nextChar();
            if (ch == '\"' || ch == Constants.EOI) {
                if (ch != '\"') {
                    throw new LexerParseException(String.format("String format error: %s", new String(sBuf)));
                }
                addMorpheme();
                try {
                    return getToken(TokenKind.STRINGLITERAL, pos);
                } finally {
                    sBuf = null;
                }
            }
        }
    }

    /**
     * 读取数字字面值
     *
     * @param pos
     * @return
     */
    private Token scanNumber(int pos) {
        var isPoint = false;
        while (true) {
            switch (ch) {
                //@formatter:off
                case '0': case '1': case '2': case '3': case '4':
                case '5': case '6': case '7': case '8': case '9':
                    break;
                case '.':
                    isPoint = true;
                    break;
                //@formatter:on
                default:
                    try {
                        // 返回Token
                        return getToken(isPoint ? TokenKind.FLOATLITERAL : TokenKind.INTLITERAL, pos);
                    } finally {
                        // 回溯上一个符号
                        prevChar();
                        sBuf = null;
                    }
            }
            addMorpheme();
            nextChar();
        }
    }

    /**
     * 读取一个完整的标识符
     *
     * @param pos
     * @return
     */
    private Token scanIdent(int pos) {
        while (true) {
            switch (ch) {
                //@formatter:off
                case 'A': case 'B': case 'C': case 'D': case 'E':
                case 'F': case 'G': case 'H': case 'I': case 'J':
                case 'K': case 'L': case 'M': case 'N': case 'O':
                case 'P': case 'Q': case 'R': case 'S': case 'T':
                case 'U': case 'V': case 'W': case 'X': case 'Y':
                case 'Z':
                case 'a': case 'b': case 'c': case 'd': case 'e':
                case 'f': case 'g': case 'h': case 'i': case 'j':
                case 'k': case 'l': case 'm': case 'n': case 'o':
                case 'p': case 'q': case 'r': case 's': case 't':
                case 'u': case 'v': case 'w': case 'x': case 'y':
                case 'z':
                case '$': case '_':
                case '0': case '1': case '2': case '3': case '4':
                case '5': case '6': case '7': case '8': case '9':
                    break;
                //@formatter:on
                default:
                    // 判断是否是汉子编码
                    if (Utils.isChinese(ch)) {
                        break;
                    }
                    try {
                        // 返回Token
                        return getToken(pos);
                    } finally {
                        // 回溯上一个符号
                        prevChar();
                        sBuf = null;
                    }
            }
            // 组装词素
            addMorpheme();
            // 预读下一个字符
            nextChar();
        }
    }

    /**
     * 获取Token
     *
     * @param pos Token起始位
     * @return
     */
    private Token getToken(int pos) {
        return getToken(null, pos);
    }

    private Token getToken(TokenKind tokenKind, int pos) {
        // 根据词素从符号表中获取出TokenAttribute
        var attribute = symbolTable.getAttribute(sBuf);
        // 指定Token类型后返回
        return new Token(attribute, Objects.nonNull(tokenKind) ? tokenKind :
                getTokenKin(attribute.getFlag()), pos);
    }

    /**
     * 获取TokenKind类型
     *
     * @param flag 标记位
     * @return
     */
    private TokenKind getTokenKin(int flag) {
        // 如果flag <= maxflag则意味着是Token的类型为预定义类型，反之为标识符类型
        if (flag <= maxflag) {
            // 根据flag获取预定义类型的TokenKind枚举序数
            var ordinal = ordinals.get(flag);
            return TokenKind.values()[ordinal];
        }
        return TokenKind.IDENTIFIER;
    }

    @Override
    public void prevToken(int pos) {
        index = pos;
        ch = codes[index];
    }

    @Override
    public void prevChar() {
        ch = codes[--index];
    }

    @Override
    public void nextChar() {
        if (index < codes.length) {
            ch = codes[index++];
        }
    }

    @Override
    public String toString() {
        return "Scanner{" +
                "codes=" + Arrays.toString(codes) +
                ", ch=" + ch +
                ", symbolTable=" + symbolTable +
                ", maxflag=" + maxflag +
                ", ordinals=" + ordinals +
                ", sBuf=" + Arrays.toString(sBuf) +
                ", index=" + index +
                '}';
    }
}
