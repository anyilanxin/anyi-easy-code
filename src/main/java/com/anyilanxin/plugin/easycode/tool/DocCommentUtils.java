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

import com.intellij.psi.PsiElement;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.javadoc.PsiDocToken;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

/**
 * 文档注释工具类
 *
 * @author makejava
 * @version 1.0.0
 * @date 2021/08/16 17:37
 */
public class DocCommentUtils {

    /**
     * 获取注释信息，获取第一条文本类型注释内容，不存在则返回null
     *
     * @param docComment 文档注释
     * @return 注释内容
     */
    public static String getComment(@Nullable PsiDocComment docComment) {
        if (docComment == null) {
            return null;
        }
        return Arrays.stream(docComment.getDescriptionElements())
                .filter(o -> o instanceof PsiDocToken)
                .map(PsiElement::getText)
                .findFirst()
                .map(String::trim)
                .orElse(null);
    }

}
