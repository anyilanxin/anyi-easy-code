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
package com.anyilanxin.plugin.easycode.entity;

import lombok.Data;

/**
 * 调试方法实体类
 *
 * @author makejava
 * @version 1.0.0
 * @since 2018/09/03 11:10
 */
@Data
public class DebugMethod {
    /**
     * 方法名
     */
    private String name;
    /**
     * 方法描述
     */
    private String desc;
    /**
     * 执行方法得到的值
     */
    private Object value;
}
