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

import com.intellij.openapi.ui.Splitter;
import com.intellij.util.ui.JBUI;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

/**
 * 左右组件
 *
 * @author makejava
 * @version 1.0.0
 * @date 2021/08/10 16:49
 */
public class LeftRightComponent {
    /**
     * 主面板
     */
    @Getter
    private JPanel mainPanel;
    /**
     * 左边面板
     */
    private JPanel leftPanel;
    /**
     * 右边面板
     */
    private JPanel rightPanel;
    /**
     * 分割比例
     */
    private float proportion;
    /**
     * 预设值窗口大小
     */
    private Dimension preferredSize;

    public LeftRightComponent(JPanel leftPanel, JPanel rightPanel) {
        this(leftPanel, rightPanel, 0.2F, JBUI.size(400, 300));
    }

    public LeftRightComponent(JPanel leftPanel, JPanel rightPanel, float proportion, Dimension preferredSize) {
        this.leftPanel = leftPanel;
        this.rightPanel = rightPanel;
        this.proportion = proportion;
        this.preferredSize = preferredSize;
        this.init();
    }

    private void init() {
        this.mainPanel = new JPanel(new BorderLayout());
        Splitter splitter = new Splitter(false, proportion);
        splitter.setFirstComponent(this.leftPanel);
        splitter.setSecondComponent(this.rightPanel);
        this.mainPanel.add(splitter, BorderLayout.CENTER);
        mainPanel.setPreferredSize(this.preferredSize);
    }
}
