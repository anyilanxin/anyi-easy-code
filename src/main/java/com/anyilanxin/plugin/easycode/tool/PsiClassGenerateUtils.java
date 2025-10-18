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

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiModifierList;

/**
 * 从实体类生成代码业务工具类
 *
 * @author Mario Luo
 */
public final class PsiClassGenerateUtils {

    private PsiClassGenerateUtils() {
    }

    /**
     * 是否是主键字段
     */
    public static boolean isPkField(PsiField field) {
        if ("id".equals(field.getName())) {
            return true;
        }
        if (existsAnnotation(field, "org.springframework.data.annotation.Id")) {
            return true;
        }
        return existsAnnotation(field, "javax.persistence.Id");
    }

    /**
     * 是否需要跳过该字段
     */
    public static boolean isSkipField(PsiField field) {
        PsiModifierList modifierList = field.getModifierList();
        if (modifierList != null && modifierList.hasExplicitModifier(PsiModifier.STATIC)) {
            return true;
        }
        if (existsAnnotation(field, "org.springframework.data.annotation.Transient")) {
            return true;
        }
        return existsAnnotation(field, "javax.persistence.Transient");
    }

    private static boolean existsAnnotation(PsiField field, String clsName) {
        return getAnnotation(field, clsName) != null;
    }

    private static PsiAnnotation getAnnotation(PsiField field, String clsName) {
        PsiModifierList list = field.getModifierList();
        return list == null ? null : list.findAnnotation(clsName);
    }
}
