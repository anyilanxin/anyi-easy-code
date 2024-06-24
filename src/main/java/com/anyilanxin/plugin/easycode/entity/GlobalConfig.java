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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 全局配置实体类
 *
 * @author makejava
 * @version 1.0.0
 * @since 2018/07/27 13:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlobalConfig implements AbstractEditorItem<GlobalConfig> {
    /**
     * 名称
     */
    private String name;
    /**
     * 值
     */
    private String value;

    @Override
    public GlobalConfig defaultVal() {
        return new GlobalConfig("demo", "value");
    }

    @Override
    public void changeFileName(String name) {
        this.name = name;
    }

    @Override
    public String fileName() {
        return this.name;
    }

    @Override
    public void changeFileContent(String content) {
        this.value = content;
    }

    @Override
    public String fileContent() {
        return this.value;
    }
}
