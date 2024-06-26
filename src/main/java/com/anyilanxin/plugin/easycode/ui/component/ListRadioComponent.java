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

import com.anyilanxin.plugin.easycode.tool.CollectionUtil;
import com.intellij.openapi.ui.VerticalFlowLayout;
import com.intellij.ui.components.JBRadioButton;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 列表单选组件
 *
 * @author makejava
 * @version 1.0.0
 * @since 2021/08/12 11:12
 */
public class ListRadioComponent extends JPanel {
    /**
     * 标题
     */
    private String title;
    /**
     * 原属列表
     */
    private Collection<String> items;
    /**
     * 复选框列表
     */
    private List<JBRadioButton> radioList;

    /**
     * 默认构造方法
     */
    public ListRadioComponent(String title, Collection<String> items) {
        // 使用垂直流式布局
        super(new VerticalFlowLayout());
        this.title = title;
        this.items = items;
        this.init();
    }

    /**
     * 初始化操作
     */
    private void init() {
        JTextPane textPane = new JTextPane();
        textPane.setText(title);
        textPane.setEditable(false);
        add(textPane);
        JPanel radioPanel = new JPanel(new GridLayout(1, 4));
        if (CollectionUtil.isEmpty(items)) {
            return;
        }
        this.radioList = new ArrayList<>(items.size());
        ButtonGroup buttonGroup = new ButtonGroup();
        for (String item : items) {
            JBRadioButton radioButton = new JBRadioButton(item);
            this.radioList.add(radioButton);
            radioPanel.add(radioButton);
            buttonGroup.add(radioButton);
        }
        add(radioPanel);
        // 默认选中第一个
        this.radioList.get(0).setSelected(true);
    }

    /**
     * 获取已选中的元素
     *
     * @return 已选中的元素
     */
    public String getSelectedItem() {
        if (CollectionUtil.isEmpty(this.radioList)) {
            return null;
        }
        for (JBRadioButton radioButton : this.radioList) {
            if (radioButton.isSelected()) {
                return radioButton.getText();
            }
        }
        return null;
    }
}
