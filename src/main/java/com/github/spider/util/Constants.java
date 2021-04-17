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
package com.github.spider.util;

/**
 * 统一常量类
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2021/4/15 11:18 下午
 */
public class Constants {
    /**
     * 结束符号
     */
    public static final byte EOI = 0x1A;

    /**
     * 换行符
     */
    public static final byte LF = 0xA;

    /**
     * 回车符
     */
    public static final byte CR = 0xD;

    /**
     * 文件编码
     */
    public final static String FILE_ENCODING = System.getProperty("file.encoding");
}
