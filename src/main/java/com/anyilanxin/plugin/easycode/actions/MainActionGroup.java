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
package com.anyilanxin.plugin.easycode.actions;

import com.anyilanxin.plugin.easycode.dict.GlobalDict;
import com.anyilanxin.plugin.easycode.service.TableInfoSettingsService;
import com.anyilanxin.plugin.easycode.tool.CacheDataUtils;
import com.intellij.database.psi.DbTable;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作按钮分组
 *
 * @author makejava
 * @version 1.0.0
 * @since 2018/07/17 13:10
 */
public class MainActionGroup extends ActionGroup {
    /**
     * 缓存数据工具类
     */
    private final CacheDataUtils cacheDataUtils = CacheDataUtils.getInstance();

    /**
     * 是否不存在子菜单
     */
    private boolean notExistsChildren;

    //    /**
    //     * 是否分组按钮
    //     *
    //     * @return 是否隐藏
    //     */
    //    @Override
    //    public boolean hideIfNoVisibleChildren() {
    //        return this.notExistsChildren;
    //    }


    /**
     * 根据右键在不同的选项上展示不同的子菜单
     *
     * @param event 事件对象
     * @return 动作组
     */
    @NotNull
    @Override
    public AnAction[] getChildren(@Nullable AnActionEvent event) {
        // 获取当前项目
        Project project = getEventProject(event);
        if (project == null) {
            return getEmptyAnAction();
        }
        //获取选中的所有表
        PsiElement[] psiElements = event.getData(LangDataKeys.PSI_ELEMENT_ARRAY);
        if (psiElements == null || psiElements.length == 0) {
            return getEmptyAnAction();
        }
        List<DbTable> dbTableList = new ArrayList<>(psiElements.length);
        for (PsiElement element : psiElements) {
            if (element instanceof DbTable dbTable) {
                dbTableList.add(dbTable);
            }
        }
        if (dbTableList.isEmpty()) {
            return getEmptyAnAction();
        }

        //保存数据到缓存
        cacheDataUtils.setDbTableList(dbTableList);
        cacheDataUtils.setSelectDbTable(dbTableList.getFirst());
        this.notExistsChildren = false;
        return getMenuList();
    }

    /**
     * 初始化注册子菜单项目
     *
     * @return 子菜单数组
     */
    private AnAction[] getMenuList() {
        String mainActionId = "com.anyilanxin.plugin.easycode.action.generate";
        String configActionId = "com.anyilanxin.plugin.easycode.action.config";
        ActionManager actionManager = ActionManager.getInstance();
        // 代码生成菜单
        AnAction mainAction = actionManager.getAction(mainActionId);
        if (mainAction == null) {
            mainAction = new MainAction("Generate Code");
            actionManager.registerAction(mainActionId, mainAction);
        }
        // 表配置菜单
        AnAction configAction = actionManager.getAction(configActionId);
        if (configAction == null) {
            configAction = new ConfigAction("Config Table");
            actionManager.registerAction(configActionId, configAction);
        }
        AnAction clearConfigAction = new AnAction("Clear Config") {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
                DbTable dbTable = CacheDataUtils.getInstance().getSelectDbTable();
                if (dbTable == null) {
                    return;
                }
                TableInfoSettingsService.getInstance().removeTableInfo(dbTable);
                Messages.showInfoMessage(dbTable.getName() + "表配置信息已重置成功", GlobalDict.TITLE_INFO);
            }
        };
        // 返回所有菜单
        return new AnAction[]{mainAction, configAction, clearConfigAction};
    }


    /**
     * 获取空菜单组
     *
     * @return 空菜单组
     */
    private AnAction[] getEmptyAnAction() {
        this.notExistsChildren = true;
        return AnAction.EMPTY_ARRAY;
    }
}
