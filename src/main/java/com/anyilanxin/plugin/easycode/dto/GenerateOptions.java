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
package com.anyilanxin.plugin.easycode.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 生成选项
 *
 * @author makejava
 * @version 1.0.0
 * @date 2021/08/17 09:08
 */
@Data
@Builder
public class GenerateOptions {
    /**
     * 实体类模式
     */
    private Boolean entityModel;
    /**
     * 统一配置
     */
    private Boolean unifiedConfig;
    /**
     * 重新格式化代码
     */
    private Boolean reFormat;
    /**
     * 提示选是
     */
    private Boolean titleSure;
    /**
     * 提示选否
     */
    private Boolean titleRefuse;
}
