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

import com.anyilanxin.plugin.easycode.dict.GlobalDict;
import com.anyilanxin.plugin.easycode.dto.SettingsStorageDTO;
import com.anyilanxin.plugin.easycode.service.ExportImportSettingsService;
import com.anyilanxin.plugin.easycode.tool.JSON;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.util.ui.TextTransferable;

import java.awt.datatransfer.DataFlavor;

/**
 * 剪切板导入导出配置服务实现
 *
 * @author makejava
 * @version 1.0.0
 * @date 2021/08/12 14:57
 */
public class ClipboardExportImportSettingsServiceImpl implements ExportImportSettingsService {
    /**
     * 导出设置
     *
     * @param settingsStorage 要导出的设置
     */
    @Override
    public void exportConfig(SettingsStorageDTO settingsStorage) {
        String json = JSON.toJsonByFormat(settingsStorage);
        CopyPasteManager.getInstance().setContents(new TextTransferable(json));
        Messages.showInfoMessage("Config info success write to clipboard！", GlobalDict.TITLE_INFO);
    }

    /**
     * 导入设置
     *
     * @return 设置信息
     */
    @Override
    public SettingsStorageDTO importConfig() {
        String json = CopyPasteManager.getInstance().getContents(DataFlavor.stringFlavor);
        return JSON.parse(json, SettingsStorageDTO.class);
    }
}
