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
package com.anyilanxin.plugin.easycode.service.impl;

import com.anyilanxin.plugin.easycode.dto.TableInfoSettingsDTO;
import com.anyilanxin.plugin.easycode.entity.TableInfo;
import com.anyilanxin.plugin.easycode.service.TableInfoSettingsService;
import com.intellij.database.psi.DbTable;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.psi.PsiClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static com.anyilanxin.plugin.easycode.service.TableInfoSettingsService.SAVE_PATH;

/**
 * @author makejava
 * @version 1.0.0
 * @date 2021/08/14 15:20
 */
@State(name = "AyEasyCodeTableSetting", storages = @Storage(SAVE_PATH))
public class TableInfoSettingsServiceImpl implements TableInfoSettingsService {

    private TableInfoSettingsDTO tableInfoSettings = new TableInfoSettingsDTO();

    @Nullable
    @Override
    public TableInfoSettingsDTO getState() {
        return tableInfoSettings;
    }

    @Override
    public void loadState(@NotNull TableInfoSettingsDTO state) {
        this.tableInfoSettings = state;
    }

    /**
     * 获取表格信息
     *
     * @param dbTable 数据库表
     * @return {@link TableInfo}
     */
    @Override
    public TableInfo getTableInfo(DbTable dbTable) {
        return Objects.requireNonNull(getState()).readTableInfo(dbTable);
    }

    /**
     * 获取表信息
     *
     * @param psiClass psi类
     * @return {@link TableInfo}
     */
    @Override
    public TableInfo getTableInfo(PsiClass psiClass) {
        return Objects.requireNonNull(getState()).readTableInfo(psiClass);
    }

    /**
     * 保存表信息
     *
     * @param tableInfo 表信息
     */
    @Override
    public void saveTableInfo(TableInfo tableInfo) {
        Objects.requireNonNull(getState()).saveTableInfo(tableInfo);
    }

    /**
     * 重置表信息
     *
     * @param dbTable 数据库表
     */
    @Override
    public void resetTableInfo(DbTable dbTable) {
        Objects.requireNonNull(getState()).resetTableInfo(dbTable);
    }

    /**
     * 删除表信息
     *
     * @param dbTable 数据库表
     */
    @Override
    public void removeTableInfo(DbTable dbTable) {
        Objects.requireNonNull(getState()).removeTableInfo(dbTable);
    }
}
