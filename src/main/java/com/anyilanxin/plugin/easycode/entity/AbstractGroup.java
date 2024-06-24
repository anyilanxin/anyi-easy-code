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

import com.anyilanxin.plugin.easycode.factory.AbstractItemFactory;
import com.anyilanxin.plugin.easycode.tool.CloneUtils;
import com.anyilanxin.plugin.easycode.tool.ReflectionUtils;

import java.util.List;

/**
 * 抽象分组类
 *
 * @author makejava
 * @version 1.0.0
 * @since 2018/07/17 13:10
 */
public interface AbstractGroup<T, E extends AbstractItem<E>> {
    /**
     * 获取分组名称
     *
     * @return 分组名称
     */
    String getName();

    /**
     * 设置分组名称
     *
     * @param name 分组名称
     */
    void setName(String name);

    /**
     * 获取元素集合
     *
     * @return 元素集合
     */
    List<E> getElementList();

    /**
     * 设置元素集合
     *
     * @param elementList 元素集合
     */
    void setElementList(List<E> elementList);

    /**
     * 默认子元素
     *
     * @return {@link E}
     */
    @SuppressWarnings("unchecked")
    default E defaultChild() {
        Class<E> cls = (Class<E>) ReflectionUtils.getGenericClass(this, 1);
        return AbstractItemFactory.createDefaultVal(cls);
    }

    /**
     * 克隆对象
     *
     * @return {@link T}
     */
    @SuppressWarnings("unchecked")
    default T cloneObj() {
        return (T) CloneUtils.cloneByJson(this);
    }
}
