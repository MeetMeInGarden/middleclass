package com.middleclass.middleclass.stock.controller.domestic;

import com.middleclass.middleclass.stock.common.TrId;
import com.middleclass.middleclass.stock.controller.common.APIGetConnectionHttp;
import com.middleclass.middleclass.stock.controller.common.UtilService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

// 개장일, 휴일 등 Date 관련 Controller
@RestController
@RequestMapping("day")
public class DayController implements APIGetConnectionHttp {
    @Value("#{key['stock.token']}")
    private String TOKEN;

    @Value("#{key['stock.appkey']}")
    private String APPKEY;

    @Value("#{key['stock.appsecret']}")
    private String BEARER_TOKEN;

    @GetMapping("holiday")
    public void holiday() throws IOException {
        String url = "https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/quotations/chk-holiday";
        String tr_id = TrId.CTCA0903R.getValue();
        Map<String, String> queryStrMap = new HashMap<>();
        // ?BASS_DT=20240401 & CTX_AREA_NK= & CTX_AREA_FK=
        queryStrMap.put("BASS_DT", "20240401");
        queryStrMap.put("CTX_AREA_NK", "");
        queryStrMap.put("CTX_AREA_FK", "");

        UtilService utilService = new UtilService();
        this.getApi(url, utilService.makeQueryString(queryStrMap), tr_id);
    }

    public Map<String, Object> getApi(String UrlData, String ParamData, String TrId) throws IOException {
        String totalUrl = totalUrl = UrlData.trim().toString();

        URL url = null;
        HttpURLConnection conn = null;

        String responseData = "";
        BufferedReader br = null;

        StringBuffer sb = null;
        String returnData = "";

        try {
            url = new URL(totalUrl + ParamData);
            conn = (HttpURLConnection) url.openConnection();

            // header 정보(필수인 것들만 세팅)
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("authorization", "Bearer " + TOKEN);
            conn.setRequestProperty("appKey", APPKEY);
            conn.setRequestProperty("appSecret", BEARER_TOKEN);
            conn.setRequestProperty("tr_id", TrId);
            conn.setRequestProperty("custtype", "P");
            conn.setDoOutput(true);
            conn.connect();

            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        } catch (IOException e) {
            br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
        } finally {
            try {
                sb = new StringBuffer();
                while ((responseData = br.readLine()) != null) {
                    sb.append(responseData);
                }
                returnData = sb.toString();
                String responseCode = String.valueOf(conn.getResponseCode());

                System.out.println("http 응답 코드 : " + responseCode);
                System.out.println("http 응답 데이터 : " + returnData);

                if (br != null){
                    br.close();
                }
            } catch (IOException e){
                throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
            }
        }
        return null;
    }
}
