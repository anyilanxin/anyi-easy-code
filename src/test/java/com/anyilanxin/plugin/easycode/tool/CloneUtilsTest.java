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

import com.anyilanxin.plugin.easycode.entity.TableInfo;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

/**
 * 克隆工具类测试用例
 *
 * @author makejava
 * @version 1.0.0
 * @since 2018/09/02 14:11
 */
public class CloneUtilsTest {

    @Test
    public void cloneByJson() {
        TableInfo tableInfo = new TableInfo();
        tableInfo.setComment("Hello World!");
        TableInfo newTableInfo = CloneUtils.cloneByJson(tableInfo, false);
        assertNotSame(tableInfo, newTableInfo);
        assertEquals(tableInfo, newTableInfo);
    }
}