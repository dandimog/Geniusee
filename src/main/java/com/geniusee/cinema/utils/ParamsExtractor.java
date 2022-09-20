package com.geniusee.cinema.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ParamsExtractor {
    public static <T> Map<String, String> extractValidParamsForType(Map<String, String> queryMap, Class<T> type) {
        Set<String> fields = Arrays.stream(type.getDeclaredFields()).map(Field::getName).collect(Collectors.toSet());
        Set<String> params = queryMap.keySet();
        params.retainAll(fields);
        queryMap.keySet().forEach(key -> {
            if (!params.contains(key)) queryMap.remove(key);
        });
        return queryMap;
    }
}
