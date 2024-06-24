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
package com.anyilanxin.plugin.easycode.ui.base;

import com.anyilanxin.plugin.easycode.tool.StringUtils;
import com.intellij.openapi.ui.InputValidator;

import java.util.Collection;

/**
 * 输入存在验证器
 *
 * @author makejava
 * @version 1.0.0
 * @date 2021/08/10 17:16
 */
public class InputExistsValidator implements InputValidator {

    private Collection<String> itemList;

    public InputExistsValidator(Collection<String> itemList) {
        this.itemList = itemList;
    }

    @Override
    public boolean checkInput(String inputString) {
        return !StringUtils.isEmpty(inputString) && !itemList.contains(inputString);
    }

    @Override
    public boolean canClose(String inputString) {
        return this.checkInput(inputString);
    }
}
