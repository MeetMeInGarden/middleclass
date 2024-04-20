package com.middleclass.middleclass.stock.controller.common;

import java.io.IOException;
import java.util.Map;

@FunctionalInterface
public interface APIPostConnectionHttp {
    Map<String, Object> postApi(String UrlData, String ParamData, String TrId) throws IOException;
}
