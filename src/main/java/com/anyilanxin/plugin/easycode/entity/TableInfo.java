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
import com.intellij.database.psi.DbTable;
import lombok.Data;

import java.util.List;

/**
 * 表信息
 *
 * @author makejava
 * @version 1.0.0
 * @since 2018/07/17 13:10
 */
@Data
public class TableInfo {
    /**
     * 原始对象
     */
    @JsonIgnore
    private DbTable obj;

    /**
     * 原始对象（从实体生成）
     * <p>
     * Note: 实际类型是com.intellij.psi.PsiClass，为了避免velocity反射出现ClassNotFound，写为Object类型
     */
    @JsonIgnore
    private Object psiClassObj;

    /**
     * 表名（首字母大写）
     */
    private String name;
    /**
     * 表名前缀
     */
    private String preName;

    /**
     * 版本号
     */
    private String version;

    /**
     * 作者
     */
    private String author;

    /**
     * 基础包路径
     */
    private String basePackage;

    /**
     * 注释
     */
    private String comment;
    /**
     * 模板组名称
     */
    private String templateGroupName;
    /**
     * 模板组名称
     */
    private String globalConfigGroupName;
    /**
     * 所有列
     */
    private List<ColumnInfo> fullColumn;

    /**
     * 主键列
     */
    private List<ColumnInfo> pkColumn;
    /**
     * 其他列
     */
    private List<ColumnInfo> otherColumn;
    /**
     * 保存的包名称
     */
    private String savePackageName;

    /**
     * 保存的包相对路径，非斜杠开头
     */
    private String savePackageNamePath;
    /**
     * 保存路径
     */
    private String savePath;
    /**
     * 保存的model名称
     */
    private String saveModelName;
}
