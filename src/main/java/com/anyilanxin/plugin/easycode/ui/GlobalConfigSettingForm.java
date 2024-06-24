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

import com.anyilanxin.plugin.easycode.dict.GlobalDict;
import com.anyilanxin.plugin.easycode.dto.SettingsStorageDTO;
import com.anyilanxin.plugin.easycode.entity.GlobalConfig;
import com.anyilanxin.plugin.easycode.entity.GlobalConfigGroup;
import com.anyilanxin.plugin.easycode.tool.CloneUtils;
import com.anyilanxin.plugin.easycode.ui.component.EditListComponent;
import com.anyilanxin.plugin.easycode.ui.component.EditorComponent;
import com.anyilanxin.plugin.easycode.ui.component.GroupNameComponent;
import com.anyilanxin.plugin.easycode.ui.component.LeftRightComponent;
import com.fasterxml.jackson.core.type.TypeReference;
import com.intellij.ide.fileTemplates.impl.UrlUtil;
import com.intellij.openapi.options.Configurable;
import com.intellij.util.ExceptionUtil;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author makejava
 * @version 1.0.0
 * @date 2021/08/10 16:14
 */
public class GlobalConfigSettingForm implements Configurable, BaseSettings {
    /**
     * 全局变量描述信息，说明文档
     */
    private static final String TEMPLATE_DESCRIPTION_INFO;

    static {
        String descriptionInfo = "";
        try {
            descriptionInfo = UrlUtil.loadText(GlobalConfigSettingForm.class.getResource("/description/globalConfigDescription.html"));
        } catch (IOException e) {
            ExceptionUtil.rethrow(e);
        } finally {
            TEMPLATE_DESCRIPTION_INFO = descriptionInfo;
        }
    }

    private JPanel mainPanel;
    /**
     * 类型映射配置
     */
    private Map<String, GlobalConfigGroup> globalConfigGroupMap;
    /**
     * 当前分组名
     */
    private GlobalConfigGroup currGlobalConfigGroup;
    /**
     * 编辑框组件
     */
    private EditorComponent<GlobalConfig> editorComponent;
    /**
     * 分组操作组件
     */
    private GroupNameComponent<GlobalConfig, GlobalConfigGroup> groupNameComponent;
    /**
     * 编辑列表框
     */
    private EditListComponent<GlobalConfig> editListComponent;


    public GlobalConfigSettingForm() {
        this.mainPanel = new JPanel(new BorderLayout());
    }


    private void initGroupName() {
        Consumer<GlobalConfigGroup> switchGroupOperator = globalConfigGroup -> {
            this.currGlobalConfigGroup = globalConfigGroup;
            refreshUiVal();
            // 切换分组情况编辑框
            this.editorComponent.setFile(null);
        };

        this.groupNameComponent = new GroupNameComponent<>(switchGroupOperator, this.globalConfigGroupMap);
        this.mainPanel.add(groupNameComponent.getPanel(), BorderLayout.NORTH);
    }

    private void initEditList() {
        Consumer<GlobalConfig> switchItemFun = globalConfig -> {
            refreshUiVal();
            if (globalConfig != null) {
                this.editListComponent.setCurrentItem(globalConfig.getName());
            }
            editorComponent.setFile(globalConfig);
        };
        this.editListComponent = new EditListComponent<>(switchItemFun, "GlobalConfig Name:", GlobalConfig.class, this.currGlobalConfigGroup.getElementList());
    }

    private void initEditor() {
        this.editorComponent = new EditorComponent<>(null, TEMPLATE_DESCRIPTION_INFO);
    }

    private void initPanel() {
        this.loadSettingsStore(getSettingsStorage());
        // 初始化表格
        this.initGroupName();
        // 初始化编辑列表组件
        this.initEditList();
        // 初始化编辑框组件
        this.initEditor();
        // 左右组件
        LeftRightComponent leftRightComponent = new LeftRightComponent(editListComponent.getMainPanel(), this.editorComponent.getMainPanel());
        this.mainPanel.add(leftRightComponent.getMainPanel(), BorderLayout.CENTER);
    }

    @Override
    public String getDisplayName() {
        return "Global Config";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return getDisplayName();
    }

    @Override
    public void loadSettingsStore(SettingsStorageDTO settingsStorage) {
        // 复制配置，防止篡改
        this.globalConfigGroupMap = CloneUtils.cloneByJson(settingsStorage.getGlobalConfigGroupMap(), new TypeReference<Map<String, GlobalConfigGroup>>() {
        });
        this.currGlobalConfigGroup = this.globalConfigGroupMap.get(settingsStorage.getCurrGlobalConfigGroupName());
        if (this.currGlobalConfigGroup == null) {
            this.currGlobalConfigGroup = this.globalConfigGroupMap.get(GlobalDict.DEFAULT_GROUP_NAME);
        }
        // 解决reset后编辑框未清空BUG
        if (this.editorComponent != null) {
            this.editorComponent.setFile(null);
        }
        this.refreshUiVal();
    }

    @Override
    public @Nullable JComponent createComponent() {
        this.initPanel();
        return mainPanel;
    }

    @Override
    public boolean isModified() {
        return !this.globalConfigGroupMap.equals(getSettingsStorage().getGlobalConfigGroupMap())
                || !getSettingsStorage().getCurrGlobalConfigGroupName().equals(this.currGlobalConfigGroup.getName());
    }

    @Override
    public void apply() {
        getSettingsStorage().setGlobalConfigGroupMap(this.globalConfigGroupMap);
        getSettingsStorage().setCurrGlobalConfigGroupName(this.currGlobalConfigGroup.getName());
        // 保存包后重新加载配置
        this.loadSettingsStore(getSettingsStorage());
    }

    private void refreshUiVal() {
        if (this.groupNameComponent != null) {
            this.groupNameComponent.setGroupMap(this.globalConfigGroupMap);
            this.groupNameComponent.setCurrGroupName(this.currGlobalConfigGroup.getName());
        }
        if (this.editListComponent != null) {
            this.editListComponent.setElementList(this.currGlobalConfigGroup.getElementList());
        }
    }
}
