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
package com.github.spider.exception;

/**
 * 解析异常类
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2021/4/15 10:49 下午
 */
public class ParseException extends SpiderException {
    private static final long serialVersionUID = -1058399474484985932L;

    public ParseException() {
        super();
    }

    public ParseException(String str) {
        super(str);
    }

    public ParseException(Throwable throwable) {
        super(throwable);
    }

    public ParseException(String str, Throwable throwable) {
        super(str, throwable);
    }
}
