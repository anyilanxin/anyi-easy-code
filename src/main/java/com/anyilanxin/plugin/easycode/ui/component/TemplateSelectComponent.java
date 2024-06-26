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
package com.anyilanxin.plugin.easycode.ui.component;

import com.anyilanxin.plugin.easycode.entity.Template;
import com.anyilanxin.plugin.easycode.entity.TemplateGroup;
import com.anyilanxin.plugin.easycode.service.SettingsStorageService;
import com.anyilanxin.plugin.easycode.tool.StringUtils;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBCheckBox;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 模板选择组件
 *
 * @author makejava
 * @version 1.0.0
 * @date 2021/08/16 16:18
 */
public class TemplateSelectComponent {
    @Getter
    private JPanel mainPanel;

    /**
     * 分组
     */
    private ComboBox<String> groupComboBox;

    /**
     * 选中所有复选框
     */
    private JBCheckBox allCheckbox;

    /**
     * 所有复选框
     */
    private List<JBCheckBox> checkBoxList;

    /**
     * 模板面板
     */
    private JPanel templatePanel;

    public TemplateSelectComponent() {
        this.init();
    }

    private void init() {
        this.mainPanel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel(new BorderLayout());
        this.groupComboBox = new ComboBox<>();
        this.groupComboBox.setSwingPopup(false);
        this.groupComboBox.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String groupName = (String) groupComboBox.getSelectedItem();
                if (StringUtils.isEmpty(groupName)) {
                    return;
                }
                refreshTemplatePanel(groupName);
            }
        });

        this.allCheckbox = new JBCheckBox("All");
        this.allCheckbox.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkBoxList == null) {
                    return;
                }
                for (JBCheckBox checkBox : checkBoxList) {
                    checkBox.setSelected(allCheckbox.isSelected());
                }
            }
        });
        topPanel.add(this.groupComboBox, BorderLayout.WEST);
        topPanel.add(this.allCheckbox, BorderLayout.EAST);
        this.mainPanel.add(topPanel, BorderLayout.NORTH);
        this.templatePanel = new JPanel(new GridLayout(-1, 2));
        this.mainPanel.add(templatePanel, BorderLayout.CENTER);
        this.refreshData();
    }

    private void refreshData() {
        this.groupComboBox.removeAllItems();
        for (String groupName : SettingsStorageService.getSettingsStorage().getTemplateGroupMap().keySet()) {
            this.groupComboBox.addItem(groupName);
        }
    }

    private void refreshTemplatePanel(String groupName) {
        this.allCheckbox.setSelected(false);
        this.templatePanel.removeAll();
        this.checkBoxList = new ArrayList<>();
        TemplateGroup templateGroup = SettingsStorageService.getSettingsStorage().getTemplateGroupMap().get(groupName);
        // 按名称排序
        templateGroup.getElementList().sort(Comparator.comparing(Template::getName));
        for (Template template : templateGroup.getElementList()) {
            JBCheckBox checkBox = new JBCheckBox(template.getName());
            this.checkBoxList.add(checkBox);
            this.templatePanel.add(checkBox);
        }
        this.mainPanel.updateUI();
    }

    public String getSelectedGroupName() {
        return (String) this.groupComboBox.getSelectedItem();
    }

    public void setSelectedGroupName(String groupName) {
        this.groupComboBox.setSelectedItem(groupName);
    }

    public List<Template> getAllSelectedTemplate() {
        String groupName = (String) this.groupComboBox.getSelectedItem();
        if (StringUtils.isEmpty(groupName)) {
            return Collections.emptyList();
        }
        TemplateGroup templateGroup = SettingsStorageService.getSettingsStorage().getTemplateGroupMap().get(groupName);
        Map<String, Template> map = templateGroup.getElementList().stream().collect(Collectors.toMap(Template::getName, val -> val));
        List<Template> result = new ArrayList<>();
        for (JBCheckBox checkBox : this.checkBoxList) {
            if (checkBox.isSelected()) {
                Template template = map.get(checkBox.getText());
                if (template != null) {
                    result.add(template);
                }
            }
        }
        return result;
    }
}
