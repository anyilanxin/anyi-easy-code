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

import com.anyilanxin.plugin.easycode.dto.GenerateOptions;
import com.anyilanxin.plugin.easycode.entity.TableInfo;
import com.anyilanxin.plugin.easycode.entity.Template;
import com.anyilanxin.plugin.easycode.service.impl.CodeGenerateServiceImpl;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * 额外的代码生成工具
 *
 * @author makejava
 * @version 1.0.0
 * @since 2018/11/01 10:11
 */
public class ExtraCodeGenerateUtils {
    /**
     * 代码生成服务
     */
    private CodeGenerateServiceImpl codeGenerateService;
    /**
     * 表信息对象
     */
    private TableInfo tableInfo;
    /**
     * 生成配置
     */
    private GenerateOptions generateOptions;

    public ExtraCodeGenerateUtils(CodeGenerateServiceImpl codeGenerateService, TableInfo tableInfo, GenerateOptions generateOptions) {
        this.codeGenerateService = codeGenerateService;
        this.tableInfo = tableInfo;
        this.generateOptions = generateOptions;
    }

    /**
     * 生成代码
     *
     * @param templateName 模板名称
     * @param param        附加参数
     */
    public void run(String templateName, Map<String, Object> param) {
        // 获取到模板
        Template currTemplate = null;
        for (Template template : CurrGroupUtils.getCurrTemplateGroup().getElementList()) {
            if (Objects.equals(template.getName(), templateName)) {
                currTemplate = template;
            }
        }
        if (currTemplate == null) {
            return;
        }
        // 生成代码
        codeGenerateService.generate(Collections.singletonList(currTemplate), Collections.singletonList(this.tableInfo), this.generateOptions, param, null);
    }
}
