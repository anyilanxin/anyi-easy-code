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

import com.anyilanxin.plugin.easycode.entity.GlobalConfig;
import com.anyilanxin.plugin.easycode.entity.GlobalConfigGroup;
import com.anyilanxin.plugin.easycode.entity.Template;

import java.util.Collection;
import java.util.Collections;

/**
 * 模板工具，主要用于对模板进行预处理
 *
 * @author makejava
 * @version 1.0.0
 * @since 2018/09/01 15:07
 */
public final class TemplateUtils {
    /**
     * 不允许创建实例对象
     */
    private TemplateUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * 向模板中注入全局变量
     *
     * @param template      模板
     * @param globalConfigs 全局变量
     * @return 处理好的模板
     */
    public static String addGlobalConfig(String template, Collection<GlobalConfig> globalConfigs) {
        if (CollectionUtil.isEmpty(globalConfigs)) {
            return template;
        }
        for (GlobalConfig globalConfig : globalConfigs) {
            String name = globalConfig.getName();
            // 正则被替换字符转义处理
            String value = globalConfig.getValue().replace("\\", "\\\\").replace("$", "\\$");

            // 将不带{}的变量加上{}
            template = template.replaceAll("\\$!?" + name + "(\\W)", "\\$!{" + name + "}$1");
            // 统一替换
            template = template.replaceAll("\\$!?\\{" + name + "}", value);
        }
        return template;
    }

    /**
     * 向模板中注入全局变量
     *
     * @param template      模板对象
     * @param globalConfigs 全局变量
     */
    public static void addGlobalConfig(Template template, Collection<GlobalConfig> globalConfigs) {
        if (template == null || StringUtils.isEmpty(template.getCode())) {
            return;
        }
        // 模板后面添加换行符号，防止在模板末尾添加全局变量导致无法匹配问题
        template.setCode(addGlobalConfig(template.getCode() + "\n", globalConfigs));
    }

    /**
     * 向模板中注入全局变量
     *
     * @param templates     多个模板
     * @param globalConfigs 全局变量
     */
    public static void addGlobalConfig(Collection<Template> templates, Collection<GlobalConfig> globalConfigs) {
        if (CollectionUtil.isEmpty(templates)) {
            return;
        }
        templates.forEach(template -> addGlobalConfig(template, globalConfigs));
    }

    /**
     * 向模板中注入全局变量
     *
     * @param templates 多个模板
     */
    public static void addGlobalConfig(Collection<Template> templates, GlobalConfigGroup selectedGroup) {
        addGlobalConfig(templates, selectedGroup.getElementList());
    }

    /**
     * 向模板中注入全局变量
     *
     * @param template 单个模板
     */
    public static void addGlobalConfig(Template template) {
        if (template != null) {
            addGlobalConfig(Collections.singleton(template), CurrGroupUtils.getCurrGlobalConfigGroup().getElementList());
        }
    }
}
