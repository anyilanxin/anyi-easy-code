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
package com.anyilanxin.plugin.easycode.tool;

import com.anyilanxin.plugin.easycode.dict.GlobalDict;
import com.intellij.openapi.project.Project;
import com.intellij.util.ui.UIUtil;

import javax.swing.*;

/**
 * 消息弹框工具类
 * 对message dialog弹框进行兼容处理
 *
 * @author makejava
 * @version 1.0.0
 * @since 2021/02/01 15:36
 */
public class MessageDialogUtils {

    /**
     * yes no确认框
     *
     * @param msg 消息
     * @return 是否确认
     */
    public static boolean yesNo(String msg) {
        return yesNo(null, msg);
    }

    /**
     * yes no确认框
     *
     * @param project 项目对象
     * @param msg     消息
     * @return 是否确认
     */
    public static boolean yesNo(Project project, String msg) {
        Object[] options = new Object[]{"Yes", "No"};
        return JOptionPane.showOptionDialog(null,
                msg, GlobalDict.TITLE_INFO,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                UIUtil.getQuestionIcon(),
                options, options[0]) == 0;
    }

    /**
     * 显示确认框
     *
     * @param msg     确认框消息
     * @param project 项目
     * @return 点击按钮
     */
    public static int yesNoCancel(Project project, String msg, String yesText, String noText, String cancelText) {
        Object[] options = new Object[]{yesText, noText, cancelText};
        return JOptionPane.showOptionDialog(null,
                msg, GlobalDict.TITLE_INFO,
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                UIUtil.getQuestionIcon(),
                options, options[0]);
    }

}
