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

import org.junit.Assert;
import org.junit.Test;

/**
 * ToUnicodeTest 类
 *
 * @author czb 2019/11/11 9:43
 * @version 1.0
 **/
public class ToUnicodeTest {

    @Test
    public void toUnicode() {
        GlobalTool tool = GlobalTool.getInstance();
        String out;
        System.out.println(out = tool.toUnicode("金融机构，Finance organization{0},！"));
        Assert.assertEquals("\\u91d1\\u878d\\u673a\\u6784\\uff0cFinance organization{0},\\uff01", out);
    }

}
