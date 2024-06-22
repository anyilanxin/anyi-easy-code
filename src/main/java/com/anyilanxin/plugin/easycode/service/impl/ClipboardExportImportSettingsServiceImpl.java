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
