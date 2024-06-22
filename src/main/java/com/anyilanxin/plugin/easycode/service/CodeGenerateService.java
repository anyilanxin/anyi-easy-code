package com.anyilanxin.plugin.easycode.service;

import com.anyilanxin.plugin.easycode.dto.GenerateOptions;
import com.anyilanxin.plugin.easycode.entity.GlobalConfigGroup;
import com.anyilanxin.plugin.easycode.entity.TableInfo;
import com.anyilanxin.plugin.easycode.entity.Template;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * 代码生成服务，Project级别Service
 *
 * @author makejava
 * @version 1.0.0
 * @since 2018/09/02 12:48
 */
public interface CodeGenerateService {
    /**
     * 获取实例对象
     *
     * @param project 项目对象
     * @return 实例对象
     */
    static CodeGenerateService getInstance(@NotNull Project project) {
        return ServiceManager.getService(project, CodeGenerateService.class);
    }

    /**
     * 生成
     *
     * @param templates       模板
     * @param generateOptions 生成选项
     */
    void generate(Collection<Template> templates, GenerateOptions generateOptions, GlobalConfigGroup selectedGroup);


    /**
     * 生成代码
     *
     * @param template  模板
     * @param tableInfo 表信息对象
     * @return 生成好的代码
     */
    String generate(Template template, TableInfo tableInfo);
}
