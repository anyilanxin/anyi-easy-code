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
package com.anyilanxin.plugin.easycode.factory;

import com.anyilanxin.plugin.easycode.entity.AbstractItem;

import java.lang.reflect.InvocationTargetException;

/**
 * 抽象的项目工厂
 *
 * @author makejava
 * @version 1.0.0
 * @date 2021/08/11 10:44
 */
public class AbstractItemFactory {

    public static <T extends AbstractItem<T>> T createDefaultVal(Class<T> cls) {
        try {
            T instance = cls.getDeclaredConstructor().newInstance();
            return instance.defaultVal();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            throw new IllegalArgumentException("构建示例失败", e);
        }
    }

}
