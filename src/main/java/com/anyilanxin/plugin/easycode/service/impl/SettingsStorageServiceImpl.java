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

import com.anyilanxin.plugin.easycode.dto.SettingsStorageDTO;
import com.anyilanxin.plugin.easycode.service.SettingsStorageService;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 设置储存服务实现
 *
 * @author makejava
 * @version 1.0.0
 * @date 2021/08/07 11:32
 */
@State(name = "AyEasyCodeSetting", storages = @Storage("easy-code-setting.xml"))
public class SettingsStorageServiceImpl implements SettingsStorageService {

    private SettingsStorageDTO settingsStorage = SettingsStorageDTO.defaultVal();

    /**
     * 获取配置
     *
     * @return 配置对象
     */
    @Nullable
    @Override
    public SettingsStorageDTO getState() {
        return settingsStorage;
    }

    /**
     * 加载配置
     *
     * @param state 配置对象
     */
    @Override
    public void loadState(@NotNull SettingsStorageDTO state) {
        // 加载配置后填充默认值，避免版本升级导致的配置信息不完善问题
        state.fillDefaultVal();
        this.settingsStorage = state;
    }
}
