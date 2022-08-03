package io.swen90007sm2.alpheccaboot.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * utils for handling url string
 *
 * @author xiaotian
 */
public class UrlUtil {
    /**
     * remove url's redundant address, only remains query params
     */
    private static String truncateUrlPage(String url) {
        String strAllParam = null;
        String[] arrSplit = null;
        url = url.trim().toLowerCase();
        arrSplit = url.split("[?]");
        if (url.length() > 1) {
            if (arrSplit.length > 1) {
                for (int i = 1; i < arrSplit.length; i++) {
                    strAllParam = arrSplit[i];
                }
            }
        }
        return strAllParam;
    }

    /**
     * put url query param into a map
     */
    public static Map<String, String> getQueryParamMap(String url) {
        Map<String, String> mapRequest = new HashMap<String, String>();
        String[] arrSplit = null;
        String strUrlParam = truncateUrlPage(url);
        if (strUrlParam == null) {
            return mapRequest;
        }
        arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]");
            // 解析出键值
            if (arrSplitEqual.length > 1) {
                // 正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
            } else {
                if (!Objects.equals(arrSplitEqual[0], "")) {
                    // 只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }
}
