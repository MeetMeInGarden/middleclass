package com.middleclass.middleclass.stock.controller.overseas;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("overseas")
public class OverseasController {
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
}
