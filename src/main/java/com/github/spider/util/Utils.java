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
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2021/4/17 6:48 下午
 */
public class Utils {
    /**
     * 判断是否是中文字符
     *
     * @param ch
     * @return
     */
    public static boolean isChinese(char ch) {
        // 在汉字Unicode编码的区间进行判断
        return ch >= 0x4E00 && ch <= 0x29FA5;
    }
}
