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
package com.anyilanxin.plugin.easycode.ui;

import com.anyilanxin.plugin.easycode.dto.SettingsStorageDTO;
import com.anyilanxin.plugin.easycode.service.SettingsStorageService;
import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nullable;

/**
 * @author makejava
 * @version 1.0.0
 * @since 2021/08/07 19:42
 */
public interface BaseSettings extends Configurable {
    /**
     * 帮助提示信息
     *
     * @return 提示信息
     */
    @Nullable
    @Override
    default String getHelpTopic() {
        return getDisplayName();
    }

    /**
     * 重置设置
     */
    @Override
    default void reset() {
        loadSettingsStore();
    }

    /**
     * 获取设置信息
     *
     * @return 获取设置信息
     */
    default SettingsStorageDTO getSettingsStorage() {
        return SettingsStorageService.getSettingsStorage();
    }

    /**
     * 加载配置信息
     */
    default void loadSettingsStore() {
        this.loadSettingsStore(getSettingsStorage());
    }

    /**
     * 加载配置信息
     *
     * @param settingsStorage 配置信息
     */
    void loadSettingsStore(SettingsStorageDTO settingsStorage);

}
