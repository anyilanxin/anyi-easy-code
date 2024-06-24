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
package com.anyilanxin.plugin.easycode.tool;

import com.intellij.database.psi.DbTable;
import com.intellij.psi.PsiClass;
import lombok.Data;

import java.util.List;

/**
 * 缓存数据工具类
 *
 * @author makejava
 * @version 1.0.0
 * @since 2018/07/17 13:10
 */
@Data
public class CacheDataUtils {
    private volatile static CacheDataUtils cacheDataUtils;

    /**
     * 单例模式
     */
    public static CacheDataUtils getInstance() {
        if (cacheDataUtils == null) {
            synchronized (CacheDataUtils.class) {
                if (cacheDataUtils == null) {
                    cacheDataUtils = new CacheDataUtils();
                }
            }
        }
        return cacheDataUtils;
    }

    private CacheDataUtils() {
    }

    /**
     * 当前选中的表
     */
    private DbTable selectDbTable;
    /**
     * 所有选中的表
     */
    private List<DbTable> dbTableList;

    /**
     * 选中的类
     */
    private PsiClass selectPsiClass;

    /**
     * 所有选中的表
     */
    private List<PsiClass> psiClassList;
}
