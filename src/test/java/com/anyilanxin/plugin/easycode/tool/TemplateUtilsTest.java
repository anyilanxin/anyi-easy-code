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
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

/**
 * 模板工具测试用例
 *
 * @author makejava
 * @version 1.0.0
 * @since 2018/09/01 15:28
 */
public class TemplateUtilsTest {

    /**
     * 测试向模板中添加全局配置
     */
    @Test
    public void addGlobalConfig() {
        GlobalConfig globalConfig = new GlobalConfig("init", "Hello World!");
        String template = "$init,${init}";
        // 添加全局变量
        template = TemplateUtils.addGlobalConfig(template, Collections.singleton(globalConfig));
        Assert.assertEquals(template, "Hello World!,Hello World!");
    }
}