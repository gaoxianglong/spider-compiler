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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.Objects;

/**
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2021/4/15 11:39 下午
 */
public class ByteBufferCache {
    private static ByteBuffer cached;
    private static Logger logger = LoggerFactory.getLogger(ByteBufferCache.class);

    protected static ByteBuffer get(int capacity) {
        capacity = capacity < 20480 ? 20480 : capacity;
        ByteBuffer result = null;
        if (Objects.nonNull(cached) && cached.capacity() >= capacity) {
            logger.debug("before Cache Hashcode:{}", System.identityHashCode(cached));
            result = cached.clear();
            logger.debug("after Cache Hashcode:{}", System.identityHashCode(result));
        } else {
            result = ByteBuffer.allocate(capacity + capacity >> 1);
        }
        cached = null;
        return result;
    }

    protected static void put(ByteBuffer x) {
        cached = x;
    }
}
