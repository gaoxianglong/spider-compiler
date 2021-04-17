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
package com.github.test.spider.core.lexer;

import com.github.spider.core.lexer.Scanner;
import com.github.spider.core.lexer.SpiderLexer;
import com.github.spider.core.lexer.Token;
import com.github.spider.core.lexer.TokenKind;
import org.junit.Assert;
import org.junit.Test;

import java.util.Objects;

/**
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2021/4/17 3:06 下午
 */
public class ScannerTest {
    @Test
    public void nextToken() {
        String codes = "int value = 10;\n" +
                "bool value_2 = true;// 注释1\n" +
                "string value_3 = \"Hello Spider\";";
        try {
            var lexerCls = Scanner.class;
            var constructor = lexerCls.getDeclaredConstructor(new Class[]{char[].class});
            constructor.setAccessible(true);
            var lexer = (SpiderLexer) constructor.newInstance(new Object[]{codes.toCharArray()});
            var initMethod = lexerCls.getDeclaredMethod("init");
            initMethod.setAccessible(true);
            // 执行init方法初始化词法解析器
            initMethod.invoke(lexer);

            Token token = null;
            do {
                token = lexer.nextToken();
                if (Objects.nonNull(token)) {
                    System.out.println(token);
                }
            } while (Objects.nonNull(token));

            var field = lexerCls.getDeclaredField("index");
            field.setAccessible(true);
            field.set(lexer, 0);
            Assert.assertEquals(TokenKind.INT, lexer.nextToken().getTokenKind());
            Assert.assertEquals(TokenKind.IDENTIFIER, lexer.nextToken().getTokenKind());
            Assert.assertEquals(TokenKind.EQ, lexer.nextToken().getTokenKind());
            Assert.assertEquals(TokenKind.INTLITERAL, lexer.nextToken().getTokenKind());
            Assert.assertEquals(TokenKind.SEMI, lexer.nextToken().getTokenKind());

            Assert.assertEquals(TokenKind.BOOL, lexer.nextToken().getTokenKind());
            Assert.assertEquals(TokenKind.IDENTIFIER, lexer.nextToken().getTokenKind());
            Assert.assertEquals(TokenKind.EQ, lexer.nextToken().getTokenKind());
            Assert.assertEquals(TokenKind.TRUE, lexer.nextToken().getTokenKind());
            Assert.assertEquals(TokenKind.SEMI, lexer.nextToken().getTokenKind());
            Assert.assertEquals(TokenKind.ANNOTATION, lexer.nextToken().getTokenKind());

            Assert.assertEquals(TokenKind.STRING, lexer.nextToken().getTokenKind());
            Assert.assertEquals(TokenKind.IDENTIFIER, lexer.nextToken().getTokenKind());
            Assert.assertEquals(TokenKind.EQ, lexer.nextToken().getTokenKind());
            Assert.assertEquals(TokenKind.STRINGLITERAL, lexer.nextToken().getTokenKind());
            Assert.assertEquals(TokenKind.SEMI, lexer.nextToken().getTokenKind());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
