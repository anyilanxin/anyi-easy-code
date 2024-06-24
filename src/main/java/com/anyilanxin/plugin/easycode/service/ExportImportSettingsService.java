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
package com.anyilanxin.plugin.easycode.service;

import com.anyilanxin.plugin.easycode.dto.SettingsStorageDTO;

/**
 * 导出导入设置服务
 *
 * @author makejava
 * @version 1.0.0
 * @date 2021/08/11 17:24
 */
public interface ExportImportSettingsService {

    /**
     * 导出设置
     *
     * @param settingsStorage 要导出的设置
     */
    void exportConfig(SettingsStorageDTO settingsStorage);

    /**
     * 导入设置
     *
     * @return 设置信息
     */
    SettingsStorageDTO importConfig();

}
