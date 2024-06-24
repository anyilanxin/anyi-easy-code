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

import com.anyilanxin.plugin.easycode.entity.TypeMapper;
import com.anyilanxin.plugin.easycode.enums.MatchType;
import com.anyilanxin.plugin.easycode.tool.CurrGroupUtils;
import com.anyilanxin.plugin.easycode.tool.DocCommentUtils;
import com.anyilanxin.plugin.easycode.tool.NameUtils;
import com.intellij.database.model.DasColumn;
import com.intellij.psi.PsiField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

/**
 * 列信息传输对象
 *
 * @author makejava
 * @version 1.0.0
 * @date 2021/08/14 17:29
 */
@Data
@NoArgsConstructor
public class ColumnInfoDTO {

    public ColumnInfoDTO(PsiField field) {
        this.name = field.getName();
        this.comment = DocCommentUtils.getComment(field.getDocComment());
        this.type = field.getType().getCanonicalText();
        this.custom = false;
        this.ext = "{}";
    }

    public ColumnInfoDTO(DasColumn column) {
        this.name = NameUtils.getInstance().getJavaName(column.getName());
        this.comment = column.getComment();
        this.type = getJavaType(column.getDasType().toDataType().toString());
        this.custom = false;
        this.ext = "{}";
    }

    private String getJavaType(String dbType) {
        for (TypeMapper typeMapper : CurrGroupUtils.getCurrTypeMapperGroup().getElementList()) {
            if (typeMapper.getMatchType() == MatchType.ORDINARY) {
                if (dbType.equalsIgnoreCase(typeMapper.getColumnType())) {
                    return typeMapper.getJavaType();
                }
            } else {
                // 不区分大小写的正则匹配模式
                if (Pattern.compile(typeMapper.getColumnType(), Pattern.CASE_INSENSITIVE).matcher(dbType).matches()) {
                    return typeMapper.getJavaType();
                }
            }
        }
        return "java.lang.Object";
    }

    /**
     * 名称
     */
    private String name;
    /**
     * 注释
     */
    private String comment;
    /**
     * 全类型
     */
    private String type;
    /**
     * 标记是否为自定义附加列
     */
    private Boolean custom;
    /**
     * 扩展数据(JSON字符串)
     */
    private String ext;
}
