package com.middleclass.middleclass.stock.controller.common;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

// 공통 기능을 담은 class
// TODO 이거 설정을 통해 모든 클래스에서 별도의 import 없이 사용할 수 있게 하기
public class UtilService {
    // GET 요청시 Query String 을 만들어주는 method
    public String makeQueryString(Map<String, String> param) {
        String result = "";
        if(param.size() > 0) {
            Set<String> keySet = param.keySet();
            for (String key : keySet) {
                result += key + "=" + param.get(key) + "&";
            }
            // 앞에 "?" 추가
            result = "?" + result;
            // 마지막에 붙은 "&" 제거
            result = result.substring(0, result.length() - 1);
        }
        System.out.println("result : " + result);
        return result;
    };

    // convert from Json to Map
    public Map<String, Object> jsonToMap() {
        return null;
    }
}
