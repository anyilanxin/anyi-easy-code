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

import com.anyilanxin.plugin.easycode.enums.ColumnConfigType;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 列配置信息
 *
 * @author makejava
 * @version 1.0.0
 * @since 2018/07/17 13:10
 */
@Data
@NoArgsConstructor
public class ColumnConfig implements AbstractItem<ColumnConfig> {
    /**
     * 标题
     */
    private String title;
    /**
     * 类型
     */
    private ColumnConfigType type;
    /**
     * 可选值，逗号分割
     */
    private String selectValue;

    public ColumnConfig(String title, ColumnConfigType type) {
        this.title = title;
        this.type = type;
    }

    public ColumnConfig(String title, ColumnConfigType type, String selectValue) {
        this.title = title;
        this.type = type;
        this.selectValue = selectValue;
    }

    @Override
    public ColumnConfig defaultVal() {
        return new ColumnConfig("demo", ColumnConfigType.TEXT);
    }
}
