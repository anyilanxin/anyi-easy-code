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

import com.anyilanxin.plugin.easycode.tool.CloneUtils;

/**
 * 抽象的项
 *
 * @author makejava
 * @version 1.0.0
 * @date 2021/08/11 09:47
 */
public interface AbstractItem<T extends AbstractItem> {
    /**
     * 默认值
     *
     * @return {@link T}
     */
    T defaultVal();

    /**
     * 克隆对象
     *
     * @return 克隆结果
     */
    @SuppressWarnings("unchecked")
    default T cloneObj() {
        return (T) CloneUtils.cloneByJson(this);
    }
}
