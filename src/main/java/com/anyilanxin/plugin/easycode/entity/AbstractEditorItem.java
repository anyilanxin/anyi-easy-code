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

/**
 * 抽象的可编辑元素
 *
 * @author makejava
 * @version 1.0.0
 * @date 2021/08/11 13:45
 */
public interface AbstractEditorItem<T extends AbstractItem> extends AbstractItem<T> {
    /**
     * 更改文件名称
     *
     * @param name 文件名称
     */
    void changeFileName(String name);

    /**
     * 获取文件名称
     *
     * @return {@link String}
     */
    String fileName();

    /**
     * 改变文件内容
     *
     * @param content 内容
     */
    void changeFileContent(String content);

    /**
     * 获取文件内容
     *
     * @return {@link String}
     */
    String fileContent();
}
