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
import com.anyilanxin.plugin.easycode.entity.ColumnConfig;
import com.anyilanxin.plugin.easycode.entity.ColumnConfigGroup;
import com.anyilanxin.plugin.easycode.enums.ColumnConfigType;
import com.anyilanxin.plugin.easycode.factory.CellEditorFactory;
import com.anyilanxin.plugin.easycode.tool.CloneUtils;
import com.anyilanxin.plugin.easycode.ui.component.GroupNameComponent;
import com.anyilanxin.plugin.easycode.ui.component.TableComponent;
import com.fasterxml.jackson.core.type.TypeReference;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.ui.ComboBoxTableRenderer;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author makejava
 * @version 1.0.0
 * @date 2021/08/10 13:27
 */
public class ColumnConfigSettingForm implements Configurable, BaseSettings {
    private final JPanel mainPanel;
    /**
     * 列配置
     */
    private Map<String, ColumnConfigGroup> columnConfigGroupMap;
    /**
     * 当前分组名
     */
    private ColumnConfigGroup currColumnConfigGroup;
    /**
     * 表格组件
     */
    private TableComponent<ColumnConfig> tableComponent;
    /**
     * 分组操作组件
     */
    private GroupNameComponent<ColumnConfig, ColumnConfigGroup> groupNameComponent;

    public ColumnConfigSettingForm() {
        this.mainPanel = new JPanel(new BorderLayout());
    }

    private void initTable() {
        // 第一列，类型
        String[] columnConfigTypeNames = Stream.of(ColumnConfigType.values()).map(ColumnConfigType::name).toArray(String[]::new);
        TableCellEditor typeEditor = CellEditorFactory.createComboBoxEditor(false, columnConfigTypeNames);
        TableCellRenderer typeRenderer = new ComboBoxTableRenderer<>(columnConfigTypeNames);
        TableComponent.Column<ColumnConfig> typeColumn = new TableComponent.Column<>("type", item -> item.getType().name(), (entity, val) -> entity.setType(ColumnConfigType.valueOf(val)), typeEditor, typeRenderer);
        // 第二列标题
        TableCellEditor titleEditor = CellEditorFactory.createTextFieldEditor();
        TableComponent.Column<ColumnConfig> titleColumn = new TableComponent.Column<>("title", ColumnConfig::getTitle, ColumnConfig::setTitle, titleEditor, null);
        // 第三列选项
        TableCellEditor selectValueEditor = CellEditorFactory.createTextFieldEditor();
        TableComponent.Column<ColumnConfig> selectValueColumn = new TableComponent.Column<>("selectValue", ColumnConfig::getSelectValue, ColumnConfig::setSelectValue, selectValueEditor, null);
        List<TableComponent.Column<ColumnConfig>> columns = Arrays.asList(typeColumn, titleColumn, selectValueColumn);

        // 表格初始化
        this.tableComponent = new TableComponent<>(columns, this.currColumnConfigGroup.getElementList(), ColumnConfig.class);
        this.mainPanel.add(this.tableComponent.createPanel(), BorderLayout.CENTER);
    }

    private void initGroupName() {

        // 切换分组操作
        Consumer<ColumnConfigGroup> switchGroupOperator = typeColumnConfigGroupMap -> {
            this.currColumnConfigGroup = typeColumnConfigGroupMap;
            refreshUiVal();
        };

        this.groupNameComponent = new GroupNameComponent<>(switchGroupOperator, this.columnConfigGroupMap);
        this.mainPanel.add(groupNameComponent.getPanel(), BorderLayout.NORTH);
    }

    private void initPanel() {
        this.loadSettingsStore(getSettingsStorage());
        // 初始化表格
        this.initTable();
        this.initGroupName();
    }

    @Override
    public String getDisplayName() {
        return "Column Config";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return getDisplayName();
    }

    @Override
    public @Nullable JComponent createComponent() {
        this.initPanel();
        return mainPanel;
    }

    @Override
    public boolean isModified() {
        return !this.columnConfigGroupMap.equals(getSettingsStorage().getColumnConfigGroupMap())
                || !getSettingsStorage().getCurrColumnConfigGroupName().equals(this.currColumnConfigGroup.getName());
    }

    @Override
    public void apply() {
        getSettingsStorage().setColumnConfigGroupMap(this.columnConfigGroupMap);
        getSettingsStorage().setCurrColumnConfigGroupName(this.currColumnConfigGroup.getName());
        // 保存包后重新加载配置
        this.loadSettingsStore(getSettingsStorage());
    }

    /**
     * 加载配置信息
     *
     * @param settingsStorage 配置信息
     */
    @Override
    public void loadSettingsStore(SettingsStorageDTO settingsStorage) {
        // 复制配置，防止篡改
        this.columnConfigGroupMap = CloneUtils.cloneByJson(settingsStorage.getColumnConfigGroupMap(), new TypeReference<Map<String, ColumnConfigGroup>>() {
        });
        this.currColumnConfigGroup = this.columnConfigGroupMap.get(settingsStorage.getCurrColumnConfigGroupName());
        if (this.currColumnConfigGroup == null) {
            this.currColumnConfigGroup = this.columnConfigGroupMap.get(GlobalDict.DEFAULT_GROUP_NAME);
        }
        this.refreshUiVal();
    }

    private void refreshUiVal() {
        if (this.tableComponent != null) {
            this.tableComponent.setDataList(this.currColumnConfigGroup.getElementList());
        }
        if (this.groupNameComponent != null) {
            this.groupNameComponent.setGroupMap(this.columnConfigGroupMap);
            this.groupNameComponent.setCurrGroupName(this.currColumnConfigGroup.getName());
        }
    }
}
