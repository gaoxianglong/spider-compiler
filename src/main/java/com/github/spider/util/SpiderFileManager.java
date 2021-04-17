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

import com.github.spider.exception.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Objects;

/**
 * Spider文件管理类
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2021/4/15 11:37 下午
 */
public class SpiderFileManager {
    private static Logger log = LoggerFactory.getLogger(SpiderFileManager.class);

    /**
     * 将源文件内容解析到ByteBuffer中
     *
     * @param in
     * @return
     * @throws Throwable
     */
    private static ByteBuffer makeByteBuffer(InputStream in) throws Throwable {
        Objects.requireNonNull(in);
        var limit = in.available() <= 1024 ? 1024 : in.available();
        var result = ByteBufferCache.get(limit);
        // 将文件内容读入到ByteBuffer中，并设置其起始位置
        result.position(in.read(result.array(), 0, limit));
        return result.flip();// 复位position,limit=position,position=0
    }

    /**
     * 将源码读取到char[]中
     *
     * @param file
     * @return
     * @throws Throwable
     */
    public static CharBuffer getCharContent(File file) throws Throwable {
        InputStream in = new FileInputStream(file);
        if (0 >= in.available()) {
            throw new ParseException(String.format("The file content is empty, file: %s", file.getName()));
        }
        var bb = makeByteBuffer(in);
        ByteBufferCache.put(bb);
        return Charset.forName(Constants.FILE_ENCODING).decode(bb);//解码后直接返回
    }

    /**
     * 将CharBuffer转换为char[]
     *
     * @param cb
     * @return
     */
    public static char[] toArray(CharBuffer cb) {
        var src = cb.hasArray() ? ((CharBuffer) cb.compact().flip()).array() :
                cb.toString().toCharArray();
        var target = new char[src.length + 3];
        System.arraycopy(src, 0, target, 1, src.length);
        // 将源文件填充到{}中
        target[0] = '{';
        target[target.length - 2] = '\n';
        target[target.length - 1] = '}';
        return target;
    }
}
