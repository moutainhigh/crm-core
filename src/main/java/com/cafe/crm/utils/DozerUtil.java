package com.cafe.crm.utils;


import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import java.util.ArrayList;
import java.util.List;

public class DozerUtil {

    public static <T, U> List<U> map(final List<T> source, final Class<U> destType) {
        return map(new DozerBeanMapper(),source,destType);
    }

    public static <T, U> List<U> map(final Mapper mapper, final List<T> source, final Class<U> destType) {

        final List<U> dest = new ArrayList<>();

        for (T element : source) {
            dest.add(mapper.map(element, destType));
        }

        return dest;
    }

    public static <T, U> U map(final Mapper mapper,final T source, final Class<U> destType) {
        return mapper.map(source,destType);
    }

    public static <T, U> U map(final T source, final Class<U> destType) {
        return new DozerBeanMapper().map(source,destType);
    }

}
