package com.mcoding.base.common.util.excel;

public interface ObjToStrConverter<T> {

    public String convert(T t, Object item, int index);
}
