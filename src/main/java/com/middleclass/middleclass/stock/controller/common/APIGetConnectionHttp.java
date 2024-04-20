package com.middleclass.middleclass.stock.controller.common;

import java.io.IOException;
import java.util.Map;

@FunctionalInterface
public interface APIGetConnectionHttp {
    Map<String, Object> getApi(String UrlData, String ParamData, String TrId) throws IOException;
}
