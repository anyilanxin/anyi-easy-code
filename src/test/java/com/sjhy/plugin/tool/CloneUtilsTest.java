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