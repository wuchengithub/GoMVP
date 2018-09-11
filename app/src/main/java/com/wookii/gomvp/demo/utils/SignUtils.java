package com.wookii.gomvp.demo.utils;

import java.security.MessageDigest;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lvqi
 * Date: 2018/1/9
 * Time: 20:43
 * To change this template use File | Settings | File and Code Templates | Includes.
 */
public class SignUtils {

    public static String buildSign(Map<String, String> sParaTemp,
                                   String key, boolean isBlank, List list) {

        // 除去数组中的签名参数
        Map<String, String> sPara = MapUtils.remSign(sParaTemp, isBlank, list);

        // 把map所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
        String parmStr = MapUtils.mapToLinkString(sPara);

        // 把参数串+安全校验码拼接后进行MD5加密

        return md5(key + parmStr + key);
    }

    private static String md5(String string) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = string.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
}
