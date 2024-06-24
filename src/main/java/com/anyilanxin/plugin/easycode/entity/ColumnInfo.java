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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.intellij.database.model.DasColumn;
import lombok.Data;

import java.util.Map;

/**
 * 列信息
 *
 * @author makejava
 * @version 1.0.0
 * @since 2018/07/17 13:10
 */
@Data
public class ColumnInfo {
    /**
     * 原始对象
     */
    @JsonIgnore
    private DasColumn obj;
    /**
     * 名称
     */
    private String name;
    /**
     * 注释
     */
    private String comment;
    /**
     * 全类型
     */
    private String type;
    /**
     * 短类型
     */
    private String shortType;
    /**
     * 标记是否为自定义附加列
     */
    private Boolean custom;
    /**
     * 扩展数据
     */
    private Map<String, Object> ext;
}
