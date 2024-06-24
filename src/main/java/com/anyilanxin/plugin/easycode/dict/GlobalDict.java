/*
 * Copyright (c) 2018 makejava The MIT License (MIT)
 * Copyright © 2024 anyilanxin xuanhongzhou(anyilanxin@aliyun.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.anyilanxin.plugin.easycode.dict;

/**
 * 全局字典
 *
 * @author makejava
 * @version 1.0.0
 * @date 2021/08/07 11:41
 */
public interface GlobalDict {
    /**
     * 提示信息
     */
    String TITLE_INFO = "AyEasyCode Title Info";
    /**
     * 版本号
     */
    String VERSION = "1.0.0";
    /**
     * 作者名称
     */
    String AUTHOR = "anyilanxin";
    /**
     * 默认分组名称
     */
    String DEFAULT_GROUP_NAME = "Default";
    /**
     * 默认的Java类型列表
     */
    String[] DEFAULT_JAVA_TYPE_LIST = new String[]{
            "java.lang.String",
            "java.lang.Integer",
            "java.lang.Long",
            "java.util.Boolean",
            "java.util.Date",
            "java.time.LocalDateTime",
            "java.time.LocalDate",
            "java.time.LocalTime",
            "java.lang.Short",
            "java.lang.Byte",
            "java.lang.Character",
            "java.math.BigDecimal",
            "java.math.BigInteger",
            "java.lang.Double",
            "java.lang.Float",
            "java.lang.String[]",
            "java.util.List",
            "java.util.Set",
            "java.util.Map",
    };
}
