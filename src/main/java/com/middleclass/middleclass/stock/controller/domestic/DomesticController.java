package com.middleclass.middleclass.stock.controller.domestic;

import com.google.gson.Gson;
import com.middleclass.middleclass.stock.common.TrId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("domestic")
public class DomesticController {
    @Value("#{key['stock.token']}")
    private String TOKEN;

    @Value("#{key['stock.appkey']}")
    private String APPKEY;

    @Value("#{key['stock.appsecret']}")
    private String BEARER_TOKEN;

    @Value("#{db['db.url']}")
    private String URL;

    @Value("#{db['db.id']}")
    private String ID;

    @Value("#{db['db.url']}")
    private String PW;

    // 거 방법 존나 많네
    // @ConfigurationProperties
    // @PropertySource

    // 국내주식시세 - 주식현재가 시세
    @GetMapping("stockPrice")
    public ModelAndView getStockData() throws IOException {
        String url = "https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/quotations/inquire-price";
        String tr_id = TrId.FHKST01010100.getValue();
        String data = "?fid_cond_mrkt_div_code=J" + "&fid_input_iscd=005930"; // FID 입력 종목코드 + FID 조건 시장 분류 코드
        Map<String, Object> map = httpGetConnection(url, data, tr_id);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("stockPrice");
        mv.addObject("responseCode", map.get("responseCode"));
        mv.addObject("returnData", map.get("returnData"));
        return mv;
    }

    // TODO 이거 공통 모듈로 만들기
    public Map<String, Object> httpGetConnection(String UrlData, String ParamData,String TrId) throws IOException {
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
            // header 정보 세팅, Content-Type 제외하고 필수 아닌 항목은 주석 처리
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("authorization", "Bearer " + TOKEN);
            conn.setRequestProperty("appKey", APPKEY);
            conn.setRequestProperty("appSecret", BEARER_TOKEN);
            // conn.setRequestProperty("personalSeckey", "{personalSeckey}");
            conn.setRequestProperty("tr_id", TrId);
            // conn.setRequestProperty("tr_cont", " ");
            // conn.setRequestProperty("custtype", "법인(B), 개인(P)");
            // conn.setRequestProperty("seq_no", "법인(01), 개인( )");
            // conn.setRequestProperty("mac_address", "{Mac_address}");
            // conn.setRequestProperty("phone_num", "P01011112222");
            // conn.setRequestProperty("ip_addr", "{IP_addr}");
            // conn.setRequestProperty("hashkey", "{Hash값}");
            // conn.setRequestProperty("gt_uid", "{Global UID}");
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
                Map<String, Object> returnMap = new HashMap<>();
                returnMap.put("responseCode", responseCode);

                // 24.04.05 JSON 변환 추가
                Type type = new com.google.gson.reflect.TypeToken<Map<String, Object>>(){}.getType();
                System.out.println("http 응답 데이터2 : " + new Gson().fromJson(returnData, type));
                returnMap.put("returnData", new Gson().fromJson(returnData, type));

                return returnMap;
            } catch (IOException e){
                throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
            }
        }
    }

    @GetMapping("chkholiday")
    public void chkholiday() throws IOException {
        String url = "https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/quotations/chk-holiday";
        String tr_id = TrId.CTCA0903R.getValue();
        String data = "?BASS_DT=20240401&CTX_AREA_NK=&CTX_AREA_FK=";
        this.httpGetConnection_chkholiday(url, data, tr_id);
    }

    // TODO 이거 공통 모듈로 만들기
    public Map<String, Object> httpGetConnection_chkholiday(String UrlData, String ParamData,String TrId) throws IOException {
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

