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

import com.anyilanxin.plugin.easycode.tool.CacheDataUtils;
import com.anyilanxin.plugin.easycode.ui.SelectSavePath;
import com.google.common.collect.Lists;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiModifier;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 从Java类生成代码菜单
 *
 * @author Mario Luo
 */
public class AyEasyCodeEntityAction extends AnAction {

    private final CacheDataUtils cacheDataUtils = CacheDataUtils.getInstance();

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getProject();
        if (project == null) {
            return;
        }
        // 过滤选择Java文件
        VirtualFile[] psiFiles = event.getData(CommonDataKeys.VIRTUAL_FILE_ARRAY);
        if (psiFiles == null) {
            return;
        }
        PsiManager psiManager = PsiManager.getInstance(project);
        List<PsiJavaFile> psiJavaFiles = Arrays.stream(psiFiles)
                .map(psiManager::findFile)
                .filter(f -> f instanceof PsiJavaFile)
                .map(f -> (PsiJavaFile) f)
                .collect(Collectors.toList());
        if (psiJavaFiles.isEmpty()) {
            return;
        }
        // 获取选中的类
        List<PsiClass> psiClassList = resolvePsiClassByFile(psiJavaFiles);
        if (psiClassList.isEmpty()) {
            return;
        }
        // 缓存选中值
        cacheDataUtils.setSelectPsiClass(psiClassList.get(0));
        cacheDataUtils.setPsiClassList(psiClassList);
        new SelectSavePath(project, true).show();
    }

    /**
     * 解析类
     */
    private List<PsiClass> resolvePsiClassByFile(List<PsiJavaFile> psiJavaFiles) {
        List<PsiClass> psiClassList = Lists.newArrayListWithCapacity(psiJavaFiles.size());
        for (PsiJavaFile psiJavaFile : psiJavaFiles) {
            Arrays.stream(psiJavaFile.getClasses())
                    .filter(o -> o.getModifierList() != null && o.getModifierList().hasModifierProperty(PsiModifier.PUBLIC))
                    .findFirst().ifPresent(psiClassList::add);
        }
        return psiClassList;
    }

    @Override
    public void update(@NotNull AnActionEvent event) {
        // 不存在模块不展示：选择多个模块
        Project project = event.getData(CommonDataKeys.PROJECT);
        Module module = event.getData(LangDataKeys.MODULE);
        if (project == null || module == null) {
            event.getPresentation().setVisible(false);
            return;
        }

        // 非java的文件不显示
        VirtualFile file = event.getDataContext().getData(CommonDataKeys.VIRTUAL_FILE);
        if (file != null && !file.isDirectory() && !"java".equals(file.getExtension())) {
            event.getPresentation().setVisible(false);
        }
    }


}
