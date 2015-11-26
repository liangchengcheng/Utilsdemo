package mddemo.library.com.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年11月14日23:46:29
 * Description:map的工具类
 */
public class MapUtils {


    public static final String DEFAULT_KEY_AND_VALUE_SEPARATOR      = ":";

    public static final String DEFAULT_KEY_AND_VALUE_PAIR_SEPARATOR = ",";
    
    private MapUtils() {
        throw new AssertionError();
    }

    //判断是否为空或者长度是0
    public static <K, V> boolean isEmpty(Map<K, V> sourceMap) {
        return (sourceMap == null || sourceMap.size() == 0);
    }

    //增加键值对需要非空
    public static boolean putMapNotEmptyKey(Map<String, String> map, String key, String value) {
        if (map == null || StringUtils.isEmpty(key)) {
            return false;
        }

        map.put(key, value);
        return true;
    }

    public static boolean putMapNotEmptyKeyAndValue(Map<String, String> map, String key, String value) {
        if (map == null || StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return false;
        }

        map.put(key, value);
        return true;
    }


    public static boolean putMapNotEmptyKeyAndValue(Map<String, String> map, String key, String value,
            String defaultValue) {
        if (map == null || StringUtils.isEmpty(key)) {
            return false;
        }

        map.put(key, StringUtils.isEmpty(value) ? defaultValue : value);
        return true;
    }

    public static <K, V> boolean putMapNotNullKey(Map<K, V> map, K key, V value) {
        if (map == null || key == null) {
            return false;
        }

        map.put(key, value);
        return true;
    }

    public static <K, V> boolean putMapNotNullKeyAndValue(Map<K, V> map, K key, V value) {
        if (map == null || key == null || value == null) {
            return false;
        }

        map.put(key, value);
        return true;
    }

  //获取键值对
    public static <K, V> K getKeyByValue(Map<K, V> map, V value) {
        if (isEmpty(map)) {
            return null;
        }

        for (Entry<K, V> entry : map.entrySet()) {
            if (ObjectUtils.isEquals(entry.getValue(), value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static Map<String, String> parseKeyAndValueToMap(String source, String keyAndValueSeparator,
            String keyAndValuePairSeparator, boolean ignoreSpace) {
        if (StringUtils.isEmpty(source)) {
            return null;
        }

        if (StringUtils.isEmpty(keyAndValueSeparator)) {
            keyAndValueSeparator = DEFAULT_KEY_AND_VALUE_SEPARATOR;
        }
        if (StringUtils.isEmpty(keyAndValuePairSeparator)) {
            keyAndValuePairSeparator = DEFAULT_KEY_AND_VALUE_PAIR_SEPARATOR;
        }
        Map<String, String> keyAndValueMap = new HashMap<String, String>();
        String[] keyAndValueArray = source.split(keyAndValuePairSeparator);
        if (keyAndValueArray == null) {
            return null;
        }

        int seperator;
        for (String valueEntity : keyAndValueArray) {
            if (!StringUtils.isEmpty(valueEntity)) {
                seperator = valueEntity.indexOf(keyAndValueSeparator);
                if (seperator != -1) {
                    if (ignoreSpace) {
                        MapUtils.putMapNotEmptyKey(keyAndValueMap, valueEntity.substring(0, seperator).trim(),
                                valueEntity.substring(seperator + 1).trim());
                    } else {
                        MapUtils.putMapNotEmptyKey(keyAndValueMap, valueEntity.substring(0, seperator),
                                valueEntity.substring(seperator + 1));
                    }
                }
            }
        }
        return keyAndValueMap;
    }

    public static Map<String, String> parseKeyAndValueToMap(String source, boolean ignoreSpace) {
        return parseKeyAndValueToMap(source, DEFAULT_KEY_AND_VALUE_SEPARATOR, DEFAULT_KEY_AND_VALUE_PAIR_SEPARATOR,
                ignoreSpace);
    }

    public static Map<String, String> parseKeyAndValueToMap(String source) {
        return parseKeyAndValueToMap(source, DEFAULT_KEY_AND_VALUE_SEPARATOR, DEFAULT_KEY_AND_VALUE_PAIR_SEPARATOR,
                true);
    }

    public static String toJson(Map<String, String> map) {
        if (map == null || map.size() == 0) {
            return null;
        }

        StringBuilder paras = new StringBuilder();
        paras.append("{");
        Iterator<Entry<String, String>> ite = map.entrySet().iterator();
        while (ite.hasNext()) {
            Entry<String, String> entry = (Entry<String, String>)ite.next();
            paras.append("\"").append(entry.getKey()).append("\":\"").append(entry.getValue()).append("\"");
            if (ite.hasNext()) {
                paras.append(",");
            }
        }
        paras.append("}");
        return paras.toString();
    }
}
