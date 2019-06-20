package com.wookii.gomvp.demo.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lvqi
 * Date: 2018/1/9
 * Time: 21:10
 * To change this template use File | Settings | File and Code Templates | Includes.
 */
class MapUtils {

    public static Map<String, String> getReqParams(Map requestParams) {
        Map<String, String> params = new HashMap<>();
        for (Object o : requestParams.keySet()) {
            String name = (String) o;
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        return params;
    }

    /**
     * 除去map中的签名参数
     *
     * @param sArray  签名数组
     * @param isBlank true时候去除数组中空值参数  false 不去除为空参数
     * @param list    签名前去掉不参与签名的参数
     * @return
     */
    public static Map<String, String> remSign(Map<String, String> sArray, boolean isBlank, List list) {
        Map<String, String> result = new HashMap<>();
        if (sArray == null || sArray.size() <= 0) {
            return result;
        }
        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (isBlank) {
                if (value == null || value.equals("")) {
                    continue;
                }
            }
            if (key.equalsIgnoreCase("sign")) {
                continue;
            }
            if (list != null && list.size() > 0) {
                int size = 0;
                for (int i = 0; i < list.size(); i++) {
                    if (key.equals(list.get(i))) {
                        size++;
                    }
                }
                if (size > 0) {
                    continue;
                }
            }
            result.put(key, value);
        }
        return result;
    }

    /**
     * 把map所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * 排序规则:每一个参数从a到z的顺序排序，若遇到相同首字母，则看第二个字母，以此类推
     *
     * @param map map
     * @return
     */
    public static String mapToLinkString(Map<String, String> map) {
        List<String> keys = new ArrayList<>(map.keySet());
        Collections.sort(keys);

        StringBuilder prestr = new StringBuilder();

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = map.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr.append(key).append("=").append(value);
            } else {
                prestr.append(key).append("=").append(value).append("&");
            }
        }

        return prestr.toString();
    }
}
