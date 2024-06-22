package com.anyilanxin.plugin.easycode.factory;

import com.anyilanxin.plugin.easycode.entity.AbstractItem;

import java.lang.reflect.InvocationTargetException;

/**
 * 抽象的项目工厂
 *
 * @author makejava
 * @version 1.0.0
 * @date 2021/08/11 10:44
 */
public class AbstractItemFactory {

    public static <T extends AbstractItem<T>> T createDefaultVal(Class<T> cls) {
        try {
            T instance = cls.getDeclaredConstructor().newInstance();
            return instance.defaultVal();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            throw new IllegalArgumentException("构建示例失败", e);
        }
    }

}